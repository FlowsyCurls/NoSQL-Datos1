package sample; 
 
import com.fasterxml.jackson.core.JsonProcessingException; 
import com.fasterxml.jackson.databind.JsonNode; 
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.fasterxml.jackson.databind.node.ObjectNode; 
 
import Errores.EsquemaNuloException; 
import Listas.Esquema; 
import Listas.ListaEsquemas; 
import Listas.ListaString; 
import Listas.ListaTables; 
import Listas.ListaTamano; 
import Listas.Nodo; 
import Listas.NodoList; 
import javafx.collections.FXCollections; 
import javafx.collections.ObservableList; 
import javafx.event.ActionEvent; 
import javafx.fxml.FXML; 
import javafx.fxml.FXMLLoader; 
import javafx.geometry.Pos; 
import javafx.scene.Parent; 
import javafx.scene.Scene; 
import javafx.scene.control.Button; 
import javafx.scene.control.ChoiceBox; 
import javafx.scene.control.Label; 
import javafx.scene.control.ListView; 
import javafx.scene.control.TextField; 
import javafx.scene.input.MouseEvent; 
import javafx.scene.layout.AnchorPane; 
import javafx.scene.layout.Region; 
import javafx.scene.layout.StackPane; 
import javafx.scene.paint.Color; 
import javafx.scene.text.Font; 
import sample.Main; 
import javafx.stage.Stage; 
 
import org.slf4j.Logger; 
import org.slf4j.LoggerFactory; 
 
import java.io.DataInputStream; 
import java.io.DataOutputStream; 
import java.io.IOException; 
import java.net.InetAddress; 
import java.net.Socket; 
import java.net.URL; 
import java.util.ArrayList; 
import java.util.Hashtable; 
import java.util.List; 
import java.util.ResourceBundle; 
 
public class Controller { 
	 
	 
	public ListaEsquemas listaEsquemas =new ListaEsquemas(); 
	private ArrayList<String> categorylist; 
	 
	public void datos() throws EsquemaNuloException { 
		listaEsquemas.emptyList(); 
		listaEsquemas.addLast(new Esquema("Paraiso,Nombre:Narnia:1,Region:INT:1")); 
		listaEsquemas.addLast(new Esquema("Tornado,Nombre:Nodico:3,Rango:INT:5,Clima:Seco:2,Rango de Humedad:INT:4,DATA:STRING:7,dat7:INT:7,AC:STRING:4,dat4:INT:4")); 
		listaEsquemas.addLast(new Esquema("Bosque Seco,Clima:Seco:2,Rango de Humedad:INT:4")); 
		listaEsquemas.addLast(new Esquema("Ragos4,AC:STRING:4,dat4:INT:4")); 
		listaEsquemas.addLast(new Esquema("Yeso5,BN:STRING:5,dat5:INT:5")); 
		listaEsquemas.addLast(new Esquema("Nononosi6,SI:STRING:6,dat6:INT:6")); 
		listaEsquemas.addLast(new Esquema("NoSQ7,DATA:STRING:7,dat7:INT:7")); 
		listaEsquemas.addLast(new Esquema("Pea8,RUNNER:STRING:8,dat8:INT:8")); 
		listaEsquemas.addLast(new Esquema("Oie9,DRIVE:STRING:9,dat9:INT:9")); 
		System.out.println("cargados...."); 
	} 
//	private ArrayList<String> array = new ArrayList<String>(); 
	 
	//FXML// 
	@FXML // fx:id="base" 
	public  AnchorPane base= new AnchorPane(); 
	@FXML // fx:id="diagramsList" 
	private ListView<String> diagramsList = new ListView<String>(); 
	private ObservableList<String> diagrams = FXCollections.observableArrayList(); 
    @FXML // fx:id="choicebox" 
    private ChoiceBox<String> choicebox = new ChoiceBox<String>(); 
	private ObservableList<String> availableChoices = FXCollections.observableArrayList(); 
	@FXML //fx:id="tableview" 
	private ObservableList<Esquema> descriptionEsquemas = FXCollections.observableArrayList(); 
	@FXML // fx:id="screen" 
	private AnchorPane screen = new AnchorPane(); 
    @FXML // fx:id="search" 
    private TextField searchSTR =  new TextField(); 
    @FXML // fx:id="add" 
    private Button add =  new Button(); 
    //FMLX nothingMessage 
	private final StackPane stack = new StackPane(); 
	private final Label nothing =new Label(); 
	//objextmapper 
    private ObjectMapper objectMapper=new ObjectMapper(); 
 
