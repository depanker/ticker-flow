package com.depanker.ticker.events.manager;

import com.depanker.ticker.beans.TickerCase;
import com.depanker.ticker.events.listners.TickerEventConsumer;
import com.depanker.ticker.events.producer.TickerEventProducer;
import com.depanker.ticker.helper.FileWriter;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.BlockingQueue;

@Component
@RequiredArgsConstructor
@Slf4j
public class EventManager implements FileWriter {

    private final BlockingQueue<TickerCase> tickerQueue;
    private final ObjectMapper objectMapper;
    private final TickerEventConsumer tickerEventConsumerImpl;
    private final TickerEventProducer tickerEventProducerImpl;
    @Value("${app.backup:backup.json}")
    public String queueSaveFile;

    @PreDestroy
    public void saveMessages() throws IOException {
        tickerEventConsumerImpl.setShutdownSignal(true);
        tickerEventProducerImpl.setShutdownSignal(true);
        if (!tickerQueue.isEmpty()) {
            String queue = objectMapper.writeValueAsString(tickerQueue);
            writeToFile(queueSaveFile, queue);
        }
    }

    @PostConstruct
    public void reloadQueue() throws IOException {
        try {
            File file = new File(queueSaveFile);
            tickerQueue.addAll(Arrays.asList( objectMapper.readValue(new File(queueSaveFile), TickerCase[].class)));
            file.delete();
        } catch (Exception e) {
            log.info("No previous data found.");
        }
    }

    @Override
    public boolean isAppend() {
        return false;
    }
}
