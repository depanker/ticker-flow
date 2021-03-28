package com.depanker.ticker.events.producer;

import com.depanker.ticker.beans.TickerCase;

public interface TickerEventProducer {
    void publishEvent(final TickerCase message);
    void setShutdownSignal(boolean shutdownSignal);
}
