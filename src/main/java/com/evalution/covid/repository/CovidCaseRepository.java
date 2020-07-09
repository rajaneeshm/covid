package com.evalution.covid.repository;


import com.evalution.covid.entity.CovidCase;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CovidCaseRepository extends CrudRepository<CovidCase, Long> {

    Optional<CovidCase> findByPatientnumber(Long patientNumber);

    @Query("SELECT coalesce(max(c.patientnumber), 0) FROM CovidCase c")
    Long getMaxPatientnumber();

}
