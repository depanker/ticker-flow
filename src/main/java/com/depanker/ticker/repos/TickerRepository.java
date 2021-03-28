package com.depanker.ticker.repos;

import com.depanker.ticker.beans.Ticker;
import com.depanker.ticker.helper.FileWriter;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

@Repository
@Slf4j
public class TickerRepository implements FileWriter {
    private final CsvMapper csvMapper;
    private final ReentrantLock reentrantLock = new ReentrantLock();
    @Value("${csv.directory:}")
    private String fileDirectory;

    public TickerRepository() {
        csvMapper = new CsvMapper();
    }

    public List<Ticker> save(String ric, List<Ticker> tickers) throws IOException {
        synchronized (ric.intern()) {
            log.info("Saving RIC={}",ric);
            String filePath = fileDirectory + ric + ".csv";
            csvMapper.configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
            CsvSchema schema = csvMapper.schemaFor(Ticker.class);
            if (getFileLength(filePath) == 0) {
                schema = schema.withHeader();
            }
            ObjectWriter writer = csvMapper.writerFor(List.class).with(schema);
            String data = writer.writeValueAsString(tickers);
            writeToFile(filePath, data);
        }
        return  tickers;
    }

    @Override
    public boolean isAppend() {
        return true;
    }
}
