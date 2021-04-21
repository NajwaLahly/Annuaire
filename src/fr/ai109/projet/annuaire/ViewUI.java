package fr.ai109.projet.annuaire;

import java.awt.TextField;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ViewUI extends Application{//test






	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {


		primaryStage.setTitle("ANNUAIRE EQL");
		primaryStage.setWidth(1600);
		primaryStage.setHeight(1000);

		VBox root = new VBox(0);   //3 boxes piled up: user interface, Table view, one simple pane with "export pdf" btn
		Scene scene = new Scene(root,1600,1000);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();

		//1st VBox topView=user interface
		GridPane topView = new GridPane();
		topView.setMinHeight(450);
		topView.setMinWidth(1600);

		Button add = new Button("ADD");
		Button delete = new Button("DELETE");
		Button update = new Button("UPDATE");
		Button search = new Button("SEARCH");
		Button showAll= new Button("SHOW ALL");
		Button help = new Button("HELP");

		Label lastName = new Label("NOM");
		Label firstName = new Label("PRENOM");
		Label zipCode = new Label("DEPARTEMENT");
		Label batch = new Label("PROMOTION");
		Label batchNb = new Label("NUM PROMOTION");
		Label year = new Label("ANNEE");


		TextField firstNameT = new TextField();
		TextField lastNameT = new TextField();
		TextField zipCodeT = new TextField();
		TextField batchT = new TextField();
		TextField batchNbT = new TextField();
		TextField yearT = new TextField();

		//topView.addRow(0, add, lastName, lastNameT);

		//2nd Vbox tableView




		TableView<Trainee> tableView = new TableView<Trainee>();//(observableTrainee);
		tableView.setMinHeight(450);
		tableView.setMinWidth(1600);

		TableColumn<Trainee, String> colLastName = new TableColumn<Trainee, String>("NOM");
		colLastName.setCellValueFactory(new PropertyValueFactory<Trainee, String>("lastName"));

		TableColumn<Trainee, String> colFirstName = new TableColumn<Trainee, String>("PRENOM");
		colFirstName.setCellValueFactory(new PropertyValueFactory<Trainee, String>("firstName"));

		TableColumn<Trainee, Integer> colpostCode = new TableColumn<Trainee, Integer>("DEPARTEMENT");
		colpostCode.setCellValueFactory(new PropertyValueFactory<Trainee, Integer>("postCode"));

		TableColumn<Trainee, Integer> colPromo = new TableColumn<Trainee, Integer>("PROMOTION");
		colPromo.setCellValueFactory(new PropertyValueFactory<Trainee, Integer>("promo"));

		TableColumn<Trainee, Integer> colPromoNb = new TableColumn<Trainee, Integer>("NUM PROMOTION");
		colPromoNb.setCellValueFactory(new PropertyValueFactory<Trainee, Integer>("promoNumber"));

		TableColumn<Trainee, Integer> colYear = new TableColumn<Trainee, Integer>("ANNEE");
		colYear.setCellValueFactory(new PropertyValueFactory<Trainee, Integer>("year"));

		//Donner la colonne a notre tableview
		tableView.getColumns().addAll(colLastName,colFirstName,colpostCode,colPromo,colPromoNb,colYear);

		//Ajuster la taille du tableau a son contenu
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);



		//3rd VBox pane with export pdf btn

		Pane bottomView = new Pane();
		bottomView.setMinHeight(100);
		bottomView.setMinWidth(1600);

		Button export = new Button("EXPORT TO PDF");
		bottomView.getChildren().addAll(export);



		root.getChildren().addAll(topView,tableView,bottomView);




		Stage helpStage = new Stage();  //show() when help btn clicked (for user documentation)
		Stage passwordStage = new Stage();//show() when update/delete btn clicked (admin mode)
		passwordStage.setResizable(false);



		primaryStage.show();
	}
}