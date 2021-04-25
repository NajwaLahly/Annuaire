package fr.ai109.projet.annuaire;



import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import javafx.application.Application;
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
		return tableView;
	}


	public static ArrayList<Trainee> fileData () { //changer le nom!!
		// TODO Auto-generated method stub
		File binaryfile = new File(destinationPath);
		Trainee trainee = new Trainee();
		TraineeDao traineeDao = new TraineeDao();
		try {
			//revoir new file pour originePath
			RandomAccessFile raf = new RandomAccessFile(binaryfile, "rw");
			BufferedReader reader = new BufferedReader(new FileReader(originPath));
			BinaryTreeToFile binaryTreeToFile = new BinaryTreeToFile();

			//binaryTreeToFile.originFileToDestinationFile(reader, raf);
			traineeDao.getAll(raf, trainee, binaryTreeToFile);
			traineeDao.sortTreeInOrder(raf, trainee,binaryTreeToFile);
			for(Trainee t:TraineeDao.sortedTree) {
				System.out.println(t);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return traineeDao.traineeList;
	}	

	@Override
	public void start(Stage primaryStage) throws Exception {


		primaryStage.setTitle("ANNUAIRE EQL");
		primaryStage.setWidth(1600);
		primaryStage.setHeight(1000);

		//3 boxes piled up: user interface, Table view, one simple pane with "export pdf" btn
		Scene scene = new Scene(primaryRoot,1600,1000);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();

		//1st VBox topView=user interface= hBox with GridPane on the left and BorderPane with icon on the right
		BorderPane topViewRight = new BorderPane();
		topViewRight.setStyle("-fx-background-color:papayawhip");
		ImageView iv = new ImageView(getClass().getResource(imagePath).toString());
		topViewRight.setCenter(iv);
		topViewRight.setMinWidth(400);
		topViewRight.setMinHeight(500);


		GridPane topViewLeft = new GridPane();
		topViewLeft.setStyle("-fx-background-color:bisque");
		topViewLeft.setMinHeight(500);
		topViewLeft.setMinWidth(1200);

		Button add = new Button("ADD");
		add.setFont(new Font("Cambria",16));
		add.setStyle("-fx-background-color:peru");
		Button showSorted = new Button("SHOW SORTED LIST");
		showSorted.setFont(new Font("Cambria",16));
		showSorted.setStyle("-fx-background-color:peru");
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
		Label year = new Label("ANNEE");
		year.setFont(new Font("Cambria",16));

		TextField lastNameT = new TextField();
		TextField firstNameT = new TextField();
		TextField zipCodeT = new TextField();
		TextField batchT = new TextField();
		TextField yearT = new TextField();


		topViewLeft.addRow(0,titre);
		topViewLeft.addRow(1, add, lastName, lastNameT);
		topViewLeft.addRow(2, delete, firstName,firstNameT);
		topViewLeft.addRow(3,update, zipCode,zipCodeT);
		topViewLeft.addRow(4, search, batch,batchT);
		topViewLeft.addRow(5, showSorted, year,yearT);
		topViewLeft.addRow(6, help);
		topViewLeft.setHgap(100);;//comment ça marche?
		topViewLeft.setVgap(45);


		topView.getChildren().addAll(topViewLeft,topViewRight);

		//2nd Vbox tableView

		ObservableList<Trainee> observableTrainees = FXCollections.observableArrayList(fileData());
		TableView<Trainee> tableView = getTable(observableTrainees);


		//3rd VBox pane with export pdf btn


		bottomView.setMinHeight(100);
		bottomView.setMinWidth(1600);
		bottomView.setStyle("-fx-background-color:papayawhip");

		Button export = new Button("EXPORT TO PDF");
		export.setFont(new Font("Cambria",16));
		export.setStyle("-fx-background-color:peru");
		bottomView.getChildren().addAll(export);

		primaryRoot.setTop(topView);
		primaryRoot.setCenter(tableView);
		primaryRoot.setBottom(bottomView);


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
		passwordRoot.getChildren().addAll(admin,adminTf);
		Scene passwordScene = new Scene(passwordRoot,300,200);
		passwordStage.setScene(passwordScene);
		passwordStage.setResizable(false);

		//pressing add button

		add.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Trainee newTrainee = new Trainee(lastNameT.getText(),firstNameT.getText(),zipCodeT.getText(),batchT.getText(),Integer.parseInt(yearT.getText()));
				observableTrainees.add(newTrainee);
				TraineeDao.addTraineeInRaf(newTrainee);
			}
		});


		//pressing delete button opens new password window
		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				passwordStage.show();
				//if adminTf == password, set on action, delete Trainee

			}
		});
		//pressing update button opens password window
		update.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				passwordStage.show();
				//if adminTf == password, set on action, update trainee

			}
		});
		//pressing show sorted list button changes TableView
		showSorted.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Trainee trainee = new Trainee();
				TraineeDao traineeDao = new TraineeDao();
				BinaryTreeToFile bf = new BinaryTreeToFile();
				RandomAccessFile raf;
				
				try {
					raf = new RandomAccessFile(destinationPath, "rw");
					traineeDao.sortTreeInOrder(raf, trainee,bf);//
					for(Trainee t:TraineeDao.sortedTree) {
						System.out.println(t);
					}
					ObservableList<Trainee> obs = FXCollections.observableArrayList(TraineeDao.sortedTree);
					refresh(obs);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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