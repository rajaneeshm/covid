package com.evalution.covid.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Audited
@Setter
@Getter
@NoArgsConstructor
public class DailyTotalStateCases {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String state;
    private long totalCases;
    private long activeCases;
    private long recoveredCases;
    private long deceasedCases;
    private long totalCasesCumulative;
    private long activeCasesCumulative;
    private long recoveredCasesCumulative;
    private long deceasedCasesCumulative;
    private Date recordedOn;
}
