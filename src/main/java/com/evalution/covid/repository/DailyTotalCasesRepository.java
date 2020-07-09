package com.evalution.covid.repository;


import com.evalution.covid.entity.DailyTotalCases;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DailyTotalCasesRepository extends CrudRepository<DailyTotalCases, Long> {

    Optional<DailyTotalCases> findByRecordedOn(Date date);
    Optional<DailyTotalCases> findTopByOrderByIdDesc();
    Optional<DailyTotalCases> findTopByOrderById();
}
