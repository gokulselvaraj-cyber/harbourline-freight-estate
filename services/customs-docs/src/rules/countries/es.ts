import { CountryRules, restrictedChapters } from "./common";

/** Spain: DUA, Bilbao and Valencia. */
export const es: CountryRules = {
  country: "ES",
  transitViaGb: false,
  check: restrictedChapters(["9301"]),
};
