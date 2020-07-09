package com.evalution.covid.service.implementation;

import com.evalution.covid.constant.CaseStatus;
import com.evalution.covid.dto.CovidCaseDTO;
import com.evalution.covid.entity.CovidCase;
import com.evalution.covid.repository.CovidCaseRepository;
import com.evalution.covid.service.defitition.ICovidCaseService;
import com.evalution.covid.service.defitition.IUpdateSummary;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service("covidCaseService")
public class CovidCaseServiceImpl implements ICovidCaseService {

    static List<String> states = new ArrayList<String>(Arrays.asList("Delhi", "Telangana", "Andhra Pradesh", "Jammu and Kashmir", "Tamil Nadu", "Punjab", "Rajasthan", "Uttar Pradesh", "Haryana", "Karnataka", "Ladakh", "Maharashtra", "Kerala", "Gujarat", "Chandigarh", "Puducherry", "Chhattisgarh", "Uttarakhand", "Odisha", "West Bengal", "Madhya Pradesh", "Himachal Pradesh", "Bihar", "Manipur", "Mizoram", "Goa", "Andaman and Nicobar Islands", "Jharkhand", "Assam", "Arunachal Pradesh", "Tripura", "Meghalaya"));
    private CovidCaseRepository covidCaseRepository;
    private SimpleDateFormat dateFormater;
    private SimpleDateFormat dateFormaterFromForm;
    private IUpdateSummary updateSummary;


