package main;


import com.ex18.config.AppConfig;
import com.ex18.entity.Personne;
import com.ex18.service.PersonneService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {


        var ctx = new AnnotationConfigApplicationContext(AppConfig.class);

           //  var scanner = new Scanner(System.in);



            PersonneService service = ctx.getBean(PersonneService.class);
            boolean continuer = true;
            System.out.println("START");

            ajouter(service);
            afficher(service);

        }


    private static void ajouter(PersonneService service) {
        System.out.print("Nom : ");
        String nom = "ZAOUI";
        System.out.print("Prénom : ");
        String prenom = "Gilles";
        System.out.print("Email : ");
        String email = "gizaoui@gmail.com";
        System.out.print("Date de naissance (AAAA-MM-JJ, vide si inconnue) : ");
        String saisie = "1971-03-04";

        try {
            LocalDate date = saisie.isEmpty() ? null : LocalDate.parse(saisie);
            Personne p = service.creer(nom, prenom, email, date);
            System.out.println("Personne enregistrée : " + p);
        } catch (DateTimeParseException e) {
            System.out.println("Date invalide, format attendu : AAAA-MM-JJ");
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }

    private static void afficher(PersonneService service) {
        List<Personne> personnes = service.lister();
        if (personnes.isEmpty()) {
            System.out.println("Aucune personne enregistrée.");
        } else {
            personnes.forEach(System.out::println);
        }
    }
}