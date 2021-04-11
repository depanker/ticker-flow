package com.depanker.ticker.performance;

import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class TickQueryTest {

    @Test
    public void testPerformance() throws IOException {
        //this is just to log details of each request stats
        TestPlanStats stats = testPlan(
                threadGroup(4, 10,
                        httpSampler("http://localhost:8080/lookup?query=select * from AAPL.OQ where CLOSE_PRICE != null")
                                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhYmMifQ.zqQixZbzz1sQH_GuspsNb0GozyrCFJ5qO2wSm4sY_w1z9WAmRy47XtMdU2DMrs8ucVnLFvFxvHGwblnNHdFSOw")

                )
                //this is just to log details of each request stats
//                jtlWriter("test-query.csv")
        ).run();
        assertThat(stats.overall().elapsedTimePercentile90()).isLessThan(Duration.ofMillis(55));
    }
}
