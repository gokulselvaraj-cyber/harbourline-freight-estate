import { randomUUID } from "crypto";
import { chargeableKg } from "./chargeableWeight";
import { HsClassifier } from "./hsClassifier";
import { BrokerGatewayClient } from "./brokerGatewayClient";
import { rulesFor } from "../rules/countries";
import { Declaration, DeclarationRequest, DeclarationType } from "../types";

const store = new Map<string, Declaration>();

export function declarationType(origin: string, destination: string): DeclarationType {
  const o = origin.slice(0, 2);
  const d = destination.slice(0, 2);
  if (o === "GB" && d !== "GB") return "EXPORT_GB";
  if (o !== "GB" && d === "GB") return "IMPORT_GB";
  return rulesFor(d).transitViaGb ? "TRANSIT_T1" : "EXPORT_EU";
}

export class DeclarationService {
  constructor(private hs = new HsClassifier(), private broker = new BrokerGatewayClient()) {}

  async create(req: DeclarationRequest): Promise<Declaration> {
    const rules = rulesFor(req.destination.slice(0, 2));
    const lines = [];
    for (const item of req.items) {
      const hsCode = await this.hs.classify(item, req.shipTo, req.destination);
      rules.check(hsCode, item);
      lines.push({ ...item, hsCode, chargeableKg: chargeableKg(item.grossKg, item.volumeM3) });
    }
    const decl: Declaration = {
      declarationRef: `CD-${randomUUID().slice(0, 8).toUpperCase()}`,
      bookingRef: req.bookingRef,
      type: declarationType(req.origin, req.destination),
      lines,
      status: "DRAFT",
    };
    decl.brokerRef = await this.broker.submit(decl, req.shipTo);
    decl.status = "SUBMITTED";
    store.set(decl.declarationRef, decl);
    return decl;
  }

  get(ref: string): Declaration | undefined {
    return store.get(ref);
  }
}
