package sample; 
 
import java.io.DataInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import Listas.Esquema;
import Listas.ListaEsquemas;
import Listas.ListaString;
import Listas.Nodo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Controller { 
	
	public static ListaEsquemas listaEsquemas =new ListaEsquemas();
//	private ArrayList<String> array = new ArrayList<String>(); 
	 
	//FXML// 
	@FXML // fx:id="base" 
	public  AnchorPane baseSample= new AnchorPane(); 
	@FXML // fx:id="diagramsList" 
	private ListView<String> diagramsList = new ListView<String>(); 
	private ObservableList<String> diagrams = FXCollections.observableArrayList(); 
	@FXML //fx:id="tableview"
	private TableView<String[]> tableview = new TableView<String[]>();
	private ObservableList<String[]> datosObservable;
	@FXML // fx:id="screen" 
	private AnchorPane screen = new AnchorPane();
    @FXML // fx:id="textfieldFilas"
    private ChoiceBox<String> choiceboxEdit = new ChoiceBox<String>(); 
	private ObservableList<String> availableType = FXCollections.observableArrayList();
    @FXML
    private ChoiceBox<String> choiceboxSearch = new ChoiceBox<String>(); 
	private ObservableList<String> availableChoices = FXCollections.observableArrayList(); 
	@FXML private ScrollPane scrollpane = new ScrollPane();
    @FXML private TextField textfieldFilas;
    @FXML private TextField textfieldEdit;
    public ArrayList<String[]> jdata = new ArrayList<String[]>(); //Here is the dat
    private String[] c;
    @FXML // fx:id="search" 
    private TextField searchSTR =  new TextField(); 
    //FMLX nothingMessage 
	private final StackPane stack = new StackPane(); 
	private final Label nothing =new Label(); 
	//objextmapper 
    private ObjectMapper objectMapper=new ObjectMapper(); 
 
    public static Logger log = LoggerFactory.getLogger(Controller.class);
	private Cliente cliente = new Cliente();
 
    //initializer 
    public void initialize() throws IOException {//URL location, ResourceBundle resouces) { 
    	//cargar esquemas del servidor.
    	try {
    		Datos Datos = this.cliente.acciones(this.cliente, null, "recibiresquemas", null, null, null);
    		System.out.println(Datos.getConstructores().getHead().getNodo());
    		if (!Datos.getRespuesta().equals("constructores enviados")) {
    			System.out.println("NO CARGOOOOOO MIIIIIIIEEEEERRRRRDAAAA"); 
    			return;}
    		Nodo<String> tmp = Datos.getConstructores().getHead();
			while (tmp!=null){ 
    			this.listaEsquemas.addLast(new Esquema(tmp.getNodo(), true));
    			System.out.println("Cargado Esquema ||| "+tmp.getNodo());
		    	tmp=tmp.next;} 
    		this.setChoiceBoxEdit();
    		this.loadDiagrams(null);
    	} catch (IOException m) { System.out.println("NO FUNCOOOOO MIIIIIIIEEEEERRRRRDAAAA");
    		m.printStackTrace();
    		log.debug("error"); return;} }
    
    @FXML  //evento de anadir 
	public void addButtonAction(ActionEvent event) throws IOException { 
    	String inputString = this.textfieldFilas.getText();
    	if (inputString.isEmpty()) {this.textfieldFilas.clear(); return;}
    	else if (!this.TryParse(inputString)) {
    		UserMessage message = new UserMessage(AlertType.ERROR,
    				inputString,"Data entered is not a integer number");
    		message.showAndWait();
    		this.textfieldFilas.clear();
            return;}
    	this.textfieldFilas.clear();
		int inputInt = Integer.parseInt(inputString);
		if (inputInt==0) return;
    	this.addWindow(inputInt);
	}private  boolean TryParse(String cadena){
		try {
			Integer.parseInt(cadena);
			return true;
			} 
		catch (NumberFormatException e) {
			return false;}
	}private void addWindow(int columns) {//cambia a la pantalla de fin del juego
	    try {
	        Stage addStage = new Stage();
	        Parent root;
	        FXMLLoader loader;
	        loader = new FXMLLoader(getClass().getResource("add.fxml"));
	        root=loader.load();
	        addStage.setTitle("Add a Diagram");
	        ControllerAdd controller= loader.getController();
	        controller.drawing(columns);
	        Scene scene = new Scene(root,1200,800);
	        scene.addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
	            if (KeyCode.ESCAPE == event.getCode()) {addStage.close();}});
	        addStage.setScene(scene);//me crea una nuevo escenario y me carga todo lo del fxml
	        addStage.setResizable(false);
	        addStage.getIcons().add(new Image("/Media/save.png"));
	        addStage.show();
//	        addStage.initModality(Modality.WINDOW_MODAL);
	        Stage currentstage=(Stage) this.baseSample.getScene().getWindow();
	        currentstage.close();
	    } catch (IOException e) {
	        e.printStackTrace();}}
	
	@FXML //evento de editar esquemas 
	public void editButtonAction(ActionEvent event) throws IOException { 
    	String detail = this.textfieldEdit.getText();
    	if (detail.isEmpty())return;
    	String selectedChoice = this.choiceboxEdit.getSelectionModel().getSelectedItem();
    	System.out.println("detail: "+detail);
    	System.out.println("selectedChoice: "+selectedChoice);
    	if (selectedChoice == "ID") {return;}
    	else if (selectedChoice == "Name") {
    		this.listaEsquemas.buscar(selectedChoice);
    		return;}
    	else if (selectedChoice == "Index") {
    		if (!this.TryParse(detail)) {
    			UserMessage message = new UserMessage(AlertType.ERROR,
    					detail,"The written index is not a whole number");
    			message.showAndWait();
    			this.textfieldEdit.clear();
    			return;}
    		int inputInt = Integer.parseInt(detail);
    		Esquema e = this.listaEsquemas.buscar(inputInt);
    		if (e==null) {
    			UserMessage message = new UserMessage(AlertType.INFORMATION,
    					detail,"Your diagram was not found");
    			message.showAndWait();
    			return;}
        	this.editWindow(e);
    		System.out.println("esquema: "+e.getNombre()+" ,id: "+e.getID());
    		return;}
	}private void setChoiceBoxEdit() { //setChoiceBoxEdit de editbuttonaction.
//		this.choiceboxEdit.getItems().clear();
    	this.availableType.addAll("ID","Name","Index");
		this.choiceboxEdit.setItems(availableType); 
		this.choiceboxEdit.getSelectionModel().select(2);
		//de una vez agregamos la opcion buscar esquema especifico a setChoiceSearch
//		this.availableChoices.add("Other..."); this.choiceboxSearch.setItems(availableChoices); 
//		this.choiceboxSearch.getSelectionModel().select(0);
	}private void editWindow(Esquema e) {
	    try {
	        Stage editStage = new Stage();
	        Parent root;
	        FXMLLoader loader;
	        loader = new FXMLLoader(getClass().getResource("edit.fxml"));
	        root=loader.load();
	        editStage.setTitle("Edit Diagram");
	        ControllerEdit controller= loader.getController();
	        controller.getEsquema(e, this.cliente);
	        Scene scene = new Scene(root,1200,800);
	        editStage.setScene(scene);//me crea una nuevo escenario y me carga todo lo del fxml
	        editStage.setResizable(false);
	        editStage.getIcons().add(new Image("/Media/edit.png"));
	        editStage.show();
	        Stage currentstage=(Stage) this.baseSample.getScene().getWindow();
	        currentstage.close();
	    } catch (IOException error) {
	    	error.printStackTrace();}}

	@FXML //evento de buscar esquemas 
    public void searchButtonAction(ActionEvent event) throws IOException { 
    	String detail = searchSTR.getText();
    	System.out.println("detail: "+detail); 
    	if (detail.isEmpty() || (detail.trim().isEmpty())){
    		detail=null; this.searchSTR.clear();
    		System.out.println("Escrito en detail --->>  "+detail);
    	}
    	//analizando escogencia....
    	String selectedChoice = this.choiceboxSearch.getSelectionModel().getSelectedItem(); 
    	System.out.println("Seleccionado en el Choices --->> "+selectedChoice); 
    	if (selectedChoice.equals("Other...") || selectedChoice==null){
    		this.loadDiagrams(detail);
    		this.searchSTR.clear();
    		return;}
    	else {
    		//pero para los demas se necesita el esquema so..
			Esquema usedDiagram = this.listaEsquemas.buscar(searchSTR.getPromptText());
			
			//variables locales
			String respuesta = null;
			ArrayList<String[]> filasbuscadas = new ArrayList<String[]> ();;
					
			//buscando filas con id...
			if (selectedChoice.equals("ID("+usedDiagram.getNombre()+")")) {
				respuesta = this.cliente.acciones(cliente, this.listaEsquemas.buscar(searchSTR.getPromptText()), "buscarID", null, null, 0);
				
				/*codigo para agregar cada 
				 * fila que sera string[]
				 *  de coincidencia en un
				 *   ArrayList<String[]>*/
				filasbuscadas= new ArrayList<String[]> ();
			}

			//buscando con nombres...
			else if (selectedChoice.equals("Name")) {
				respuesta = this.cliente.acciones(cliente, this.listaEsquemas.buscar(searchSTR.getPromptText()), "buscardatos", null, null, 0);
				
				/*codigo para agregar cada
				 *  fila que sera string[]
				 *  de coincidencia en un
				 *  ArrayList<String[]>*/
				filasbuscadas= new ArrayList<String[]> ();
			}
			
			//buscando con indices...
			else if (selectedChoice.equals("Index")) {
				/* pen
				 * dien
				 * te*/
				
			}
			
			//buscando con joins...
			else if (selectedChoice.equals("Joins")) {
				respuesta = this.cliente.acciones(cliente, this.listaEsquemas.buscar(searchSTR.getPromptText()), "buscardatosjoin", detail, null, 0);
				
				/*codigo para agregar cada
				 *  fila que sera string[]
				 *  de coincidencia en un
				 *   ArrayList<String[]>*/
				filasbuscadas= new ArrayList<String[]> ();
			}
			
			else {
				System.out.println("NO ESTA AUN VALIDADA LA ENTRADA DE ESTE PARAMETRO "+selectedChoice);
			}
			//verificar que se haya hecho el proceso de busqueda y conexion.
			if (respuesta!="datos enviados") {this.messenger("ERROR while searching ", "Try again later!");return;}
			//verificar que por lo menos haya algun coincidencia.
			if (filasbuscadas.isEmpty()) {this.messenger("No matches for ", detail); return;}
			this.setCxF(usedDiagram, true, filasbuscadas, detail);
			return;}
	}private void messenger(String content, String detail) {
		UserMessage message = new UserMessage(AlertType.INFORMATION,
				detail,content);
		message.showAndWait();
	}private void loadDiagrams(String detail) throws IOException { //loadDiagramas. 
    	this.diagrams.removeAll(diagrams);
    	if (detail!=null) { // si estoy buscando.... 
    		this.loadDiagrams_buscaraux(detail); 
			System.out.println("DIAGRAMAS cargados "+diagrams); 
			this.diagramsList.setItems(diagrams); 
			return;} 
    	this.addNamesOneByOne(); 
		System.out.println("DIAGRAMAS cargados "+diagrams); 
	    this.diagramsList.setItems(diagrams);
    }private void loadDiagrams_buscaraux(String detail) { //buscar coincidencias para cargarlas. 
//		System.out.println("Entra en buscar"); 
//		System.out.println(this.listaEsquemas.getLargo()); 
    	ArrayList<Esquema> coincided = this.listaEsquemas.buscarcoincidencias(detail); 
    	if (coincided.isEmpty()) return; 
    	for (int i=0; i<coincided.size(); i++){ 
    		Esquema element =coincided.get(i); 
			System.out.println("ESQUEMA encontrado :"+" |name "+element.getNombre()+" |id "+element.getID()); 
    		this.diagrams.add(element.getNombre()); //add coincidences... 
    	}coincided.removeAll(coincided); 
    }private void addNamesOneByOne() { //cargar todos los diagramas. 
			Nodo<Esquema>tmp = this.listaEsquemas.getHead(); 
			System.out.println("NODO ESQUEMA "+tmp); 
			while (tmp!=null){ 
		    	this.diagrams.add(tmp.getNodo().getNombre()); 
		    	tmp=tmp.next;}return;} 
 
	//evento de seleccion de diagrama. 
    public void displaySelectedDiagram(MouseEvent event) throws IOException {
    	String selectedDiagram = diagramsList.getSelectionModel().getSelectedItem(); 
    	System.out.println("SELECTED DIAGRAM IN LISTVIEW: "+selectedDiagram); 
		if (selectedDiagram == null) { 
			searchSTR.setPromptText("diagram detail"); //set to default. 
			screen.getChildren().clear(); //clean screen 
			stack.getChildren().clear(); //clean stack 
			this.nothingMessage();//mensaje visual. 
			return;} 
		this.clean();
		//nombre general del esquema (CURSO, PROFESOR, EDIFICIO...) 
		searchSTR.setPromptText(selectedDiagram); //set to watching.
    	for (int i=0; i< this.jdata.size(); i++){
    		System.out.println(this.datosObservable.get(i));}
		Esquema e = this.listaEsquemas.buscar(selectedDiagram); 
		//nombre de cada columna del esquema (ID, NOMBRE, APELLIDO, CARNE, CEDULA) 
		this.setCxF(e, false, null, null);
	}private void setCxF(Esquema e, boolean s, ArrayList<String[]> filasbuscadas, String detail){
		/* Acomodar las columnas en choicebox*/
		this.setChoiceBox(e.obtenercolumnas().getArraycolumnas(e.obtenercolumnas()));
		/* Obtener cada columna String*/
    	this.c = e.obtenercolumnas().getStringArraycolumnas(e.obtenercolumnas());
    	/* Obtener cada fila String[]*/
    	if (s) {
    		for (int i =0; i < filasbuscadas.size(); i++) {
    			jdata.add(filasbuscadas.get(i));}}
//    	else {
//    		String string = e.buscardatos(detail, e.getNombre());
//    		String[] data = string.split(";");
//    		String[] joins = new String[data.length];
//    		String[] rows = joins[1].split(",");
//    		
//    		for (int l = 0; l <data.length; l++) {
//    			jdata.add(rows);}
//    	}
    		
    	/* Obtener cada fila String[]*/
//    	seguir con algo maas
    	
		String[] d = new String[5];
		String[] a = new String[5];
		String[] b = new String[5];
		String[] c = new String[5];
    	a[0] = "a"; a[1] = "b"; a[2] = "c"; a[3] = "d"; a[4] = "e"; 
    	b[0] = "1"; b[1] = null; b[2] = "3"; b[3] = "4"; b[4] = "5";
    	d[0] = "X"; d[1] = "Z"; d[2] = "V"; d[3] = "G"; d[4] = "U";
    	jdata.add(a);
    	jdata.add(b);
		jdata.add(d);
		jdata.add(c);

		showTable();
    }private void setChoiceBox(ArrayList<String> arrayList) {  //acomodar los keys en choicebox. 
		this.choiceboxSearch.getItems().clear();
		this.availableChoices.add("Other...");
		this.availableChoices.add("ID");
		this.availableChoices.add("Name");
		this.availableChoices.add("Index");
		this.availableChoices.add("Joins");
    	this.availableChoices.addAll(arrayList);
		this.choiceboxSearch.setItems(availableChoices); 
		this.choiceboxSearch.getSelectionModel().select(0);
    }private void showTable() {
        int size = this.c.length; //number of the columns
        for (int i = 0; i < size; i++) {
            TableColumn<String[], String> firstNameCol = new TableColumn<>(this.c[i]);
            int index = i ;
            firstNameCol.setCellValueFactory(cellData -> {
                String[] rowData = cellData.getValue();
                if (index >= rowData.length) {return new ReadOnlyStringWrapper("");} 
                else {String cellValue = rowData[index];return new ReadOnlyStringWrapper(cellValue);}});
            firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            firstNameCol.setMinWidth(200);
            firstNameCol.setStyle("-fx-alignment: CENTER;");
            this.tableview.getColumns().add(firstNameCol);}
        datosObservable = FXCollections.observableList(this.jdata);
        tableview.getItems().addAll(this.datosObservable);
        tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableview.prefWidthProperty().bind(screen.widthProperty());
        tableview.prefHeightProperty().bind(screen.heightProperty());  
        tableview.setFocusTraversable(false);
		screen.getChildren().add(tableview);
		log.debug("TableColumns Finished --> "+"table cargado..."); 
    }private void nothingMessage() { 	//muestra que no se busca nada. 
		nothing.setText("Nothing Displayed"); nothing.setTextFill(Color.GHOSTWHITE); nothing.setFont(new Font("Arial", 25)); 
		stack.prefWidthProperty().bind(scrollpane.widthProperty());  
		stack.prefHeightProperty().bind(scrollpane.heightProperty());  
		stack.setAlignment(Pos.CENTER);  
		stack.getChildren().add(nothing); 
	    screen.getChildren().add(stack);
	}private void clean() {
		this.screen.getChildren().clear();
		this.tableview.getItems().clear();
		this.tableview.getColumns().clear();
		this.jdata.clear();}
		    
	public void hola() throws IOException { 
	        System.out.println("estoy en la funcion"); 
	        ObjectNode perro = objectMapper.createObjectNode(); 
	        perro.put("hola", 82).put("charanco","tornado"); 
	        perro.put("hola", "tornado"); 
	        System.out.println(perro); 
	   try { 
	       Integer.parseInt("23.4"); 
	       } 
	       catch (NumberFormatException e){ 
	           e.getCause(); 
	       } 
	} 
	 
	@SuppressWarnings({ "rawtypes", "unchecked", "resource", "unused" })
	public void table() throws IOException { 
	        Hashtable hashtable= new Hashtable(); 
	        hashtable.put("hello", "pico" ); 
	        hashtable.put("Paco",85); 
	        hashtable.put("Paco",89); 
	        Hashtable holga = new Hashtable(); 
	        holga= (Hashtable) hashtable.clone(); 
	        System.out.println(objectMapper.readValue(objectMapper.writeValueAsString(holga),Hashtable.class)); 
	        System.out.println(holga); 
	        Socket client = new Socket(InetAddress.getLocalHost(), 9500);
	        DataInputStream datosentrada = new DataInputStream(client.getInputStream());
	        log.debug("entrada se conecto"); 
	        Datos datosrecibidos = objectMapper.readValue(datosentrada.readUTF(), Datos.class); 
	        log.debug("se creo objeto"); 
	    }
}
