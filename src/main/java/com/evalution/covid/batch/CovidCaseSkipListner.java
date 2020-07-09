package com.evalution.covid.batch;

import com.evalution.covid.entity.CovidCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.annotation.OnSkipInProcess;
import org.springframework.batch.core.annotation.OnSkipInRead;
import org.springframework.batch.core.annotation.OnSkipInWrite;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CovidCaseSkipListner {

    @OnSkipInRead
    public void onSkipInRead(Throwable t) {
        log.debug("From onSkipInRead -> " + t.getMessage());
    }

    @OnSkipInWrite
    public void onSkipInWrite(CovidCase item, Throwable t) {
        log.debug("From onSkipInWrite: " + item.getPatientnumber() + " -> " + t.getMessage());
    }

    @OnSkipInProcess
    public void onSkipInProcess(CovidCase item, Throwable t) {
        log.debug("From onSkipInProcess: " + item.getPatientnumber() + " -> " + t.getMessage());
    }
}
