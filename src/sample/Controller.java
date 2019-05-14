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

import Errores.EsquemaNuloException;
import Listas.Esquema;
import Listas.ListaEsquemas;
import Listas.Nodo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
	
	public static ListaEsquemas listaEsquemas = null;
//	private ArrayList<String> array = new ArrayList<String>(); 
	 
	//FXML// 
	@FXML // fx:id="base" 
	public  AnchorPane baseSample= new AnchorPane(); 
	@FXML // fx:id="diagramsList" 
	private ListView<String> diagramsList = new ListView<String>(); 
	private ObservableList<String> diagrams = FXCollections.observableArrayList();
    @FXML private ContextMenu contextmenu = new ContextMenu();
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

	public static int counter= 1;
	private ArrayList<String[]> filas = new ArrayList<String[]>();
	private Cliente cliente = new Cliente();
 
    //initializer 
    public void initialize() throws IOException {
    	//cargar esquemas del servidor.
    	try {
    		counter = 1;
			Datos Datos = this.cliente.recibirEsquemas();
			if (!Datos.getRespuesta().equals("constructores enviados")) {
    			System.out.println(Datos.getRespuesta());
    			System.out.println("NO CARGOOOOOO MIIIIIIIEEEEERRRRRDAAAA"); 
    			return;}
    		Controller.listaEsquemas = new ListaEsquemas();
    		Nodo<String> tmp = Datos.getConstructores().getHead();
			while (tmp!=null){ 
    			Controller.listaEsquemas.addLast(new Esquema(tmp.getNodo(), true));
//    			System.out.println("\rCargado Esquema ||| "+tmp.getNodo());
		    	tmp=tmp.next;}
			//set default choicesearch
			this.availableChoices.add("OTHERS...");
			this.choiceboxSearch.setItems(availableChoices);
			this.choiceboxSearch.getSelectionModel().select(0);
			/*LLAMADAS INICIALES*/
    		setChoiceBoxEdit();
    		loadDiagrams(null);
    		nothingMessage();
    		/*LLAMADAS INICIALES*/
    	} catch (IOException m) { 
    		System.out.println("NO FUNCOOOOO");
    		m.printStackTrace();
    		log.debug("error"); return;
    		} 
    	catch (NullPointerException n) { 
    		System.out.println("Compa inicie el Server primero...");
    		return;
    		}
    	}
    
    @FXML  //evento de anadir 
	public void addButtonAction(ActionEvent event) throws IOException { 
    	String inputString = this.textfieldFilas.getText();
    	if (inputString.isEmpty()) {this.textfieldFilas.clear(); return;}
    	else if (!Controller.TryParse(inputString, "INTEGER")) {
			UserMessage message = new UserMessage(AlertType.ERROR,
					"\n\rNot double, not long, not float. \nJust integer... \n\t\tPLEASE..!",
					"Write a valid number");
    		message.showAndWait();
    		this.textfieldFilas.clear();
            return;}
    	this.textfieldFilas.clear();
		int inputInt = Integer.parseInt(inputString);
		if (inputInt==0) inputInt=1;
    	this.addWindow(inputInt);
	}public static boolean TryParse(String cadena, String tipo){
		if (tipo == "INTEGER") {
			try {Integer.parseInt(cadena);return true;} 
			catch (NumberFormatException e) {return false;}
		}
		else if (tipo == "DOUBLE") {
			try {Double.parseDouble(cadena);return true;} 
			catch (NumberFormatException e) {return false;}
		}
		else if (tipo == "LONG") {
			try {Long.parseLong(cadena);return true;} 
			catch (NumberFormatException e) {return false;}
		}
		else if (tipo == "FLOAT") {
			try {Float.parseFloat(cadena);return true;} 
			catch (NumberFormatException e) {return false;}
		}return false;
		
	}private void addWindow(int columns) {//cambia a la pantalla de fin del juego
	    try {
	        Stage addStage = new Stage();
	        Parent root;
	        FXMLLoader loader;
	        loader = new FXMLLoader(getClass().getResource("add.fxml"));
	        root=loader.load();
	        addStage.setTitle("Add a Diagram");
	        ControllerAdd<?> controller= loader.getController();
	        controller.drawing(columns, null);
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
	
	private boolean verifyTextField(String detail) {
    	if (detail.isEmpty() || (detail.trim().isEmpty())) {
    		return true;
    	}return false;
	}
	
	@FXML //evento de editar esquemas 
	public void editButtonAction(ActionEvent event) throws IOException {
		//readvalues
    	String detail = this.textfieldEdit.getText();
    	String prompt = this.textfieldEdit.getPromptText();
    	if (this.verifyTextField(detail) && prompt.equals("diagram's detail") ) {
			UserMessage message = new UserMessage(AlertType.INFORMATION,
					"\n\rAlso you can choose \"Name\" or \"ID\", just.. \nwrite the respective case.",
					"SORRY..!\nFirst, you have to select a diagram");
			message.showAndWait();
    		return;
    	}
    	//readchoice
    	String selectedChoice = this.choiceboxEdit.getSelectionModel().getSelectedItem();
    	System.out.println("\rdetail: "+detail);
    	System.out.println("selectedChoice: "+selectedChoice);
    	//compare
    	if (selectedChoice.equals("ID")) {
    		System.out.println(Controller.listaEsquemas.buscar(prompt));
    		System.out.println(Controller.listaEsquemas.buscar(detail));
    		/*codigo 
    		 * deberia
    		 * que 
    		 * hace 
    		 * algo */
    		return;
    		}
    	
    	else if (selectedChoice.equals("NAME")) {
    		System.out.println(Controller.listaEsquemas.buscar(prompt));
    		System.out.println(Controller.listaEsquemas.buscar(detail));
    		/*codigo 
    		 * deberia
    		 * que 
    		 * hace 
    		 * algo */
    		return;
    		}
    	
    	
    	else if (selectedChoice.equals("INDEX")) {
    		//validacion de la entrada.
    		if (!Controller.TryParse(detail, "INTEGER")) {
    			//si no hay numero ingresado.
    			if (!prompt.equals("diagram's detail") && this.verifyTextField(detail)) {
        			UserMessage message = new UserMessage(AlertType.ERROR,"\n\r\tJust sayin'...   ¬¬","Try to write a number");
        			message.showAndWait();
        			this.textfieldEdit.clear();
        		}else{
        			//si numero no es entero.
        			UserMessage message = new UserMessage(AlertType.ERROR,
        					"\n\rNot double, not long, not float. \nJust integer... \n\t\tPLEASE..!",
        					"Write a valid number");
        			message.showAndWait();
        			this.textfieldEdit.clear();
    			}return;}
    		//busqueda y abrir otra ventana.
    		int inputInt = Integer.parseInt(detail);
    		Esquema e = Controller.listaEsquemas.buscar(inputInt);
    		if (e==null) { UserMessage message = new UserMessage(AlertType.INFORMATION, detail,"Your diagram was not found");
    			message.showAndWait();
    			return;}
        	this.editWindow(e);
    		System.out.println("esquema: "+e.getNombre()+" ,id: "+e.getID());
    		return;}
	}private void setChoiceBoxEdit() { //setChoiceBoxEdit de editbuttonaction.
//		this.choiceboxEdit.getItems().clear();
    	this.availableType.addAll("ID","NAME","INDEX");
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
	        Scene scene = new Scene(root);
	        editStage.setScene(scene);//me crea una nuevo escenario y me carga todo lo del fxml
	        editStage.setResizable(false);
	        editStage.getIcons().add(new Image("/Media/edit.png"));
	        editStage.show();
	        Stage currentstage=(Stage) this.baseSample.getScene().getWindow();
	        currentstage.close();
	    } catch (IOException error) {
	    	error.printStackTrace();}}

	@FXML //evento de buscar esquemas 
    public void searchButtonAction(ActionEvent event) throws IOException, NullPointerException { 
		/*que se esta buscando*/
    	String detail = searchSTR.getText();
    	System.out.println("detail: "+detail); 
    	if (this.verifyTextField(detail)){ detail=null; this.searchSTR.clear();
    		System.out.println("Escrito en detail --->>  "+detail);
    	}
    	//analizando escogencia....
    	String selectedChoice = this.choiceboxSearch.getSelectionModel().getSelectedItem(); 
    	System.out.println("Seleccionado en el Choices --->> "+selectedChoice); 
    	if (selectedChoice.equals("OTHERS...") || selectedChoice==null){
    		this.loadDiagrams(detail);
    		this.searchSTR.clear();
    		return;}
    	else {
    		//pero para los demas se necesita el esquema so..
			Esquema usedDiagram = Controller.listaEsquemas.buscar(searchSTR.getPromptText());
			
			//variables locales
			String respuesta = null;
			ArrayList<String[]> filasbuscadas = new ArrayList<String[]> ();;

			//buscando con nombres...
			if (selectedChoice.equals("NAME")) {
				respuesta = this.cliente.acciones(cliente, Controller.listaEsquemas.buscar(searchSTR.getPromptText()), "buscardatos", null, null, 0);
				
				/*codigo para agregar cada
				 *  fila que sera string[]
				 *  de coincidencia en un
				 *  ArrayList<String[]>*/
				filasbuscadas= new ArrayList<String[]> ();
			}
			
			//buscando con indices...
			else if (selectedChoice.equals("INDEX")) {
				/* pen
				 * dien
				 * te*/
				
			}
			
			//buscando con joins...
			else if (selectedChoice.equals("JOINS")) {
				respuesta = this.cliente.acciones(cliente, Controller.listaEsquemas.buscar(searchSTR.getPromptText()), "buscardatosjoin", detail, null, 0);
				
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
	}
    private ArrayList<String> SearchID(String id, Esquema usedDiagram){
    	ArrayList<String> filasencontradas = new ArrayList<String>();
    	return filasencontradas;
    }
    private ArrayList<String> SearchName(String name, Esquema usedDiagram){
    	ArrayList<String> filasencontradas = new ArrayList<String>();
    	return filasencontradas;
    }
    private ArrayList<String> SearchIndex(String index, Esquema usedDiagram){
    	ArrayList<String> filasencontradas = new ArrayList<String>();
    	return filasencontradas;
    }
    private ArrayList<String> SearchJoins(String join, Esquema usedDiagram){
    	ArrayList<String> filasencontradas = new ArrayList<String>();
    	return filasencontradas;
    }
	
	
	
	
	private void messenger(String content, String detail) {
		UserMessage message = new UserMessage(AlertType.INFORMATION,
				detail,content);
		message.showAndWait();
	}private void loadDiagrams(String detail) throws IOException { //loadDiagramas.
    	this.diagrams.removeAll(diagrams);
    	if (detail!=null) { // si estoy buscando.... 
    		this.loadDiagrams_buscaraux(detail); 
			System.out.println("\rDIAGRAMAS cargados "+diagrams); 
			this.diagramsList.setItems(diagrams); 
			return;} 
    	this.addNamesOneByOne(); 
		System.out.println("\rDIAGRAMAS cargados "+diagrams); 
	    this.diagramsList.setItems(diagrams);
    }private void loadDiagrams_buscaraux(String detail) { //buscar coincidencias para cargarlas. 
//		System.out.println("Entra en buscar"); 
//		System.out.println(this.listaEsquemas.getLargo()); 
    	ArrayList<Esquema> coincided = Controller.listaEsquemas.buscarcoincidencias(detail); 
    	if (coincided.isEmpty()) return; 
    	for (int i=0; i<coincided.size(); i++){ 
    		Esquema element =coincided.get(i); 
			System.out.println("\rESQUEMA encontrado :"+" |name "+element.getNombre()+" |id "+element.getID()); 
    		this.diagrams.add(element.getNombre()); //add coincidences... 
    	}coincided.removeAll(coincided); 
    }private void addNamesOneByOne() { //cargar todos los diagramas. 
			Nodo<Esquema>tmp = Controller.listaEsquemas.getHead(); 
//			System.out.println("NODO ESQUEMA "+tmp); 
			while (tmp!=null){ 
		    	this.diagrams.add(tmp.getNodo().getNombre()); 
		    	tmp=tmp.next;}return;} 
 
	//evento de seleccion de diagrama. 
    public void displaySelectedDiagram(MouseEvent event) throws IOException {
    	String selectedDiagram = diagramsList.getSelectionModel().getSelectedItem(); 
    	System.out.println("SELECTED DIAGRAM IN LISTVIEW: "+selectedDiagram); 
		if (selectedDiagram == null) { 
			searchSTR.setPromptText("diagram detail"); //set to default. 
			textfieldEdit.setPromptText("diagram detail"); //set to default. 
			screen.getChildren().clear(); //clean screen 
			stack.getChildren().clear(); //clean stack 
			this.nothingMessage();//mensaje visual. 
			return;} 
		this.clean();
		//nombre general del esquema (CURSO, PROFESOR, EDIFICIO...) 
		searchSTR.setPromptText(selectedDiagram); //set to watching.
		textfieldEdit.setPromptText(selectedDiagram); //set to default. 
    	for (int i=0; i< this.jdata.size(); i++){
    		System.out.println(this.datosObservable.get(i));}
		Esquema e = Controller.listaEsquemas.buscar(selectedDiagram); 
		//nombre de cada columna del esquema (ID, NOMBRE, APELLIDO, CARNE, CEDULA) 
		this.setCxF(e, false, null, null);
	}private void setCxF(Esquema e, boolean s, ArrayList<String[]> filasbuscadas, String detail) throws EsquemaNuloException{
		/* Acomodar las columnas en choicebox*/
		this.setChoiceBoxSearch(e.obtenercolumnas().getArraycolumnas(e.obtenercolumnas()));
		/* Obtener cada columna String*/
    	this.c = e.obtenercolumnas().getStringArraycolumnas(e.obtenercolumnas());
        System.out.println("sss " +this.c.length);
    	/* Obtener cada fila String[]*/
    	if (!s) {  /*No buscando*/
       		String datos = this.cliente.buscartodoslosdatos(e.getNombre()).getDatos();
       		System.out.println("\nDATOS DE BUSCAR TODOS --> "+datos);
       		if (datos == null) {
       			this.jdata.add( new String[this.c.length-1]);
       			showTable(); return;}
       		datos = ControllerEdit.addnumber(datos);
       		System.out.println("DATOS CON NUMERO DE FILAS AGREGADOS --> "+datos+"\n");		
       		this.filas = this.toStringArray(datos.split(";"));
    		this.jdata.addAll(filas);
    		showTable();
       	}else {  /*Buscando*/
       		String datos = this.cliente.buscartodoslosdatos(e.getNombre()).getDatos();
       		System.out.println("\nDATOS DE BUSCAR TODOS --> "+datos);
       		if (datos == null) {
       			this.jdata.add( new String[this.c.length-1]);
       			showTable(); return;}
//       		datos = ControllerEdit.addnumber(datos);
//       		System.out.println("DATOS CON NUMERO DE FILAS AGREGADOS --> "+datos+"\n");		
       		this.filas = this.toStringArray(datos.split(";"));
    		for (int z=0; z<=filas.size()-1;z++) {
    			if (filasbuscadas.contains(filas.get(z))) {
    				this.jdata.add(filas.get(z));}
    		}showTable();
    	}

	}private ArrayList<String[]> toStringArray(String[] toConvert) {
		ArrayList<String[]> list = new ArrayList<>();
		for (int j=0; j<=toConvert.length-1;j++) {
			list.add(toConvert[j].split(","));
			System.out.println(list.get(j)[0]);
		}
		return list;
    }private void setChoiceBoxSearch(ArrayList<String> arrayList) {  //acomodar los keys en choicebox. 
		this.choiceboxSearch.getItems().clear();
		this.availableChoices.add("OTHERS...");
		this.availableChoices.add("NAME");
		this.availableChoices.add("INDEX");
		this.availableChoices.add("JOINS");
    	this.availableChoices.addAll(arrayList);
		this.choiceboxSearch.setItems(availableChoices); 
		this.choiceboxSearch.getSelectionModel().select(0);
    }private void showTable() {
		tableview = new TableView<String[]>();
        int size = this.c.length; //number of the columns
        ArrayList<String> nombres = ControllerEdit.addNamesxIDOneByOne();
        for (int i = 0; i < size; i++) {
        	/*Crear una columna por cada nombre de la lista*/
            TableColumn<String[], String> firstNameCol = new TableColumn<>(this.c[i]);
            int index = i ;
            
            /*Asignar el tipo de dato que tendra a la fila;*/
            firstNameCol.setCellValueFactory(cellData -> {
                String[] rowData = cellData.getValue();
                if (index >= rowData.length) {return new ReadOnlyStringWrapper("");}
                else {
                	String cellValue = rowData[index];
                    if (nombres.contains(this.c[index])) {
//            			System.out.println("TITULO COLUMNA no editable  "+this.c[index]);
                    	firstNameCol.setEditable(false);
//                    	return new ReadOnlyStringWrapper(cellValue);
                    }
                    else if (this.c[index]=="#") {
//            			System.out.println("TITULO # no editable  "+this.c[index]);
                    	firstNameCol.setEditable(false);
                    	firstNameCol.setMinWidth(50);
                    	firstNameCol.setMaxWidth(Control.USE_COMPUTED_SIZE);
                    	return new ReadOnlyStringWrapper(cellValue);
                    }
                    firstNameCol.setMinWidth(100);
                	firstNameCol.setResizable(true);
                	return new ReadOnlyStringWrapper(cellValue);}});
            firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            firstNameCol.setStyle("-fx-alignment: CENTER;"); 
          this.tableview.getColumns().add(firstNameCol);} 
      datosObservable = FXCollections.observableList(this.jdata); 
      tableview.getItems().addAll(this.datosObservable); 
      tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 
      tableview.prefWidthProperty().bind(screen.widthProperty()); 
      tableview.prefHeightProperty().bind(screen.heightProperty()); 
      tableview.setStyle("-fx-control-inner-background: #192251; -fx-background-insets: 1; -fx-border-width: 0;"
				+ " -fx-border-color: #192251; -fx-focus-color: transparent;");
		screen.getChildren().add(tableview); 
		log.debug("TableColumns Finished --> "+"table cargado...");         
    }private void nothingMessage() { 	//muestra que no se busca nada. 
		nothing.setText("Nothing Displayed"); nothing.setTextFill(Color.GHOSTWHITE); nothing.setFont(new Font("Arial", 25)); 
		stack.prefWidthProperty().bind(screen.widthProperty());  
		stack.prefHeightProperty().bind(screen.heightProperty());  
		stack.setAlignment(Pos.CENTER);  
		stack.getChildren().add(nothing); 
	    screen.getChildren().add(stack);
	}private void clean() {
		this.screen.getChildren().clear();
		this.tableview.getItems().clear();
		this.tableview.getColumns().clear();
		this.jdata.clear();}
	

    @FXML
    public void editContextMenu(ActionEvent event) {
    	String selectedDiagram = diagramsList.getSelectionModel().getSelectedItem(); 
    	this.editWindow(Controller.listaEsquemas.buscar(selectedDiagram));
    }
    @FXML
	public void deleteDiagram(ActionEvent event) throws IOException {
    	String selectedDiagram = diagramsList.getSelectionModel().getSelectedItem(); 
		String respuesta = this.cliente.eliminarEsquema(selectedDiagram);
		if ((respuesta.equals("esquema eliminado"))) {
			this.initialize();
			return;
		}else if ((respuesta.equals("esquema usado"))) {
    		UserMessage message = new UserMessage(AlertType.INFORMATION,
    		"\n\r\tTry removing it's links first, then try again.","Sorry bruh..! \nThis chart is already in use...");
        	message.showAndWait();return;
		}UserMessage message = new UserMessage(AlertType.ERROR,
		"\n\rSorry something gone wrong. \n\tJejeps..!","Try again later.");
		message.showAndWait();return;
    }
    
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
