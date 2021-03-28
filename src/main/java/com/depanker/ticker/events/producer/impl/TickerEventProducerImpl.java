package com.depanker.ticker.events.producer.impl;

import com.depanker.ticker.beans.TickerCase;
import com.depanker.ticker.events.TickerInsertEvent;
import com.depanker.ticker.events.producer.TickerEventProducer;
import com.depanker.ticker.exceptions.OperationFailedException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
@RequiredArgsConstructor
@Slf4j
public class TickerEventProducerImpl implements TickerEventProducer {

    private final BlockingQueue<TickerCase> tickerQueue;
    private final ApplicationEventPublisher applicationEventPublisher;
    private boolean shutdownSignal;

    @SneakyThrows
    @Async
    public void publishEvent(final TickerCase message) {
        if (shutdownSignal) {
            throw  new OperationFailedException("Cannot submit new data, about to shutdown...");
        }
        log.info("Publishing Ticker event of size {}", message.getTickers().size());
        TickerInsertEvent customSpringEvent = new TickerInsertEvent(this, message);
        tickerQueue.put(message);
        applicationEventPublisher.publishEvent(customSpringEvent);
    }

    public boolean isShutdownSignal() {
        return shutdownSignal;
    }

    public void setShutdownSignal(boolean shutdownSignal) {
        this.shutdownSignal = shutdownSignal;
    }
}
