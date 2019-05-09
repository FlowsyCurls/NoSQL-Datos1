package sample;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class ControllerPrueba {

    @FXML
    private Text idText;

    @FXML
    private Text nameText;

    @FXML
    private TextField newnameText;

    @FXML
    private AnchorPane screen;

    @FXML
    private Button cancel;
    
	@FXML
	private TableView<String[]> tableview = new TableView<String[]>();
	private ObservableList<String[]> datosObservable = FXCollections.observableArrayList(); 
    public List<String[]> jdata = new LinkedList<>(); //Here is the data

	private String[] c = new String[5];
	private String[] f = new String[5];
    
	
    public void initialize() throws IOException {
    	c[0] = "a";
    	c[1] = "b";
    	c[2] = "c";
    	c[3] = "d";
    	c[4] = "e";
       	f[0] = "1";
//    	f[1] = "2";
    	f[2] = "3";
    	f[3] = "4";
    	f[4] = "5";
    	jdata.add(c);
    	jdata.add(f);
    }
    
    public void showTable() {
        int size = c.length; //number of the columns

        for (int i = 0; i < size; i++) {
            TableColumn<String[], String> firstNameCol = new TableColumn<>("\tC"+(i+1)+" \t");
            firstNameCol.setMinWidth(20);
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
            firstNameCol.setMinWidth(200);
            firstNameCol.setStyle("-fx-alignment: CENTER;");
            firstNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
            firstNameCol.setOnEditCommit(event -> {
                String[] row = event.getRowValue();
                row[index] = event.getNewValue();
            });
            tableview.getColumns().add(firstNameCol);
        }
        datosObservable = FXCollections.observableList(jdata);
        tableview.getItems().addAll(datosObservable);
        screen.getChildren().add(tableview);
	this.tableview.setEditable(true);
    }

    @FXML
    void cancel(ActionEvent event) {

    }

    @FXML
    void edit(ActionEvent event) {
    	this.screen.getChildren().clear();
    	this.tableview.getItems().clear();
    	tableview.getColumns().clear();
    	showTable();
    }
}
