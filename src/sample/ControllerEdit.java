package sample;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Errores.DatoNoExistenteException;
import Errores.DatosUsadosException;
import Errores.EsquemaNuloException;
import Listas.Esquema;
import Listas.ListaEsquemas;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringPropertyBase;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;


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
	private ListaEsquemas listaEsquemas=new ListaEsquemas();
	private ArrayList<String> columnsarray = new ArrayList<String>();
    public ArrayList<String[]> jdata = new ArrayList<String[]>(); //Here is the dat
    public static Logger log = LoggerFactory.getLogger(ControllerEdit.class);
	private String[] c;
	private boolean saved = false;
    
    //initializer 
    public void initialize() throws IOException {
    	this.datos();
		this.setTexts();
		this.setCxF();
    }
	public void datos() throws DatosUsadosException {
		try {
//			listaEsquemas.emptyList();
			listaEsquemas.addLast(new Esquema("Tornado,Nombre:Nodico:3,Zona:INT:5,Clima:Seco:2,Rango de Humedad:INT:4,Gravedad:STRING:7,Velocidad:INT:7,Precauciones:STRING:4,Extras:INT:4")); 

		for (int i=0; i!=11; i++){ 
			String str = "E"+i+",Nombre"+i+":STRING"+":"+i+",Rango"+i+":INT"+":"+i;
			System.out.println(str);

				listaEsquemas.addLast(new Esquema(str));
			}
		listaEsquemas.addLast(new Esquema("Paraiso,Nombre:Narnia:1,Region:INT:1")); 
		listaEsquemas.addLast(new Esquema("Bosque Seco,Clima:Seco:2,Rango de Humedad:INT:4")); 
		listaEsquemas.addLast(new Esquema("Ragos4,AC:STRING:4,dat4:INT:4")); 
		listaEsquemas.addLast(new Esquema("Yeso5,BN:STRING:5,dat5:INT:5")); 
		listaEsquemas.addLast(new Esquema("Nononosi6,SI:STRING:6,dat6:INT:6")); 
		listaEsquemas.addLast(new Esquema("NoSQ7,DATA:STRING:7,dat7:INT:7")); 
		listaEsquemas.addLast(new Esquema("Pea8,RUNNER:STRING:8,dat8:INT:8")); 
		listaEsquemas.addLast(new Esquema("Oie9,DRIVE:STRING:9,dat9:INT:9")); 
		System.out.println("cargados....");
		this.e = this.listaEsquemas.getHead().getNodo();
		this.columnsarray.addAll(e.obtenercolumnas().getArraycolumnas(e.obtenercolumnas()));
    	System.out.println(this.columnsarray.size());
		}
	 catch (EsquemaNuloException e1) {
		 System.out.println(e1);
	 }
	}
	
	public void getEsquema(Esquema e) {
    	this.e = e;
    	System.out.println("Esquema Recibido: "+this.e);
    }
	private void setTexts() {
		this.nameText.setText(e.getNombre());
		this.idText.setText(e.getID());
	}
	private void setCxF(){
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
                if (index >= rowData.length) {
                    return new ReadOnlyStringWrapper("");
                } else {
                    String cellValue = rowData[index];
                    return new ReadOnlyStringWrapper(cellValue);
                }
            });
            firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            firstNameCol.setOnEditCommit(event -> {
                String[] row = event.getRowValue();
                row[index] = event.getNewValue();
            });
            firstNameCol.setMinWidth(200);
            firstNameCol.setStyle("-fx-alignment: CENTER;");
            this.tableview.getColumns().add(firstNameCol);
        }
        this.datosObservable = FXCollections.observableList(this.jdata);
        this.tableview.getItems().addAll(this.datosObservable);
        this.tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.tableview.prefWidthProperty().bind(screen.widthProperty());
        this.tableview.prefHeightProperty().bind(screen.heightProperty());  
		screen.getChildren().add(tableview); 
		log.debug("TableColumns Finished --> "+"table cargado..."); 
	this.tableview.setEditable(true);
}

	@FXML
	public void cancel(ActionEvent event) {
	    try {
	    	UserMessage message = new UserMessage(AlertType.CONFIRMATION, e.getNombre(), "Are you sure you want to CANCEL");
	        Optional<ButtonType> result = message.showAndWait();
	        if ((result.get() == ButtonType.CANCEL)){return;}
	    	Stage sampleStage = new Stage();
	        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
	        sampleStage.setTitle("NOSQL");
	        sampleStage.setScene(new Scene(root));//me crea una nuevo escenario y me carga todo lo del fxml
	        sampleStage.getIcons().add(new Image("/Media/nosql.png"));
	        sampleStage.show();
	        Stage currentStage = (Stage) this.cancel.getScene().getWindow();
	        this.clean();
	        currentStage.close();
	      
	        log.debug("Logra abrir ventana otra vez");
	    } catch (IOException e) {
	        System.out.println("Al abrir nuevamente ocurrio esto : "+e);//e.printStackTrace();
	    }
	}

	@FXML
    public void edit(ActionEvent event) throws DatosUsadosException, EsquemaNuloException {
		this.clean();
		this.setCxF();
//    	String newName = this.newnameText.getText();
//    	if (newName.trim().isEmpty()) { this.newnameText.clear(); return;}
//    	//llamar a funcion que verifica que se salvó
////    	this.saved = true;
//    	//despues de todo.
//	    if (this.saved) {
//	    	this.clean();
//	    	this.saved = false;
//	    }
    }
	private void clean() {
    	this.datosObservable.clear();
    	this.tableview.getItems().clear(); 
    	this.screen.getChildren().clear();
    }

