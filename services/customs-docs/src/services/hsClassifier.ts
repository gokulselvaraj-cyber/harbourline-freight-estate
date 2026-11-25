import fs from "fs";
import path from "path";
import axios from "axios";
import { lookup } from "tariff-codes-lite";
import { config, secrets } from "../config";
import { DeclarationLine, ShipTo } from "../types";

const PROMPT = fs.readFileSync(path.join(__dirname, "..", "..", "prompts", "hs-classify.prompt.txt"), "utf8");

/** Suggests an HS code when the shipper left it empty. Calls the AI gateway with the prompt template. */
export class HsClassifier {
  async classify(line: DeclarationLine, shipTo: ShipTo, destination: string): Promise<string> {
    if (line.hsCode && lookup(line.hsCode)) return line.hsCode;
    const prompt = PROMPT.replace("{{description}}", line.description)
      .replace("{{packages}}", String(line.packages))
      .replace("{{destination}}", destination)
      .replace("{{ship_to_name}}", shipTo.name)
      .replace("{{ship_to_address}}", `${shipTo.address}, ${shipTo.city} ${shipTo.postcode}, ${shipTo.country}`);
    const res = await axios.post(
      config.hsClassifier.endpoint,
      { model: config.hsClassifier.model, prompt, max_tokens: 64 },
      { headers: { Authorization: `Bearer ${secrets.aiGatewayKey}` }, timeout: 6000 },
    );
    const { hsCode, confidence } = res.data;
    if (confidence < config.hsClassifier.minConfidence) {
      throw new Error(`HS code for '${line.description}' needs manual review (${confidence})`);
    }
    return hsCode;
  }
}
