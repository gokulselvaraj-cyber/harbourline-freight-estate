import { CountryRules, restrictedChapters } from "./common";

/** Netherlands: Douane NL: AGS import system. */
export const nl: CountryRules = {
  country: "NL",
  transitViaGb: false,
  check: restrictedChapters(["0106", "9301"]),
};
