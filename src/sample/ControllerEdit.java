package sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Listas.Esquema;
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
		System.out.println("se llama");
		setCxF(datos);
	}
	
	private void setTexts() {
		this.nameText.setText(esquema.getNombre());
		this.idText.setText(esquema.getID());
		veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8)");
	}
	
	private void setCxF(String datos){
		this.clean();
   		/*SI NO EXISTEN FILAS*/
   		if (datos == null) {
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
	                String respuesta = Controller.cliente.cambiardato(esquema.getNombre(), filas.get(event.getTablePosition().getRow())[1],
	            			columnas[event.getTablePosition().getColumn()], event.getNewValue());
            		if (!respuesta.equals("dato cambiado")) {
            			System.out.println("Respuesta al intento de edicion |>> "+respuesta);
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
        tableview.setStyle("-fx-control-inner-background:  black;");
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
	

	@FXML
	void cancel(ActionEvent event) {
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
	    	UserMessage message = new UserMessage(AlertType.INFORMATION, "\n\r\t"+respuesta, "Sorry..");
	    	message.show();}
    	log.debug("Se logra editar el esquema --> "+ esquema.getNombre());
		this.setEsquema();
    }

    @FXML
	void refresh(ActionEvent event) throws IOException {
		this.setEsquema();
		this.addfield.clear();
	}
	
	
}
