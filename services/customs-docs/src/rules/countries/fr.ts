import { CountryRules, restrictedChapters } from "./common";

/** France: DELTA G, Le Havre and Dunkirk. */
export const fr: CountryRules = {
  country: "FR",
  transitViaGb: false,
  check: restrictedChapters(["9301", "2208"]),
};
