package com.evalution.covid.batch;

import com.evalution.covid.dto.CovidCaseDTO;
import com.evalution.covid.entity.CovidCase;
import com.evalution.covid.service.defitition.ICovidCaseService;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CovidCaseProcessor implements ItemProcessor<CovidCaseDTO, CovidCase> {
    @Autowired
    ICovidCaseService covidCaseService;

    @Override
    public CovidCase process(CovidCaseDTO covidCaseDTO) throws Exception {
        /**
         * FIXME Update Redis
         * Convert to Entity
         * update Redis
         * coommit to DB
         * Hospitalised Total+1 , Active +1
         * Recovered    Active -1 , Revored +1
         * Deceased     Active - 1 , Deceased + 1
         *
         */
        final CovidCase covidCase = covidCaseService.getEntity(covidCaseDTO);

        return covidCase;
    }
}
