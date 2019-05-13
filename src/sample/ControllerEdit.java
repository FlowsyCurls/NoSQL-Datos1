package sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Errores.DatosUsadosException;
import Errores.EsquemaNuloException;
import Listas.Esquema;
import Listas.ListaEsquemas;
import Listas.Nodo;
import Listas.NodoList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class ControllerEdit {

	@FXML
	private TableView<String[]> tableview = new TableView<String[]>();
	private ObservableList<String[]> datosObservable = FXCollections.observableArrayList(); 
	
    @FXML private AnchorPane screen = new AnchorPane();
    
    @FXML private Button cancel = new Button();
    
    @FXML private Text idText; 
    
    @FXML private Text nameText; 
    
    @FXML private TextField newnameText;
    
    //Variables
    private Esquema e;
	@SuppressWarnings("unused")
	private ListaEsquemas listaEsquemas=new ListaEsquemas();
	private ArrayList<String> columnsarray = new ArrayList<String>();
    public ArrayList<String[]> jdata = new ArrayList<String[]>(); //Here is the dat
    public static Logger log = LoggerFactory.getLogger(ControllerEdit.class);
	private String[] c;
	private Cliente cliente;
	private boolean saved = false;
	
	//LISTA CON LOS ESPACIOS DE EDICION
	private NodoList<String> oldValue = new NodoList<>();
	private NodoList<String> newValue = new NodoList<>();
	private NodoList<String> newValueCOLUMNA = new NodoList<>();
	private NodoList<Integer> newValueROW = new NodoList<>();
	private int lasteditedCOLUMN = 0;
	private int lasteditedROW = 0;
	private ArrayList<String[]> filas = new ArrayList<String[]>();
    
    //initializer 
    public void initialize() throws IOException {}
    private void clean() {
		this.jdata.clear();
		this.columnsarray.clear();
		this.datosObservable.clear();
		this.tableview.getColumns().clear();
		this.tableview.getItems().clear();
		this.screen.getChildren().clear();
	}
	//conexion principal controller
	public void getEsquema(Esquema e, Cliente cliente) {
		this.cliente = cliente;
		this.e = e;
		System.out.println("Cliente: "+cliente+"---> Esquema Recibido: "+this.e);
		this.setTexts();
		this.setCxF();
	}
	private void setTexts() {
		this.nameText.setText(e.getNombre());
		this.idText.setText(e.getID());
	}
	private void setCxF(){
		this.clean();
		/* Obtener cada columna String*/
    	this.c = e.obtenercolumnas().getStringArraycolumnas(e.obtenercolumnas());
    	/* Obtener cada fila String[]*/
   		String datos = this.cliente.buscartodoslosdatos(e.getNombre()).getDatos();
   		System.out.println(datos);		
   		this.filas = this.toStringArray(datos.split(";"));
		this.jdata.addAll(filas);
		showTable();
	}private ArrayList<String[]> toStringArray(String[] toConvert) {
		ArrayList<String[]> list = new ArrayList<>();
		for (int j=0; j<=toConvert.length-1;j++) {
			list.add(toConvert[j].split(","));
			System.out.println(list.get(j)[0]);
		}
		return list;
	
	}private void showTable() {
        int size = this.c.length; //number of the columns
        ArrayList<String> nombres = this.addNamesxIDOneByOne();
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
                    	firstNameCol.setEditable(false);}
                	return new ReadOnlyStringWrapper(cellValue);}});
            firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            
        	/*Asignar propiedad editable a la celda.*/
	            firstNameCol.setOnEditCommit(event -> {
	                String[] row = event.getRowValue();
	                /*Prints.*/
//	                System.out.println("old value "+event.getOldValue());
//            		System.out.println("new value "+event.getNewValue());
//            		System.out.println("column pos"+(event.getTablePosition().getColumn()-1));
//            		System.out.println("row pos "+event.getTablePosition().getRow());
	                if (this.lasteditedCOLUMN == event.getTablePosition().getColumn() && 
	                		this.lasteditedROW == event.getTablePosition().getRow()) {
	            		this.newValue.removeLast();
	            		this.newValueCOLUMNA.removeLast();
	            		this.newValueROW.removeLast();
	                }
	                oldValue.addLast(event.getOldValue());
            		newValue.addLast(event.getNewValue());
            		newValueCOLUMNA.addLast(this.c[event.getTablePosition().getColumn()]);
            		lasteditedCOLUMN = event.getTablePosition().getColumn();
            		newValueROW.addLast(event.getTablePosition().getRow());
            		lasteditedROW = event.getTablePosition().getRow();
            		//cambio
            		row[index] = event.getNewValue();
	                return;
	            });
	            firstNameCol.setMinWidth(200);
	            firstNameCol.setStyle("-fx-alignment: CENTER;");
	            this.tableview.getColumns().add(firstNameCol);
        }
        datosObservable = FXCollections.observableList(this.jdata);
        tableview.getItems().addAll(this.datosObservable);
        tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableview.prefWidthProperty().bind(screen.widthProperty());
        tableview.prefHeightProperty().bind(screen.heightProperty());  
        tableview.setFocusTraversable(false);
		screen.getChildren().add(tableview); 
		log.debug("TableColumns Finished --> "+"table cargado..."); 
	this.tableview.setEditable(true);
	}private ArrayList<String> addNamesxIDOneByOne() { //cargar todos los diagramas.
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
    	else { String str = ("Are you sure to change "+e.getNombre()+"'s name to "+newname);
    		UserMessage message = new UserMessage(AlertType.CONFIRMATION, null, str);
    		Optional<ButtonType> result = message.showAndWait();
    		if ((result.get() == ButtonType.CANCEL))return;}
    		try {String respuesta = cliente.acciones(cliente, e, "cambiarnombreesquema", newname, null, 0);
    			if (respuesta == "Nombre cambiado") {
    				this.newnameText.setPromptText(newname);
    				this.saved = true; System.out.print(respuesta);
    				return;} 
			} catch (IOException n) {
				System.out.print("Ocurre un error al cambiar nombre"); n.printStackTrace();}
    }
    
    @FXML
    public void edit(ActionEvent event) throws DatosUsadosException, EsquemaNuloException {
    	int verify = 0;
    	int size = this.newValue.getLargo();
    	System.out.println("AQUI VA EL LARGO   "+size);
    	for (int x = 0; x < size; x++) {
    		System.out.println("ID CAMBIAR "+filas.get(this.newValueROW.get(x))[0]);
    		System.out.println(this.newValueCOLUMNA.get(x));
    		String r = this.cliente.cambiardato(e.getNombre(), filas.get(this.newValueROW.get(x))[0], this.newValueCOLUMNA.get(x), this.newValue.get(x));
    		verify = x;
    		if (!(r.equals("dato cambiado"))) {
    			System.out.println(r);
    			log.debug("No se logra editar --> "+this.newValueCOLUMNA.get(x));
    			break;}
    		continue;
    	}
    	System.out.println("verify --> "+verify);
    	/*Si se salva correctamente*/
    	if (verify == size) { this.saved = true; 
    		this.cancel(event); //Llamar a cancel, con la condicion de que se salva
    		log.debug("Se logra editar el esquema --> "+ e.getNombre());}
//    	UserMessage message = new UserMessage(AlertType.INFORMATION, this.newValue.get(verify), "Sorry an ERROR has ocurred while setting "+this.oldValue.get(verify)+" to the new value ");
//    	message.show();
    	return;
    	}
    
	@FXML
	public void cancel(ActionEvent event) {
		if (!this.saved) {
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
	public void clear(ActionEvent event) {
		this.newnameText.clear();
		this.oldValue.empty();
		this.newValue.empty();
		this.newValueCOLUMNA.empty();
		this.newValueROW.empty();
		this.setCxF();
	}
	
	
}
