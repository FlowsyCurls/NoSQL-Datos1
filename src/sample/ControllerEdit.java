package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Errores.DatosUsadosException;
import Listas.Esquema;
import Listas.Nodo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Control;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class ControllerEdit {
	@FXML
	private TableView<String[]> tableview = new TableView<String[]>();
	private ObservableList<String[]> datosObservable = FXCollections.observableArrayList(); 
	
    @FXML private AnchorPane screen = new AnchorPane();
    
    @FXML private Button cancel = new Button();
    
    @FXML public Region veil = new Region();
    
    @FXML private Text idText = new Text();
    
    @FXML private Text nameText = new Text();

    @FXML private ChoiceBox<String> deleteBox = new ChoiceBox<String>();
    
    @FXML private TextField addfield = new TextField();
    
    @FXML private Button deleteButton  = new Button();
    
    @FXML private Button addButton  = new Button();
    
    @FXML private TextField newnameText = new TextField();
    
	private ObservableList<String> posiblesids= FXCollections.observableArrayList(); 

    //Variables
    public ArrayList<String[]> jdata = new ArrayList<String[]>(); //Here is the dat
    public static Logger log = LoggerFactory.getLogger(ControllerEdit.class);
	public static int numerofilas;
    public  Esquema esquema;
    public  String[] columnas_esquema; 
    
	private static boolean saved = false;
	private ArrayList<String[]> filas;
    
    //initializer 
    public void initialize() throws IOException {
    }
    private void clean() {
    	jdata.clear();
    	posiblesids.clear();
    }
    
	//conexion principal controller
	public void getEsquema(String nombre) throws IOException {
		Controller.Cargaresquemas();
		esquema = Controller.listaEsquemas.buscar(nombre);
		System.out.println("vamos denuevo "+esquema.getNombre());
		setCxF();
		setTexts();
	}

	private void setTexts() {
		this.nameText.setText(esquema.getNombre());
		this.idText.setText(esquema.getID());
		veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8)");

	}
	public static void printArray(String[] array, String nombre) { 
		if (array.length==0)return; 
		String str= ""; 
		System.out.print("\n"+nombre+" : "); 
		for (int j=0; j<=array.length-1; j++) { 
			if (str.isEmpty()) {  
				str = array[j].toString(); 
				continue;} 
			str = str +"   +   "+ array[j].toString(); 
			} 
		System.out.println(str); 
		System.out.print("\n"); 
	} 
	
	public void setCxF(){
		this.clean();
		/* Obtener cada columna String*/
    	columnas_esquema = esquema.obtenercolumnas().getStringArraycolumnas(esquema.obtenercolumnas());
    	/* Obtener cada fila String[]*/
   		String datos = Controller.cliente.buscartodoslosdatos(esquema.getNombre()).getDatos();
   		System.out.println("\nDATOS DE BUSCAR TODOS --> "+datos);
   		
   		/*SI NO EXISTEN FILAS*/
   		if (datos == null) {
   			jdata.add(new String[columnas_esquema.length-1]);
   			ControllerEdit.numerofilas = 0;
   			showTable(); 
   			return;
   			}
   		
   		/*SI EXISTEN FILAS*/
   		datos = ControllerEdit.addnumber(datos);
   		System.out.println("DATOS CON NUMERO DE FILAS AGREGADOS --> "+datos+"\n");
   		filas = new ArrayList<String[]>();
   		filas = this.toStringArray(datos.split(";"));
   		printArray(datos.split(";"), "filas ");
   		jdata.addAll(filas);
		showTable();
	}
	public static String addnumber(String sinnumer) {
		System.out.println("\n\n\nSINNUMER: "+sinnumer);
		int remind = -1;
		int cont = 0;
		int last = (sinnumer.split(";").length-1);
		String concate = "";
		for (int s=0; s<=sinnumer.length()-1;s++) {
			
//			System.out.println("remind "+remind);
			
			if (sinnumer.charAt (s) == ';') {
				String substring = sinnumer.substring(remind+1, s);
//				System.out.println("\n\rSubstring "+substring);
				substring = String.valueOf(cont)+","+substring;
//				System.out.println("String "+substring);
				concate = concate+";"+substring;
				if (remind==-1) concate = substring;
//				System.out.println("concate "+concate);
				remind = s;
				cont++;
			}
			else if(cont == last && s==sinnumer.length()-1 && remind==-1) {
				concate = String.valueOf(cont)+","+sinnumer;
				cont++;
			}
			else if (cont == last && s==sinnumer.length()-1) {
				String substring = sinnumer.substring(remind+1);
//				System.out.println("\n\rSubstring "+substring);
				substring = ";"+String.valueOf(cont)+","+substring;
//				System.out.println("String "+substring);
				concate = concate+substring;
				cont++;
//				System.out.println("concate "+concate);
			}
		}
//		System.out.println("concate "+concate);
		ControllerEdit.numerofilas=cont;
		System.out.println("Controller numf "+ControllerEdit.numerofilas);
		return concate;
	}
	private ArrayList<String[]> toStringArray(String[] toConvert) {
		ArrayList<String[]> list = new ArrayList<>();
		for (int j=0; j<=toConvert.length-1;j++) {
			list.add(toConvert[j].split(","));
//			System.out.println(list.get(j)[0]);
		}
		return list;
	
	}private void showTable() {
		tableview = new TableView<String[]>();
        int size = columnas_esquema.length; //number of the columns
        ArrayList<String> nombres = ControllerEdit.addNamesxIDOneByOne();
        for (int i = 0; i < size; i++) {
        	/*Crear una columna por cada nombre de la lista*/
            TableColumn<String[], String> firstNameCol = new TableColumn<>(columnas_esquema[i]);
            int index = i ;
            
            /*Asignar el tipo de dato que tendra a la fila;*/
            firstNameCol.setCellValueFactory(cellData -> {
                String[] rowData = cellData.getValue();
                if (index >= rowData.length) {return new ReadOnlyStringWrapper("");}
                else {
                	String cellValue = rowData[index];
                    if (nombres.contains(columnas_esquema[index])) {
//            			System.out.println("TITULO COLUMNA no editable  "+EsquemaEditadoCOLUMNAS[index]);
                    	firstNameCol.setEditable(false);
//                    	return new ReadOnlyStringWrapper(cellValue);
                    }
                    else if (columnas_esquema[index]=="#") {
//            			System.out.println("TITULO # no editable  "+EsquemaEditadoCOLUMNAS[index]);
                    	firstNameCol.setEditable(false);
                    	firstNameCol.setMinWidth(50);
                    	firstNameCol.setMaxWidth(Control.USE_COMPUTED_SIZE);
                    	return new ReadOnlyStringWrapper(cellValue);
                    }
                    firstNameCol.setMinWidth(150);
                	firstNameCol.setResizable(true);
                	return new ReadOnlyStringWrapper(cellValue);}});
            firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        	/*Asignar propiedad editable a la celda.*/
	            firstNameCol.setOnEditCommit(event -> {
	                String[] row = event.getRowValue();
	                Controller.cliente.cambiardato(esquema.getNombre(), filas.get(event.getTablePosition().getRow())[1],
	            			columnas_esquema[event.getTablePosition().getColumn()], event.getNewValue());
            		//cambio
            		row[index] = event.getNewValue();
   	                return;
	            });
	            firstNameCol.setStyle("-fx-alignment: CENTER;");
	            this.tableview.getColumns().add(firstNameCol);
        }
        datosObservable = FXCollections.observableList(this.jdata);
        tableview.getItems().addAll(this.datosObservable);
        tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
		tableview.prefWidthProperty().bind(screen.widthProperty()); 
        tableview.prefHeightProperty().bind(screen.heightProperty()); 
        tableview.setStyle("-fx-control-inner-background:  black;");
        tableview.setFocusTraversable(false);
		screen.getChildren().add(tableview); 
		log.debug("TableColumns Finished --> "+"table cargado..."); 
	this.tableview.setEditable(true);
	this.deleteChoiceBox();

	
	}private void deleteChoiceBox() {
		if (ControllerEdit.numerofilas == 0 ) {
			this.posiblesids.clear();
			this.deleteBox = new ChoiceBox<String>();
		}
		for (int id = 0; id < filas.size() ; id++) {
	    	posiblesids.add(filas.get(id)[1]);
		}
		this.deleteBox.setItems(posiblesids);
		deleteBox.getSelectionModel().select(0); 
	}
	public static ArrayList<String> addNamesxIDOneByOne() { //cargar todos los diagramas.
		ArrayList<String> nombresxid = new  ArrayList<>(); 
		Nodo<Esquema>tmp = Controller.listaEsquemas.getHead(); 
		while (tmp!=null){ 
			nombresxid.add(tmp.getNodo().getNombre());
			nombresxid.add(tmp.getNodo().getID()); 
//			System.out.println("NODO ESQUEMA "+tmp.getNodo().getNombre());
	    	tmp=tmp.next;}
		return nombresxid;} 

	public void editNameText() {
		String newname = this.newnameText.getText(); 
		System.out.println("newname: "+newname); 
		if (newname.isEmpty() || (newname.trim().isEmpty())) { this.newnameText.clear(); 
			UserMessage message = new UserMessage(AlertType.INFORMATION, "blank", "Sorry but name can't be");
			message.show();
			return;}
		else { String str = ("Are you sure to change "+esquema.getNombre()+"'s name to "+newname);
			UserMessage message = new UserMessage(AlertType.CONFIRMATION, null, str);
			Optional<ButtonType> result = message.showAndWait();
			if ((result.get() == ButtonType.CANCEL))return;}
			String respuesta = Controller.cliente.cambiarnombreesquema(esquema.getNombre(), newname);
				if (respuesta == "Nombre cambiado") {
					this.newnameText.setPromptText(newname);
					saved = true; System.out.print(respuesta);
					return;}
	}
	public void addRows(String fila, String nombre) throws IOException {
		this.initialize();
		System.out.println(nombre);
		String respuesta = Controller.cliente.insertardatos(nombre, fila);
		System.out.println("FILAS recibidas  >>>>>><<<<< "+fila);
		System.out.println("Respuesta "+respuesta);
		//sino solo muestra mensaje y retorna.
		if (respuesta == null) {
			UserMessage message = new UserMessage(AlertType.INFORMATION, fila, "Sorry an ERROR has ocurred while adding ");message.show(); 
			this.addfield.clear(); return;
		}
		this.getEsquema(nombre);
		}

	@FXML 
    public void handleButtonAdd(ActionEvent event) throws IOException {
		//casilla de nombre de la fila a eliminar.
		String toAdd_id = addfield.getText();
		if (toAdd_id.isEmpty())return;
		/*verificar si aparece dentro de las filas*/
		int apariciones = 0;
		for (int id = 0; id < filas.size(); id++) { 
			System.out.println(filas.get(id)[1]);
			if (filas.get(id)[1].equals(toAdd_id)) apariciones++;}
		/*ver si aparece o que*/
		if (apariciones != 0) { UserMessage message = new UserMessage(AlertType.INFORMATION, "has been used." +"\n\rEach person is unique and unrepeatable, \rthe same applies to your IDs ",  "Hmm' sorry bruh! "+ toAdd_id); message.show(); this.addfield.clear(); return;}

	        FXMLLoader loader = new FXMLLoader(getClass().getResource("addRows.fxml"));
	        Parent root1 = (Parent) loader.load();
	        Stage stage = new Stage();
	        Stage currentStage = (Stage) this.addButton.getScene().getWindow();
	        stage.initOwner(currentStage);
	        stage.initModality(Modality.APPLICATION_MODAL);
	        stage.initStyle(StageStyle.UNDECORATED);
	        stage.setResizable(false);
	        stage.setScene(new Scene(root1));
	        this.veil.setVisible(true);
	        ControllerAddRows controller= loader.getController();
	        controller.getID(toAdd_id, columnas_esquema, esquema, veil);
	        stage.showAndWait();
	}
	@FXML
    public void handleButtonDelete(ActionEvent event) throws DatosUsadosException {
    	//casilla de nombre de la fila a eliminar.
    	String toDelete_id = deleteBox.getSelectionModel().getSelectedItem();
    	String respuesta = Controller.cliente.eliminardatos(esquema.getNombre(), toDelete_id);
		if (!respuesta.equals("datos elimandos")) { 
	    	UserMessage message = new UserMessage(AlertType.INFORMATION, "\n\r\t"+respuesta, "Sorry..");
	    	message.show();
    	log.debug("Se logra editar el esquema --> "+ esquema.getNombre());
		this.setCxF();
		}
    }



    @FXML
	public void cancel(ActionEvent event) {
		if (!saved) {
			UserMessage message = new UserMessage(AlertType.CONFIRMATION, null, "Are you sure you want to CANCEL the operation?");
			Optional<ButtonType> result = message.showAndWait();
			if ((result.get() == ButtonType.CANCEL)){return;}
		}
		try {
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
    public void edit(ActionEvent event) throws IOException {
    	//mensaje de seguridad.
    	UserMessage message = new UserMessage(AlertType.CONFIRMATION, "NO","Are you sure you want to edit? \n\rChanges can not be undone!");
		Optional<ButtonType> result = message.showAndWait();
		if ((result.get() == ButtonType.NO)){return;}
    	log.debug("Se logra editar el esquema --> "+ esquema.getNombre());
    	saved = true;
    	cancel(event);
    }
    
	@FXML
	public void refresh(ActionEvent event) throws IOException {
		this.setCxF();
		this.addfield.clear();
	}
	
	
}
