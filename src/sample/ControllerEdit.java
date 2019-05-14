package sample;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Errores.DatoNoExistenteException;
import Errores.DatosUsadosException;
import Errores.EsquemaNuloException;
import Errores.TamanoException;
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


public class ControllerEdit {

	@FXML
	private TableView<String[]> tableview = new TableView<String[]>();
	private ObservableList<String[]> datosObservable = FXCollections.observableArrayList(); 
	
    @FXML private AnchorPane screen = new AnchorPane();
    
    @FXML private Button cancel = new Button();
    
    @FXML public Region veil = new Region();
    
    @FXML private Text idText; 
    
    @FXML private Text nameText;

    @FXML private TextField deletefield;
    
    @FXML private TextField addfield;
    
    @FXML private Button deleteButton;
    
    @FXML private Button addButton;

    
    @FXML private TextField newnameText;
    
    //Variables
    public static Esquema e;
	@SuppressWarnings("unused")
	private ListaEsquemas listaEsquemas=new ListaEsquemas();
	private ArrayList<String> columnsarray = new ArrayList<String>();
    public ArrayList<String[]> jdata = new ArrayList<String[]>(); //Here is the dat
    public static Logger log = LoggerFactory.getLogger(ControllerEdit.class);
	public static int numerofilas;
	private String[] c;
	private Cliente cliente;
	private boolean saved = false;
	
