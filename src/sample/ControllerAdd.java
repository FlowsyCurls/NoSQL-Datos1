package sample;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class ControllerAdd {

	//fxml
    @FXML private AnchorPane screen = new AnchorPane();
    @FXML private Button cancel = new Button();
    @FXML private Label columnslabel = new Label();
    @FXML private ScrollPane scrollpane = new ScrollPane();
    @FXML private ChoiceBox<String> choicebox = new ChoiceBox<String>(); 
	private ObservableList<String> types = FXCollections.observableArrayList(); 

	//variables
	private int posx = 5, posy = 5, columns=0;
	private boolean typessetted = false;
	
	//statics
    public static Logger log = LoggerFactory.getLogger(ControllerAdd.class);
 
    
    
    //initializer 
    public void initialize() throws IOException {
    	int columnasrecibidasdelotrocontroller = 5;
		this.drawing(columnasrecibidasdelotrocontroller);
    }
    public void drawing(int columns) throws IOException {
		if (this.columns==0) {
			this.columns=columns;
			this.drawing(this.columns);
//			this.scrollpane.setContent(screen);
		    this.scrollpane.setVbarPolicy(ScrollBarPolicy.NEVER);
		    this.scrollpane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		    this.scrollpane.setPannable(true);
		    return;}
		while (columns!=0) {
			this.setAll();
			columns--;
		}this.setlabel();
	}
	@FXML
	public void addcolumn(ActionEvent event) throws IOException {
		this.drawing(1); this.columns++;
		this.setlabel();
		this.scrollpane.setHvalue((Double) this.screen.getPrefWidth() );
	}
	private void setlabel() {this.columnslabel.setText(""+this.columns);}
	private final void setAll() {
		TextField key = new TextField ();
		key.setTranslateX(posx+110); key.setTranslateY(posy); key.setMaxWidth(145); key.setPromptText("set key");
		//choicekey
		choicebox = new ChoiceBox<String>();
		choicebox.setTranslateX(posx); choicebox.setTranslateY(posy);
		if (!this.typessetted) {
	    	types.addAll("STRING","INT","DOUBLE","LONG","FLOAT");
			this.typessetted = true;}
		choicebox.setItems(types);
		choicebox.getSelectionModel().select(0);
		screen.getChildren().addAll(choicebox, key);
		this.posy+=49;
		if (this.posy+20 > this.screen.getPrefHeight()) {
			this.posx+=285; this.posy=5;
			if (this.posx>923) {
				this.screen.setPrefWidth(this.posx+270);
			}
		}
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
    	int index = this.screen.getChildren().size();
    	for ( int i=0; i<index; i++ ){
    		Node child = this.screen.getChildren().get(i);
    		System.out.println(child);
    		
    	}clean();
    }
    private void clean() {
    	this.types.clear();
    	this.screen.getChildren().clear();
    }
}
