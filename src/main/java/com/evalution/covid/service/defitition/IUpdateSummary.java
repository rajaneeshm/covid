package com.evalution.covid.service.defitition;

import com.evalution.covid.entity.CovidCase;

import java.util.List;

public interface IUpdateSummary {
    void updateSummary(List<CovidCase> covidCases);
}
