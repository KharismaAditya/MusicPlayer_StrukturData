package UI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class volumeBox {
    Slider volumeSlider;

    public volumeBox(Slider volumeSlider) {
        this.volumeSlider = volumeSlider;
    }

    public VBox volumeBoxInt(){
        Label volumeLabel = new Label("Volume");

        VBox volumeBox1 = new VBox();
        volumeBox1.setPadding(new Insets(10));
        volumeBox1.setStyle("-fx-background-color: #FFF8EC;");
        volumeBox1.setMinSize(130,60);volumeBox1.setAlignment(Pos.CENTER);
        volumeBox1.getChildren().addAll(volumeLabel,volumeSlider);

        return volumeBox1;
    }
}
