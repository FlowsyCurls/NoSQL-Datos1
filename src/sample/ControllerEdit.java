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
		this.c = this.e.obtenercolumnas().getStringArraycolumnas(e.obtenercolumnas());
		System.out.println(c);
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
		/* aqui va la logica 
		 * de agarrar 
		 * el esquema 
		 * y conseguir 
		 * sus datos 
		 * en un String[]*/
		showTable();
	}
    private void showTable() {
        int size = this.c.length; //number of the columns
        for (int i = 0; i < size; i++) {
            TableColumn<String[], String> firstNameCol = new TableColumn<>(this.c[i]);
            int index = i ;
            firstNameCol.setCellValueFactory(cellData -> {
                String[] rowData = cellData.getValue();
                if (index >= rowData.length) {return new ReadOnlyStringWrapper("");}
                else {String cellValue = rowData[index];
                	return new ReadOnlyStringWrapper(cellValue);}});
            firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            firstNameCol.setOnEditCommit(event -> {
                String[] row = event.getRowValue();
                try {String respuesta = this.cliente.acciones(cliente, e, "cambiardato", event.getNewValue(), this.c[index], index);
                	if (respuesta=="dato cambiado") {
                		row[index] = event.getNewValue();
                		this.saved = true; System.out.print(respuesta);
                		return;} 
                	UserMessage message = new UserMessage(AlertType.INFORMATION, event.getNewValue(), "Sorry an ERROR has ocurred while setting the new value");
					message.show();
					this.setCxF();
                }catch (NullPointerException | IOException a) {
					UserMessage message = new UserMessage(AlertType.INFORMATION, event.getNewValue(), "Sorry an ERROR has ocurred while setting the new value");
					message.show();
					System.out.print("Ocurre un error al editar"); a.printStackTrace();}});
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
	this.tableview.setEditable(true);
	}
    
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
	public void cancel(ActionEvent event) {
		if (!this.saved) {
			UserMessage message = new UserMessage(AlertType.CONFIRMATION, null, "Are you sure you want to CANCEL "+e.getNombre()+"'s editation?");
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
    public void edit(ActionEvent event) throws DatosUsadosException, EsquemaNuloException {
		this.setCxF();
    	if (!this.saved) return; //llamar a funcion que verifica que se salvó
    	//despues de todo.
		this.cancel(event);}
}
