package sample;

import java.io.IOException;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Errores.DatosUsadosException;
import Errores.EsquemaNuloException;
import Listas.Esquema;
import Listas.NodoList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ControllerAdd<T> {

	//fxml
    @FXML private AnchorPane screen = new AnchorPane();
    @FXML private Button cancel = new Button();
    @FXML private Label columnslabel = new Label();
    @FXML private ScrollPane scrollpane = new ScrollPane();
    @FXML private ChoiceBox<String> choicebox = new ChoiceBox<String>(), idbox = new ChoiceBox<String>();
    @FXML private TextField idnum = new TextField();
    @FXML private VBox radiusVBox = new VBox();
    @FXML private TextField id = new TextField();
    @FXML private TextField name = new TextField();
    @FXML private RadioButton b1,b2,b3,b4,b5;
    @FXML private ToggleGroup togglegroup = new ToggleGroup();

	private ObservableList<String> types = FXCollections.observableArrayList(); 

	//variables
	private int posx = 5, posy = 5, columns=0;
	private boolean typessetted =false;
	private NodoList<ChoiceBox<String>> children1 = new NodoList<ChoiceBox<String>>();
	private NodoList<TextField> children2 = new NodoList<TextField>(), children3 = new NodoList<TextField>();
	private boolean saved = false;
	private Cliente cliente = new Cliente();
	//statics
    public static Logger log = LoggerFactory.getLogger(ControllerAdd.class);
 
    
    
    //initializer 
    public void initialize() throws IOException {
    }
    public void drawing(int columns, String selectedType) throws IOException {
		if (this.columns==0) {
			this.columns=columns;
			this.drawing(this.columns, null);
//			this.scrollpane.setContent(screen);
		    this.scrollpane.setVbarPolicy(ScrollBarPolicy.NEVER);
		    this.scrollpane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
		    this.scrollpane.setPannable(true);
		    return;}
		while (columns!=0) {
			this.setAll(selectedType);
			columns--;
		}this.setlabel();
	}
    
	@FXML
	public void addcolumn(ActionEvent event) throws IOException {
		RadioButton selectedRadioButton = (RadioButton) togglegroup.getSelectedToggle();
		String selectedType = selectedRadioButton.getText();
		System.out.println(selectedType);
		this.drawing(1,selectedType); this.columns++;
		this.setlabel();
		this.scrollpane.setHvalue((Double) this.screen.getPrefWidth() );
	}
	private void setlabel() {this.columnslabel.setText(""+this.columns);}
	private final void setAll(String selectedType) {
		TextField key = new TextField (), len = new TextField ();
		key.setTranslateX(posx+107); key.setTranslateY(posy); key.setMaxWidth(145); key.setPromptText("set key "+(Controller.counter++));
		len.setTranslateX(posx+253); len.setTranslateY(posy); len.setMinWidth(30); 
		len.setPrefWidth(Control.USE_COMPUTED_SIZE); len.setMaxWidth(50); len.setPromptText("size");
		//choicekey
		choicebox = new ChoiceBox<String>();
		choicebox.setTranslateX(posx); choicebox.setTranslateY(posy);
		if (!this.typessetted) {
	    	types.addAll("STRING","INTEGER","DOUBLE","LONG","FLOAT");
			this.typessetted = true;}
		choicebox.setItems(types); 
		this.idbox.setItems(types);
		choicebox.getSelectionModel().select(0); 
		this.idbox.getSelectionModel().select(0);
		screen.getChildren().addAll(choicebox, key, len);
		this.posy+=49;
		
		if (selectedType!=null) choicebox.getSelectionModel().select(selectedType);
		/*Styling*/
		choicebox.setStyle("-fx-control-inner-background:  lightgray; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: black; -fx-background-insets: 1; -fx-focus-color: transparent;");
		len.setStyle("-fx-background-color:  beige; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: black; -fx-focus-color: transparent;");
		key.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: black; -fx-focus-color: transparent;");
		/*add to the lists*/
		children1.addLast(choicebox);
		children2.addLast(key);
		children3.addLast(len);
		if (this.posy+20 > this.screen.getPrefHeight()) {
			this.posx+=330; this.posy=5;
			if (this.posx>923) {
				this.screen.setPrefWidth(this.posx+315);
			}
		}
	}
	
    @FXML
    public void cancel(ActionEvent event) {
		if (!this.saved) {
			UserMessage message = new UserMessage(AlertType.CONFIRMATION, null, "Are you sure you want to CANCEL the operation?");
			Optional<ButtonType> result = message.showAndWait();
			if ((result.get() == ButtonType.CANCEL)){return;}
		}
    	try {
    		clean();
 	    	Stage sampleStage = new Stage();
 	        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
 	        Scene scene = new Scene(root);
 	        scene.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent esc) -> {
 	            if (KeyCode.ESCAPE == esc.getCode()) {sampleStage.close();}});
 	        sampleStage.setTitle("NOSQL");
 	        sampleStage.setScene(scene);//me crea una nuevo escenario y me carga todo lo del fxml
 	        sampleStage.getIcons().add(new Image("/Media/nosql.png"));
 	        sampleStage.show();
 	        Stage currentStage = (Stage) this.cancel.getScene().getWindow();
 	        this.clean();
 	        currentStage.close();
 	        log.debug("Logra abrir ventana otra vez");
 	    }catch (IOException e) {
 	        System.out.println("Al abrir nuevamente ocurrio esto : "+e); e.printStackTrace();}
    }
    
    
    @FXML
    public void submit(ActionEvent event) throws IOException {
    	/*Primero el ID y el NOMBRE*/
    	String ID = this.id.getText();
    	String NAME = this.name.getText();
		String idchoose = this.idbox.getSelectionModel().getSelectedItem();
		String idlen = this.idnum.getText();
    	//AGREGAR.... all al STRING CON QUE SE LLAMA EL CONSTRUCTOR.
    	String CONSTRUCTOR = NAME+","+ID+":"+idchoose+":"+idlen;
    	/*Segundo guardar y verificar las columnas.*/
    	int index = this.children1.getLargo();
    	String choosebox=null, text=null, num=null; int i;	
    	for (i=1; i<index; i++ ){
    		choosebox= this.children1.get(i).getSelectionModel().getSelectedItem();
    		text = this.children2.get(i).getText();
    		if (text.isEmpty()) { UserMessage info = new UserMessage(AlertType.INFORMATION,"\n\r"+"Required to fill all the spaces","Oh Oh..!"); info.showAndWait();return;}
    		num = this.children3.get(i).getText();
    		System.out.println(choosebox +" "+ text+" "+num);
    		CONSTRUCTOR = CONSTRUCTOR+","+text+":"+choosebox+":"+num;}
		String respuesta = this.cliente.crearEsquema(CONSTRUCTOR);
		System.out.println(CONSTRUCTOR);
		System.out.println(respuesta);
		String message = "";
		if ((respuesta.equals("esquema creado"))) { 
			UserMessage info = new UserMessage(AlertType.CONFIRMATION,"NO","Woohoo..! \r\tScheme succesfully created! \n\n\rDo you want to build another one?");
			Optional<ButtonType> result = info.showAndWait();
			if ((result.get() == ButtonType.YES)){
				int tmp = this.columns;
				this.clean(); 
				this.drawing(tmp, null);return;}
			this.saved = true;
			this.cancel(event);
			return;
		}else{
			if ((respuesta.equals("nombre ya utilizado"))) { message="This name is already in use."; }
			else if ((respuesta.equals("Hay columnas repetidas."))) { message="There are repeated columns."; }
			else if ((respuesta.equals("el tamano solo recibe enteros"))) { message="Size only can be integers."; }
			else if ((respuesta.equals("No existe esquema de join indicado"))) { message="There is no join scheme indicated."; }
			else if ((respuesta.equals("esquema usado"))) { message = "Sorry bruh..! \nThis chart is already in use..."; }
			UserMessage info = new UserMessage(AlertType.INFORMATION,"\n\r"+message,"Sorry bruh..!"); info.showAndWait();return;}
    	}
    	
    
    private void clean() {
    	this.types.clear();
    	Controller.counter = 1;
		this.typessetted=false;
    	this.screen.getChildren().clear();
    	this.posx=5; this.posy=5; this.columns = 0; 
    }
}
;