package com.evalution.covid.service.implementation;

import com.evalution.covid.cache.DailyStateTotalCasesCache;
import com.evalution.covid.cache.DailyTotalCasesCache;
import com.evalution.covid.cache.TotalCasesCache;
import com.evalution.covid.cache.TotalStateCasesCache;
import com.evalution.covid.constant.CaseStatus;
import com.evalution.covid.dto.DailyTotalCasesDTO;
import com.evalution.covid.dto.DailyTotalStateCasesDTO;
import com.evalution.covid.dto.TotalCasesDTO;
import com.evalution.covid.dto.TotalStateCasesDTO;
import com.evalution.covid.entity.*;
import com.evalution.covid.repository.DailyTotalCasesRepository;
import com.evalution.covid.repository.DailyTotalStateCasesRepository;
import com.evalution.covid.repository.TotalCasesRepository;
import com.evalution.covid.repository.TotalStateCasesRepository;
import com.evalution.covid.service.defitition.IUpdateSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service("updateSummary")
public class UpdateSummaryImpl implements IUpdateSummary {

    @Autowired
    private TotalCasesRepository totalCasesRepository;
    @Autowired
    private DailyTotalCasesRepository dailyTotalCasesRepository;
    @Autowired
    private DailyTotalStateCasesRepository dailyTotalStateCasesRepository;
    @Autowired
    private TotalStateCasesRepository totalStateCasesRepository;


    @Autowired
    private TotalCasesCache totalCasesCache;
    @Autowired
    private TotalStateCasesCache totalStateCasesCache;
    @Autowired
    private DailyTotalCasesCache dailyTotalCasesCache;
    @Autowired
    private DailyStateTotalCasesCache dailyStateTotalCasesCache;

    @Override
    public void updateSummary(List<CovidCase> covidCases) {
        updateTotals(covidCases);
        updateDailyTotal(covidCases);
        updateStateTotal(covidCases);
        updateStateDailyTotals(covidCases);
    }

    private void updateDailyTotal(List<CovidCase> covidCases) {
        for (CovidCase covidCase : covidCases) {
            DailyTotalCases dailyTotalCases;
            Optional<DailyTotalCases> optional;
            if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Hospitalized.name())) {
                optional = dailyTotalCasesRepository.findByRecordedOn(covidCase.getDateannounced());
            } else {
                optional = dailyTotalCasesRepository.findByRecordedOn(covidCase.getStatuschangedate());
            }

