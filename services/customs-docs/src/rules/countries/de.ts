import { CountryRules, restrictedChapters } from "./common";

/** Germany: ATLAS. */
export const de: CountryRules = {
  country: "DE",
  transitViaGb: false,
  check: restrictedChapters(["9301", "3604"]),
};
