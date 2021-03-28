package com.depanker.ticker.services.impl;

import com.depanker.ticker.beans.Ticker;
import com.depanker.ticker.beans.TickerCase;
import com.depanker.ticker.exceptions.OperationFailedException;
import com.depanker.ticker.repos.TickerRepository;
import com.depanker.ticker.services.TickRecorder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TickRecorderImpl implements TickRecorder {
    private final TickerRepository tickerRepository;

    @Override
    public void save(TickerCase tickerCase) {
        Map<String, List<Ticker>> groupedByRic = tickerCase.getTickers()
                .stream().collect(Collectors.groupingBy(Ticker::getRic));
        groupedByRic.entrySet()
                .stream().filter(s -> {
                    boolean closePresent = s.getValue().stream()
                            .anyMatch(ticker -> Objects.nonNull(ticker.getClosePrice()));
                    return closePresent;
                }).forEach(tickerGroup -> {
            try {
                tickerRepository.save(tickerGroup.getKey(), tickerGroup.getValue());
            } catch (IOException e) {
                log.error(e.getMessage(),e);
                throw new OperationFailedException(e.getMessage());
            }
        });

    }
}
