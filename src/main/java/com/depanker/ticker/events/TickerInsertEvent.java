package com.depanker.ticker.events;

import com.depanker.ticker.beans.TickerCase;
import org.springframework.context.ApplicationEvent;

public class TickerInsertEvent  extends ApplicationEvent {
    private TickerCase message;
    public TickerInsertEvent(Object source, TickerCase message) {
        super(source);
        this.message = message;
    }

    public TickerCase getMessage() {
        return message;
    }

    public void setMessage(TickerCase message) {
        this.message = message;
    }
}
