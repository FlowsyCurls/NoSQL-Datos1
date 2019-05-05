package sample;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;


public class ControllerAdd {
	
    @FXML // fx:id="choicebox" 
    private ChoiceBox<String> choicebox = new ChoiceBox<String>(); 
	private ObservableList<String> types = FXCollections.observableArrayList(); 
	int posx = 40, posy = 40;
	
	
    private final void setChoiceBoxWithLabel() {
    	//text
		TextField key = new TextField ();
		key.setTranslateX(posx+60); key.setTranslateY(posy); key.setMaxWidth(145); key.setPromptText("set key");
		//choicekey
		choicebox = new ChoiceBox<String>();
		choicebox.setTranslateX(posx); choicebox.setTranslateY(posy);
    	types.addAll("STRING","INT","DOUBLE","LONG","FLOAT");
		choicebox.setItems(types); 
		choicebox.getSelectionModel().select(0);
		this.posy+=40;
    }
    
    public void drawing(int rows) throws IOException {
    	System.out.print("ERRRRR: ");
		while (rows!=0) {
			this.setChoiceBoxWithLabel();
			rows--;
		}
    }
}
