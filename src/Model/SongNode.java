package Model;

public class SongNode {
    String songPath;

    SongNode next;
    SongNode prev;

    public SongNode(String songPath) {
        this.songPath = songPath;
    }

    public String getSongPath() {
        return songPath;
    }

    public SongNode getNext() {
        return next;
    }

    @Override
    public String toString() {

        if(songPath == null) {
            return "Unknown Song";
        }

        String filename = songPath;

        // Support Windows (\) dan Linux/Mac (/)
        int slashIndex = Math.max(
                filename.lastIndexOf('/'),
                filename.lastIndexOf('\\')
        );

        if(slashIndex != -1) {
            filename = filename.substring(slashIndex + 1);
        }

        int dotIndex = filename.lastIndexOf('.');

        return (dotIndex > 0)
                ? filename.substring(0, dotIndex)
                : filename;
    }
}
