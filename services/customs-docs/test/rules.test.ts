import { describe, expect, it } from "vitest";
import { rulesFor } from "../src/rules/countries";

describe("country rules", () => {
  it("Norway needs org number", () => expect(rulesFor("NO").requiresOrgNumber).toBe(true));
  it("Ireland uses GB land-bridge transit", () => expect(rulesFor("IE").transitViaGb).toBe(true));
  it("blocks spirits to Sweden", () => expect(() => rulesFor("SE").check("220830", {
    description: "Spirits", packages: 1, grossKg: 10, volumeM3: 0.1 })).toThrow());
});
