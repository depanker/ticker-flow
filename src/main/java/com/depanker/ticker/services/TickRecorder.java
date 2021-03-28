package com.depanker.ticker.services;

import com.depanker.ticker.beans.TickerCase;

public interface TickRecorder {
    void save(TickerCase tickerCase);
}
