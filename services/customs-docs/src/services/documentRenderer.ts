import fs from "fs";
import path from "path";
import Handlebars from "handlebars";
import { Declaration } from "../types";

const dir = path.join(__dirname, "..", "..", "templates");
const cache = new Map<string, HandlebarsTemplateDelegate>();

export function render(doc: "commercial-invoice" | "packing-list" | "t1-transit", decl: Declaration): string {
  if (!cache.has(doc)) {
    cache.set(doc, Handlebars.compile(fs.readFileSync(path.join(dir, `${doc}.hbs`), "utf8")));
  }
  return cache.get(doc)!(decl);
}
