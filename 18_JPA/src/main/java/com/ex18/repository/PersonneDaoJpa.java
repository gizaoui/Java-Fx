package com.ex18.repository;


import com.ex18.entity.Personne;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonneDaoJpa implements PersonneDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Personne save(Personne personne) {
        em.persist(personne);
        return personne;
    }

    @Override
    public Optional<Personne> findById(Long id) {
        return Optional.ofNullable(em.find(Personne.class, id));
    }

    @Override
    public List<Personne> findAll() {
        return em.createQuery("select p from Personne p order by p.nom, p.prenom", Personne.class)
                .getResultList();
    }
}
