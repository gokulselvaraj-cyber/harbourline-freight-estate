import { CountryRules, restrictedChapters } from "./common";

/** Italy: AIDA, Genoa. */
export const it: CountryRules = {
  country: "IT",
  transitViaGb: false,
  check: restrictedChapters(["9301", "2402"]),
};
