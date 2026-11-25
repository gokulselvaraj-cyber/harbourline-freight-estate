import amqp from "amqplib";
import { config } from "../config";
import { logger } from "../util/logger";

/** Listens to booking.confirmed.v1 (queue customs.booking-confirmed) to warm up broker drafts. */
export async function startBookingConfirmedConsumer(): Promise<void> {
  const conn = await amqp.connect(config.rabbit.url);
  const ch = await conn.createChannel();
  await ch.consume(config.rabbit.queue, (msg) => {
    if (!msg) return;
    const evt = JSON.parse(msg.content.toString());
    if (evt.customsRequired) logger.info({ bookingRef: evt.bookingRef }, "customs draft expected");
    ch.ack(msg);
  });
}
