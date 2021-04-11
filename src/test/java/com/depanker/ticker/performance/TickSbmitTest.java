package com.depanker.ticker.performance;

import org.eclipse.jetty.http.MimeTypes;
import org.junit.jupiter.api.Test;
import us.abstracta.jmeter.javadsl.core.TestPlanStats;

import java.io.IOException;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;
import static us.abstracta.jmeter.javadsl.JmeterDsl.*;

public class TickSbmitTest {

    @Test
    public void testPerformance() throws IOException {
        //this is just to log details of each request stats
        TestPlanStats stats = testPlan(
                threadGroup(10, 10,
                        httpSampler("http://localhost:8080/submit-tick")
                                .header("Authorization", "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkZXBhbmtlciJ9.L3A9pzvMXgpOgktubpFO6RzHOw-CTDb-PDna019UFG9sFvwoBn5Pm9zNUza6vIgiCZWHsIZcyspr_jKT0rPnHw")
                                .post(getMockTick(), MimeTypes.Type.APPLICATION_JSON)
                )
        ).run();
        assertThat(stats.overall().elapsedTimePercentile90()).isLessThan(Duration.ofMillis(60));
    }


    private String getMockTick() {
        return "TIMESTAMP=123123|PRICE=5.24|CLOSE_PRICE=|CURRENCY=EUR|RIC=AAPL.OQ\n" +
                "TIMESTAMP=4564564|PRICE=5.24|CLOSE_PRICE=|CURRENCY=EUR|RIC=IBM.N \n" +
                "TIMESTAMP=6786754|PRICE=|CLOSE_PRICE=7.5|CURRENCY=EUR|RIC=AAPL.OQ";
    }

}
