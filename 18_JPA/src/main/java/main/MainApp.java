package main;


import com.ex18.config.AppConfig;
import com.ex18.entity.Personne;
import com.ex18.service.PersonneService;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainApp extends Application {

    private AnnotationConfigApplicationContext context;
    private PersonneService service;

    private final ObservableList<Personne> personnes = FXCollections.observableArrayList();

    private final TextField champNom = new TextField();
    private final TextField champPrenom = new TextField();
    private final TextField champEmail = new TextField();
    private final DatePicker champDate = new DatePicker();
    private final Label message = new Label();

    @Override
    public void init() {
        // Démarrage du contexte Spring avant l'affichage de la fenêtre
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        service = context.getBean(PersonneService.class);
    }

    @Override
    public void start(Stage stage) {
        champNom.setPromptText("Nom");
        champPrenom.setPromptText("Prénom");
        champEmail.setPromptText("email@exemple.com");
        champDate.setPromptText("Date de naissance");

        GridPane formulaire = new GridPane();
        formulaire.setHgap(10);
        formulaire.setVgap(8);
        formulaire.addRow(0, new Label("Nom *"), champNom);
        formulaire.addRow(1, new Label("Prénom *"), champPrenom);
        formulaire.addRow(2, new Label("Email"), champEmail);
        formulaire.addRow(3, new Label("Naissance"), champDate);

        Button boutonAjouter = new Button("Ajouter");
        boutonAjouter.setDefaultButton(true);
        boutonAjouter.setOnAction(e -> ajouter());

        Button boutonEffacer = new Button("Effacer");
        boutonEffacer.setOnAction(e -> viderFormulaire());

        HBox boutons = new HBox(10, boutonAjouter, boutonEffacer);

        TableView<Personne> table = creerTable();
        VBox.setVgrow(table, Priority.ALWAYS);

        VBox racine = new VBox(12, formulaire, boutons, message, table);
        racine.setPadding(new Insets(15));

        rafraichir();

        stage.setTitle("Gestion des personnes");
        stage.setScene(new Scene(racine, 650, 1000));

//        Rectangle2D zone = Screen.getPrimary().getVisualBounds();
//        stage.setX(zone.getMinX());
//        stage.setY(zone.getMinY());
//        stage.setWidth(zone.getWidth());
//        stage.setHeight(zone.getHeight());

        stage.setMaximized(true); // fenêtre agrandie au démarrage
        stage.show();
    }

    @Override
    public void stop() {
        if (context != null) {
            context.close();
        }
    }

    private TableView<Personne> creerTable() {
        TableView<Personne> table = new TableView<>(personnes);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);

        TableColumn<Personne, String> colId = new TableColumn<>("Id");
        colId.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getId())));

        TableColumn<Personne, String> colNom = new TableColumn<>("Nom");
        colNom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNom()));

        TableColumn<Personne, String> colPrenom = new TableColumn<>("Prénom");
        colPrenom.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPrenom()));

        TableColumn<Personne, String> colEmail = new TableColumn<>("Email");
        colEmail.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getEmail()));

        TableColumn<Personne, String> colDate = new TableColumn<>("Naissance");
        colDate.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().getDateNaissance() == null ? "" : c.getValue().getDateNaissance().toString()));

        table.getColumns().add(colId);
        table.getColumns().add(colNom);
        table.getColumns().add(colPrenom);
        table.getColumns().add(colEmail);
        table.getColumns().add(colDate);
        return table;
    }

    private void ajouter() {
        try {
            Personne p = service.creer(
                    champNom.getText(),
                    champPrenom.getText(),
                    champEmail.getText(),
                    champDate.getValue());
            message.setText("Personne enregistrée : " + p.getPrenom() + " " + p.getNom());
            viderFormulaire();
            rafraichir();
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.WARNING, e.getMessage()).showAndWait();
        } catch (RuntimeException e) {
            new Alert(Alert.AlertType.ERROR, "Erreur d'enregistrement : " + e.getMessage()).showAndWait();
        }
    }

    private void viderFormulaire() {
        champNom.clear();
        champPrenom.clear();
        champEmail.clear();
        champDate.setValue(null);
        champNom.requestFocus();
    }

    private void rafraichir() {
        personnes.setAll(service.lister());
    }

    public static void main(String[] args) {
        launch(args);
    }
}