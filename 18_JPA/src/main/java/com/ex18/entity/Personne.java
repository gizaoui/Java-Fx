package com.ex18.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "personne")
public class Personne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(nullable = false, length = 100)
    private String prenom;

    @Column(length = 150)
    private String email;

    @Column(name = "date_naissance")
    private LocalDate dateNaissance;

    protected Personne() {
        // requis par JPA
    }

    public Personne(String nom, String prenom, String email, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.dateNaissance = dateNaissance;
    }

    public Long getId() { return id; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }

    @Override
    public String toString() {
        return "Personne{id=%d, nom='%s', prenom='%s', email='%s', dateNaissance=%s}"
                .formatted(id, nom, prenom, email, dateNaissance);
    }
}
