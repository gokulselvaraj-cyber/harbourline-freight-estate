/** shipTo = the receiver of the goods (booking-service Consignee, table delivery_party). */
export interface ShipTo {
  name: string;
  company?: string;
  address: string;
  city: string;
  postcode: string;
  country: string;
  eori?: string;
}

export interface DeclarationLine {
  description: string;
  hsCode?: string;
  packages: number;
  grossKg: number;
  volumeM3: number;
}

export interface DeclarationRequest {
  bookingRef: string;
  exporterEori: string;
  origin: string;
  destination: string;
  shipTo: ShipTo;
  items: DeclarationLine[];
}

export type DeclarationType = "EXPORT_GB" | "IMPORT_GB" | "EXPORT_EU" | "TRANSIT_T1";

export interface Declaration {
  declarationRef: string;
  bookingRef: string;
  type: DeclarationType;
  lines: (DeclarationLine & { hsCode: string; chargeableKg: number })[];
  status: "DRAFT" | "SUBMITTED" | "ACCEPTED" | "REJECTED";
  brokerRef?: string;
}
