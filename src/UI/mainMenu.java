package UI;

import Model.Playlist;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;


public class mainMenu extends Application {

    MediaPlayer mediaPlayer;
    Playlist playlist = new Playlist();

    Slider progressSlider;
    Slider volumeSlider = new Slider(0,100,20);
    Label currentSongName = new  Label();

    SongListBoxIsrael songUI = new SongListBoxIsrael(this, playlist, mediaPlayer);

    @Override
    public void start(Stage stage) {
        playlist.addSong("music/Ai No Sukima.mp3");
        playlist.addSong("music/No Title.mp3");
        playlist.addSong("music/Utopia.mp3");
        playlist.addSong("music/My Dearest.mp3");
        playlist.addSong("music/UmaSugi!.mp3");
        loadSong();

        volumeSlider.setPrefSize(100,50);
        progressSlider = new Slider();


        mediaPlayer.setOnReady(() -> {
            Duration totalDuration = mediaPlayer.getMedia().getDuration();
            progressSlider.setMin(0);
            progressSlider.setMax(totalDuration.toSeconds());
        });

        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            if (!progressSlider.isValueChanging()) {
                progressSlider.setValue(newTime.toSeconds());
            }
        });

        VBox root = new VBox();
        root.setStyle("-fx-background-color: #40555B;");
        root.setMinSize(550,300); root.setAlignment(Pos.BOTTOM_CENTER);

        GridPane grid = songUI.songList();

        HBox musicInfoBox = new HBox();
        musicInfoBox.setMinSize(550,60);

        Interaction inReact = new Interaction(this,playlist,progressSlider,currentSongName, volumeSlider);
        HBox interactionBox = inReact.InteractionBox();

        titleInfo tiInfo = new titleInfo(currentSongName,progressSlider,playlist);
        VBox titleInfoBox = tiInfo.titleInfoBox();

        volumeBox volBox = new volumeBox(volumeSlider);
        VBox volumeBox = volBox.volumeBoxInt();

        musicInfoBox.getChildren().addAll(interactionBox,titleInfoBox,volumeBox);
        root.getChildren().addAll(grid,musicInfoBox);

        root.getStylesheets().add(
                getClass().getResource("/styles.css").toExternalForm()
        );

        stage.setScene(new Scene(root));
        stage.setResizable(false);
        Font.loadFont(getClass().getResourceAsStream("/PressStart2P.ttf"), 10);
        stage.show();
    }

    public void loadSong() {

        if(mediaPlayer != null) {
            mediaPlayer.stop();
        }

        Media media = new Media(
                new File(playlist.getCurrentSong())
                        .toURI()
                        .toString()
        );

        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setVolume(0.2);

        mediaPlayer.setOnReady(() -> {

            Duration totalDuration = mediaPlayer.getMedia().getDuration();

            progressSlider.setMin(0);
            progressSlider.setMax(totalDuration.toSeconds());
            progressSlider.setValue(0);
        });

        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {

            if(!progressSlider.isValueChanging()) {
                progressSlider.setValue(newTime.toSeconds());
            }
        });

        mediaPlayer.setOnEndOfMedia(() -> {

            playlist.nextSong();

            loadSong();

            mediaPlayer.play();
        });
    }

    public void playSong(String songPath){

        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }

        Media media = new Media(
                new File(songPath)
                        .toURI()
                        .toString()
        );

        mediaPlayer = new MediaPlayer(media);

        mediaPlayer.setVolume(
                volumeSlider.getValue()/100
        );

        currentSongName.setText(
                playlist.toString()
        );

        mediaPlayer.setOnReady(() -> {

            Duration totalDuration =
                    mediaPlayer.getMedia().getDuration();

            progressSlider.setMin(0);
            progressSlider.setMax(totalDuration.toSeconds());
            progressSlider.setValue(0);
        });

        mediaPlayer.currentTimeProperty().addListener(
                (obs, oldTime, newTime) -> {

                    if(!progressSlider.isValueChanging()){

                        progressSlider.setValue(
                                newTime.toSeconds()
                        );
                    }
                }
        );

        mediaPlayer.setOnEndOfMedia(() -> {

            playlist.nextSong();

            playSong(
                    playlist.getCurrentSong()
            );
        });

        mediaPlayer.play();
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public static void main(String[] args) {
        launch();
    }
}