//  TablePosition pos =table.getSelectionModel().getSelectedCells().get(0);
//	int row = pos.getRow();
//
//	// Item here is the table view type:
//	Item item = table.getItems().get(row);
//
//	TableColumn col = pos.getTableColumn();
//
//	// this gives the value in the selected cell:
//	String data = (String) col.getCellObservableValue(item).getValue();


//	private void loadData() throws EsquemaNuloException, DatosUsadosException {
//		ObservableList<Esquema> esq = FXCollections.observableArrayList();
//		for (int i=0; i<1; i++) {
//			esq.add(new Esquema("Yeso5,BN:STRING:5,dat5:INT:5")); 
//			esq.add(new Esquema("Nononosi6,SI:STRING:6,dat6:INT:6")); 
//			esq.add(new Esquema("NoSQ7,DATA:STRING:7,dat7:INT:7")); 
//			esq.add(new Esquema("Pea8,RUNNER:STRING:8,dat8:INT:8")); 
//			esq.add(new Esquema("Oie9,DRIVE:STRING:9,dat9:INT:9")); 
//		}
//		this.tableview.setItems(esq);
//		log.debug("TableData Finished --> "+"asigna los datos...");
//	}
    
//    @SuppressWarnings("unchecked")
//	public void buildData() {
//        try {
//            /**
//             * ********************************
//             * TABLE COLUMN ADDED DYNAMICALLY *
//             *********************************
//             */
//            for (int i = 0; i < 7; i++) {
//                //We are using non property style for making dynamic table
//                final int j = i;
//                TableColumn col = new TableColumn(""+(i + 1));
//                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
//                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
//                        return new SimpleStringProperty(param.getValue().get(j).toString());
//                    }
//                });
//                this.tableview.getColumns().addAll(col);
//                System.out.println("Column [" + i + "] ");
//            }
// 
//            /**
//             * ******************************
//             * Data added to ObservableList *
//             *******************************
//             */
//            int f=0;
//			while (!(f==9)) {
//                //Iterate Row
//                ObservableList<String> row = FXCollections.observableArrayList();
//                for (int i = 1; i <= 7; i++) {
//                    //Iterate Column
//                    row.add(this.columnsarray.get(i));
//                }
//                System.out.println("Row [1] added "+f+" " + row);
//                this.tabledata.add(row.toString());
//                f++;
//            }
// 
//            //FINALLY ADDED TO TableView
//            this.tableview.setItems(this.tabledata);
////            this.screen.getChildren().add(this.tableview); 
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("Error on Building Data");
//        }
//    }
}
