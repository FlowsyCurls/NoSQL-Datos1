package sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Listas.Esquema;
import Listas.ListaString;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
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
    
    @FXML private ChoiceBox<String> structuresBox = new ChoiceBox<String>();

    @FXML  private ChoiceBox<String> structureskey = new ChoiceBox<String>();

	private ObservableList<String> posibleStructures = FXCollections.observableArrayList();
	private ObservableList<String> posibleKeys = FXCollections.observableArrayList();
	private ObservableList<String> posiblesids= FXCollections.observableArrayList(); 
    public ArrayList<String[]> jdata = new ArrayList<String[]>(); //Here is the dat

    //Variables
	public static int numerofilas;
	private static String [] columnas;
	private ArrayList<String[]> filas;
    public  Esquema esquema = new Esquema();
	private static boolean saved = false;
	
    public static Logger log = LoggerFactory.getLogger(ControllerEdit.class);

    
    //initializer 
    public void initialize() throws IOException {
    }
    private void clean() {
    	jdata.clear();
    	posiblesids.clear();
    }
    
	//conexion principal controller
	public void getEsquema(String nombre) throws IOException {
		this.addfield.clear();
		Controller.Cargaresquemas();
		esquema = Controller.listaEsquemas.buscar(nombre);
		System.out.println("Primera vez "+esquema.getNombre());
		/* Obtener cada columna String*/ columnas = esquema.obtenercolumnasparaedit().getStringArraycolumnas(esquema.obtenercolumnasparaedit());
    	/* Obtener cada datos String*/ String datos = Controller.cliente.buscardatosparaedit(esquema.getNombre()).getDatos();
		setTexts();
		setCxF(datos);
	}
	private void setEsquema() throws NullPointerException, IOException {
		Controller.Cargaresquemas();
		esquema = Controller.listaEsquemas.buscar(esquema.getNombre());
		System.out.println("Otras veces "+esquema.getNombre());
		/* Obtener cada columna String*/ columnas = esquema.obtenercolumnasparaedit().getStringArraycolumnas(esquema.obtenercolumnasparaedit());
		/* Obtener cada datos String*/ String datos = Controller.cliente.buscardatosparaedit(esquema.getNombre()).getDatos();
		setTexts();
		System.out.println("se llama setEsquema");
		setCxF(datos);
	}
	
	private void setTexts() {
		this.nameText.setText(esquema.getNombre());
		this.idText.setText(esquema.getID());
        structuresBox.getSelectionModel().selectedItemProperty().addListener( 
        		(ObservableValue<? extends String> observable, String oldValue, String newValue) -> this.updateStructuresKey(esquema.getNombre())); 
        this.posibleStructures.clear();
        this.posibleStructures.addAll("Binary","R-B","B","B+","AVL","AA");
        this.structuresBox.setItems(this.posibleStructures);
		structuresBox.getSelectionModel().select(0); 
		veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8)");
	}
	
	private void setCxF(String datos){
		this.clean();
   		/*SI NO EXISTEN FILAS*/
   		if (datos == null || datos.equals("")) {
   			ControllerEdit.numerofilas = 0;
   	   		filas = new ArrayList<String[]>();
   			showTable();
   			return;
   			}
   		/*SI EXISTEN FILAS*/
   		System.out.println("\nDatos |>> "+datos);
   		datos = Controller.addnumber(datos);   		
   		System.out.println("Enumeracion |>> "+datos);
    	/* Obtener cada fila String[]*/
   		filas = new ArrayList<String[]>();
   		filas = ControllerEdit.toStringArray(datos.split(";"));
   		Controller.printArray(datos.split(";"), "filas ");
   		jdata.addAll(filas);
		showTable();
		this.deleteChoiceBox();
	}
		
	public static ArrayList<String[]> toStringArray(String[] toConvert) {
		ArrayList<String[]> list = new ArrayList<>();
		for (int j=0; j<=toConvert.length-1;j++) {
			list.add(toConvert[j].split(","));
//			System.out.println(list.get(j)[0]);
			}
		return list;
	}
	
	private void showTable() {
		tableview = new TableView<String[]>();
        int size = columnas.length; //number of the columns
        for (int i = 0; i < size; i++) {
        	/*Crear una columna por cada nombre de la lista*/
            TableColumn<String[], String> firstNameCol = new TableColumn<>(columnas[i]);
            int index = i ;

            /*Asignar el tipo de dato que tendra a la fila;*/
            firstNameCol.setCellValueFactory(cellData -> {
                String[] rowData = cellData.getValue();
                if (index >= rowData.length) {return new ReadOnlyStringWrapper("");}
                else {
                	String cellValue = rowData[index];
                    if (columnas[index].equals(esquema.getID())) {
//            			System.out.println("\nC-ID editable "+columnas[index]);
                    	firstNameCol.setEditable(false);
                    	return new ReadOnlyStringWrapper(cellValue);
                    }
                    else if (columnas[index]=="#") {
//            			System.out.println("\nC-# editable"+columnas[index]);
                    	firstNameCol.setEditable(false);
                    	firstNameCol.setMinWidth(50);
                    	firstNameCol.setMaxWidth(70);
                    	return new ReadOnlyStringWrapper(cellValue);
                    }
                    firstNameCol.setMinWidth(150);
                	firstNameCol.setResizable(true);
                	return new ReadOnlyStringWrapper(cellValue);}});
            firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        	/*Asignar propiedad editable a la celda.*/
	            firstNameCol.setOnEditCommit(event -> {
	                String[] row = event.getRowValue();
	                String respuesta = Controller.cliente.cambiardato(esquema.getNombre(), filas.get(event.getTablePosition().getRow())[1],
	            			columnas[event.getTablePosition().getColumn()], event.getNewValue());
            		if (!respuesta.equals("dato cambiado")) {
            			UserMessage message = new UserMessage(AlertType.ERROR, "\n\n\r\t>>"+respuesta, "Respuesta al intento de edicion >> "); message.showAndWait();
//            			System.out.println("\n\n\nRespuesta al intento de edicion |>> "+respuesta);
            			return;}
            		/*cambio visual*/
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
//        tableview.setStyle("-fx-control-inner-background:  black;");
        tableview.setFocusTraversable(false);
		screen.getChildren().add(tableview); 
		log.debug("TableColumns Finished --> "+"table cargado..."); 
	this.tableview.setEditable(true);
	}

	private void deleteChoiceBox() {
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
	
    public void updateStructuresKey(String scheme) {
		Esquema esquema_con_columnas = Controller.listaEsquemas.buscar(scheme);
		ArrayList<String>  keys_columnas = esquema_con_columnas.obtenercolumnasparaedit().getArraycolumnas(esquema_con_columnas.obtenercolumnasparaedit());
		System.out.println("Encontrado "+esquema_con_columnas.getNombre());
    	this.posibleKeys.clear();
   		for (int i = 0; i<keys_columnas.size(); i++ ) {
   			posibleKeys.add(keys_columnas.get(i).split(",")[0]);
		}
		this.structureskey.setItems(posibleKeys);
		structureskey.getSelectionModel().select(0);
    }

	@FXML
	void cancel(ActionEvent event) {
		if (!saved) {
//			UserMessage message = new UserMessage(AlertType.CONFIRMATION, null, "Are you sure you want to CANCEL the operation?");
//			Optional<ButtonType> result = message.showAndWait();
//			if ((result.get() == ButtonType.CANCEL)){return;}
			return;
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
	void edit(ActionEvent event) throws IOException {
		//mensaje de seguridad.
		UserMessage message = new UserMessage(AlertType.CONFIRMATION, "NO","Are you sure you want to edit? \n\rChanges can not be undone!");
		Optional<ButtonType> result = message.showAndWait();
		if ((result.get() == ButtonType.NO)){return;}
		log.debug("Se logra editar el esquema --> "+ esquema.getNombre());
		saved = true;
		cancel(event);
	}
	@FXML
    void editNameText(ActionEvent event) throws NullPointerException, IOException {
		String newname = this.newnameText.getText(); 
		System.out.println("newname: "+newname); 
		if (newname.isEmpty() || (newname.trim().isEmpty())) { this.newnameText.clear(); 
			UserMessage message = new UserMessage(AlertType.INFORMATION, "blank", "Sorry but name can't be");
			message.show();
			return;}
		else { 
			String str = ("Are you sure to change "+esquema.getNombre()+"'s name to "+newname);
			UserMessage message = new UserMessage(AlertType.CONFIRMATION, null, str);
			Optional<ButtonType> result = message.showAndWait();
			if ((result.get() == ButtonType.CANCEL))return;}
			String respuesta = Controller.cliente.cambiarnombreesquema(esquema.getNombre(), newname);
			if (respuesta.equals("Nombre cambiado")) {
				this.newnameText.setPromptText("prev. "+esquema.getNombre());
				this.newnameText.clear();
				this.esquema.setNombre(newname);
				this.setEsquema();
				return;}
			System.out.println(respuesta);	
    }
	@FXML 
    void handleButtonAdd(ActionEvent event) throws IOException {
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
	        controller.getID(toAdd_id, columnas, esquema, veil, currentStage);
	        stage.showAndWait();
	}
	@FXML
    void handleButtonDelete(ActionEvent event) throws NullPointerException, IOException {
    	//casilla de nombre de la fila a eliminar.
			String toDelete_id = deleteBox.getSelectionModel().getSelectedItem();
			String respuesta = Controller.cliente.eliminardatos(esquema.getNombre(), toDelete_id);
			if (!respuesta.equals("datos eliminados")) {
				UserMessage message = new UserMessage(AlertType.INFORMATION, "\n\r\t" + respuesta, "Sorry..");
				message.show();
			}
			log.debug("Se logra editar el esquema --> " + esquema.getNombre());
			this.setEsquema();

    }
	
    @FXML
    void handleButtonAddStructure(ActionEvent event) {
    	String Structure = this.structuresBox.getSelectionModel().getSelectedItem();
    	String Key = this.structureskey.getSelectionModel().getSelectedItem();
    	UserMessage message = new UserMessage(AlertType.INFORMATION, "\n\r"+Structure,"Hey! You can't index two times with \nthe same structure"); 
    	IndiceBoolean tmp = esquema.columnasconindice.buscarindice(Key);
    	System.out.println(esquema.columnasconindice.getLargo());
    	System.out.println("\n--> "+"\n  Estructura: "+Structure+"  \n  Llave: "+Key+"\n");
    	if (!tmp.Noexiste()) {
    		System.out.println("si existe esta columna");
    		if(tmp.tienearbolAA && Structure.equals("AA")) {message.show(); return;}
    		else if(tmp.tienearbolBinario && Structure.equals("Binary")) {message.show(); return;}
    		else if(tmp.tienearbolRB && Structure.equals("R-B")) {message.show(); return;}
    		else if(tmp.tienearbolBPlus && Structure.equals("B+")) {message.show(); return;}
    		else if(tmp.tienearbolB && Structure.equals("B")) {message.show(); return;}
    		else if(tmp.tieneAvl && Structure.equals("AVL")) {message.show(); return;}
    	}
    	if (Structure.equals("Binary")) Structure = "ArbolBinario";
    	else if (Structure.equals("R-B")) Structure = "ArbolRB";
    	else if (Structure.equals("B+")) Structure = "ArbolBPlus";
    	else if (Structure.equals("AA")) Structure = "ArbolAA";
    	else if (Structure.equals("B")) Structure = "ArbolB";
    	else if (Structure.equals("AVL")) Structure = "AVL";
    	System.out.println("\n--> "+"\n  Estructura: "+Structure+"  \n  Llave: "+Key+"\n");
    	String respuesta = Controller.cliente.crearindice(esquema.getNombre(), Key, Structure);
    	System.out.println(respuesta);
    	if (respuesta.equals("indice creado")) {
    		if (Structure.equals("ArbolBinario")) tmp.tienearbolBinario = true;
    		else if (Structure.equals("ArbolRB")) tmp.tienearbolRB = true;
    		else if (Structure.equals("ArbolBPlus")) tmp.tienearbolBPlus = true;
    		else if (Structure.equals("ArbolAA")) tmp.tienearbolAA = true;
    		else if (Structure.equals("ArbolB")) tmp.tienearbolB = true;
    		else if (Structure.equals("AVL")) tmp.tieneAvl = true;
    	}
		this.showTable();
//        	UserMessage dialog = new UserMessage(AlertType.INFORMATION, "\n\r"+Structure+" : "+Key,"Hey! \nIndex successfully created ");
//        	dialog.show();
//    		final Timer timer = new Timer();
//            timer.schedule(new TimerTask() {
//                public void run() {
//                	dialog.close();
//                    timer.cancel(); //this will cancel the timer of the system
//                }
//            }, 1500);
//    	}
    	log.debug("Se logra guardar en la estructura --> "+"Estructura: "+Structure+"  _  Llave: "+Key);

    }

    @FXML
    void handleButtonDeleteStructure(ActionEvent event) {
    	String Structure = this.structuresBox.getSelectionModel().getSelectedItem();
    	String Key = this.structureskey.getSelectionModel().getSelectedItem();
    	UserMessage message = new UserMessage(AlertType.INFORMATION, "\n\r\t"+Key+" -> "+Structure,"Hey! You can't delete something \nnon-existent"); 
    	IndiceBoolean tmp = esquema.columnasconindice.buscarindice(Key);
    	System.out.println(esquema.columnasconindice.getLargo());
    	System.out.println("\n--> "+"\n  Estructura: "+Structure+"  \n  Llave: "+Key+"\n");
    	if (tmp.Noexiste()) {message.show(); return;}
		System.out.println("si existe esta columna");
		//Cambiar la entrada antes, mejor.
    	if (Structure.equals("Binary")) {Structure = "ArbolBinario";}
    	else if (Structure.equals("R-B")) {Structure = "ArbolRB";}
    	else if (Structure.equals("B+")) {Structure = "ArbolBPlus";}
    	else if (Structure.equals("AA")) {Structure = "ArbolAA";}
    	else if (Structure.equals("B")) {Structure = "ArbolB";}
    	else if (Structure.equals("AVL")) {Structure = "AVL";}
    	System.out.println("\n--> "+"\n  Estructura: "+Structure+"  \n  Llave: "+Key+"\n");
    	System.out.println("ahora a ver si tiene el arbol");
    	String respuesta = "";
    	if(tmp.tienearbolAA && Structure.equals("ArbolAA")) {
    		respuesta = Controller.cliente.eliminarindice(esquema.getNombre(), Key, Structure);
		}
		else if(tmp.tienearbolBinario && Structure.equals("ArbolBinario")) {
			respuesta = Controller.cliente.eliminarindice(esquema.getNombre(), Key, Structure);
		}
		else if(tmp.tienearbolRB && Structure.equals("ArbolRB")) {
			respuesta = Controller.cliente.eliminarindice(esquema.getNombre(), Key, Structure);
		}
		else if(tmp.tienearbolBPlus && Structure.equals("ArbolBPlus")) {
			respuesta = Controller.cliente.eliminarindice(esquema.getNombre(), Key, Structure);
		}
		else if(tmp.tienearbolB && Structure.equals("ArbolB")) {
			respuesta = Controller.cliente.eliminarindice(esquema.getNombre(), Key, Structure);
		}
		else if(tmp.tieneAvl && Structure.equals("AVL")) {
			respuesta = Controller.cliente.eliminarindice(esquema.getNombre(), Key, Structure);
		}
    	System.out.println(respuesta);
		System.out.println(Structure);
    	if (respuesta.equals("Indice eliminado")) {
    		if (Structure.equals("ArbolBinario")) tmp.tienearbolBinario = false;
    		else if (Structure.equals("ArbolRB")) tmp.tienearbolRB = false;
    		else if (Structure.equals("ArbolBPlus")) tmp.tienearbolBPlus = false;
    		else if (Structure.equals("ArbolAA")) tmp.tienearbolAA = false;
    		else if (Structure.equals("ArbolB")) tmp.tienearbolB = false;
    		else if (Structure.equals("AVL")) tmp.tieneAvl = false;
    	}
    	this.showTable();
    }
    	
    @FXML
	void refresh(ActionEvent event) throws IOException {
		this.setEsquema();
		this.addfield.clear();
	}
	
	
}
