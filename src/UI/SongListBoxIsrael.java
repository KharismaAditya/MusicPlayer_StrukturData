package UI;

import Model.Playlist;
import Model.SongNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaPlayer;

public class SongListBoxIsrael {
    private final mainMenu player;
    Playlist playlist;
    MediaPlayer mediaPlayer;
    private GridPane grid; // simpan referensi grid

    public SongListBoxIsrael(mainMenu player, Playlist playlist, MediaPlayer mediaPlayer) {
        this.player = player;
        this.playlist = playlist;
        this.mediaPlayer = mediaPlayer;
    }

    public GridPane songList(){
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);

        populateGrid(); // pisahkan logika pengisian ke method sendiri

        grid.setMinSize(550,240);
        grid.setPadding(new Insets(10));
        return grid;
    }

    // Method baru: isi grid dengan lagu dari playlist
    private void populateGrid() {
        grid.getChildren().clear(); // bersihkan dulu sebelum diisi ulang

        int columns = 4;
        int index = 0;

        SongNode current = playlist.getHead();

        while(current != null){
            HBox item = songItem(playlist, current);

            int col = index % columns;
            int row = index / columns;

            grid.add(item, col, row);

            current = current.getNext();
            index++;
        }
    }

    // Method baru: dipanggil dari luar ketika ada lagu baru ditambahkan
    public void refreshSongList() {
        populateGrid();
    }

    public HBox songItem(Playlist playlist, SongNode node){
        HBox songItem = new HBox();
        songItem.setPrefSize(125,30);

        VBox songTitle = new VBox();
        Label songTitleLabel = new Label(node.toString());

        songTitle.setMinSize(95,30); songTitle.setAlignment(Pos.CENTER);
        songTitle.setStyle("-fx-background-color: #89B280");
        songTitle.getChildren().add(songTitleLabel);

        VBox playSongBox = new VBox();
        playSongBox.setMinSize(30,30); playSongBox.setAlignment(Pos.CENTER);
        playSongBox.setStyle("-fx-background-color: #FFFFFF");
        Button playButton = playSong();

        playButton.setOnAction(e -> {
            playlist.setCurrent(node);
            player.playSong(node.getSongPath());
        });

        playSongBox.getChildren().add(playButton);

        songItem.getChildren().addAll(songTitle, playSongBox);
        return songItem;
    }

    public Button playSong(){
        Button btn = new Button("►");
        btn.setMinSize(20,20);
        onStyle(btn);
        return btn;
    }

    public void onStyle(Button btn){
        btn.setStyle("-fx-background-color: #283E23;-fx-font-size: 8px;-fx-text-fill: white;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #233020; -fx-font-size: 8px; -fx-text-fill: white;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: #283E23;-fx-font-size: 8px; -fx-text-fill: white;"));
    }
}