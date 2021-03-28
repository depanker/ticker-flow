package com.depanker.ticker.controllers;

import com.depanker.ticker.beans.TickAcceptanceResponse;
import com.depanker.ticker.beans.TickerCase;
import com.depanker.ticker.events.producer.TickerEventProducer;
import com.depanker.ticker.parsers.TickRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.ACCEPTED;

@RestController
@Validated
@RequiredArgsConstructor
public class TickReceiverController {

    private final TickerEventProducer tickerEventProducerImpl;

    @PostMapping(value = "/submit-tick")
    @ResponseStatus(ACCEPTED)
    public  TickAcceptanceResponse acceptTicks(@Valid @TickRequestBody TickerCase tickers) {
        tickerEventProducerImpl.publishEvent(tickers);
        return new  TickAcceptanceResponse(String.format("Accepted ticks of" +
                " size %d, to persist", tickers.getTickers().size()));

    }
}
