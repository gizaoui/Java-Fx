package com.ex18.repository;


import com.ex18.entity.Personne;

import java.util.List;
import java.util.Optional;

public interface PersonneDao {

    Personne save(Personne personne);

    Optional<Personne> findById(Long id);

    List<Personne> findAll();
}
