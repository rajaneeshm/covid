package com.evalution.covid.service.defitition;

import com.evalution.covid.dto.CovidCaseDTO;
import com.evalution.covid.entity.CovidCase;

public interface ICovidCaseService {
    CovidCase saveNewCase(CovidCaseDTO dto);

    CovidCaseDTO getCaseById(long patientNumber);

    CovidCase getEntity(CovidCaseDTO dto);

    CovidCase update(CovidCaseDTO covidCaseDTO);
}
