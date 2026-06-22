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
    private GridPane grid;

    // Add a state tracker to the UI class
    private boolean viewingFavorites = false;

    public SongListBoxIsrael(mainMenu player, Playlist playlist, MediaPlayer mediaPlayer) {
        this.player = player;
        this.playlist = playlist;
        this.mediaPlayer = mediaPlayer;
    }

    public VBox songList(){
        VBox container = new VBox(10); // 10px spacing between switch bar and grid
        container.setPadding(new Insets(10));

        // ---- VIEW SWITCHER BAR ----
        HBox switchBar = new HBox(10); // Row layout for buttons
        switchBar.setAlignment(Pos.CENTER_LEFT);

        Button allSongsBtn = new Button("All Songs");
        Button favoritesBtn = new Button("♡ Favorites");

        // Style the switch buttons
        onStyle(allSongsBtn, "#283E23", "#233020");
        onStyle(favoritesBtn, "#757575", "#616161"); // Default gray for inactive favorites

        // Handle clicking "All Songs"
        allSongsBtn.setOnAction(e -> {
            viewingFavorites = false;
            onStyle(allSongsBtn, "#283E23", "#233020"); // Highlight Green
            onStyle(favoritesBtn, "#757575", "#616161"); // Fade Gray
            populateGrid(); // Re-traverse list
        });

        // Handle clicking "Favorites"
        favoritesBtn.setOnAction(e -> {
            viewingFavorites = true;
            onStyle(allSongsBtn, "#757575", "#616161"); // Fade Gray
            onStyle(favoritesBtn, "#D32F2F", "#B71C1C"); // Highlight Red
            populateGrid(); // Re-traverse list with filter active
        });

        switchBar.getChildren().addAll(allSongsBtn, favoritesBtn);
        // ---------------------------

        // ---- THE SONG GRID ----
        grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setMinSize(550,240);

        populateGrid(); // Initial build

        // Add both rows into the main layout container
        container.getChildren().addAll(switchBar, grid);
        return container;
    }

    private void populateGrid() {
        grid.getChildren().clear();

        int columns = 3; // Reduced slightly to 3 columns to give the widened delete boxes breathing room
        int index = 0;

        SongNode current = playlist.getHead();

        while(current != null){
            if (viewingFavorites && !current.isFavorite()) {
                current = current.getNext();
                continue;
            }

            HBox item = songItem(playlist, current);

            int col = index % columns;
            int row = index / columns;

            grid.add(item, col, row);

            current = current.getNext();
            index++;
        }
    }

    public void refreshSongList() {
        populateGrid();
    }

    public HBox songItem(Playlist playlist, SongNode node){
        HBox songItem = new HBox();
        songItem.setPrefSize(190, 30); // Expanded from 160 to 190 to comfortably fit all 3 buttons

        // 1. Song Title Label Container
        VBox songTitle = new VBox();
        Label songTitleLabel = new Label(node.toString());
        songTitleLabel.setStyle("-fx-text-fill: black; -fx-padding: 0 5 0 5;");

        songTitle.setMinSize(100,30);
        songTitle.setMaxWidth(100); // Prevents text overflow from pushing buttons out of view
        songTitle.setAlignment(Pos.CENTER_LEFT);
        songTitle.setStyle("-fx-background-color: #89B280;");
        songTitle.getChildren().add(songTitleLabel);

        // 2. Play Button Container
        VBox playSongBox = new VBox();
        playSongBox.setMinSize(30,30); playSongBox.setAlignment(Pos.CENTER);
        playSongBox.setStyle("-fx-background-color: #FFFFFF");
        Button playButton = playSong();

        playButton.setOnAction(e -> {
            playlist.setCurrent(node);
            player.playSong(node.getSongPath());
        });
        playSongBox.getChildren().add(playButton);

        // 3. NEW: Favorite Button Container
        VBox favoriteSongBox = new VBox();
        favoriteSongBox.setMinSize(30, 30); favoriteSongBox.setAlignment(Pos.CENTER);
        favoriteSongBox.setStyle("-fx-background-color: #FFFFFF");
        Button favoriteButton = favoriteSongBtn(node);

        favoriteButton.setOnAction(e -> {
            // Toggle the favorite boolean flag inside the SongNode
            node.setFavorite(!node.isFavorite());

            // Refresh the grid to visually update the heart icon color immediately
            // (and auto-remove it if the user is currently filtering by favorites)
            populateGrid();
        });
        favoriteSongBox.getChildren().add(favoriteButton);

        // 4. Delete Button Container
        VBox deleteSongBox = new VBox();
        deleteSongBox.setMinSize(30, 30); deleteSongBox.setAlignment(Pos.CENTER);
        deleteSongBox.setStyle("-fx-background-color: #FFFFFF");
        Button deleteButton = deleteSongBtn();

        deleteButton.setOnAction(e -> {
            // Check if the song being deleted is the one currently playing
            boolean isCurrentPlaying = (playlist.getCurrentSong() != null &&
                    playlist.getCurrentSong().equals(node.getSongPath()));

            // Stop media player execution safely if it's running
            if (isCurrentPlaying && player.mediaPlayer != null) {
                player.mediaPlayer.stop();
            }

            // Call the Doubly Linked List middle-deletion utility method
            playlist.deleteNode(node);

            // If the deleted song was playing, set up the next tracks or clear title
            if (isCurrentPlaying) {
                if (playlist.getCurrentSong() != null) {
                    player.playSong(playlist.getCurrentSong());
                } else {
                    System.out.println("No songs left in the playlist.");
                }
            }

            // Trigger structural UI update
            populateGrid();
        });
        deleteSongBox.getChildren().add(deleteButton);

        // Append all components together side by side (Title -> Play -> Favorite -> Delete)
        songItem.getChildren().addAll(songTitle, playSongBox, favoriteSongBox, deleteSongBox);
        return songItem;
    }

    // Helper method to generate and dynamically style the Favorite button based on its state
    public Button favoriteSongBtn(SongNode node) {
        // Show a filled red heart if favorited, or an empty heart outline if not
        String heartIcon = node.isFavorite() ? "★" : "☆";
        Button btn = new Button(heartIcon);
        btn.setMinSize(20, 20);

        // Choose theme colors based on favorited status (Red variation vs soft Slate Gray)
        String baseColor = node.isFavorite() ? "#e0d319" : "#757575";
        String hoverColor = node.isFavorite() ? "#ada424" : "#616161";

        onStyle(btn, baseColor, hoverColor);
        return btn;
    }

    public Button playSong(){
        Button btn = new Button("►");
        btn.setMinSize(20,20);
        onStyle(btn, "#283E23", "#233020");
        return btn;
    }

    // Secondary UI styling generator for the Delete button
    public Button deleteSongBtn() {
        Button btn = new Button("🗑");
        btn.setMinSize(20, 20);
        onStyle(btn, "#A63A3A", "#822B2B"); // Red variations for structural delete theme
        return btn;
    }

    // Refactored dynamically to support both button styling contexts
    public void onStyle(Button btn, String baseColor, String hoverColor){
        btn.setStyle("-fx-background-color: " + baseColor + "; -fx-font-size: 8px; -fx-text-fill: white; -fx-cursor: hand;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: " + hoverColor + "; -fx-font-size: 8px; -fx-text-fill: white; -fx-cursor: hand;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: " + baseColor + "; -fx-font-size: 8px; -fx-text-fill: white; -fx-cursor: hand;"));
    }
}