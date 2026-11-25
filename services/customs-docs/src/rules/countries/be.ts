import { CountryRules, restrictedChapters } from "./common";

/** Belgium: PLDA/IDMS, Antwerp and Zeebrugge. */
export const be: CountryRules = {
  country: "BE",
  transitViaGb: false,
  check: restrictedChapters(["9301"]),
};
