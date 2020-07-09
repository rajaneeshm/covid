package com.evalution.covid.repository;


import com.evalution.covid.entity.DailyTotalStateCases;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface DailyTotalStateCasesRepository extends CrudRepository<DailyTotalStateCases, Long> {

    Optional<DailyTotalStateCases> findByStateAndRecordedOn(String state, Date date);

    Optional<DailyTotalStateCases> findTopByOrderByIdDesc();

    Optional<DailyTotalStateCases> findTopByOrderById();

}
