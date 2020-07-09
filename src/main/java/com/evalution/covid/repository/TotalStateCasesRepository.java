package com.evalution.covid.repository;


import com.evalution.covid.entity.TotalStateCases;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TotalStateCasesRepository extends CrudRepository<TotalStateCases, Long> {

    Optional<TotalStateCases> findByState(String state);
    @Query("SELECT DISTINCT state FROM TotalStateCases")
    List<String> findDistinctByState();
}
