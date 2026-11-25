import express from "express";
import { config } from "./config";
import { declarationsRouter } from "./routes/declarations";
import { healthRouter } from "./routes/health";
import { startBookingConfirmedConsumer } from "./consumers/bookingConfirmed";
import { logger } from "./util/logger";

const app = express();
app.use(express.json({ limit: "2mb" }));
app.use(healthRouter);
app.use(declarationsRouter());
app.use((err: any, _req: any, res: any, _next: any) => {
  logger.error({ err: err.message }, "request failed");
  res.status(err.name === "ZodError" ? 400 : 502).json({ error: err.message });
});

app.listen(config.port, () => logger.info(`customs-docs listening on ${config.port}`));
startBookingConfirmedConsumer().catch((e) => logger.error(e, "consumer failed"));
