import { CountryRules, restrictedChapters } from "./common";

/** Ireland: AIS; land-bridge via GB needs T1 transit. */
export const ie: CountryRules = {
  country: "IE",
  transitViaGb: true,
  check: restrictedChapters(["9301", "0106"]),
};