    @Autowired
    public CovidCaseServiceImpl(CovidCaseRepository covidCaseRepository, IUpdateSummary updateSummary) {
        this.covidCaseRepository = covidCaseRepository;
        this.dateFormater = new SimpleDateFormat("dd/MM/yyyy");
        this.dateFormaterFromForm = new SimpleDateFormat("yyyy-MM-dd");
        this.updateSummary = updateSummary;
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @Override
    public CovidCase saveNewCase(CovidCaseDTO dto) {
        CovidCase covidCase = new CovidCase();
        covidCase.setPatientnumber(covidCaseRepository.getMaxPatientnumber() + 1);
        try {
            covidCase.setDateannounced(dateFormaterFromForm.parse(dto.getDateannounced()));
            //covidCase.setStatuschangedate(dateFormaterFromForm.parse(dto.getStatuschangedate()));
        } catch (Exception e) {
            log.debug("Error While parsing date" + dto.getPatientnumber());
        }
        copy(dto, covidCase);
        final CovidCase saved = covidCaseRepository.save(covidCase);
        List<CovidCase> covidCases = new ArrayList<>();
        covidCases.add(saved);
        updateSummary.updateSummary(covidCases);

        return covidCase;
    }

    @Override
    public CovidCase update(CovidCaseDTO covidCaseDTO) {
        final Optional<CovidCase> optional = covidCaseRepository.findByPatientnumber(covidCaseDTO.getPatientnumber());
        if(optional.isPresent()) {
            CovidCase covidCase = optional.get();
            covidCase.setCurrentstatus(covidCaseDTO.getCurrentstatus());
            try {
                covidCase.setStatuschangedate(dateFormaterFromForm.parse(covidCaseDTO.getStatuschangedate()));
                final CovidCase saved = covidCaseRepository.save(covidCase);
                List<CovidCase> covidCases = new ArrayList<>();
                covidCases.add(saved);
                updateSummary.updateSummary(covidCases);
                return covidCase;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }
        return null;
    }

    @Override
    public CovidCaseDTO getCaseById(long patientNumber) {
        final Optional<CovidCase> optional = covidCaseRepository.findByPatientnumber(patientNumber);
        if(optional.isPresent()) {
            return convert(optional.get());
        } else {
            return null;
        }
    }

    @Override
    public CovidCase getEntity(CovidCaseDTO dto) {

        if(dto == null || dto.getPatientnumber() == null) {
            return null;
        }
        CovidCase covidCase;
        String currentstatus = "new";
        final Optional<CovidCase> optional = covidCaseRepository.findByPatientnumber(dto.getPatientnumber());
        if(optional.isPresent()) {
            covidCase = optional.get();
            covidCase.setNewEntry(false);
            currentstatus = covidCase.getCurrentstatus();
        } else {
            covidCase = new CovidCase();
            covidCase.setNewEntry(true);
        }
        final String currentstatusFromDto = dto.getCurrentstatus();
        if(currentstatusFromDto.equalsIgnoreCase(currentstatus) || currentstatus.equalsIgnoreCase(CaseStatus.Deceased.name()) || currentstatusFromDto.equalsIgnoreCase(CaseStatus.Migrated.name())
           || currentstatus.equalsIgnoreCase(CaseStatus.Recovered.name())) {
            return null;
        }
        covidCase.setPatientnumber(dto.getPatientnumber());
        covidCase.setEntryid(dto.getEntryid());
        try {
            covidCase.setDateannounced(dateFormater.parse(dto.getDateannounced()));
            covidCase.setStatuschangedate(dateFormater.parse(dto.getStatuschangedate()));
        } catch (Exception e) {
            log.debug("Error While parsing date" + dto.getPatientnumber());
        }
        copy(dto, covidCase);
        return covidCase;
    }

    private CovidCaseDTO convert(CovidCase covidCase) {

        if(covidCase == null) {
            return null;
        }
        CovidCaseDTO covidCaseDTO = new CovidCaseDTO();
        covidCaseDTO.setEntryid(covidCase.getEntryid());
        covidCaseDTO.setId(covidCase.getId());
        covidCaseDTO.setAgebracket(covidCase.getAgebracket());
        covidCaseDTO.setBackupnotes(covidCase.getBackupnotes());
        covidCaseDTO.setContractedfromwhichpatientsuspected(covidCase.getContractedfromwhichpatientsuspected());
        covidCaseDTO.setCurrentstatus(covidCase.getCurrentstatus());
        covidCaseDTO.setDetectedcity(covidCase.getDetectedcity());
        covidCaseDTO.setDetecteddistrict(covidCase.getDetecteddistrict());
        covidCaseDTO.setDetectedstate(covidCase.getDetectedstate());
        covidCaseDTO.setEstimatedonsetdate(covidCase.getEstimatedonsetdate());
        covidCaseDTO.setGender(covidCase.getGender());
        covidCaseDTO.setNationality(covidCase.getNationality());
        covidCaseDTO.setNotes(covidCase.getNotes());
        covidCaseDTO.setNumcases(covidCase.getNumcases());
        covidCaseDTO.setPatientnumber(covidCase.getPatientnumber());
        covidCaseDTO.setSource1(covidCase.getSource1());
        covidCaseDTO.setSource2(covidCase.getSource2());
        covidCaseDTO.setSource3(covidCase.getSource3());
        covidCaseDTO.setStatecode(covidCase.getStatecode());
        covidCaseDTO.setStatepatientnumber(covidCase.getStatepatientnumber());
        covidCaseDTO.setTypeoftransmission(covidCase.getTypeoftransmission());
        try {
            covidCaseDTO.setDateannounced(dateFormaterFromForm.format(covidCase.getDateannounced()));
            covidCaseDTO.setStatuschangedate(dateFormaterFromForm.format(covidCase.getStatuschangedate()));
        } catch (Exception e) {

        }
        return covidCaseDTO;
    }

    private CovidCase copy(CovidCaseDTO dto, CovidCase covidCase) {
        covidCase.setCurrentstatus(dto.getCurrentstatus());
        covidCase.setDetectedcity(dto.getDetectedcity());
        covidCase.setDetecteddistrict(dto.getDetecteddistrict());
        covidCase.setDetectedstate(dto.getDetectedstate());
        covidCase.setGender(dto.getGender());
        covidCase.setNationality(dto.getNationality());
        covidCase.setNotes(dto.getNotes());
        covidCase.setStatepatientnumber(dto.getStatepatientnumber());
        covidCase.setTypeoftransmission(dto.getTypeoftransmission());
        covidCase.setAgebracket(dto.getAgebracket());
        covidCase.setBackupnotes(dto.getBackupnotes());
        covidCase.setContractedfromwhichpatientsuspected(dto.getContractedfromwhichpatientsuspected());
        return covidCase;
    }

    public void addTestData(int n) {

        for (int i = 0; i < n; i++) {
            int month = getRandomNumber(1, 7);
            int day = getRandomNumber(1, 21);
            int increment = getRandomNumber(1, 6);
            CovidCaseDTO covidCaseDTO = new CovidCaseDTO();
            covidCaseDTO.setDateannounced("2020-" + month + "-" + day);
            covidCaseDTO.setDetectedstate(states.get(getRandomNumber(1, 20)));
            covidCaseDTO.setCurrentstatus(CaseStatus.Hospitalized.name());
            final CovidCase covidCase = saveNewCase(covidCaseDTO);

            if(i % 5 < getRandomNumber(1, 100)) {
                if(i % 3 == 0)
                    covidCaseDTO.setCurrentstatus(CaseStatus.Recovered.name());
                else
                    covidCaseDTO.setCurrentstatus(CaseStatus.Deceased.name());
            }
            covidCaseDTO.setStatuschangedate("2020-" + month + "-" + day + increment);
            covidCaseDTO.setPatientnumber(covidCase.getPatientnumber());
            update(covidCaseDTO);
        }
    }
}