            if(optional.isPresent()) {
                dailyTotalCases = optional.get();
            } else {
                final Optional<DailyTotalCases> topByOrderByIdDesc = dailyTotalCasesRepository.findTopByOrderByIdDesc();
                DailyTotalCases dailyTotalCasesMostRecent = null;
                if(topByOrderByIdDesc.isPresent()) {
                    dailyTotalCasesMostRecent = topByOrderByIdDesc.get();
                }
                dailyTotalCases = new DailyTotalCases();
                if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Hospitalized.name())) {
                    dailyTotalCases.setRecordedOn(covidCase.getDateannounced());
                } else {
                    dailyTotalCases.setRecordedOn(covidCase.getStatuschangedate());
                }
                if(dailyTotalCasesMostRecent != null) {
                  //  dailyTotalCases.setActiveCasesCumulative(dailyTotalCasesMostRecent.getActiveCasesCumulative());
                  //  dailyTotalCases.setTotalCasesCumulative(dailyTotalCasesMostRecent.getTotalCasesCumulative());
                  //  dailyTotalCases.setDeceasedCasesCumulative(dailyTotalCasesMostRecent.getDeceasedCasesCumulative());
                 //   dailyTotalCases.setRecoveredCasesCumulative(dailyTotalCasesMostRecent.getRecoveredCasesCumulative());
                }
            }

            if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Hospitalized.name())) {
                dailyTotalCases.setActiveCases(dailyTotalCases.getActiveCases() + 1);
                dailyTotalCases.setTotalCases(dailyTotalCases.getTotalCases() + 1);
               // dailyTotalCases.setTotalCasesCumulative(dailyTotalCases.getTotalCasesCumulative() + 1);
               // dailyTotalCases.setActiveCasesCumulative(dailyTotalCases.getActiveCasesCumulative() + 1);
            } else if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Recovered.name())) {
               // if(dailyTotalCases.getActiveCases() > 0) {
                    dailyTotalCases.setActiveCases(dailyTotalCases.getActiveCases() - 1);
                    //dailyTotalCases.setActiveCasesCumulative(dailyTotalCases.getActiveCasesCumulative() - 1);
               // }
                dailyTotalCases.setRecoveredCases(dailyTotalCases.getRecoveredCases() + 1);
               // dailyTotalCases.setRecoveredCasesCumulative(dailyTotalCases.getRecoveredCasesCumulative() + 1);
            } else if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Deceased.name())) {
               // if(dailyTotalCases.getActiveCases() > 0) {
                    dailyTotalCases.setActiveCases(dailyTotalCases.getActiveCases() - 1);
                   // dailyTotalCases.setActiveCasesCumulative(dailyTotalCases.getActiveCasesCumulative() - 1);
              //  }
                dailyTotalCases.setDeceasedCases(dailyTotalCases.getDeceasedCases() + 1);
               // dailyTotalCases.setDeceasedCasesCumulative(dailyTotalCases.getDeceasedCasesCumulative() + 1);
            }
            dailyTotalCasesCache.set(convert(dailyTotalCases));
            dailyTotalCasesRepository.save(dailyTotalCases);
        }

    }


    private void updateTotals(List<CovidCase> covidCases) {
        TotalCases totalCases;
        final Optional<TotalCases> optional = totalCasesRepository.findById(1L);
        if(optional.isPresent()) {
            totalCases = optional.get();
        } else {
            totalCases = new TotalCases();
        }
        for (CovidCase covidCase : covidCases) {
            if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Hospitalized.name())) {
                totalCases.setActiveCases(totalCases.getActiveCases() + 1);
                totalCases.setTotalCases(totalCases.getTotalCases() + 1);
            } else if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Recovered.name())) {
                if(totalCases.getActiveCases() > 0)
                    totalCases.setActiveCases(totalCases.getActiveCases() - 1);
                totalCases.setRecoveredCases(totalCases.getRecoveredCases() + 1);
            } else if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Deceased.name())) {
                if(totalCases.getActiveCases() > 0)
                    totalCases.setActiveCases(totalCases.getActiveCases() - 1);
                totalCases.setDeceasedCases(totalCases.getDeceasedCases() + 1);
            }


        }
        totalCasesCache.set(convert(totalCases));
        totalCasesRepository.save(totalCases);
    }

    public void updateStateTotal(List<CovidCase> covidCases) {
        Map<String, TotalStateCases> map = new HashMap<>();
        for (CovidCase covidCase : covidCases) {
            TotalStateCases totalStateCases;
            if(map.containsKey(covidCase.getDetectedstate())) {
                totalStateCases = map.get(covidCase.getDetectedstate());
            } else {
                final Optional<TotalStateCases> optional = totalStateCasesRepository.findByState(covidCase.getDetectedstate());
                if(optional.isPresent()) {
                    totalStateCases = optional.get();
                    map.put(covidCase.getDetectedstate(), totalStateCases);
                } else {
                    totalStateCases = new TotalStateCases();
                    totalStateCases.setState(covidCase.getDetectedstate());
                    map.put(covidCase.getDetectedstate(), totalStateCases);
                }
            }

            if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Hospitalized.name())) {
                totalStateCases.setActiveCases(totalStateCases.getActiveCases() + 1);
                totalStateCases.setTotalCases(totalStateCases.getTotalCases() + 1);
            } else if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Recovered.name())) {
                if(totalStateCases.getActiveCases() > 0)
                    totalStateCases.setActiveCases(totalStateCases.getActiveCases() - 1);
                totalStateCases.setRecoveredCases(totalStateCases.getRecoveredCases() + 1);
            } else if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Deceased.name())) {
                if(totalStateCases.getActiveCases() > 0)
                    totalStateCases.setActiveCases(totalStateCases.getActiveCases() - 1);
                totalStateCases.setDeceasedCases(totalStateCases.getDeceasedCases() + 1);
            }
        }
        totalStateCasesRepository.saveAll(map.values());
        for (TotalStateCases value : map.values()) {
            totalStateCasesCache.set(convert(value));
        }
    }

    public void updateStateDailyTotals(List<CovidCase> covidCases) {
        Map<String, DailyTotalStateCases> map = new HashMap<>();
        for (CovidCase covidCase : covidCases) {
            DailyTotalStateCases dailyTotalStateCases;
            if(map.containsKey(covidCase.getDetectedstate())) {
                dailyTotalStateCases = map.get(covidCase.getDetectedstate());
            } else {
                Optional<DailyTotalStateCases> optional;
                if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Hospitalized.name())) {
                    optional = dailyTotalStateCasesRepository.findByStateAndRecordedOn(covidCase.getDetectedstate(), covidCase.getDateannounced());
                } else {
                    optional = dailyTotalStateCasesRepository.findByStateAndRecordedOn(covidCase.getDetectedstate(), covidCase.getStatuschangedate());
                }
                if(optional.isPresent()) {
                    dailyTotalStateCases = optional.get();
                    map.put(covidCase.getDetectedstate(), dailyTotalStateCases);
                } else {
                    DailyTotalStateCases dailyTotalStateCasesMostRecent = null;
                    final Optional<DailyTotalStateCases> topByOrderByIdDesc = dailyTotalStateCasesRepository.findTopByOrderByIdDesc();
                    if(topByOrderByIdDesc.isPresent()) {
                        dailyTotalStateCasesMostRecent = topByOrderByIdDesc.get();
                    }
                    dailyTotalStateCases = new DailyTotalStateCases();
                    dailyTotalStateCases.setState(covidCase.getDetectedstate());
                    if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Hospitalized.name())) {
                        dailyTotalStateCases.setRecordedOn(covidCase.getDateannounced());
                    } else {
                        dailyTotalStateCases.setRecordedOn(covidCase.getStatuschangedate());
                    }

                    if(dailyTotalStateCasesMostRecent != null) {
                       // dailyTotalStateCases.setActiveCasesCumulative(dailyTotalStateCasesMostRecent.getActiveCasesCumulative());
                       // dailyTotalStateCases.setTotalCasesCumulative(dailyTotalStateCasesMostRecent.getTotalCasesCumulative());
                       // dailyTotalStateCases.setDeceasedCasesCumulative(dailyTotalStateCasesMostRecent.getDeceasedCasesCumulative());
                      //  dailyTotalStateCases.setRecoveredCasesCumulative(dailyTotalStateCasesMostRecent.getRecoveredCasesCumulative());
                    }
                    map.put(covidCase.getDetectedstate(), dailyTotalStateCases);
                }
            }

            if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Hospitalized.name())) {
                dailyTotalStateCases.setActiveCases(dailyTotalStateCases.getActiveCases() + 1);
                dailyTotalStateCases.setTotalCases(dailyTotalStateCases.getTotalCases() + 1);
               // dailyTotalStateCases.setActiveCasesCumulative(dailyTotalStateCases.getActiveCasesCumulative() + 1);
               // dailyTotalStateCases.setTotalCasesCumulative(dailyTotalStateCases.getTotalCasesCumulative() + 1);
            } else if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Recovered.name())) {
                //if(dailyTotalStateCases.getActiveCases() > 0) {
                    dailyTotalStateCases.setActiveCases(dailyTotalStateCases.getActiveCases() - 1);
                  //  dailyTotalStateCases.setActiveCasesCumulative(dailyTotalStateCases.getActiveCasesCumulative() - 1);
               // }
                dailyTotalStateCases.setRecoveredCases(dailyTotalStateCases.getRecoveredCases() + 1);
                //dailyTotalStateCases.setRecoveredCasesCumulative(dailyTotalStateCases.getRecoveredCasesCumulative() + 1);
            } else if(covidCase.getCurrentstatus().equalsIgnoreCase(CaseStatus.Deceased.name())) {
                //if(dailyTotalStateCases.getActiveCases() > 0) {
                    dailyTotalStateCases.setActiveCases(dailyTotalStateCases.getActiveCases() - 1);
                 //   dailyTotalStateCases.setActiveCasesCumulative(dailyTotalStateCases.getActiveCasesCumulative() - 1);
               // }
                dailyTotalStateCases.setDeceasedCases(dailyTotalStateCases.getDeceasedCases() + 1);
               // dailyTotalStateCases.setDeceasedCasesCumulative(dailyTotalStateCases.getDeceasedCasesCumulative() + 1);
            }
        }
        dailyTotalStateCasesRepository.saveAll(map.values());
        for (DailyTotalStateCases value : map.values()) {
            dailyStateTotalCasesCache.set(convert(value));
        }
    }

    private DailyTotalStateCasesDTO convert(DailyTotalStateCases value) {

        if(value == null) {
            return null;
        }
        DailyTotalStateCasesDTO dailyTotalStateCasesDTO = new DailyTotalStateCasesDTO();
        dailyTotalStateCasesDTO.setId(value.getId());
        dailyTotalStateCasesDTO.setState(value.getState());
        dailyTotalStateCasesDTO.setTotalCases(value.getTotalCases());
        dailyTotalStateCasesDTO.setActiveCases(value.getActiveCases());
        dailyTotalStateCasesDTO.setRecoveredCases(value.getRecoveredCases());
        dailyTotalStateCasesDTO.setDeceasedCases(value.getDeceasedCases());
        dailyTotalStateCasesDTO.setTotalCasesCumulative(value.getTotalCasesCumulative());
        dailyTotalStateCasesDTO.setActiveCasesCumulative(value.getActiveCasesCumulative());
        dailyTotalStateCasesDTO.setRecoveredCasesCumulative(value.getRecoveredCasesCumulative());
        dailyTotalStateCasesDTO.setDeceasedCasesCumulative(value.getDeceasedCasesCumulative());
        dailyTotalStateCasesDTO.setRecordedOn(value.getRecordedOn());
        return dailyTotalStateCasesDTO;
    }

    private TotalStateCasesDTO convert(TotalStateCases totalStateCases) {


        if(totalStateCases == null) {
            return null;
        }
        TotalStateCasesDTO totalStateCasesDTO = new TotalStateCasesDTO();
        totalStateCasesDTO.setId(totalStateCases.getId());
        totalStateCasesDTO.setState(totalStateCases.getState());
        totalStateCasesDTO.setTotalCases(totalStateCases.getTotalCases());
        totalStateCasesDTO.setActiveCases(totalStateCases.getActiveCases());
        totalStateCasesDTO.setRecoveredCases(totalStateCases.getRecoveredCases());
        totalStateCasesDTO.setDeceasedCases(totalStateCases.getDeceasedCases());
        return totalStateCasesDTO;
    }

    private TotalCasesDTO convert(TotalCases totalCases) {

        if(totalCases == null) {
            return null;
        }
        TotalCasesDTO totalCasesDTO = new TotalCasesDTO();
        totalCasesDTO.setId(totalCases.getId());
        totalCasesDTO.setTotalCases(totalCases.getTotalCases());
        totalCasesDTO.setActiveCases(totalCases.getActiveCases());
        totalCasesDTO.setRecoveredCases(totalCases.getRecoveredCases());
        totalCasesDTO.setDeceasedCases(totalCases.getDeceasedCases());
        return totalCasesDTO;
    }

    private DailyTotalCasesDTO convert(DailyTotalCases dailyTotalCases) {

        if(dailyTotalCases == null) {
            return null;
        }
        DailyTotalCasesDTO dailyTotalCasesDTO = new DailyTotalCasesDTO();
        dailyTotalCasesDTO.setId(dailyTotalCases.getId());
        dailyTotalCasesDTO.setTotalCases(dailyTotalCases.getTotalCases());
        dailyTotalCasesDTO.setActiveCases(dailyTotalCases.getActiveCases());
        dailyTotalCasesDTO.setRecoveredCases(dailyTotalCases.getRecoveredCases());
        dailyTotalCasesDTO.setDeceasedCases(dailyTotalCases.getDeceasedCases());

        dailyTotalCasesDTO.setTotalCasesCumulative(dailyTotalCases.getTotalCasesCumulative());
        dailyTotalCasesDTO.setActiveCasesCumulative(dailyTotalCases.getActiveCasesCumulative());
        dailyTotalCasesDTO.setRecoveredCasesCumulative(dailyTotalCases.getRecoveredCasesCumulative());
        dailyTotalCasesDTO.setDeceasedCasesCumulative(dailyTotalCases.getDeceasedCasesCumulative());
        dailyTotalCasesDTO.setRecordedOn(dailyTotalCases.getRecordedOn());
        return dailyTotalCasesDTO;
    }
}
