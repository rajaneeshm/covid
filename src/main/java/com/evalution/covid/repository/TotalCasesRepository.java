package com.evalution.covid.repository;


import com.evalution.covid.entity.TotalCases;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalCasesRepository extends CrudRepository<TotalCases, Long> {


}
