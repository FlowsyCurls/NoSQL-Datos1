package sample;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Errores.DatoNoExistenteException;
import Errores.DatosUsadosException;
import Errores.EsquemaNuloException;
import Listas.Esquema;
import Listas.ListaEsquemas;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
	//FXML
    @FXML private TableView tableview = new TableView(); 
	private ObservableList tabledata = FXCollections.observableArrayList(); 
    @FXML private AnchorPane screen = new AnchorPane();
    @FXML private Button cancel = new Button();
    @FXML private Text idText; 
    @FXML private Text nameText; 
    @FXML private TextField newnameText;
    //Variables
    private Esquema e;
	private ListaEsquemas listaEsquemas=new ListaEsquemas();
	private ArrayList<String> columnsarray = new ArrayList<String>();
    
    public static Logger log = LoggerFactory.getLogger(ControllerEdit.class);
 
    //initializer 
    public void initialize() throws IOException {
    	this.datos();
//		this.setTexts();
//		this.initTable(e);
//		this.loadData();
    }
    //////////
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
	/////////
    public void getEsquema(Esquema e) {
    	this.e = e;
    	System.out.println("Esquema Recibido: "+this.e);
    }
	private void setTexts() {
		this.nameText.setText(e.getNombre());
		this.idText.setText(e.getID());
	}

    @FXML
    public void edit(ActionEvent event) {
    	prueba();
    	String newName = this.newnameText.getText();
    	if (newName.trim().isEmpty()) { this.newnameText.clear(); return;}
    	
    	//despues de todo.
//    	this.clean();
    }
    public void prueba() {
		try {
			this.datos();
			buildData();
//			this.initTable(e);
//			this.setTexts();
		} catch (DatosUsadosException e) {
			// TODO Auto-generated catch block
			System.out.print(e);
		}
	}
//	private void initTable(Esquema e) {
//		this.columnsarray = e.obtenercolumnas().getArraycolumnas(e.obtenercolumnas());
//		this.initColumns(e.obtenercolumnas().getArraycolumnas(e.obtenercolumnas()));
//		System.out.print(this.columnsarray);
//	}
//
//    private void initColumns(ArrayList<String> array) {
//	    	this.tableview.getItems().clear(); this.tabledata.clear();
////	    	this.tabledata.addAll(array);
//	    	for (int i = 0; i<array.size(); i++) {
//	    		String columnname = array.get(i);
//	    		if (i==0) {
//		    		TableColumn<Esquema, String> c = new TableColumn<> ("ID");
//		    		c.setCellValueFactory(new PropertyValueFactory<Esquema, String>("ID"));
//		    		System.out.println("joiiiiiiiiiiiiiin "+"ID");
//					this.tableview.getColumns().add(c);
//					c.setId("ID");;
//					c.setStyle( "-fx-alignment: CENTER;");
//					this.editColumns(c);
//					continue;
//	    		
//	    		}
//	    		if (e.getMijoinsArray().contains(columnname)) {
//		    		TableColumn<Esquema, String> c = new TableColumn<> (columnname);
//		    		c.setCellValueFactory(new PropertyValueFactory<Esquema, String>(columnname));
//		    		System.out.println("joiiiiiiiiiiiiiin "+columnname);
//					this.tableview.getColumns().add(c);
//					c.setId(columnname);;
//					c.setStyle( "-fx-alignment: CENTER;");
//					this.editColumns(c);
//					continue;
//	    		}
//	    		TableColumn<Esquema, String> c = new TableColumn<> (columnname);
//	    		c.setCellValueFactory(new PropertyValueFactory<Esquema, String>(columnname));
//	    		c.setId(columnname);;
//	    		System.out.println("editableeeeeeeee "+columnname);
//				this.tableview.getColumns().add(c);
//				c.setStyle( "-fx-alignment: CENTER;");
//				this.editColumns(c);
//	    	}
//			this.tableview.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//			this.tableview.prefWidthProperty().bind(screen.widthProperty());
//			this.tableview.prefHeightProperty().bind(screen.heightProperty());
//			this.screen.getChildren().add(this.tableview); 
//	    	log.debug("TableColumns Finished --> "+"crea las columnas");
//	    }
//    private void editColumns(TableColumn<Esquema, String> c) {
//    	c.setCellFactory(TextFieldTableCell.forTableColumn());
//    	c.setOnEditCommit(d ->{
//				try {
//					d.getTableView().getItems().get(d.getTablePosition().getRow()).cambiardato(e.getID(),
//							d.getTableView().getColumns().get(d.getTablePosition().getColumn()).getId(),
//									d.getNewValue());
//				} catch (DatoNoExistenteException e) {
//					e.printStackTrace();
//				}
//    	});
//    	this.tableview.setEditable(true);
//    }
    
//    private void loadData() throws EsquemaNuloException, DatosUsadosException {
//    	ObservableList<Esquema> esq = FXCollections.observableArrayList();
//    	for (int i=0; i<1; i++) {
//    		esq.add(new Esquema("Yeso5,BN:STRING:5,dat5:INT:5")); 
//    		esq.add(new Esquema("Nononosi6,SI:STRING:6,dat6:INT:6")); 
//    		esq.add(new Esquema("NoSQ7,DATA:STRING:7,dat7:INT:7")); 
//    		esq.add(new Esquema("Pea8,RUNNER:STRING:8,dat8:INT:8")); 
//    		esq.add(new Esquema("Oie9,DRIVE:STRING:9,dat9:INT:9")); 
//    	}
//    	this.tableview.setItems(esq);
//    	log.debug("TableData Finished --> "+"asigna los datos...");
//    }
	//    	TablePosition pos = table.getSelectionModel().getSelectedCells().get(0);
	//    	int row = pos.getRow();
	//
	//    	// Item here is the table view type:
	//    	Item item = table.getItems().get(row);
	//
	//    	TableColumn col = pos.getTableColumn();
	//
	//    	// this gives the value in the selected cell:
	//    	String data = (String) col.getCellObservableValue(item).getValue();

	@FXML
	public void cancel(ActionEvent event) {
	    try {
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

	private void clean() {
    	this.tabledata.clear();
    	this.tableview.getItems().clear(); 
    	this.screen.getChildren().clear();
    }
	
    @SuppressWarnings("unchecked")
	public void buildData() {
        try {
            /**
             * ********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
             */
            for (int i = 0; i < 7; i++) {
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(""+(i + 1));
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });
                this.tableview.getColumns().addAll(col);
                System.out.println("Column [" + i + "] ");
            }
 
            /**
             * ******************************
             * Data added to ObservableList *
             *******************************
             */
            int f=0;
			while (!(f==9)) {
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= 7; i++) {
                    //Iterate Column
                    row.add(this.columnsarray.get(i));
                }
                System.out.println("Row [1] added "+f+" " + row);
                this.tabledata.add(row.toString());
                f++;
            }
 
            //FINALLY ADDED TO TableView
            this.tableview.setItems(this.tabledata);
//            this.screen.getChildren().add(this.tableview); 
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
}
