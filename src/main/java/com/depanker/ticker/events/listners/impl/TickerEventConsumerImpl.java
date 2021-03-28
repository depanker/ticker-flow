package com.depanker.ticker.events.listners.impl;

import com.depanker.ticker.beans.TickerCase;
import com.depanker.ticker.events.TickerInsertEvent;
import com.depanker.ticker.events.listners.TickerEventConsumer;
import com.depanker.ticker.services.TickRecorder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;

@Component
@RequiredArgsConstructor
@Slf4j
public class TickerEventConsumerImpl  implements TickerEventConsumer {
    private final BlockingQueue<TickerCase> tickerQueue;
    private final TickRecorder tickRecorder;
    private boolean shutdownSignal;

    @EventListener
    public void handleContextStart(TickerInsertEvent cse) {
        log.info("Handling ticker insert event.");
        consume();
    }

    @Async
    public void consume() {
        TickerCase tickerCase = null;
        try {
            tickerCase = tickerQueue.take();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        tickRecorder.save(tickerCase);
    }

    public boolean isShutdownSignal() {
        return shutdownSignal;
    }

    public void setShutdownSignal(boolean shutdownSignal) {
        this.shutdownSignal = shutdownSignal;
    }
}
