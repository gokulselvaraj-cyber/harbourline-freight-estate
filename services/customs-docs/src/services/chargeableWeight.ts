/**
 * Chargeable weight for the declaration's gross-weight box on road groupage.
 * Volumetric factor: 1 m3 = 300 kg, rounded to the nearest kg.
 */
export const ROAD_VOLUMETRIC_KG_PER_M3 = 300;

export function chargeableKg(grossKg: number, volumeM3: number): number {
  return Math.round(Math.max(grossKg, volumeM3 * ROAD_VOLUMETRIC_KG_PER_M3));
}
