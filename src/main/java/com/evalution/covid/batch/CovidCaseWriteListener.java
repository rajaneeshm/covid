package com.evalution.covid.batch;

import com.evalution.covid.entity.CovidCase;
import com.evalution.covid.service.defitition.IUpdateSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class CovidCaseWriteListener implements ItemWriteListener<CovidCase> {
    @Autowired
    private IUpdateSummary updateSummary;

    @Override
    public void beforeWrite(List<? extends CovidCase> list) {

    }

    @Override
    public void afterWrite(List<? extends CovidCase> list) {
        List<CovidCase> covidCases = new ArrayList<>();
        for (CovidCase covidCase : list) {
            covidCases.add(covidCase);
        }
        updateSummary.updateSummary(covidCases);
    }

    @Override
    public void onWriteError(Exception e, List<? extends CovidCase> list) {
        for (CovidCase covidCase : list) {
            log.debug("Error While Writing : "+covidCase.getPatientnumber() );
        }
    }
}
