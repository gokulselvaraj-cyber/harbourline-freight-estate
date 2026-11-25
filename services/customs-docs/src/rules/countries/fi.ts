import { CountryRules, restrictedChapters } from "./common";

/** Finland: Uljas. */
export const fi: CountryRules = {
  country: "FI",
  transitViaGb: false,
  check: restrictedChapters(["9301"]),
};
