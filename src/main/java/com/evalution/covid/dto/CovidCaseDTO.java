package com.evalution.covid.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class CovidCaseDTO implements Serializable {
    private Long entryid;
    private long id;
    private String agebracket;
    private String backupnotes;
    private String contractedfromwhichpatientsuspected;
    private String currentstatus;
    private String dateannounced;
    private String detectedcity;
    private String detecteddistrict;
    private String detectedstate;
    private String estimatedonsetdate;
    private String gender;
    private String nationality;
    private String notes;
    private String numcases;
    private Long patientnumber;
    private String source1;
    private String source2;
    private String source3;
    private String statecode;
    private String statepatientnumber;
    private String statuschangedate;
    private String typeoftransmission;
}
