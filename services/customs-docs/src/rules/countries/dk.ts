import { CountryRules, restrictedChapters } from "./common";

/** Denmark: DMS. */
export const dk: CountryRules = {
  country: "DK",
  transitViaGb: false,
  check: restrictedChapters(["9301"]),
};
