package com.harbourline.booking.service;

import com.harbourline.booking.config.EventProperties;
import com.harbourline.booking.events.BookingConfirmedEvent;
import com.harbourline.booking.events.BookingDispatchedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/** Publishes to exchange hf.bookings. Keys: booking.confirmed.v1, booking.dispatched.v1. */
@Component
public class BookingEventPublisher {
    private final RabbitTemplate rabbit;
    private final EventProperties props;

    public BookingEventPublisher(RabbitTemplate rabbit, EventProperties props) {
        this.rabbit = rabbit;
        this.props = props;
    }

    public void confirmed(BookingConfirmedEvent e) {
        rabbit.convertAndSend(props.exchange(), props.confirmedKey(), e);
    }

    public void dispatched(BookingDispatchedEvent e) {
        rabbit.convertAndSend(props.exchange(), props.dispatchedKey(), e);
    }
}
