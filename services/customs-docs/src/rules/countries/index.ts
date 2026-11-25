import { nl } from "./nl";
import { be } from "./be";
import { de } from "./de";
import { fr } from "./fr";
import { pl } from "./pl";
import { ie } from "./ie";
import { es } from "./es";
import { it } from "./it";
import { dk } from "./dk";
import { se } from "./se";
import { fi } from "./fi";
import { lt } from "./lt";
import { pt } from "./pt";
import { no } from "./no";
import { gb } from "./gb";
import { CountryRules, restrictedChapters } from "./common";

const ALL: CountryRules[] = [nl, be, de, fr, pl, ie, es, it, dk, se, fi, lt, pt, no, gb];

const fallback: CountryRules = { country: "??", transitViaGb: false, check: restrictedChapters(["9301"]) };

export function rulesFor(country: string): CountryRules {
  return ALL.find((r) => r.country === country) ?? fallback;
}
