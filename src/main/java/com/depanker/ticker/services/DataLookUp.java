package com.depanker.ticker.services;

import com.depanker.ticker.beans.TickerCase;

public interface DataLookUp {
    TickerCase getData(String query);
}
