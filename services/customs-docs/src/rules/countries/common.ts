import { DeclarationLine } from "../../types";

export interface CountryRules {
  country: string;
  transitViaGb: boolean;
  requiresOrgNumber?: boolean;
  check: (hsCode: string, line: DeclarationLine) => void;
}

/** Rejects HS headings that need a licence we do not handle (arms, live animals, ...). */
export function restrictedChapters(prefixes: string[]) {
  return (hsCode: string, line: DeclarationLine) => {
    const hit = prefixes.find((p) => hsCode.startsWith(p));
    if (hit) throw new Error(`HS ${hsCode} (${line.description}) needs a licence: heading ${hit}`);
  };
}
