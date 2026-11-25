import { CountryRules, restrictedChapters } from "./common";

/** Norway: Not EU: TVINN; EORI not used, org number needed. */
export const no: CountryRules = {
  country: "NO",
  transitViaGb: false,
  // Norway needs the receiver organisation number on every line set.
  requiresOrgNumber: true,
  check: restrictedChapters(["9301", "2208", "0207"]),
};
