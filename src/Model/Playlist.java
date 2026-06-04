package Model;

public class Playlist {

    SongNode head;
    SongNode tail;
    SongNode current;

    private int size = 0;

    // tambah lagu
    public void addSong(String path) {

        SongNode newSong = new SongNode(path);

        if(head == null) {

            head = newSong;
            tail = newSong;
            current = head;

        } else {

            tail.next = newSong;
            newSong.prev = tail;
            tail = newSong;
        }

        size++;
    }

    public int size() {
        return size;
    }

    // next song
    public void nextSong() {

        if(current.next != null) {
            current = current.next;
        } else {
            current = head;
        }
    }

    // previous song
    public void prevSong() {

        if(current.prev != null) {
            current = current.prev;
        } else {
            current = tail;
        }
    }

    public String getCurrentSong() {
        return current.songPath;
    }

    public SongNode getHead() {
        return head;
    }

    public void setCurrent(SongNode node){
        current = node;
    }

    @Override
    public String toString() {

        if(current == null) {
            return "No song";
        }

        String filename = getCurrentSong()
                .substring(getCurrentSong().lastIndexOf("/") + 1);

        int dotIndex = filename.lastIndexOf('.');

        return (dotIndex > 0)
                ? filename.substring(0, dotIndex)
                : filename;
    }
}