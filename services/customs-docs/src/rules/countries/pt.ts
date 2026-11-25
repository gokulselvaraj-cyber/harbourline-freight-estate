import { CountryRules, restrictedChapters } from "./common";

/** Portugal: STADA, Leixoes. */
export const pt: CountryRules = {
  country: "PT",
  transitViaGb: false,
  check: restrictedChapters(["9301"]),
};
