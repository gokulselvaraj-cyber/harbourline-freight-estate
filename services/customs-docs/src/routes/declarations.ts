import { Router } from "express";
import { z } from "zod";
import { DeclarationService } from "../services/declarationService";
import { render } from "../services/documentRenderer";

const ShipTo = z.object({
  name: z.string().min(1), company: z.string().optional(), address: z.string(), city: z.string(),
  postcode: z.string(), country: z.string().length(2), eori: z.string().optional(),
});
const Body = z.object({
  bookingRef: z.string(), exporterEori: z.string(), origin: z.string().length(5),
  destination: z.string().length(5), shipTo: ShipTo,
  items: z.array(z.object({
    description: z.string(), hsCode: z.string().optional(), packages: z.number().int(),
    grossKg: z.number(), volumeM3: z.number(),
  })).min(1),
});

export function declarationsRouter(svc = new DeclarationService()): Router {
  const r = Router();
  r.post("/v1/declarations", async (req, res, next) => {
    try {
      const decl = await svc.create(Body.parse(req.body));
      res.status(201).json({ declarationRef: decl.declarationRef, status: decl.status, brokerRef: decl.brokerRef });
    } catch (e) {
      next(e);
    }
  });
  r.get("/v1/declarations/:ref", (req, res) => {
    const d = svc.get(req.params.ref);
    d ? res.json(d) : res.status(404).end();
  });
  r.get("/v1/declarations/:ref/documents/:doc", (req, res) => {
    const d = svc.get(req.params.ref);
    if (!d) return res.status(404).end();
    res.type("html").send(render(req.params.doc as any, d));
  });
  return r;
}
