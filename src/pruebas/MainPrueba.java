package pruebas;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainPrueba extends Application {
	
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
    	
        Parent root = FXMLLoader.load(getClass().getResource("prueba.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("NOSQL World");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("/Media/nosql.png"));
        primaryStage.show();
    }
    public void loadInfo() {
    	
    }
}

//package sample;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.LinkedList;
//import java.util.List;
//
//import Listas.Esquema;
//import javafx.beans.property.ReadOnlyStringWrapper;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.Button;
//import javafx.scene.control.TableColumn;
//import javafx.scene.control.TableView;
//import javafx.scene.control.TextField;
//import javafx.scene.control.cell.TextFieldTableCell;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.text.Text;
//
//public class ControllerPrueba {
//
//    @FXML
//    private Text idText;
//
//    @FXML
//    private Text nameText;
//
//    @FXML
//    private TextField newnameText;
//
//    @FXML
//    private AnchorPane screen;
//
//    @FXML
//    private Button cancel;
//    
//	@FXML
//	private TableView<ArrayList<String>> tableview = new TableView<ArrayList<String>>();
//	private ObservableList<ArrayList<String>> datosObservable = FXCollections.observableArrayList(); 
//    public List<ArrayList<String>> jdata = new LinkedList<>(); //Here is the data
//
//	private ArrayList<String> c = new ArrayList<String>();
//	private ArrayList<String> f = new ArrayList<String>();
//	private ArrayList<String> f1 = new ArrayList<String>();
//	private ArrayList<String> f2 = new ArrayList<String>();
//	private ArrayList<String> f3 = new ArrayList<String>();
//
//	
//    public void initialize() throws IOException {
//    	c.add("a"); c.add("b"); c.add("c"); c.add("d"); c.add("a");
//       	f.add("0");f.add("1");f.add("2");f.add("3");f.add("4");
//       	f2.add("0");
//       	f3.add("3");
//       	f1.add("se logra");
//    	jdata.add(c);
//    	jdata.add(f);
//    	jdata.add(f1);
//    	jdata.add(f2);
//    	jdata.add(f3);
//    }
//    
//    public void showTable() {
//        int size = c.size(); //number of the columns
//
//        for (int i = 0; i < size; i++) {
//            TableColumn<ArrayList<String>, String> firstNameCol = new TableColumn<>("\tC"+(i+1)+" \t");
//            firstNameCol.setMinWidth(20);
//            int index = i ;
//            
//            firstNameCol.setCellValueFactory(cellData -> {
//                ArrayList<String> rowData = cellData.getValue();
//                if (index >= rowData.size()) {
//                    return new ReadOnlyStringWrapper("");
//                } else {
//                    String cellValue = rowData.get(index);
//                    return new ReadOnlyStringWrapper(cellValue);
//                }
//            });
//            firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
//            firstNameCol.setOnEditCommit(event -> {
//                ArrayList<String> row = event.getRowValue();
//                row.set(index, event.getNewValue());
//            });
//            tableview.getColumns().add(firstNameCol);
//        }
//        datosObservable = FXCollections.observableList(jdata);
//        tableview.getItems().addAll(datosObservable);
//        screen.getChildren().add(tableview);
//    
//    
//  private void editColumns(TableColumn<ArrayList<String>, String> c) {
//	c.setCellFactory(TextFieldTableCell.forTableColumn());
//	c.setOnEditCommit(d ->{
//			try {
//				d.getTableView().getItems().get(d.getTablePosition().getRow()).cambiardato(e.getID(),
//						d.getTableView().getColumns().get(d.getTablePosition().getColumn()).getId(),
//								d.getNewValue());
//			} catch (DatoNoExistenteException e) {
//				e.printStackTrace();
//			}
//	});
//	this.tableview.setEditable(true);
//}
//
//    @FXML
//    void cancel(ActionEvent event) {
//
//    }
//
//    @FXML
//    void edit(ActionEvent event) {
//    	showTable();
//    }
//}

