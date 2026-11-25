import { CountryRules, restrictedChapters } from "./common";

/** Lithuania: AIS, Klaipeda. */
export const lt: CountryRules = {
  country: "LT",
  transitViaGb: false,
  check: restrictedChapters(["9301"]),
};