    public static Logger log = LoggerFactory.getLogger(Controller.class); 
 
    //initializer 
    public void initialize(URL location, ResourceBundle resouces) { 
    	try { 
			this.loadDiagrams(null); 
		}  
    	catch (IOException e) { 
			System.out.println("Dio este error |||| "+e); 
			log.debug("error");
		} 
    } 
     
    @FXML  //evento de anadir 
	public void addButtonAction(ActionEvent event) throws IOException { 
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("addingWindow.fxml")); 
		Parent addingWindow = (Parent) fxmlLoader.load(); 
		Stage addingStage = new Stage(); 
		addingStage.setScene(new Scene(addingWindow)); 
		addingStage.show(); 
	} 
 
	//	Stage addingStage = new Stage(); 
//	Parent addingWindow; 
//	FXMLLoader fxmloader = new FXMLLoader(getClass().getResource("new.fxml")); 
//	root=loader.load(); 
//	Controller controller= loader.getController(); 
//	addingStage.setScene(new Scene(addingWindow));//me crea una nuevo escenario y me carga todo lo del fxml 
//	addingStage.setResizable(false); 
//	addingStage.show(); 
//	primaryStage.close(); 
//     
    @FXML //evento de buscar esquemas 
    public void searchButtonAction(ActionEvent event) throws IOException { 
//    	String selectedChoice = choicebox.getSelectionModel().getSelectedItem(); 
//    	System.out.print(selectedChoice); 
    	String detail = searchSTR.getText(); 
    	System.out.println("detail: "+detail); 
    	if (detail.isEmpty()){ 
    		detail=null; 
    		System.out.println("detail: "+detail); 
    	}this.loadDiagrams(detail); 
    } 
 
	//loadDiagramas. 
    private void loadDiagrams(String detail) throws IOException { 
    	this.datos(); 
    	this.diagrams.removeAll(diagrams); 
    	if (detail!=null) { // si estoy buscando.... 
    		this.loadDiagrams_buscaraux(detail); 
//			System.out.println("DIAGRAMAS cargados "+diagrams); 
			this.diagramsList.setItems(diagrams); 
			return;} 
    	this.addNamesOneByOne(); 
//		System.out.println("DIAGRAMAS cargados "+diagrams); 
	    this.diagramsList.setItems(diagrams); 
    } 
 
	//buscar coincidencias para cargarlas. 
    private void loadDiagrams_buscaraux(String detail) { 
//		System.out.println("Entra en buscar"); 
//		System.out.println(this.listaEsquemas.getLargo()); 
    	ArrayList<Esquema> coincided = this.listaEsquemas.buscarcoincidencias(detail); 
    	if (coincided.isEmpty()) return; 
    	for (int i=0; i<coincided.size(); i++){ 
    		Esquema element =coincided.get(i); 
			System.out.println("ESQUEMA encontrado :"+" |name "+element.getNombre()+" |id "+element.getID()); 
    		this.diagrams.add(element.getNombre()); //add coincidences... 
    	}coincided.removeAll(coincided); 
    } 
 
	//cargar todos los diagramas. 
	private void addNamesOneByOne() { 
			Nodo<Esquema>tmp = this.listaEsquemas.getHead(); 
//			System.out.println("NODO ESQUEMA "+tmp); 
			while (tmp!=null){ 
		    	this.diagrams.add(tmp.getNodo().getNombre()); 
		    	tmp=tmp.next; 
		    }return; 
	} 
 
	//evento de seleccion de diagrama. 
    public void displaySelectedDiagram(MouseEvent event) throws IOException { 
    	String selectedDiagram = diagramsList.getSelectionModel().getSelectedItem(); 
    	System.out.println("SELECTED DIAGRAM IN LISTVIEW: "+selectedDiagram); 
		if (selectedDiagram == null) { 
			searchSTR.setPromptText("diagram detail"); //set to default. 
			screen.getChildren().clear(); //clean screen 
			stack.getChildren().clear(); //clean stack 
			this.nothingMessage();//mensaje visual. 
			return; 
		} 
		screen.getChildren().clear(); //clean 
		//nombre general del esquema (CURSO, PROFESOR, EDIFICIO...) 
		searchSTR.setPromptText(selectedDiagram); //set to watching. 
		Esquema e = this.listaEsquemas.buscar(selectedDiagram); 
		//nombre de cada columna del esquema (ID, NOMBRE, APELLIDO, CARNE, CEDULA) 
//		System.out.println("array keys: "+this.categorylist); 
		this.setChoiceBox(e.obtenercolumnas());  
		this.setTableView(e);} 
	//acomodar los keys en choicebox. 
    private void setChoiceBox(ListaString categorylist2) {  
		this.choicebox.getItems().clear(); 
		int cont=0;  
    	while (cont!= categorylist2.getLargo()) {  
    	    String option=categorylist2.buscar(cont);  
    		System.out.println("CURRENT CHOICE: "+option);  
    		this.availableChoices.add(option);//.getNombre());  //para cuando pueda conseguir bien la lista con las categorias correspondientes.  
    		cont++; 
    	}  
		this.choicebox.setItems(availableChoices); 
		this.choicebox.getSelectionModel().select(0);}	 
    	//despues antes de hacer search... usar esto.. para obtener la opcion seleccionada como string. e ir a esa fila determinada. 
