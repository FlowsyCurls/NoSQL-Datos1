package sample;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class ControllerAdd {
	
    @FXML // fx:id="choicebox" 
    private ChoiceBox<String> choicebox = new ChoiceBox<String>(); 
	private ObservableList<String> types = FXCollections.observableArrayList(); 
	int posx = 25, posy = 40, columns=0;
    @FXML
    private AnchorPane screen = new AnchorPane();
    @FXML
    private Button cancel = new Button();
    @FXML
    private Label columnslabel = new Label();
	private boolean typessetted = false;
    
    public static Logger log = LoggerFactory.getLogger(ControllerAdd.class);
 
    //initializer 
    public void initialize() throws IOException {
    	int columnasrecibidasdelotrocontroller = 10;
		this.drawing(columnasrecibidasdelotrocontroller);
		this.setlabel();
    }
    private void doTable() {
//		TableView<Esquema> table = new TableView<Esquema>();
//		for (int i=0; i<arrayList.size(); i++){
//			TableColumn<Esquema, String> a = new TableColumn<> (nombrecolumna);
//			a.setMinWidth(200);//a.setMaxWidth(350);
//			System.out.println("doooooooooo "+nombrecolumna);
//			a.setCellValueFactory(new PropertyValueFactory<Esquema, String>(nombrecolumna));
//			table.getColumns().add(a);
//			table.setEditable(true);}
//		//drawing
//		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//		table.prefWidthProperty().bind(screen.widthProperty());
//		table.prefHeightProperty().bind(screen.heightProperty());  
//		screen.getChildren().add(table); 
//		log.debug("TableColumns Finished --> "+"sale del for"); 	
    }
    
    public void drawing(int columns) throws IOException {
    	if (this.columns==0) {
    		this.columns=columns;
    		this.setChoiceBoxWithLabel(true);
    		this.drawing(this.columns);
    		}
		while (columns!=0) {
			this.setChoiceBoxWithLabel(false);
			columns--;
		}
    }
    
    private final void setChoiceBoxWithLabel(boolean name) {
		if (name) {
			//nombre
			Label nombrelab= new Label(),idlab= new Label();
			TextField nombretext=new TextField(), idtext=new TextField();
			nombrelab.setTranslateX(posx); nombrelab.setTranslateY(posy);  nombrelab.setText("D's Name"); 
			nombrelab.setTextFill(Color.BLACK); nombrelab.setFont(new Font("Berlin Sans FB Demi", 21));
			nombrelab.setStyle("-fx-font-weight: bold");
			nombretext.setTranslateX(posx+110); nombretext.setTranslateY(posy); nombretext.setMaxWidth(145); nombretext.setPromptText("diagram's name");
			this.posy+=40;
			//id
			idlab.setTranslateX(posx); idlab.setTranslateY(posy);  idlab.setText("D's ID"); 
			idlab.setTextFill(Color.BLACK); idlab.setFont(new Font("Berlin Sans FB Demi", 21));
			idlab.setStyle("-fx-font-weight: bold");
			idtext.setTranslateX(posx+110); idtext.setTranslateY(posy); idtext.setMaxWidth(145); idtext.setPromptText("diagram's id");
			this.posy+=40;
			//add id y nombre
			screen.getChildren().addAll(nombretext,nombrelab,idlab,idtext);
			
		}
		else {
	    	//text
			TextField key = new TextField ();
			key.setTranslateX(posx+110); key.setTranslateY(posy); key.setMaxWidth(145); key.setPromptText("set key");
			//choicekey
			choicebox = new ChoiceBox<String>();
			choicebox.setTranslateX(posx); choicebox.setTranslateY(posy);
			if (!this.typessetted) {
		    	types.addAll("STRING","INT","DOUBLE","LONG","FLOAT");
				this.typessetted = true;
			}
			choicebox.setItems(types);
			choicebox.getSelectionModel().select(0);
			screen.getChildren().addAll(choicebox, key);
			this.posy+=40;
			if (this.posy+100 > 760) {
				this.posx+=285; this.posy=40;
			}
		}
	}
	@FXML
    public void addcolumn(ActionEvent event) throws IOException {
    	this.drawing(1); this.columns++;
    	this.setlabel();
    }
    public void setlabel() {
		this.columnslabel.setText(""+this.columns);
    }

    @FXML
    public void cancel(ActionEvent event) {
	    try {
	        Stage sampleStage = new Stage();
	        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//	        Controller controller= loader.getController();
	        sampleStage.setTitle("NOSQL");
	        sampleStage.setScene(new Scene(root));//me crea una nuevo escenario y me carga todo lo del fxml
	        sampleStage.getIcons().add(new Image("/Media/nosql.png"));
	        sampleStage.show();
	        Stage base = (Stage) this.cancel.getScene().getWindow();
	        Stage addStage = base;
	        addStage.close();
	      
	        log.debug("Logra abrir ventana otra vez");
	    } catch (IOException e) {
	        System.out.println("Al abrir nuevamente ocurrio esto : "+e);//e.printStackTrace();
	    }
	}

    @FXML
    public void submit(ActionEvent event) {

    }
}
