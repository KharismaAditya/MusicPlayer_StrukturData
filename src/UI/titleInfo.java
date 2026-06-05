package UI;

import Model.Playlist;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;

public class titleInfo {
    Label currentSongName;
    Slider progressSlider;
    Playlist playlist;
    SongListBoxIsrael songUI;

    public titleInfo(Label currentSongName,Slider progressSlider,Playlist playlist, SongListBoxIsrael songUI) {
        this.currentSongName = currentSongName;
        this.progressSlider = progressSlider;
        this.playlist = playlist;
        this.songUI = songUI;
    }

    public VBox titleInfoBox() {
        VBox titleInfoBox = new VBox();
        titleInfoBox.setStyle("-fx-background-color: #A4D8BF;");
        titleInfoBox.setMinSize(170,60);titleInfoBox.setAlignment(Pos.CENTER);
        titleInfoBox.setSpacing(10);
        String songRn = playlist.toString();
        currentSongName.setText(songRn);

        Button addButton = addButton();
        addButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Pilih Lagu");

            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav", "*.aac")
            );

            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                playlist.addSongFromFile(selectedFile);
                songUI.refreshSongList();
            }
        });

        titleInfoBox.getChildren().addAll(currentSongName,progressSlider, addButton);

        return titleInfoBox;
    }

    public Button addButton() {
        Button add = new Button("ADD");
        add.setMinSize(40,20);
        onStyle(add);
        return add;
    }

    public void onStyle(Button btn){
        btn.setStyle("-fx-background-color: #5CBC59;-fx-font-size: 8px;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #3D7F3B; -fx-font-size: 8px;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #5CBC59;-fx-font-size: 8px;"));
    }
}
