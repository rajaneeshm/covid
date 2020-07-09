package com.evalution.covid.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Audited
@Setter
@Getter
@NoArgsConstructor
public class CovidCase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Column(unique = true)
    private Long patientnumber;
    private Long entryid;
    private String agebracket;
    private String backupnotes;
    private String contractedfromwhichpatientsuspected;
    private String currentstatus;
    private Date dateannounced;
    private String detectedcity;
    private String detecteddistrict;
    private String detectedstate;
    private String estimatedonsetdate;
    private String gender;
    private String nationality;
    private String notes;
    private String numcases;
    private String source1;
    private String source2;
    private String source3;
    private String statecode;
    private String statepatientnumber;
    private Date statuschangedate;
    private String typeoftransmission;
    private boolean newEntry;
}
