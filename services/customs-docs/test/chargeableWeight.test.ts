import { describe, expect, it } from "vitest";
import { chargeableKg } from "../src/services/chargeableWeight";

describe("chargeableKg", () => {
  it("uses volume when lighter than 300 kg/m3", () => expect(chargeableKg(400, 2)).toBe(600));
  it("uses gross when dense", () => expect(chargeableKg(1200, 1.5)).toBe(1200));
});
