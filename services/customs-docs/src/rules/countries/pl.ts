import { CountryRules, restrictedChapters } from "./common";

/** Poland: AIS/IMPORT PLUS. */
export const pl: CountryRules = {
  country: "PL",
  transitViaGb: false,
  check: restrictedChapters(["9301"]),
};
