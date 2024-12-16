package com.idat.ec4.Repository;

import com.idat.ec4.Entity.Countrie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountrieRepository extends JpaRepository<Countrie,String> {

    Countrie findByName(String name);
}
