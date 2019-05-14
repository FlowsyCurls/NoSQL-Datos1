package sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Listas.Esquema;
import Listas.NodoList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
	private String[] columnas;
	private String fila = " ";
	private NodoList<Text> keys = new NodoList<>();
	private NodoList<TextField> spaces = new NodoList<>();
	private ArrayList<String> nombres;

	private Region veil;
	
    public static Logger log = LoggerFactory.getLogger(Controller.class);

	
    //initializer 
    public void initialize() throws IOException {
    }
    
	public void getID(String toAdd_id, String[] strings, ArrayList<String> nombres, Region veil) {
		this.ID = toAdd_id;
		this.idText.setText(ID);
		this.columnas = strings;
		this.nombres =nombres;
		this.dibujoIterativo();
		this.veil = veil;
	}

	public  void dibujoIterativo() {
		int tamFila =  this.columnas.length;
		for (int i=0; i < tamFila ; i++ ){	
			//crear pareja
			Text key = new Text (this.columnas[i]);
			TextField space = new TextField ();
			//modificaciones
			space.setPromptText("write data");
			space.setMinWidth(30);
			/*Validaciones*/
			if (this.nombres.contains(this.columnas[i])) {
				space.setDisable(true);
				space.setPromptText("_");
				if (this.columnas[i].equals(ControllerEdit.e.getID())) {
					space.setPromptText(this.ID);
				}
			}
			else if (this.columnas[i].equals("#")) {
				System.out.println(ControllerEdit.numerofilas);
				space.setPromptText(String.valueOf(ControllerEdit.numerofilas));
				space.setDisable(true);
			}
			//agregar a Nodolist correspondiente.
			keys.addLast(key);
			spaces.addLast(space);
			//agregar a vbox en pantalla.
			panecolumns.getChildren().add(key);
			panerows.getChildren().add(space);
			System.out.println(panerows.getChildren().size());
		}
		
	}


    @FXML
    void cancel(ActionEvent event) throws IOException {
		UserMessage message = new UserMessage(AlertType.CONFIRMATION, null, "Are you sure you want to CANCEL the operation?");
		Optional<ButtonType> result = message.showAndWait();
		if ((result.get() == ButtonType.CANCEL)){return;}
	    FXMLLoader loader;
		loader = new FXMLLoader(getClass().getResource("edit.fxml"));
		ControllerEdit controller= loader.getController();
		Stage currentstage=(Stage) this.cancel.getScene().getWindow();
		veil.setVisible(false);
		currentstage.close();
		controller.addRows(this.fila);
    }

    @FXML
    void save(ActionEvent event) {

    }
	
	public void leerIterativo() {
//		for (i=0; i<index; i++ ){
//    		choosebox= this.children1.get(i).getSelectionModel().getSelectedItem();
//    		System.out.println("\nCHOOSEBOX  >>>>>><<<<< "+choosebox);
//    		text = this.children2.get(i).getText();
//    		System.out.println("\nTEXT >>>>>><<<<< "+text);
//    		num = this.children3.get(i).getText();
//    		System.out.println("\nNUM >>>>>><<<<< "+num);
//    		if (text.isEmpty() || num.isEmpty()) { UserMessage info = new UserMessage(AlertType.INFORMATION,"\n\r"+"Required to fill all the spaces","Oh Oh..!"); info.showAndWait();return;}
//    		System.out.println("\nI  >>>>>><<<<< "+i);
//    		System.out.println("INDEX  >>>>>><<<<< "+index);
//    		System.out.println("COLUMNA  >>>>>><<<<< "+choosebox +" "+ text+" "+num);
//    		CONSTRUCTOR = CONSTRUCTOR+","+text+":"+choosebox+":"+num;
//    		}
	}
	
	
	
	
	
	
	
	
	
	
	
}
