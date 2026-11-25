import axios from "axios";
import { config, secrets } from "../config";
import { Declaration, ShipTo } from "../types";

/** ClearPath Broker API (external customs broker; lodges declarations with HMRC CDS and EU systems). */
export class BrokerGatewayClient {
  private http = axios.create({
    baseURL: config.brokerApi.baseUrl,
    timeout: config.brokerApi.timeoutMs,
    headers: { Authorization: `Bearer ${secrets.brokerToken}` },
  });

  async submit(decl: Declaration, shipTo: ShipTo): Promise<string> {
    const res = await this.http.post("/v3/declarations", {
      clientRef: decl.declarationRef,
      type: decl.type,
      declarant: config.declarantEori,
      consignee: shipTo,
      goods: decl.lines,
    });
    return res.data.brokerReference;
  }
}
