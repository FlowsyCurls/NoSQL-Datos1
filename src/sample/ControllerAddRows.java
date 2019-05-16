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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
	private String[] c;
	private Esquema esquema;
	private NodoList<Text> keys = new NodoList<>();
	private NodoList<TextField> spaces = new NodoList<>();
	private ArrayList<String> nombres;
	private boolean saved = false;
	private Region veil;
	
    public static Logger log = LoggerFactory.getLogger(Controller.class);

    //initializer 
    public void initialize(URL url, ResourceBundle rb) throws IOException {
    }
    
	public void getID(String toAdd_id, String[] columnas, Esquema esquema, Region veil) {
		this.ID = toAdd_id;
		this.c = columnas;
		this.esquema = esquema;
		this.veil = veil;
		this.idText.setText(ID);
		this.nombres = Controller.addNamesxIDOneByOne("nombrexid");
		this.dibujoIterativo();
	}

	public  void dibujoIterativo() {
		int tamFila =  c.length;
		for (int i=0; i < tamFila ; i++ ){	
			//crear pareja
			Text key = new Text (c[i]);
			TextField space = new TextField ();
			//modificaciones
			space.setPromptText("write data");
			space.setMinWidth(30);
			/*Validaciones*/
			if (this.nombres.contains(c[i])) {
				space.setDisable(true);
				space.setPromptText("default");
				if (c[i].equals(esquema.getID())) {
					space.setPromptText(this.ID);
				}
			}
			else if (c[i].equals("#")) {
//				System.out.println(ControllerEdit.numerofilas);
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

    public String leerIterativo() {
	    	int index = this.keys.getLargo();
	    	String textfield="", text="", CONSTRUCTOR=c[1]+":"+this.ID;
//			System.out.println("CANTIDAD  >>>>>><<<<< "+this.panecolumns.getChildren().size());
//			System.out.println("CANTIDAD  >>>>>><<<<< "+this.panerows.getChildren().size());
			int i = 2;
			while (i>index) {
	    		text = this.keys.get(i).getText();
				textfield= this.spaces.get(i).getText();
	    		/*prints*/ // "dato1:perro,dato2:222"
	    		System.out.println("\ntext >>>>>><<<<< "+text);
	    		System.out.println("textfield  >>>>>><<<<< "+textfield);
	    		System.out.println("CONSTRUCTOR  >>>>>><<<<< "+CONSTRUCTOR);
	    		System.out.println("I  >>>>>><<<<< "+i);
	    		System.out.println("INDEX  >>>>>><<<<< "+index);
	    		if (textfield.equals("default")) {
	    			i++;
	    			continue;
	    		}else if (nombres.contains(textfield)) {
	    			i++;
	    			continue;
	    		}else if (textfield.isEmpty() && this.spaces.get(i).getPromptText().isEmpty() ) { 
	    			UserMessage info = new UserMessage(AlertType.INFORMATION,"\n\r"+"Required to fill all the spaces","Oh Oh..!"); info.showAndWait();
	    		return null;
	    		}else {
	    			CONSTRUCTOR = CONSTRUCTOR+","+text+":"+textfield;
	    			i++;
	    		}
			}
	this.fila=CONSTRUCTOR;
	return "guardado";
    }

	@FXML
    void cancel(ActionEvent event) throws IOException {
		if (!saved) {
			UserMessage message = new UserMessage(AlertType.CONFIRMATION, null, "Are you sure you want to CANCEL the operation?");
			Optional<ButtonType> result = message.showAndWait();
			if ((result.get() == ButtonType.CANCEL)){return;}}
	        @SuppressWarnings("unused")
			Parent root;
	        FXMLLoader loader;
	        loader = new FXMLLoader(getClass().getResource("edit.fxml"));
	        root=loader.load();
			Stage currentstage=(Stage) this.cancel.getScene().getWindow();
			veil.setVisible(false);
			if (this.saved) {
				try{
					System.out.println("FILAS  >>>>>><<<<< "+fila);
					ControllerEdit controller= loader.getController();	
					controller.addRows(this.fila, esquema.getNombre());
				} catch (NullPointerException envio){
					envio.printStackTrace();
				}
			}
			currentstage.close();
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