	//LISTA CON LOS ESPACIOS DE EDICION
	private NodoList<String> oldValue = new NodoList<>();
	private NodoList<String> newValue = new NodoList<>();
	private NodoList<String> newValueCOLUMNA = new NodoList<>();
	private NodoList<Integer> newValueROW = new NodoList<>();
	private NodoList<String> historialEliminadas = new NodoList<>(); 
	private NodoList<String> historialAgregados = new NodoList<>();

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
		ControllerEdit.e = e;
		System.out.println("\n\rCliente: "+cliente+"---> Esquema Recibido: "+ControllerEdit.e);
		this.setTexts();
		this.setCxF(false);
	}
	private void setTexts() {
		this.nameText.setText(e.getNombre());
		this.idText.setText(e.getID());
		veil.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8)");

	}
	private void setCxF(boolean eliminados){
		this.clean();
		/* Obtener cada columna String*/
    	this.c = e.obtenercolumnas().getStringArraycolumnas(e.obtenercolumnas());
//        System.out.println("size of this.c " +this.c.length);
    	/* Obtener cada fila String[]*/
   		String datos = this.cliente.buscartodoslosdatos(e.getNombre()).getDatos();
   		System.out.println("\nDATOS DE BUSCAR TODOS --> "+datos);
   		if (datos == null) {
   			this.jdata.add( new String[this.c.length-1]);
   			showTable(); return;}
   		datos = ControllerEdit.addnumber(datos);
   		System.out.println("DATOS CON NUMERO DE FILAS AGREGADOS --> "+datos+"\n");		
   		this.filas = this.toStringArray(datos.split(";"));
   		if (eliminados==true) {
   			ArrayList<String[]> porborrar = new ArrayList<>();
   			for (int id = 0; id < filas.size(); id++) { 
   	    		if (historialEliminadas.contains(filas.get(id)[1])) {
   	    			System.out.println("Por eliminar ---> "+ filas.get(id)[1]);
   	    			porborrar.add(filas.get(id));}
   			}this.filas.removeAll(porborrar);
   		}this.jdata.addAll(filas);
		showTable();
	}
	public static String addnumber(String sinnumer) {
		int remind = 0;
		int cont = 0;
		int last = (sinnumer.split(";").length-1);
		String concate = "";
		for (int s=0; s<=sinnumer.length()-1;s++) {
			if (sinnumer.charAt (s) == ';') {
				String substring = sinnumer.substring(remind, s);
//				System.out.println("\n\rSubstring "+substring);
				substring = String.valueOf(cont)+","+substring;
//				System.out.println("String "+substring);
				concate = concate+substring;
//				System.out.println("concate "+concate);
				remind = s;
				cont++;
			}
		
			else if (cont == last && s==sinnumer.length()-1) {
//				System.out.println("remind "+remind);
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
                    firstNameCol.setMinWidth(150);
                	firstNameCol.setResizable(true);
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
	
	
	}public static ArrayList<String> addNamesxIDOneByOne() { //cargar todos los diagramas.
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
    		try {String respuesta = this.cliente.acciones(cliente, e, "cambiarnombreesquema", newname, null, 0);
    			if (respuesta == "Nombre cambiado") {
    				this.newnameText.setPromptText(newname);
    				this.saved = true; System.out.print(respuesta);
    				return;} 
			} catch (IOException n) {
				System.out.print("Ocurre un error al cambiar nombre"); n.printStackTrace();}
    }
	
	public void addRows(String fila) {
    	String toAdd_id = addfield.getText();
    	/*intento de agre directamente*/
    	try {e.anadirfila(fila);}
    	//sino solo muestra mensaje y retorna.
    	catch (DatosUsadosException | NumberFormatException | TamanoException | DatoNoExistenteException | EsquemaNuloException e) {
    		UserMessage message = new UserMessage(AlertType.INFORMATION, toAdd_id, "Sorry an ERROR has ocurred while adding ");message.show(); this.addfield.clear(); return;}
    	
    	/*si logra aregar*/
    	this.historialAgregados.addLast(toAdd_id);
		this.addfield.clear();
		this.setCxF(true);
    }
	
	@FXML 
    private void addRowsWindow(ActionEvent event) {
		//casilla de nombre de la fila a eliminar.
		String toAdd_id = addfield.getText();
		/*verificar si aparece dentro de las filas*/
		int apariciones = 0;
		for (int id = 0; id < filas.size(); id++) { 
			System.out.println(filas.get(id)[1]);
			if (filas.get(id)[1].equals(toAdd_id)) apariciones++;}
		/*ver si aparece o que*/
		System.out.println("\nAPARICIONES "+apariciones);
		if (apariciones != 0) { UserMessage message = new UserMessage(AlertType.INFORMATION, "has been used." +"\n\rEach person is unique and unrepeatable, \rthe same applies to your IDs ",  "Hmm' sorry bruh! "+ toAdd_id); message.show(); this.addfield.clear(); return;}
		System.out.println("\nAPARICIONES "+apariciones);
		try {
	        Stage addRowStage = new Stage();
	        Parent root;
	        FXMLLoader loader;
	        loader = new FXMLLoader(getClass().getResource("addRows.fxml"));
	        root=loader.load();
	        addRowStage.setTitle("Edit Diagram");
	        ControllerAddRows controller= loader.getController();
	        controller.getID(toAdd_id, e.obtenercolumnas().getStringArraycolumnas(e.obtenercolumnas()),ControllerEdit.addNamesxIDOneByOne(), veil);
	        Scene scene = new Scene(root);
	        addRowStage.setScene(scene);//me crea una nuevo escenario y me carga todo lo del fxml
	        addRowStage.setResizable(false);
	        addRowStage.getIcons().add(new Image("/Media/edit.png"));
			veil.setVisible(true);
	        addRowStage.initModality(Modality.APPLICATION_MODAL);
	        System.out.println(addRowStage.showingProperty());
	        addRowStage.showAndWait();

	        
	    } catch (IOException error) {
	    	error.printStackTrace();}
	}
	@FXML  
    private void addRowsHistory(ActionEvent event) {
    	for (int info = 0; info < this.historialEliminadas.getLargo(); info++) { 
    		String Respuesta = this.cliente.eliminardatos(e.getNombre(), historialAgregados.get(info));
    		if (Respuesta!="datos anadidos") { 
    	    	UserMessage message = new UserMessage(AlertType.INFORMATION, historialAgregados.get(info), "Sorry an ERROR has ocurred while adding ");
    	    	message.show();
    			this.addfield.clear();
    			return;
    		}
		}
    	//Llamar a cancel, con la condicion de que se salva.
		log.debug("Se logra editar el esquema --> "+ e.getNombre());
		}
    
    @FXML
    public void deleteRows(ActionEvent event) {
    	//casilla de nombre de la fila a eliminar.
    	String toDelete_id = deletefield.getText();
    	
    	/*verificar si aparece dentro de las filas*/
    	int apariciones = 0;
    	for (int id = 0; id < filas.size(); id++) { 
//    		System.out.println(filas.get(id)[1]);
    		if (filas.get(id)[1].equals(toDelete_id)) apariciones++;}
    	
    	/*ver si aparece o que*/
    	if (apariciones == 0) { UserMessage message = new UserMessage(AlertType.INFORMATION, "\n\rNon-existent things can't be \ndeleted... " + toDelete_id, "Hmm' You can't fool me! \nI know everything!"); message.show(); this.deletefield.clear(); return;}
//		System.out.println(apariciones);

    	/*intento de borrar directamente*/
    	try {e.eliminarfila(toDelete_id);}
    	//sino sollo muestra mensaje y retorna.
    	catch (DatosUsadosException e) {UserMessage message = new UserMessage(AlertType.INFORMATION, toDelete_id, "Sorry an ERROR has ocurred while deleting ");message.show(); this.deletefield.clear(); return;}
    	
    	/*si logra eliminar*/
    	this.historialEliminadas.addLast(toDelete_id);
		this.deletefield.clear();
		this.setCxF(true);
    }
    	
    	
    private void deleteRowsHistory(ActionEvent event) {
    	for (int id = 0; id < this.historialEliminadas.getLargo(); id++) { 
    		String Respuesta = this.cliente.eliminardatos(e.getNombre(), historialEliminadas.get(id));
    		if (Respuesta!="Fila elimanda") { 
    	    	UserMessage message = new UserMessage(AlertType.INFORMATION, historialEliminadas.get(id), "Sorry an ERROR has ocurred while deleting ");
    	    	message.show();
    			this.deletefield.clear();
    			return;
    		}
		}
    	this.saved = true;
    	this.cancel(event); //Llamar a cancel, con la condicion de que se salva.
		log.debug("Se logra editar el esquema --> "+ e.getNombre());
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
//    	System.out.println("verify --> "+verify);
    	/*Si se salva correctamente*/
    	if (verify == size) {  
    		this.addRowsHistory(event);
    		this.deleteRowsHistory(event);
    		log.debug("Se logra editar el esquema --> "+ e.getNombre());}
    	UserMessage message = new UserMessage(AlertType.INFORMATION, this.newValue.get(verify), "Sorry an ERROR has ocurred while setting "+this.oldValue.get(verify)+" to the new value ");
    	message.show();
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
		this.setCxF(false);
	}
	
	
}