//    	String selectedChoice = choicebox.getSelectionModel().getSelectedItem(); 
    private void setTableView(Esquema esquema) throws EsquemaNuloException { 
    	this.descriptionEsquemas.addAll(esquema); 
    } 
	//muestra que no se busca nada. 
	private void nothingMessage() { 
		nothing.setText("Nothing Displayed"); nothing.setTextFill(Color.GRAY); nothing.setFont(new Font("Arial", 25)); 
		stack.prefWidthProperty().bind(screen.widthProperty());  
		stack.prefHeightProperty().bind(screen.heightProperty());  
		stack.setAlignment(Pos.CENTER);  
		stack.getChildren().add(nothing); 
	    screen.getChildren().add(stack); 
	} 
 
	public void hola() throws IOException { 
//	        System.out.println("estoy en la funcion"); 
//	        ObjectNode perro = objectMapper.createObjectNode(); 
//	        perro.put("hola", 82).put("charanco","tornado"); 
//	        perro.put("hola", "tornado"); 
//	        System.out.println(perro); 
	   try { 
	       Integer.parseInt("23.4"); 
	       } 
	       catch (NumberFormatException e){ 
	           e.getCause(); 
	       } 
	} 
	 
	    public void table() throws IOException { 
//	        Hashtable hashtable= new Hashtable(); 
//	        hashtable.put("hello", "pico" ); 
//	        hashtable.put("Paco",85); 
//	        hashtable.put("Paco",89); 
//	        Hashtable holga = new Hashtable(); 
//	        holga= (Hashtable) hashtable.clone(); 
//	        System.out.println(objectMapper.readValue(objectMapper.writeValueAsString(holga),Hashtable.class)); 
//	 
//	        System.out.println(holga); 
	    Cliente cliente=new Cliente(); 
	    String respuesta=cliente.crearEsquema("Esquema1,dato1:STRING:6,dato2:INT:3"); 
	    System.out.println(respuesta); 
//	        DataInputStream datosentrada = new DataInputStream(client.getInputStream()); 
//	        log.debug("entrada se conecto"); 
//	        Datos datosrecibidos = objectMapper.readValue(datosentrada.readUTF(), Datos.class); 
//	        log.debug("se creo objeto"); 
	    } 
	} 
