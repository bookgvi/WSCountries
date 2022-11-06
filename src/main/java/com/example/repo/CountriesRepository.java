package com.example.repo;

import com.example.domain.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountriesRepository extends CrudRepository<Country, Long> {
    public Country findByName(String name);
}
