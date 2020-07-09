package com.evalution.covid.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Audited
@Setter
@Getter
@NoArgsConstructor
public class TotalStateCases {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String state;
    private long totalCases;
    private long activeCases;
    private long recoveredCases;
    private long deceasedCases;
}
