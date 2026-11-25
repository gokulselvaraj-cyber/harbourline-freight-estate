import fs from "fs";
import path from "path";

type Cfg = {
  port: number;
  brokerApi: { baseUrl: string; timeoutMs: number };
  hsClassifier: { endpoint: string; model: string; minConfidence: number };
  rabbit: { url: string; queue: string };
  declarantEori: string;
};

function read(name: string): Partial<Cfg> {
  const p = path.join(__dirname, "..", "config", `${name}.json`);
  return fs.existsSync(p) ? JSON.parse(fs.readFileSync(p, "utf8")) : {};
}

function merge<T>(a: any, b: any): T {
  const out: any = { ...a };
  for (const [k, v] of Object.entries(b ?? {})) {
    out[k] = v && typeof v === "object" && !Array.isArray(v) ? merge(a?.[k] ?? {}, v) : v;
  }
  return out;
}

const env = process.env.NODE_ENV ?? "development";
const fileCfg = merge<Cfg>(read("default"), read(env));

export const config: Cfg = {
  ...fileCfg,
  port: Number(process.env.PORT ?? 3000),
};

export const secrets = {
  brokerToken: process.env.BROKER_API_TOKEN ?? "",
  aiGatewayKey: process.env.AI_GATEWAY_KEY ?? "",
};
