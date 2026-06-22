package Model;

import java.io.File;

public class SongNode {
    String songPath;

    SongNode next;
    SongNode prev;
    private boolean isFavorite = false;

    public SongNode(String songPath) {
        this.songPath = songPath;
    }

    public String getSongPath() {
        return songPath;
    }

    public SongNode getNext() {
        return next;
    }

    public boolean isFavorite() { return isFavorite; }

    public void setFavorite(boolean favorite) { this.isFavorite = favorite; }

    @Override
    public String toString() {

        if (songPath == null) {
            return "Unknown Song";
        }

        String filename = new File(songPath).getName(); // otomatis ambil nama file saja

        int dotIndex = filename.lastIndexOf('.');

        return (dotIndex > 0)
                ? filename.substring(0, dotIndex)
                : filename;
    }
}
