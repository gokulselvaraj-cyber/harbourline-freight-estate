import { describe, expect, it } from "vitest";
import { declarationType } from "../src/services/declarationService";

describe("declarationType", () => {
  it("GB export", () => expect(declarationType("GBFXT", "NLRTM")).toBe("EXPORT_GB"));
  it("GB import", () => expect(declarationType("DEHAM", "GBIMM")).toBe("IMPORT_GB"));
  it("Irish land-bridge", () => expect(declarationType("NLRTM", "IEDUB")).toBe("TRANSIT_T1"));
});
