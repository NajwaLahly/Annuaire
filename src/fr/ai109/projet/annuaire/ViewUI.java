package fr.ai109.projet.annuaire;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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


public class ViewUI extends Application{

	public String imagePath = "icon.png";
	static String originPath = "stagiaires.txt";
	static String destinationPath = "BinaryTreeFile.txt";
	private BorderPane primaryRoot = new BorderPane();
	private HBox topView = new HBox(0);
	private Pane bottomView = new Pane();
	private TextField lastNameT;
	private TextField firstNameT = new TextField();
	private TextField zipCodeT = new TextField();
	private TextField batchT = new TextField();
	private TextField yearT = new TextField();
	public boolean access = false;

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

		TableColumn<Trainee, Integer> colYear = new TableColumn<Trainee, Integer>("ANNEE");
		colYear.setCellValueFactory(new PropertyValueFactory<Trainee, Integer>("year"));

		//Donner la colonne a notre tableview
		tableView.getColumns().addAll(colLastName,colFirstName,colpostCode,colPromo,colYear);

		//Ajuster la taille du tableau a son contenu
		tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		tableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Trainee>() {

			@Override
			public void changed(ObservableValue<? extends Trainee> observable, Trainee oldValue, Trainee newValue) {
				//On affiche les attributs du personnage sélectionné dans le label:
				getLastNameT().setText(newValue.getLastName());
				getFirstNameT().setText(newValue.getFirstName());
				getBatchT().setText(newValue.getPromo());
				getZipCodeT().setText(newValue.getPostCode());
				getYearT().setText(newValue.getYear()+"");

			}
		});
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

		topView.getChildren().addAll(setTopViewLeft(),setTopViewRight());

		//2) tableView

		//TraineeDao traineeDao = new TraineeDao();
		obs = FXCollections.observableList(TraineeDao.sortedList);
		TableView<Trainee> tableView = getTable(obs);


		//3) pane with export pdf btn
		setBottomView();

		//adding three pannels to the primaryRoot borderPane
		primaryRoot.setTop(topView);
		primaryRoot.setCenter(tableView);
		primaryRoot.setBottom(bottomView);

		primaryStage.show();
	}



	private Stage setPasswordStage() {
		Stage passwordStage = new Stage();//passwordStage show() when update/delete btn clicked (admin mode)
		passwordStage.setWidth(200);
		passwordStage.setHeight(200);
		VBox passwordRoot = new VBox(50);
		passwordRoot.setStyle("-fx-background-color:orange");
		Label admin = new Label("Entrez le mot de passe administrateur");
		admin.setFont(new Font("Cambria",16));
		admin.setMinWidth(200);
		admin.setWrapText(true);//Ã§a marche pas
		TextField adminTf = new TextField();
		Button ok = new Button("OK");
		ok.setFont(new Font("Cambria",20));
		ok.setMinWidth(185);
		passwordRoot.getChildren().addAll(admin,adminTf);
		Scene passwordScene = new Scene(passwordRoot,300,200);
		passwordStage.setScene(passwordScene);
		passwordStage.setResizable(false);
		if (adminTf.getText().equals("admin")) {
			access = true;
		}
		else {
			Label tryAgain = new Label("Try Again");
			tryAgain.setFont(new Font("Cambria",16));
			tryAgain.setMinWidth(90);
			passwordRoot.getChildren().addAll(tryAgain);
		}	
		return passwordStage;
	}


	private Stage setHelpStage() {
		Stage helpStage = new Stage();  //helpStage show() when help btn clicked (for user documentation)
		helpStage.setTitle("NOTICE D'UTILISATION DU LOGICIEL");
		helpStage.setWidth(1600);
		helpStage.setHeight(1000);
		Pane helpRoot = new Pane(); 
		helpRoot.setStyle("-fx-background-color:papayawhip");
		Scene helpScene = new Scene(helpRoot,800,400);
		helpStage.setScene(helpScene);
		helpStage.sizeToScene();
		Label lbl = new Label("This superb software is pretty much self-explaining!\n It is working "
				+ "on our computer, if it's not working on yours, blame your computer."
				+ "\n When you request an alphabetical list, it will pop, snackle and snap.");
		lbl.setFont(new Font("Cambria",16));
		helpRoot.getChildren().addAll(lbl);
		return helpStage;
	}

	private GridPane setTopViewLeft() {

		GridPane topViewLeft = new GridPane();
		topViewLeft.setStyle("-fx-background-color:bisque");
		topViewLeft.setMinHeight(500);
		topViewLeft.setMinWidth(1200);

		Button add = new Button("ADD");
		add.setFont(new Font("Cambria",16));
		add.setStyle("-fx-background-color:peru");
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
		Button reset = new Button("RESET");
		reset.setFont(new Font("Cambria",16));
		reset.setStyle("-fx-background-color:grey");

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
		Label year = new Label("ANNEE");
		year.setFont(new Font("Cambria",16));

		lastNameT = new TextField();
		firstNameT = new TextField();
		zipCodeT = new TextField();
		batchT = new TextField();
		yearT = new TextField();


		topViewLeft.addRow(0,titre);
		topViewLeft.addRow(1, add, lastName, lastNameT);
		topViewLeft.addRow(2, delete, firstName,firstNameT);
		topViewLeft.addRow(3,update, zipCode,zipCodeT);
		topViewLeft.addRow(4, search, batch,batchT);
		topViewLeft.addRow(5, help, year,yearT);
		topViewLeft.addRow(6, reset);


		topViewLeft.setHgap(100);
		topViewLeft.setVgap(45);

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
					for(int i = criteria; i < criteriaTab.length; i++) {
						if(!criteriaTab[i].equals("")) {
							traineeDao.searchInList(trainee, i, criteriaTab[i], TraineeDao.Found, TraineeDao.startIdxFound);
						}
					}
					obs = FXCollections.observableList(TraineeDao.FoundFiltered);
					refresh(obs);

				}
			});
			delete.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					PasswordStage();
					if (access = true) {

					String[] criteriaTab = {lastNameT.getText(), firstNameT.getText(), zipCodeT.getText(), batchT.getText(), yearT.getText()};

					int criteria = 0;
					for(int i = 0; i < criteriaTab.length; i++) {
						if (!criteriaTab[i].equals("")) {
							criteria = i;
							traineeDao.search(raf, trainee, binaryTreeToFile, criteria, criteriaTab[criteria]);
							break;
						}
					}
					for(int i = criteria; i < criteriaTab.length; i++) {
						if(!criteriaTab[i].equals("")) {
							traineeDao.searchInList(trainee, i, criteriaTab[i], TraineeDao.Found, TraineeDao.startIdxFound);
						}
					}
					traineeDao.deleteTraineeInRaf(raf, TraineeDao.idxFoundFiltered.get(0), trainee, binaryTreeToFile);
					traineeDao.sortTreeInOrder(raf, trainee, binaryTreeToFile);
					obs = FXCollections.observableList(TraineeDao.sortedList);
					refresh(obs);

				}
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
		return topViewLeft;
	}

	private BorderPane setTopViewRight() {
		BorderPane topViewRight = new BorderPane();
		topViewRight.setStyle("-fx-background-color:papayawhip");
		//ImageView iv = new ImageView(getClass().getResource(imagePath).toString());
		//topViewRight.setCenter(iv);
		topViewRight.setMinWidth(400);
		topViewRight.setMinHeight(500);
		return topViewRight;
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

}