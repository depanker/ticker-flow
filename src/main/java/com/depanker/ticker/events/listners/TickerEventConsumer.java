package com.depanker.ticker.events.listners;

import com.depanker.ticker.events.TickerInsertEvent;


public interface TickerEventConsumer {
    void handleContextStart(TickerInsertEvent cse);
    void consume();
    void setShutdownSignal(boolean shutdownSignal);
}
