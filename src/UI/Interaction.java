package UI;

import Model.Playlist;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.util.Duration;


public class Interaction  {
    private final mainMenu player;
    Playlist playlist;
    Slider progressSlider;
    Slider volumeSlider;
    Label currentSongName;
    interactionComp comp = new  interactionComp();

    public Interaction(mainMenu player, Playlist playlist, Slider progressSlider, Label currentSongName, Slider volumeSlider) {
        this.player = player;
        this.playlist = playlist;
        this.progressSlider = progressSlider;
        this.currentSongName = currentSongName;
        this.volumeSlider = volumeSlider;
    }

    public HBox InteractionBox() {
        Button playBtn = comp.primary1("PLAY");
        Button pauseBtn = comp.primary1("PAUSE");
        Button nextBtn = comp.primary2(">");
        Button prevBtn = comp.primary2("<");
        playBtn.setOnAction(e -> {
            if(player.getMediaPlayer() != null){
                player.getMediaPlayer().play();
            }
        });

        pauseBtn.setOnAction(e -> {
            if(player.getMediaPlayer() != null){
                player.getMediaPlayer().pause();
            }
        });

        nextBtn.setOnAction(e -> {

            playlist.nextSong();

            currentSongName.setText(
                    playlist.toString()
            );

            player.playSong(
                    playlist.getCurrentSong()
            );
        });

        prevBtn.setOnAction(e -> {

            playlist.prevSong();

            currentSongName.setText(
                    playlist.toString()
            );

            player.playSong(
                    playlist.getCurrentSong()
            );
        });

        progressSlider.valueChangingProperty()
                .addListener((obs, wasChanging, isChanging) -> {

                    if(!isChanging &&
                            player.getMediaPlayer() != null){

                        player.getMediaPlayer().seek(
                                Duration.seconds(
                                        progressSlider.getValue()
                                )
                        );
                    }
                });

        volumeSlider.valueProperty()
                .addListener((obs, oldVal, newVal) -> {

                    if(player.getMediaPlayer() != null){

                        player.getMediaPlayer().setVolume(
                                newVal.doubleValue()/100
                        );
                    }
                });

        HBox interactionBox = new HBox();
        interactionBox.setStyle("-fx-background-color: #989898;");
        interactionBox.setMinSize(250,60);interactionBox.setAlignment(Pos.CENTER);
        interactionBox.setSpacing(12);
        interactionBox.getChildren().addAll(prevBtn,playBtn,pauseBtn,nextBtn);
        return interactionBox;
    }
}
