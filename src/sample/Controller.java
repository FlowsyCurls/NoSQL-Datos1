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
import Listas.ListaString;
import Listas.Nodo;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
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
	
	public static ListaEsquemas listaEsquemas = new ListaEsquemas();
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
    private ChoiceBox<String> indexBox = new ChoiceBox<String>(); 
	private ObservableList<String> availableIndex = FXCollections.observableArrayList();
	private boolean bug = false;
	
	@FXML
    private ChoiceBox<String> choiceboxSearch = new ChoiceBox<String>(); 
	private ObservableList<String> availableChoices = FXCollections.observableArrayList(); 
	
	@FXML
    private ChoiceBox<String> choiceboxSearchINDEX = new ChoiceBox<String>(); 
	private ObservableList<String> availableColumnaseditable = FXCollections.observableArrayList(); 
    
	@FXML private TextField textfieldFilas;
   
	@FXML private TextField textfieldEdit;
    public ArrayList<String[]> jdata = new ArrayList<String[]>(); //Here is the dat
   
    @FXML // fx:id="search" 
    private TextField searchSTR =  new TextField(); 
    
    @FXML private TextField searchINDEX =  new TextField(); 
    
    //FMLX nothingMessage 
	private  StackPane stack = new StackPane(); 
	private  Label nothing =new Label(); 
	
	//objextmapper 
    private ObjectMapper objectMapper=new ObjectMapper(); 
 
    public static Logger log = LoggerFactory.getLogger(Controller.class);

	public static int counter= 1;
	
	private static String [] columnas;
	
	public static Cliente cliente = new Cliente();
 
    //initializer 
    public void initialize() throws IOException {
    	try {
	    	//cargar esquemas del servidor.
	    	Controller.Cargaresquemas();
			//set default choicesearch
			this.availableChoices.add("OTHERS...");
			this.choiceboxSearch.setItems(availableChoices);
			this.choiceboxSearch.getSelectionModel().select(0);
			/*LLAMADAS INICIALES*/
			setChoiceBoxEdit();
			loadDiagrams(null);
			nothingMessage();
			/*LLAMADAS INICIALES*/
    	} catch (NullPointerException n) { 
    		System.out.println("Compa inicie el Server primero...");
    		return;
    		}
    	}
    
    
    //cargar esquemas
    public static void Cargaresquemas() throws NullPointerException, IOException {
		counter = 1;
		Datos Datos = cliente.recibirEsquemas();
		if (!Datos.getRespuesta().equals("constructores enviados")) {
			System.out.println(Datos.getRespuesta());
			System.out.println("NO CARGOOOOOO MIIIIIIIEEEEERRRRRDAAAA"); 
			return;}
		ListaEsquemas listaEsquemastmp= new ListaEsquemas();
		Nodo<String> tmp = Datos.getConstructores().getHead();
		while (tmp!=null){ 
			listaEsquemastmp.addLast(new Esquema(tmp.getNodo(), true));
//			System.out.println("\rCargado Esquema ||| "+tmp.getNodo());
	    	tmp=tmp.next;}
		System.out.println("voy bien");
		if (Controller.listaEsquemas.getLargo()!=0){
			System.out.println("entro aca");
			int cont=0;
			while (cont<listaEsquemastmp.getLargo()) {
				System.out.println(listaEsquemastmp.buscar(cont).getNombre());
				listaEsquemastmp.buscar(cont).setColumnasconindice(Controller.listaEsquemas.buscar(listaEsquemastmp.buscar(cont).getNombre()).getColumnasconindice());
				cont++;
			}
		}
		Controller.listaEsquemas=listaEsquemastmp;
    }

    
    @FXML  //evento de anadir 
	public void addButtonAction(ActionEvent event) throws IOException { 
    	String inputString = this.textfieldFilas.getText();
    	if (inputString.isEmpty()) {
    		this.textfieldFilas.clear(); 
    		return;
    	}
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
	        ControllerAdd controller= loader.getController();
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
        if (this.verifyTextField(detail) && prompt.equals("diagram's detail") ) {UserMessage message = new UserMessage(AlertType.INFORMATION,"\n\rAlso you can choose \"Name\" or \"ID\", just.. \nwrite the respective case.","SORRY..!\nFirst, you have to select a diagram");message.showAndWait();return;}
        //readchoice
        String selectedChoice = this.choiceboxEdit.getSelectionModel().getSelectedItem();
        /*buscar por nombre*/
        if (selectedChoice.equals("NAME")) {
            Esquema buscado = new Esquema();
            if (this.verifyTextField(detail)) {buscado = Controller.listaEsquemas.buscar(prompt);}
            else {buscado = Controller.listaEsquemas.buscar(detail);}
            if (buscado == null) { UserMessage message = new UserMessage(AlertType.INFORMATION, detail,"Your diagram was not found");message.showAndWait(); this.textfieldEdit.clear(); return;} //si no existe.
            this.editWindow(buscado);
            System.out.println("esquema buscado: "+buscado.getNombre()+" ,id: "+buscado.getID());
            return;}
        /*buscar por indice*/
        else if (selectedChoice.equals("INDEX")) {
        	//si no hay numero ingresado, ni esquema seleccionado.
        	if (!prompt.equals("diagram's detail") && this.verifyTextField(detail)) {
            	UserMessage message = new UserMessage(AlertType.ERROR,"\n\r\tJust sayin'...  ¬¬","Try to write a number");
            	message.showAndWait();
            	this.textfieldEdit.clear();
            	return;
            } else if (!Controller.TryParse(detail, "INTEGER")) { //si el numero ingresado no es valido.
            	UserMessage message = new UserMessage(AlertType.ERROR,"\n\rNot double, not long, not float. \nJust integer... \n\t\tPLEASE..!","Write a valid number");
            	message.showAndWait();
            	this.textfieldEdit.clear();
            	return;
            }
        }
        //busqueda y abrir otra ventana.
        int inputInt = Integer.parseInt(detail);
        Esquema buscado = Controller.listaEsquemas.buscar(inputInt);
        if (buscado==null) { UserMessage message = new UserMessage(AlertType.INFORMATION, detail,"Your diagram was not found");message.showAndWait();return;}
        this.editWindow(buscado);
        System.out.println("esquema: "+buscado.getNombre()+" ,id: "+buscado.getID());
        return;
    }

    private void setChoiceBoxEdit() { //setChoiceBoxEdit de editbuttonaction.
//		this.choiceboxEdit.getItems().clear();
        this.availableType.addAll("NAME","INDEX");
        this.choiceboxEdit.setItems(availableType);
        this.choiceboxEdit.getSelectionModel().select(0);
    }

    private void editWindow(Esquema e) {
	    try {
	        Stage editStage = new Stage();
	        Parent root;
	        FXMLLoader loader;
	        loader = new FXMLLoader(getClass().getResource("edit.fxml"));
	        root=loader.load();
	        editStage.setTitle("Edit Diagram");
	        ControllerEdit controller= loader.getController();
	        controller.getEsquema(e.getNombre());
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
    	/*analizando escogencia....*/
    	String detail = searchSTR.getText();
    	String selectedChoice = this.choiceboxSearch.getSelectionModel().getSelectedItem();
    	int selectedchoiceindex=this.choiceboxSearch.getSelectionModel().getSelectedIndex()-1;
    	
    	System.out.println("Seleccionado en el Choices --->> "+selectedChoice); 
    	System.out.println("detail: "+detail);
		String esquemaproximo="";
    	while (selectedchoiceindex>0){
    		if (Controller.listaEsquemas.contiene(this.choiceboxSearch.getItems().get(selectedchoiceindex))){
    			esquemaproximo=this.choiceboxSearch.getItems().get(selectedchoiceindex);
    			System.out.println(esquemaproximo+"AQUI ESTA EL ESQUEMA PROXIMO");
    			break;
			}
    		else {selectedchoiceindex--;}
		}
    	/*TEXT VACIO*/
    	if (this.verifyTextField(detail)){
    		detail=null; 
    		this.searchSTR.clear();
    		System.out.println("Escrito en detail --->>  "+detail);
    		return;}
    	
    	/*BUSQUEDA DE OTROS ESQUEMAS QUE COINCIDAN*/
    	if (selectedChoice.equals("OTHERS...") || selectedChoice==null){
    		this.loadDiagrams(detail);
    		this.searchSTR.clear();
    		return;}
    	//buscar esquema solicitado
		Esquema usedDiagram = Controller.listaEsquemas.buscar(searchSTR.getPromptText());
		//variables locales
		ArrayList<Integer> filasbuscadas = new ArrayList<> (); //filas por mostrar
		Datos datoscompletos;
        String datos="";
        String respuesta="";
		if (usedDiagram.getTamanos().contiene(selectedChoice)){
		    datoscompletos = Controller.cliente.buscardatos(usedDiagram.getNombre(),detail,selectedChoice);
            datos=datoscompletos.getDatos(); respuesta=datoscompletos.getRespuesta();
		}else if (usedDiagram.getMijoins().contiene(selectedChoice)){
		    datoscompletos = Controller.cliente.buscardatos(usedDiagram.getNombre(),detail,selectedChoice);
			if (Controller.listaEsquemas.buscar(esquemaproximo).getMijoins().contiene(selectedChoice) && !esquemaproximo.equals(usedDiagram.getNombre()) && !datoscompletos.getRespuesta().equals("no se encontraron datos")){
				datoscompletos = Controller.cliente.buscardatosporjoin(usedDiagram.getNombre(),detail,selectedChoice,esquemaproximo);
                datos=datoscompletos.getDatos(); respuesta=datoscompletos.getRespuesta();
			}else {datos=datoscompletos.getDatos(); respuesta=datoscompletos.getRespuesta();}
		}else {datoscompletos = Controller.cliente.buscardatosporjoin(usedDiagram.getNombre(),detail,selectedChoice,esquemaproximo);datos=datoscompletos.getDatos(); respuesta=datoscompletos.getRespuesta();}
		if (respuesta.equals("no se encontraron datos")){this.messenger("No matches for ", detail); return;}
   		 //datos de las filas del esquema.
		System.out.println("Datos "+datos);
		
    	/*buscando con indices...*/
//		if (selectedChoice.equals("INDEX")) {
//			filasbuscadas = SearchIndex(detail, datos, usedDiagram, indexBox.getSelectionModel().getSelectedItem());
//			//verificar que por lo menos haya algun coincidencia. 
//			if (filasbuscadas.isEmpty()) {this.messenger("No matches for ", detail); return;} 
//			else {this.setCxF(datos, filasbuscadas);}return;
////				respuesta = Controller.cliente.buscardatosporindice(usedDiagram.getNombre(), columna, detail);

////    	/*buscando con joins...*/
////		}else if (selectedChoice.equals("JOINS")) {
////			filasbuscadas = SearchJoins(detail, datos, usedDiagram);
////			//verificar que por lo menos haya algun coincidencia. 
////			if (filasbuscadas == null || filasbuscadas.isEmpty()) {this.messenger("No matches for ", detail); return;} 
////			else {this.setCxF(datos, filasbuscadas);}return;
////				respuesta = Controller.cliente.buscardatosporjoin(usedDiagram.getNombre(), dato, columna, nombre_joins)	
	    /*buscando con columnas...*/
			filasbuscadas = SearchAtributes(detail, selectedChoice, datos, usedDiagram);
			if (filasbuscadas.isEmpty()) {this.messenger("No matches for ", detail); return;} 
			else {this.setCxF(datos, filasbuscadas);}
			return;

	}
	public void SearchforIndex () throws EsquemaNuloException {
		String detail = searchINDEX.getText();
		if (this.verifyTextField(detail)){
			detail=null;
			this.searchSTR.clear();
			System.out.println("Escrito en detail --->>  "+detail);
			return;}
		String selectedChoice = this.choiceboxSearchINDEX.getSelectionModel().getSelectedItem();
		Esquema usedDiagram = Controller.listaEsquemas.buscar(searchSTR.getPromptText());
		//variables locales
		ArrayList<Integer> filasbuscadas = new ArrayList<>(); //filas por mostrar
		Datos datoscompletos;
		String Structure = indexBox.getSelectionModel().getSelectedItem();
		System.out.println("Seleccionado en el Choices --->> " + selectedChoice);
		System.out.println("detail: " + detail);

		String datos = SearchIndex(detail, usedDiagram, selectedChoice, Structure);
		System.out.println(datos);
		filasbuscadas=SearchAtributes(detail,selectedChoice,datos,usedDiagram);
		//verificar que por lo menos haya algun coincidencia.
		if (filasbuscadas.isEmpty()) {
			this.messenger("No matches for ", detail);
			return;
		} else {
			this.setCxF(datos, filasbuscadas);
		}
		return;

	}
		
    private String SearchIndex(String detail, Esquema usedDiagram, String Key, String Structure){
    	if (Structure.equals("Binary")) Structure = "ArbolBinario";
    	else if (Structure.equals("R-B")) Structure = "ArbolRB";
    	else if (Structure.equals("B+")) Structure = "ArbolBPlus";
    	else if (Structure.equals("AA")) Structure = "ArbolAA";
    	else if (Structure.equals("B")) Structure = "ArbolB";
    	else if (Structure.equals("AVL")) Structure = "AVL";
    	System.out.println(usedDiagram.getNombre());
    	System.out.println(detail);
    	System.out.println(Key);
    	System.out.println(Structure);
    	Datos datosencotrados=cliente.buscardatosporindice(usedDiagram.getNombre(), detail, Key, Structure);
    	String respuesta = datosencotrados.getRespuesta();
    	System.out.println("respuesta busqueda por indice "+respuesta);
    	String filas=datosencotrados.getDatos();
    	if (respuesta.equals("el dato no existe")){filas="";}
		return filas;
    }
    
    private void setIndexPosibilitys(Esquema esq, String column) {
    	try{availableIndex.clear();
    	System.out.println("entro aca para anadir los indices");
    	System.out.println(esq.getNombre()+column);
    	if (esq.columnasconindice.buscarindice(column).tienearbolAA) this.availableIndex.add("AA");
    	if (esq.columnasconindice.buscarindice(column).tieneAvl) this.availableIndex.add("AVL");
    	if (esq.columnasconindice.buscarindice(column).tienearbolBinario) this.availableIndex.add("Binary");
    	if (esq.columnasconindice.buscarindice(column).tienearbolB) this.availableIndex.add("B");
    	if (esq.columnasconindice.buscarindice(column).tienearbolBPlus) this.availableIndex.add("B+");
    	if (esq.columnasconindice.buscarindice(column).tienearbolRB) this.availableIndex.add("R-B");
		System.out.println(	esq.columnasconindice.buscarindice(column).Estoyvacio());
    	this.indexBox.setItems(availableIndex);
		this.indexBox.getSelectionModel().select(0);}
    	catch (NullPointerException r) {
    		return;
    	}
    }
    
    
    
//    private ArrayList<Integer> SearchJoins(String detail, String Datos, Esquema usedDiagram) throws EsquemaNuloException{
//		ArrayList<Integer> filas = new ArrayList<> (); //filas por mostrar
//		ArrayList<String[]> tmpfilas = this.toStringArray(Datos.split(";"));
//		String[] datos = Datos.split(";");
//		printArray(columnas,"columans");
//		int j=1;
//		boolean encuentro = false;
//		for (int i=0; i< tmpfilas.size(); i++){
//			if (tmpfilas.get(0)[i].equals("_")){
//				j+=i; encuentro=true; break;
//			}
//		}if (encuentro==false) return null;
//		System.out.println("\n------> \n Nombre Esquema:"+usedDiagram.getNombre()+"\n detail: "+detail+
//				"\n indice J: "+j+"\n NOMBREJOIN: "+columnas[j]+"\n COLUMNA: "+columnas[j+1]+"\n");
//		Datos Joins = Controller.cliente.buscardatosporjoin(usedDiagram.getNombre(), detail, columnas[j+1], columnas[j]);
//		System.out.println("Respuesta "+Joins.getRespuesta());
////		if (Joins.getRespuesta())
//		System.out.println("Joins "+Joins);
//		String[] joins = Joins.getDatos().split(";");
//		for (int i=0; i< datos.length; i++){
//			int z = 0;
//			while (z < joins.length) {
//				if (datos[i].contentEquals(joins[z])) {
//					filas.add(i);
//				}z++;
//			}
//		}
//		return filas;
//    }
    
    
	private ArrayList<Integer> SearchAtributes(String detail, String selectedChoice, String datos, Esquema usedDiagram){
		ArrayList<Integer> filas = new ArrayList<> (); //filas por mostrar
		for (int i=0; i< columnas.length; i++){
			if (columnas[i].equals(selectedChoice)) {
				String[] arrayDatos = datos.split(";");
				for (int h=0; h< arrayDatos.length; h++) {
                    System.out.println("ENTRA" + arrayDatos[h].split(",")[i - 1]);
                    System.out.println("ENTRA " + arrayDatos[h]);
                    System.out.println(h);
                    filas.add(h);
                }
				break;
			}
		}return filas;
	}
	
	public void searchIndexButtonAction(ActionEvent event) throws IOException, NullPointerException {
    	this.SearchforIndex();
	}
	
	private void messenger(String content, String detail) {
		UserMessage message = new UserMessage(AlertType.INFORMATION,
				detail,content);
		message.showAndWait();
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
	
	public static ArrayList<String> addNamesxIDOneByOne(String caso) { //cargar todos los diagramas.
		ArrayList<String> nombresxid = new  ArrayList<>(); 
		Nodo<Esquema>tmp = Controller.listaEsquemas.getHead(); 
		while (tmp!=null){ 
			if (caso.equals("nombre")) {
			nombresxid.add(tmp.getNodo().getNombre());
//			System.out.println("NODO ESQUEMA "+tmp.getNodo().getNombre());
	    	tmp=tmp.next;}
			else if (caso.equals("nombrexid")) {
				nombresxid.add(tmp.getNodo().getNombre());
				nombresxid.add(tmp.getNodo().getID()); 
//				System.out.println("NODO ESQUEMA "+tmp.getNodo().getNombre());
		    	tmp=tmp.next;}
			}
		return nombresxid;}
	
	/*PARA MODIFICAR EL LISTVIEW EN BASE A LO QUE SE BUSCA*/
	private void loadDiagrams(String detail) throws IOException { //loadDiagramas.
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
    	ArrayList<Esquema> coincided = Controller.listaEsquemas.buscarcoincidencias(detail); 
    	if (coincided.isEmpty()) return; 
    	for (int i=0; i<coincided.size(); i++){ 
    		Esquema element =coincided.get(i); 
			System.out.println("\rESQUEMA encontrado :"+" |name "+element.getNombre()+" |id "+element.getID()); 
    		this.diagrams.add(element.getNombre()); //add coincidences... 
    	}coincided.removeAll(coincided); 
    }
    /*PARA AGREGAR TODOS LOS ESQUEMAS AAL LISTVIEW*/
	private void addNamesOneByOne() { //cargar todos los diagramas. 
			Nodo<Esquema>tmp = Controller.listaEsquemas.getHead(); 
//			System.out.println("NODO ESQUEMA "+tmp); 
			while (tmp!=null){ 
		    	this.diagrams.add(tmp.getNodo().getNombre()); 
		    	tmp=tmp.next;}return;} 
 
	/*PARA MOSTRAR DIAGRAMA SELECCIONADO*/
    public void displaySelectedDiagram(MouseEvent event) throws IOException, NullPointerException {
    	String selectedDiagram = diagramsList.getSelectionModel().getSelectedItem();
    	this.availableColumnaseditable.clear();
    	ListaString t = new ListaString();
    	try{t = listaEsquemas.buscar(selectedDiagram).obtenercolumnasparaedit();}
    	catch (NullPointerException r) {return;}
    	Nodo<String> tmp = t.getHead();
    	while(tmp!=null) {
    		availableColumnaseditable.add(tmp.getNodo());
    		tmp=tmp.next;
    	}
        this.choiceboxSearchINDEX.setItems(availableColumnaseditable);
        this.choiceboxSearchINDEX.getSelectionModel().select(0);
    	if (!bug) {
    		this.setIndexPosibilitys(listaEsquemas.buscar(selectedDiagram), choiceboxSearchINDEX.getSelectionModel().getSelectedItem());
    		choiceboxSearchINDEX.getSelectionModel().selectedItemProperty().addListener( 
    				(ObservableValue<? extends String> observable, String oldValue, 
    						String newValue) -> this.setIndexPosibilitys(listaEsquemas.buscar(selectedDiagram), newValue));
    		bug =true;
    	}
    	System.out.println("SELECTED DIAGRAM IN LISTVIEW: "+selectedDiagram); 
		if (selectedDiagram == null) { 
			searchSTR.setPromptText("diagram detail"); //set to default. 
			textfieldEdit.setPromptText("diagram detail"); //set to default.
			searchINDEX.setPromptText("diagram detail");
			screen.getChildren().clear(); //clean screen 
			stack.getChildren().clear(); //clean stack 
			this.nothingMessage();//mensaje visual. 
			return;} 
		this.clean();
		//nombre general del esquema (CURSO, PROFESOR, EDIFICIO...) 
		searchSTR.setPromptText(selectedDiagram); //set to watching.
		textfieldEdit.setPromptText(selectedDiagram); //set to default.
		searchINDEX.setPromptText(selectedDiagram);
    	for (int i=0; i< this.jdata.size(); i++){
    		System.out.println(this.datosObservable.get(i));}
    	//obtener esquema
		Esquema e = Controller.listaEsquemas.buscar(selectedDiagram); 
		/* Acomodar las columnas en choicebox*/
		this.setChoiceBoxSearch(e.obtenercolumnas().getArraycolumnas(e.obtenercolumnas()));
		/* Obtener cada columna String*/
		Controller.columnas = e.obtenercolumnas().getStringArraycolumnas(e.obtenercolumnas());
    	/* Obtener cada dato en fila String[]*/
   		String datos = Controller.cliente.buscartodoslosdatos(e.getNombre()).getDatos();
    	/* Dibujarlo*/
		this.setCxF(datos, null);
		System.out.println("Cantidad Columnas |>> "+columnas.length);
		System.out.println("Datos Filas |>> "+datos);
	}
    
    private void setCxF(String datos, ArrayList<Integer> filasbuscadas) throws EsquemaNuloException{
    	this.clean();
    	/*Si no hay datos*/
    	if (datos == null) {
   			this.jdata.add(new String[columnas.length-1]);
   			showTable(); return;}
    	//agregar numeros.
    	datos = Controller.addnumber(datos);
   		System.out.println("Enumerados --> "+datos+"\n");		
    	/* Obtener cada fila String[]*/
   		ArrayList<String[]>  filas = new ArrayList<String[]>();
    	if (filasbuscadas == null) {  //NO BUSCANDO
       		filas = this.toStringArray(datos.split(";"));
    		this.jdata.addAll(filas);
    	}else{ //BUSCANDO
       		filas = this.toStringArray(datos.split(";"));
    		int done = 0;
       		while (done != filasbuscadas.size()) {
				this.jdata.add(filas.get(filasbuscadas.get(done)));
				done++;
    		}
    	}showTable();
	}
    
	public static String addnumber(String sinnumer) {
//		System.out.println("\n\n\nSINNUMER: "+sinnumer);
		int remind = -1;
		int cont = 0;
		int last = (sinnumer.split(";").length-1);
		String concate = "";
		for (int s=0; s<=sinnumer.length()-1;s++) {
			if (sinnumer.charAt (s) == ';') {
				String substring = sinnumer.substring(remind+1, s);
				substring = String.valueOf(cont)+","+substring;
				concate = concate+";"+substring;
				if (remind==-1) concate = substring;
				remind = s;
				cont++;
			}else if(cont == last && s==sinnumer.length()-1 && remind==-1) {
				concate = String.valueOf(cont)+","+sinnumer;
				cont++;
			}else if (cont == last && s==sinnumer.length()-1) {
				String substring = sinnumer.substring(remind+1);
				substring = ";"+String.valueOf(cont)+","+substring;
				concate = concate+substring;
				cont++;}}
		ControllerEdit.numerofilas=cont;
//		System.out.println("Controller numf "+ControllerEdit.numerofilas);
		return concate;
	}
	
    private ArrayList<String[]> toStringArray(String[] toConvert) {
		ArrayList<String[]> list = new ArrayList<>();
		for (int j=0; j<=toConvert.length-1;j++) {
			list.add(toConvert[j].split(","));
//			System.out.println(list.get(j)[0]);
		}
		return list;
    }
    
    private void setChoiceBoxSearch(ArrayList<String> arrayList) {  //acomodar los keys en choicebox. 
		this.choiceboxSearch.getItems().clear();
		this.availableChoices.add("OTHERS...");
//		this.availableChoices.add("NAME");
//		this.availableChoices.add("JOINS");
//		this.availableChoices.add("INDEX");
    	this.availableChoices.addAll(arrayList);
		this.choiceboxSearch.setItems(availableChoices);
		this.choiceboxSearch.getSelectionModel().select(0);
		
    }
    
    private void showTable() {
		tableview = new TableView<String[]>();
        int size = columnas.length; //number of the columns
        ArrayList<String> nombrexid = Controller.addNamesxIDOneByOne("nombrexid");
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
                    if (nombrexid.contains(columnas[index])) {
                    	firstNameCol.setEditable(false);
                    }
                    else if (columnas[index]=="#") {
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
    	nothing =new Label(); 
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
    	if (selectedDiagram==null) return;
    	this.editWindow(Controller.listaEsquemas.buscar(selectedDiagram));
    }
    @FXML
	public void deleteDiagram(ActionEvent event) throws IOException {
    	String selectedDiagram = diagramsList.getSelectionModel().getSelectedItem();
    	if (selectedDiagram==null) return;
		String respuesta = Controller.cliente.eliminarEsquema(selectedDiagram);
		if ((respuesta.equals("esquema eliminado"))) {
			this.clean();
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
