import { CountryRules, restrictedChapters } from "./common";

/** Sweden: Tulltaxering, Gothenburg. */
export const se: CountryRules = {
  country: "SE",
  transitViaGb: false,
  check: restrictedChapters(["9301", "2208"]),
};
