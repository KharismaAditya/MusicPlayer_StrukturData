package Model;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;

public class Playlist {

    SongNode head;
    SongNode tail;
    SongNode current;

    private int size = 0;

    public void addSongFromFile(File sourceFile) {
        try {
            // Pastikan folder music ada
            File musicFolder = new File("music");
            if (!musicFolder.exists()) {
                musicFolder.mkdirs();
            }

            Path source = sourceFile.toPath();
            Path destination = Paths.get("music", sourceFile.getName());

            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);

            addSong(destination.toString());

            System.out.println("Berhasil menambahkan: " + sourceFile.getName());

        } catch (Exception e) {
            System.out.println("Gagal menambahkan lagu: " + e.getMessage());
        }
    }

    public void addSongsFromFolder(String folderPath) {
        File musicFolder = new File(folderPath);
        File[] musicFiles = musicFolder.listFiles((dir, name) ->
                name.toLowerCase().endsWith(".mp3") ||
                        name.toLowerCase().endsWith(".wav") ||
                        name.toLowerCase().endsWith(".aac")
        );

        if (musicFiles != null && musicFiles.length > 0) {
            Arrays.sort(musicFiles);
            for (File file : musicFiles) {
                addSong(file.getPath());
            }
        } else {
            System.out.println("Folder kosong atau tidak ditemukan: " + folderPath);
        }
    }

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

    public void deleteNode(SongNode targetNode) {
        if (targetNode == null || head == null) return;

        deleteSongFile(targetNode.songPath);

        // If it's the current song, try to slide pointer down the list first
        if (current == targetNode) {
            nextSong();
            if (current == targetNode) current = null; // List had only 1 track
        }

        // Step 1: Change left neighbor links
        if (targetNode == head) {
            head = targetNode.next;
        } else {
            targetNode.prev.next = targetNode.next;
        }

        // Step 2: Change right neighbor links
        if (targetNode == tail) {
            tail = targetNode.prev;
        } else {
            targetNode.next.prev = targetNode.prev;
        }

        // Step 3: Sever references entirely for garbage collection isolation
        targetNode.next = null;
        targetNode.prev = null;

        size--;
    }

    private void deleteSongFile(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                boolean deleted = file.delete();
                if (deleted) {
                    System.out.println("File terhapus: " + path);
                } else {
                    System.out.println("Gagal menghapus file: " + path);
                }
            }
        } catch (Exception e) {
            System.out.println("Error saat menghapus file: " + e.getMessage());
        }
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
        if (current == null) {
            return "No song";
        }

        String filename = new File(getCurrentSong()).getName(); // handle / dan \ otomatis

        int dotIndex = filename.lastIndexOf('.');

        return (dotIndex > 0)
                ? filename.substring(0, dotIndex)
                : filename;
    }
}