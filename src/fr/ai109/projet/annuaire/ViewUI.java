package fr.ai109.projet.annuaire;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.RandomAccessFile;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class ViewUI extends Application{

	public String imagePath = "icon.png";
	static String originPath = "stagiaires.txt";
	static String destinationPath = "BinaryTreeFile.txt";
	private BorderPane primaryRoot = new BorderPane();
	private Pane bottomView = new Pane();
	private BorderPane topView = new BorderPane();
	private HBox topViewBottom = new HBox(0);
	private TextField lastNameT = new TextField();
	private TextField firstNameT = new TextField();
	private TextField zipCodeT = new TextField();
	private TextField batchT = new TextField();
	private TextField yearT = new TextField();
	private TextField passwordT = new TextField();

	public Button ok;
	private Button delete= new Button("DELETE");
	private Button update= new Button("UPDATE");
	private boolean access = false;

	public boolean isAccess() {
		return access;
	}


	public void setAccess(boolean access) {
		this.access = access;
	}


	public String getImagePath() {
		return imagePath;
	}


	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	public TextField getLastNameT() {
		return lastNameT;
	}


	public void setLastNameT(TextField lastNameT) {
		this.lastNameT = lastNameT;
	}


	public TextField getFirstNameT() {
		return firstNameT;
	}


	public void setFirstNameT(TextField firstNameT) {
		this.firstNameT = firstNameT;
	}


	public TextField getZipCodeT() {
		return zipCodeT;
	}


	public void setZipCodeT(TextField zipCodeT) {
		this.zipCodeT = zipCodeT;
	}


	public TextField getBatchT() {
		return batchT;
	}


	public void setBatchT(TextField batchT) {
		this.batchT = batchT;
	}


	public TextField getYearT() {
		return yearT;
	}


	public void setYearT(TextField yearT) {
		this.yearT = yearT;
	}


	static ObservableList<Trainee> obs = FXCollections.observableArrayList();

	public static void main(String[] args) {
		launch(args);

	}


	private void refresh(ObservableList<Trainee> obs) {
		primaryRoot.setCenter(getTable(obs));
	}


	private TableView<Trainee> getTable(ObservableList<Trainee> observableTrainees) {

		TableView<Trainee> tableView = new TableView<Trainee>(observableTrainees);

		tableView.setMaxHeight(700);
		tableView.setMinWidth(1600);

		TableColumn<Trainee, String> colLastName = new TableColumn<Trainee, String>("NOM");
		colLastName.setCellValueFactory(new PropertyValueFactory<Trainee, String>("lastName"));

		TableColumn<Trainee, String> colFirstName = new TableColumn<Trainee, String>("PRENOM");
		colFirstName.setCellValueFactory(new PropertyValueFactory<Trainee, String>("firstName"));

		TableColumn<Trainee, Integer> colpostCode = new TableColumn<Trainee, Integer>("DEPARTEMENT");
		colpostCode.setCellValueFactory(new PropertyValueFactory<Trainee, Integer>("postCode"));

		TableColumn<Trainee, Integer> colPromo = new TableColumn<Trainee, Integer>("PROMOTION");
		colPromo.setCellValueFactory(new PropertyValueFactory<Trainee, Integer>("promo"));

		TableColumn<Trainee, Integer> colYear = new TableColumn<Trainee, Integer>("ANNEE");
		colYear.setCellValueFactory(new PropertyValueFactory<Trainee, Integer>("year"));

		//Donner la colonne a notre tableview
		tableView.getColumns().addAll(colLastName,colFirstName,colpostCode,colPromo,colYear);

		//Ajuster la taille du tableau a son contenu
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		TraineeDao traineeDao = new TraineeDao();
		BinaryTreeToFile binaryTreeToFile = new BinaryTreeToFile();
		RandomAccessFile raf;
		Trainee trainee = new Trainee();
		try {
			raf = new RandomAccessFile(destinationPath, "rw");
			tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Trainee>() {

				@Override
				public void changed(ObservableValue<? extends Trainee> observable, Trainee oldValue, Trainee newValue) {
					//On affiche les attributs du personnage sélectionné dans le label:
					getLastNameT().setText(newValue.getLastName());
					getFirstNameT().setText(newValue.getFirstName());
					getBatchT().setText(newValue.getPromo());
					getZipCodeT().setText(newValue.getPostCode());
					getYearT().setText(newValue.getYear()+"");
					String[] criteriaTab = {lastNameT.getText(), firstNameT.getText(), zipCodeT.getText(), batchT.getText(), yearT.getText()};
					int criteria = 0;
					for(int i = 0; i < criteriaTab.length; i++) {
						if (!criteriaTab[i].equals("")) {
							criteria = i;
							traineeDao.search(raf, trainee, binaryTreeToFile, criteria, criteriaTab[criteria]);
							break;
						}
					}
					TraineeDao.FoundFiltered = TraineeDao.Found;
					for(int i = criteria; i < criteriaTab.length; i++) {
						if(!criteriaTab[i].equals("")) {
							traineeDao.searchInList(trainee, i, criteriaTab[i], TraineeDao.FoundFiltered, TraineeDao.startIdxFound);
						}
					}
				}
			});
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return tableView;
	}


	@Override
	public void start(Stage primaryStage) throws Exception {

		//File binaryfile = new File(destinationPath);
		BinaryTreeToFile binaryTreeToFile = new BinaryTreeToFile();
		BufferedReader reader = new BufferedReader(new FileReader(originPath));
		RandomAccessFile raf = new RandomAccessFile(destinationPath, "rw");
		Trainee trainee = new Trainee();

		if (raf.length()==0) {
			binaryTreeToFile.originFileToDestinationFile(reader, raf);
		}
		TraineeDao traineeDao = new TraineeDao();
		//		binaryTreeToFile.findParent(43776, raf, trainee);
		//traineeDao.deleteTraineeInRaf(raf, 44, trainee, binaryTreeToFile);

		traineeDao.sortTreeInOrder(raf, trainee, binaryTreeToFile);
		//		for(Trainee t:TraineeDao.sortedList) {
		//			System.out.println(t);
		//		}
		//test methode search
		//Trainee trainee1 = new Trainee("")

		primaryStage.setTitle("ANNUAIRE EQL");
		primaryStage.setWidth(1600);
		primaryStage.setHeight(1000);
		Scene scene = new Scene(primaryRoot,1600,1000);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();

		//3 pannels piled up in a borderPane: user interface(HBox)/ Table view/ pane with "export pdf" btn

		//1) HBox= topView=user interface= hBox with GridPane on the left and BorderPane with icon on the right


		//bottomView.getChildren().add(setBottomView());		//2) tableView

		//TraineeDao traineeDao = new TraineeDao();
		obs = FXCollections.observableList(TraineeDao.sortedList);
		TableView<Trainee> tableView = getTable(obs);

		topViewBottom.getChildren().addAll(setTopViewLeftBottom(), setTopViewRightBottom());
		topView.setTop(setTopViewTop());
		topView.setBottom(topViewBottom);

		//3) pane with export pdf btn
		//setBottomView();

		//adding three pannels to the primaryRoot borderPane
		primaryRoot.setTop(topView);
		primaryRoot.setCenter(tableView);
		primaryRoot.setBottom(setBottomView());

		primaryStage.show();
	}



	//	private Stage setPasswordStage() {
	//		Stage passwordStage = new Stage();//passwordStage show() when update/delete btn clicked (admin mode)
	//		passwordStage.setWidth(500);
	//		passwordStage.setHeight(500);
	//		VBox passwordRoot = new VBox(50);
	//		passwordRoot.setStyle("-fx-background-color:orange");
	//		Label admin = new Label("Entrez le mot de passe administrateur");
	//		admin.setFont(new Font("Cambria",16));
	//		admin.setMinWidth(200);
	//		admin.setWrapText(true);//Ã§a marche pas
	//		TextField adminTf = new TextField();
	//		passwordRoot.getChildren().addAll(admin,adminTf,ok);
	//		Scene passwordScene = new Scene(passwordRoot,300,200);
	//		passwordStage.setScene(passwordScene);
	//		passwordStage.setResizable(false);
	//
	//		System.out.println("acc"+ isAccess());
	//		return passwordStage;
	//	}


	private Stage setHelpStage() {
		Stage helpStage = new Stage();  //helpStage show() when help btn clicked (for user documentation)
		helpStage.setTitle("NOTICE D'UTILISATION DU LOGICIEL");
		helpStage.setWidth(1600);
		helpStage.setHeight(1000);
		VBox helpRoot = new VBox(); 
		helpRoot.setStyle("-fx-background-color:papayawhip");
		Scene helpScene = new Scene(helpRoot,800,400);
		helpStage.setScene(helpScene);
		helpStage.sizeToScene();
		Label lbl = new Label("ADD : pour ajouter un stagaire à l'Annuaire, il faut remplir les informations du stagaire et cliquer sur le bouton ADD pour l'ajouter.\n"
				+ "\n"
				+ "DELETE : il faut être Administrateur (avoir le mot de passe) pour pouvoir supprimer un stagaire de l'Annuaire.\n"
				+ "\n"
				+ "UPDATE : il faut être Administrateur (avoir le mot de passe) pour pouvoir mettre à jour les informations d'un stagaire.\n"
				+ "\n"
				+ "SEARCH : pour rechercher un ou plusieurs stagaires dans l'Annuaire, la recherche se fait selon plusieurs critères, il faut remplir un champ et cliquer sur le bouton SEARCH.\n"
				+ "\n"
				+ "RESET : pour annuller une recherche et réafficher tous les stagaires.\n"
				+ "\n"
				+ "HELP pour accéder à la NOTICE D'UTILISATION DU LOGICIEL.\n"
				+ "\n"
				+ "EXPORT TO PDF : pour exporter l'Annuaire ou un extrait issu de la recherche au format PDF.\n");
		lbl.setFont(new Font("Cambria",16));
		lbl.setWrapText(true);
		helpRoot.getChildren().addAll(lbl);
		return helpStage;
	}

	private Pane setTopViewTop() {

		Pane topViewTop = new Pane();
		topView.setStyle("-fx-background-color:tan");
		topView.setMinWidth(1600);
		topView.setMaxHeight(40);
		Label titre = new Label("BIENVENUE DANS L'ANNUAIRE EQL");
		titre.setFont(new Font("Cambria",26));
		titre.relocate(0, 10);

		Label navMode = new Label("(user mode)");
		navMode.setFont(new Font("Cambria",15));
		navMode.setStyle("-fx-text-fill:red");
		navMode.setMaxWidth(100);
		navMode.relocate(800, 10);

		Label password = new Label("password");
		password.setFont(new Font("Cambria",20));
		passwordT = new TextField();
		password.relocate(1200, 10);
		passwordT.relocate(1300, 10);


		Button ok = new Button("OK");
		ok.setFont(new Font("Cambria",20));
		ok.setMinWidth(80);
		ok.setMaxHeight(20);

		ok.relocate(1500, 10);

		topViewTop.getChildren().addAll(titre, password, navMode, passwordT,ok);

		ok.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (passwordT.getText().equals("admin")) {
					navMode.setStyle("-fx-text-fill:green");
					navMode.setText("(admin mode)");
					delete.setDisable(false);
					update.setDisable(false);

				}
			}
		});

		return topViewTop;
	}

	private GridPane setTopViewLeftBottom() {

		GridPane TopViewLeftBottom = new GridPane();
		TopViewLeftBottom.setStyle("-fx-background-color:bisque");
		TopViewLeftBottom.setMaxHeight(500);
		TopViewLeftBottom.setMinWidth(1200);
		
		

		Button add = new Button("ADD");
		add.setFont(new Font("Cambria",16));
		add.setStyle("-fx-background-color:peru");
		Button search = new Button("SEARCH");
		search.setFont(new Font("Cambria",16));
		search.setStyle("-fx-background-color:peru");
		//Button update = new Button("UPDATE");
		update.setFont(new Font("Cambria",16));
		update.setStyle("-fx-background-color:orange");
		//Button delete= new Button("DELETE");
		delete.setFont(new Font("Cambria",16));
		delete.setStyle("-fx-background-color:orange");
		Button help = new Button("HELP");
		help.setFont(new Font("Cambria",16));
		help.setStyle("-fx-background-color:red");
		Button reset = new Button("RESET");
		reset.setFont(new Font("Cambria",16));
		reset.setStyle("-fx-background-color:grey");
		
		delete.setDisable(true);
		update.setDisable(true);



		Label lastName = new Label("NOM");
		lastName.setFont(new Font("Cambria",16));
		Label firstName = new Label("PRENOM");
		firstName.setFont(new Font("Cambria",16));
		Label zipCode = new Label("DEPARTEMENT");
		zipCode.setFont(new Font("Cambria",16));
		Label batch = new Label("PROMOTION");
		batch.setFont(new Font("Cambria",16));
		Label year = new Label("ANNEE");
		year.setFont(new Font("Cambria",16));

		lastNameT = new TextField();
		firstNameT = new TextField();
		zipCodeT = new TextField();
		batchT = new TextField();
		yearT = new TextField();


		//CenterViewLeft.addRow(0,titre, password, passwordT, ok);
		TopViewLeftBottom.addRow(1, add, lastName, lastNameT);
		TopViewLeftBottom.addRow(2, delete, firstName,firstNameT);
		TopViewLeftBottom.addRow(3,update, zipCode,zipCodeT);
		TopViewLeftBottom.addRow(4, search, batch,batchT);
		TopViewLeftBottom.addRow(5, help, year,yearT);
		TopViewLeftBottom.addRow(6, reset);
		
		GridPane.setMargin(add, new Insets(0, 0, 0, 10));
		GridPane.setMargin(delete, new Insets(0, 0, 0, 10));
		GridPane.setMargin(update, new Insets(0, 0, 0, 10));
		GridPane.setMargin(search, new Insets(0, 0, 0, 10));
		GridPane.setMargin(help, new Insets(0, 0, 0, 10));
		GridPane.setMargin(reset, new Insets(0, 0, 10, 10));


		TopViewLeftBottom.setHgap(100);
		TopViewLeftBottom.setVgap(45);

		TraineeDao traineeDao = new TraineeDao();
		BinaryTreeToFile binaryTreeToFile = new BinaryTreeToFile();
		RandomAccessFile raf;
		Trainee trainee = new Trainee();
		try {
			raf = new RandomAccessFile(destinationPath, "rw");


			add.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Trainee newTrainee = new Trainee(lastNameT.getText(),firstNameT.getText(),zipCodeT.getText(),batchT.getText(),Integer.parseInt(yearT.getText()));
					traineeDao.addTraineeInRaf(newTrainee);
					traineeDao.sortTreeInOrder(raf, trainee, binaryTreeToFile);;
					obs = FXCollections.observableList(TraineeDao.sortedList);							
					refresh(obs);
				}
			});

			search.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					String[] criteriaTab = {lastNameT.getText(), firstNameT.getText(), zipCodeT.getText(), batchT.getText(), yearT.getText()};
					int criteria = 0;
					for(int i = 0; i < criteriaTab.length; i++) {
						if (!criteriaTab[i].equals("")) {
							criteria = i;
							traineeDao.search(raf, trainee, binaryTreeToFile, criteria, criteriaTab[criteria]);

							break;
						}
					}

					TraineeDao.FoundFiltered = TraineeDao.Found;
					for(int i = criteria; i < criteriaTab.length; i++) {
						if(!criteriaTab[i].equals("")) {
							traineeDao.searchInList(trainee, i, criteriaTab[i], TraineeDao.FoundFiltered, TraineeDao.startIdxFound);
						}

					}
					obs = FXCollections.observableList(TraineeDao.FoundFiltered);
					refresh(obs);

				}
			});
			delete.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
						traineeDao.deleteTraineeInRaf(raf, TraineeDao.idxFoundFiltered.get(0), trainee, binaryTreeToFile);
						traineeDao.sortTreeInOrder(raf, trainee, binaryTreeToFile);
						obs = FXCollections.observableList(TraineeDao.sortedList);

					
					refresh(obs);

				}

			});

			update.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {//faut chercher l'élément à mettre à jour
						Trainee newTrainee = new Trainee(lastNameT.getText(),firstNameT.getText(),zipCodeT.getText(),batchT.getText(),Integer.parseInt(yearT.getText()));
						traineeDao.update(newTrainee, TraineeDao.idxFoundFiltered.get(0), trainee, raf, binaryTreeToFile);
						traineeDao.sortTreeInOrder(raf, trainee, binaryTreeToFile);
						obs = FXCollections.observableList(TraineeDao.sortedList);
						refresh(obs);

					
				}
			});
			help.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					setHelpStage().show();

				}
			});

			reset.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					obs = FXCollections.observableList(TraineeDao.sortedList);
					refresh(obs);
				}
			});


		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return TopViewLeftBottom;
	}

	private BorderPane setTopViewRightBottom() {
		BorderPane CenterViewRightBottom = new BorderPane();
		CenterViewRightBottom.setStyle("-fx-background-color:papayawhip");
		//ImageView iv = new ImageView(getClass().getResource(imagePath).toString());
		//topViewRight.setCenter(iv);
		CenterViewRightBottom.setMinWidth(1000);
		CenterViewRightBottom.setMaxHeight(700);
		return CenterViewRightBottom;
	}


	private Pane setBottomView() {
		bottomView.setMinHeight(100);
		bottomView.setMinWidth(1600);
		bottomView.setStyle("-fx-background-color:papayawhip");

		Button export = new Button("EXPORT TO PDF");
		export.setFont(new Font("Cambria",16));
		export.setStyle("-fx-background-color:peru");
		bottomView.getChildren().addAll(export);

		export.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//TODO

			}
		});


		return bottomView;
	}


	public TextField getPasswordT() {
		return passwordT;
	}


	public void setPasswordT(TextField passwordT) {
		this.passwordT = passwordT;
	}

}