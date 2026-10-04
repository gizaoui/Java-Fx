package com.ex18.service;


import com.ex18.entity.Personne;
import com.ex18.repository.PersonneDao;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
public class PersonneService {

    private final PersonneDao personneDao;

    public PersonneService(PersonneDao personneDao) {
        this.personneDao = personneDao;
    }

    public Personne creer(String nom, String prenom, String email, LocalDate dateNaissance) {
        if (nom == null || nom.isBlank() || prenom == null || prenom.isBlank()) {
            throw new IllegalArgumentException("Le nom et le prénom sont obligatoires");
        }
        return personneDao.save(new Personne(nom.trim(), prenom.trim(), email, dateNaissance));
    }

    @Transactional(readOnly = true)
    public List<Personne> lister() {
        return personneDao.findAll();
    }
}
