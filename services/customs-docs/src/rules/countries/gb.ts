import { CountryRules, restrictedChapters } from "./common";

/** United Kingdom: HMRC CDS; GVMS for road. */
export const gb: CountryRules = {
  country: "GB",
  transitViaGb: false,
  check: restrictedChapters(["9301"]),
};
