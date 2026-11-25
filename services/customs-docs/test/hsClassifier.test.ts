import { describe, expect, it, vi } from "vitest";
import axios from "axios";
import { HsClassifier } from "../src/services/hsClassifier";

vi.mock("axios");

describe("HsClassifier", () => {
  const shipTo = { name: "TEST-CONSIGNEE-0001", address: "1 Test Street", city: "Testville", postcode: "ZZ99 9ZZ", country: "NL" };
  it("keeps a valid shipper HS code without calling the gateway", async () => {
    const code = await new HsClassifier().classify({ description: "Tiles", hsCode: "690721", packages: 1, grossKg: 1, volumeM3: 0.1 }, shipTo, "NLRTM");
    expect(code).toBe("690721");
    expect(axios.post).not.toHaveBeenCalled();
  });
});
