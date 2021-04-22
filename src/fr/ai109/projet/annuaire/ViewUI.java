package fr.ai109.projet.annuaire;



import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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

		VBox primaryRoot = new VBox(0);   //3 boxes piled up: user interface, Table view, one simple pane with "export pdf" btn
		Scene scene = new Scene(primaryRoot,1600,1000);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();

		//1st VBox topView=user interface= hBox with GridPane on the left and BorderPane with icon on the right
		BorderPane topViewRight = new BorderPane();
		topViewRight.setStyle("-fx-background-color:papayawhip");
		ImageView iv = new ImageView(getClass().getResource("icon.png").toString());
		topViewRight.setCenter(iv);
		topViewRight.setMinWidth(400);
		topViewRight.setMinHeight(500);
		

		GridPane topViewLeft = new GridPane();
		topViewLeft.setStyle("-fx-background-color:papayawhip");
		topViewLeft.setMinHeight(500);
		topViewLeft.setMinWidth(1200);

		Button add = new Button("ADD");
		add.setFont(new Font("Cambria",16));
		add.setStyle("-fx-background-color:peru");
		Button showAll = new Button("SHOW ALL");
		showAll.setFont(new Font("Cambria",16));
		showAll.setStyle("-fx-background-color:peru");
		Button search = new Button("SEARCH");
		search.setFont(new Font("Cambria",16));
		search.setStyle("-fx-background-color:peru");
		Button update = new Button("UPDATE");
		update.setFont(new Font("Cambria",16));
		update.setStyle("-fx-background-color:orange");
		Button delete= new Button("DELETE");
		delete.setFont(new Font("Cambria",16));
		delete.setStyle("-fx-background-color:orange");
		Button help = new Button("HELP");
		help.setFont(new Font("Cambria",16));
		help.setStyle("-fx-background-color:red");

		Label titre = new Label("BIENVENUE DANS L'ANNUAIRE EQL");
		titre.setFont(new Font("Cambria",26));
		
		
		
		Label lastName = new Label("NOM");
		lastName.setFont(new Font("Cambria",16));
		Label firstName = new Label("PRENOM");
		firstName.setFont(new Font("Cambria",16));
		Label zipCode = new Label("DEPARTEMENT");
		zipCode.setFont(new Font("Cambria",16));
		Label batch = new Label("PROMOTION");
		batch.setFont(new Font("Cambria",16));
		Label batchNb = new Label("NUM PROMOTION");
		batchNb.setFont(new Font("Cambria",16));
		Label year = new Label("ANNEE");
		year.setFont(new Font("Cambria",16));

		TextField lastNameT = new TextField();
		TextField firstNameT = new TextField();
		TextField zipCodeT = new TextField();
		TextField batchT = new TextField();
		TextField batchNbT = new TextField();
		TextField yearT = new TextField();
		
		
		topViewLeft.addRow(0,titre);
		topViewLeft.addRow(1, add, lastName, lastNameT);
		topViewLeft.addRow(2, delete, firstName,firstNameT);
		topViewLeft.addRow(3,update, zipCode,zipCodeT);
		topViewLeft.addRow(4, search, batch,batchT);
		topViewLeft.addRow(5, showAll, batchNb,batchNbT);
		topViewLeft.addRow(6, help, year,yearT);
		topViewLeft.setHgap(100);;//comment ça marche?
		topViewLeft.setVgap(45);
		
		HBox topView = new HBox(0);
		topView.getChildren().addAll(topViewLeft,topViewRight);

		//2nd Vbox tableView


		TableView<Trainee> tableView = new TableView<Trainee>();//(observableTrainee);
		tableView.setMinHeight(400);
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
		bottomView.setStyle("-fx-background-color:papayawhip");

		Button export = new Button("EXPORT TO PDF");
		export.setFont(new Font("Cambria",16));
		export.setStyle("-fx-background-color:peru");
		bottomView.getChildren().addAll(export);



		primaryRoot.getChildren().addAll(topView,tableView,bottomView);




		Stage helpStage = new Stage();  //helpStage show() when help btn clicked (for user documentation)
		helpStage.setTitle("NOTICE D'UTILISATION DU LOGICIEL");
		helpStage.setWidth(1600);
		helpStage.setHeight(1000);
		Pane helpRoot = new Pane();   
		Scene helpScene = new Scene(helpRoot,800,400);
		helpStage.setScene(helpScene);
		helpStage.sizeToScene();
		Label lbl = new Label("Voici comment utiliser ce logiciel");
		helpRoot.getChildren().addAll(lbl);
		
		Stage passwordStage = new Stage();//passwordStage show() when update/delete btn clicked (admin mode)
		passwordStage.setWidth(200);
		passwordStage.setHeight(200);
		VBox passwordRoot = new VBox(50);
		Label admin = new Label("Entrez le mot de passe admin");
		admin.setMinWidth(200);
		admin.setWrapText(true);//ça marche pas
		TextField adminTf = new TextField();
		passwordRoot.getChildren().addAll(admin,adminTf);
		Scene passwordScene = new Scene(passwordRoot,300,200);
		passwordStage.setScene(passwordScene);
		passwordStage.setResizable(false);
		
		
		//pressing delete button opens new password window
		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				passwordStage.show();
				//if adminTf == password, déclencher l'action

			}
		});
		//pressing update button opens password window
		update.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				passwordStage.show();
				//if adminTf == password, déclencher l'action

			}
		});
		//pressing help button opens documentation window
		help.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				helpStage.show();

			}
		});
		//pressing export button creates pdf of current tableview
		export.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//TODO

			}
		});
		
		primaryStage.show();
	}
}