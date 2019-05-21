package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Listas.Esquema;
import Listas.NodoList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ControllerAddRows {
	
    @FXML
    private Text idText;

    @FXML
    private VBox panecolumns;

    @FXML
    private VBox panerows;

    @FXML
    private Button cancel;
    
	private String ID;
	private String fila = " ";
	private String[] columnas;
	private Esquema esquema;
	//listas
	private NodoList<Text> keys = new NodoList<>();
	private NodoList<TextField> spaces = new NodoList<>();
	private NodoList<ChoiceBox<String>> joinspaces = new NodoList<>();
	//nombres de esquemas
	private ArrayList<String> nombres;
	//saber si se guarda.
	private boolean saved = false;
	private Stage prevedit_Stage;	
	private Region veil;
	private ObservableList<String> posiblesids= FXCollections.observableArrayList();

    public static Logger log = LoggerFactory.getLogger(Controller.class);

    //initializer 
    public void initialize(URL url, ResourceBundle rb) throws IOException {
    }
    
	public void getID(String toAdd_id, String[] columnas, Esquema esquema, Region veil, Stage currentStage) {
		this.ID = toAdd_id;
		this.columnas = columnas;
		this.esquema = esquema;
		this.veil = veil;
		this.prevedit_Stage = currentStage;
		this.idText.setText(ID);
		this.nombres = Controller.addNamesxIDOneByOne("nombre");
		this.dibujoIterativo();
	}

	public  void dibujoIterativo() {
		System.out.println("COLUMNAS SIZE "+ columnas.length);
		Text keyid = new Text ("ID  ("+columnas[1]+")");
		TextField spaceid = new TextField ();
		spaceid.setText(this.ID);
		spaceid.setDisable(true);
		spaceid.setPrefHeight(31);
		spaceid.setMaxWidth(230);
		keys.addLast(keyid);
		spaces.addLast(spaceid);
		joinspaces.addLast(new ChoiceBox<String>()); //choice vacio
		//agregar al string fila
    	this.fila = columnas[1]+":"+this.ID;
		//agregar a vbox en pantalla.
		panecolumns.getChildren().add(keyid); //ignorar primer indice 1.
		panerows.getChildren().add(spaceid);
		int tamFila =  2;
		while (tamFila != columnas.length) {
			//crear pareja
			Text key = new Text (columnas[tamFila]);
			if (this.nombres.contains(columnas[tamFila])) {
//				System.out.println("Nombre Join: "+columnas[tamFila]);
				ChoiceBox<String> joinspace = joinChoiceBox(new ChoiceBox<String>(),columnas[tamFila]);
				joinspace.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-background-insets: 1; -fx-background-color: beige;");
				/*agregar a Nodolist correspondiente.*/
				keys.addLast(key);
				joinspaces.addLast(joinspace);
				spaces.addLast(new TextField ()); //textfield vacio
				/*agregar a vbox en pantalla.*/
				panecolumns.getChildren().add(key);
				panerows.getChildren().add(joinspace);
				tamFila++;
				System.out.println(panerows.getChildren().size());
			}else {
				TextField space = new TextField ();
				//modificaciones
				space.setPromptText("write data");
				spaceid.setPrefHeight(31);
				space.setStyle("-fx-background-radius: 15; -fx-border-radius: 15; -fx-background-color: beige;");
				/*agregar a Nodolist correspondiente.*/
				keys.addLast(key);
				spaces.addLast(space);
				joinspaces.addLast(new ChoiceBox<String>()); //choicebox vacio
				/*agregar a vbox en pantalla.*/
				panecolumns.getChildren().add(key);
				panerows.getChildren().add(space);
				tamFila++;
				System.out.println(panerows.getChildren().size());
			}
		}
	}
		
	private ChoiceBox<String> joinChoiceBox(ChoiceBox<String> container, String nombre) {
		/*datos String*/String datos = Controller.cliente.buscartodoslosdatos(Controller.listaEsquemas.buscar(nombre).getNombre()).getDatos();
		/*filas Array*/ArrayList<String[]>filas = new ArrayList<>();
		filas = ControllerEdit.toStringArray(datos.split(";"));
		/*Agregar posibles joins*/
		if (ControllerEdit.numerofilas == 0 ) {
			this.posiblesids.clear();
		}
		for (int id = 0; id < filas.size() ; id++) {
	    	posiblesids.add(filas.get(id)[0]);
		}
		container.setItems(posiblesids);
		container.getSelectionModel().select(0);
		return container;
	}

    public String leerIterativo() {
    	//variables
	    String txj_field="", text=""; String CONSTRUCTOR = "";
	    int i = 1,  index = this.keys.getLargo();
		while (i<index) {
    		text = this.keys.get(i).getText();
    		if (nombres.contains(text)) {
    			txj_field = this.joinspaces.get(i).getSelectionModel().getSelectedItem();
    			CONSTRUCTOR = CONSTRUCTOR+","+text+":"+txj_field;
    			/*prints*/ // DAto1:hotel,Esquema2:liebre
//    			System.out.println("joinfield  >>>>>><<<<< "+txj_field);
//    			System.out.println("CONSTRUCTOR  >>>>>><<<<< "+CONSTRUCTOR);
    			i++; continue;
    		}else {
    			txj_field= this.spaces.get(i).getText();
    			if (txj_field.isEmpty()) { 
	    			UserMessage info = new UserMessage(AlertType.INFORMATION,"\n\r"+"Required to fill all the spaces","Oh Oh..!"); info.showAndWait();
	    		return null;
	    		}else {
	    			CONSTRUCTOR = CONSTRUCTOR+","+text+":"+txj_field;
	    			/*prints*/ // DAto1:hotel,Esquema2:liebre
//	    			System.out.println("textfield  >>>>>><<<<< "+txj_field);
//	    			System.out.println("CONSTRUCTOR  >>>>>><<<<< "+CONSTRUCTOR);
	    			i++; continue;
		}}}
		System.out.println("\nfinal constructor  >>>>>><<<<< "+CONSTRUCTOR);
		this.fila = this.fila + CONSTRUCTOR;
		return "guardado";
    }

	@FXML
    void cancel(ActionEvent event) throws IOException {
		if (!saved) {
			UserMessage message = new UserMessage(AlertType.CONFIRMATION, null, "Are you sure you want to CANCEL the operation?");
			Optional<ButtonType> result = message.showAndWait();
			if ((result.get() == ButtonType.CANCEL)){return;}}
        Stage newedit_Stage = new Stage();
		Parent root;
        FXMLLoader loader;
        loader = new FXMLLoader(getClass().getResource("edit.fxml"));
        root=loader.load();
        newedit_Stage.setTitle("Edit Diagram");
        ControllerEdit controller= loader.getController();
        Scene scene = new Scene(root);
        newedit_Stage.setScene(scene);//me crea una nuevo escenario y me carga todo lo del fxml
        newedit_Stage.setResizable(false);
        newedit_Stage.getIcons().add(new Image("/Media/edit.png"));
		Stage addrow_Stage=(Stage) this.cancel.getScene().getWindow();
		veil.setVisible(false);
		if (this.saved) {
			System.out.println("\nfinal fila  >>>>>><<<<< "+fila);
			if (addRows(addrow_Stage)) {
		        controller.getEsquema(esquema.getNombre());	
		        prevedit_Stage.close();
		        newedit_Stage.show();
			}
		}else {
	        controller.getEsquema(esquema.getNombre());	
	        prevedit_Stage.close();
	        newedit_Stage.show();
			}
    }
	
	private boolean addRows(Stage addrow_Stage) throws IOException {
		addrow_Stage.close();
		String respuesta = Controller.cliente.insertardatos(esquema.getNombre(), this.fila);
		System.out.println("FILAS recibidas  >>>>>><<<<< "+fila);
		System.out.println("Respuesta "+respuesta);
		/*verificar si se inserta*/
		if (respuesta.equals("datos anadidos")) { return true;}
		else if (respuesta.equals("no se puede crear duplicados si tiene indice")){
				UserMessage message = new UserMessage(AlertType.INFORMATION, fila+"\n\n\r"+respuesta, "no se puede crear datos duplicados en las columnas que tienen indice");message.show();
				return false;
			}
		else{ 	//sino solo muestra mensaje y retorna.
			UserMessage message = new UserMessage(AlertType.INFORMATION, fila+"\n\n\r"+respuesta, "Sorry an ERROR has ocurred while adding");message.show(); 
			 return false;}
		}
	

	
	@FXML
    void save(ActionEvent event) throws IOException, NullPointerException {
    	String respuesta = this.leerIterativo();
    	System.out.println(respuesta);
    	try{if (respuesta.equals("guardado")) {
        	this.saved = true;
        	this.cancel(event);
        	}
    	}
    	catch (NullPointerException n) {
    		return;
    	}


    }
	
	
	
	
	
	
	
	
	
	
	
}
