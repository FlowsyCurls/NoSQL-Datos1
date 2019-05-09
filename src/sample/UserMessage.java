package sample;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UserMessage extends Alert{
	private Image image = null;
	
	public UserMessage(AlertType type, String message, String content) {
		super(type);
		this.getDialogPane();
		if (type==AlertType.ERROR) {this.image = new Image("/Media/warning.png");}
		else if (type==AlertType.INFORMATION) {this.image = new Image("/Media/info.png");}
		else if (type == AlertType.CONFIRMATION) {
			this.image = new Image("/Media/confirm.png");
			ImageView imageView = new ImageView(image);
			this.setHeaderText(null);
			this.setContentText(content+" "+message+"'s editation?");
			this.setGraphic(imageView);
			this.getButtonTypes().set(0, ButtonType.YES);
			return;
		}
		ImageView imageView = new ImageView(image);
		//--
		this.setHeaderText(null);
		this.setContentText(content+":   "+message);
		this.setGraphic(imageView);
	}

}
