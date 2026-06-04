package UI;

import Model.Playlist;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;

public class titleInfo {
    Label currentSongName;
    Slider progressSlider;
    Playlist playlist;

    public titleInfo(Label currentSongName,Slider progressSlider,Playlist playlist) {
        this.currentSongName = currentSongName;
        this.progressSlider = progressSlider;
        this.playlist = playlist;
    }

    public VBox titleInfoBox() {
        VBox titleInfoBox = new VBox();
        titleInfoBox.setStyle("-fx-background-color: #A4D8BF;");
        titleInfoBox.setMinSize(170,60);titleInfoBox.setAlignment(Pos.CENTER);
        String songRn = playlist.toString();
        currentSongName.setText(songRn);
        titleInfoBox.getChildren().addAll(currentSongName,progressSlider);

        return titleInfoBox;
    };
}
