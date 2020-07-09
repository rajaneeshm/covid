package com.evalution.covid.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Audited
@Setter
@Getter
@NoArgsConstructor
public class TotalCases {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long totalCases;
    private long activeCases;
    private long recoveredCases;
    private long deceasedCases;
}
