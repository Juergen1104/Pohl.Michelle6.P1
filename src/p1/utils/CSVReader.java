package p1.utils;

import p1.model.Song;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {



    /* ***   Aufgabenteil (1a) *** */

    /**
     * Liest eine CSV-Datei und gibt eine Liste von Song-Objekten zurück.
     *
     * @param filePath Pfad zur CSV-Datei
     * @return Liste von Songs
     * @throws IOException Falls ein Fehler beim Lesen der Datei auftritt
     */

    public List<Song> parseFile(String filePath) throws IOException {

        List<Song> songs = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            br.readLine(); // Erste Zeile überspringen (Header)

            while ((line = br.readLine()) != null) {
                Song song = parseLine(line);
                if (song != null) {
                    songs.add(song);
                }
            }
        } catch (IOException e) {
            System.err.println("Fehler beim Lesen der Datei: " + e.getMessage());
        }
        return songs;
    }




    /* ***   Aufgabenteil (1b) *** */

    /**
     * Parst eine Zeile der CSV-Datei und erstellt ein Song-Objekt.
     *
     * @param line Eine Zeile aus der CSV-Datei
     * @return Ein Song-Objekt oder null, falls die Zeile ungültig ist
     */
    private Song parseLine(String line) {

        try {
            String[] parts = line.split(";");
            if (parts.length < 9) { // Mindestanzahl an Spalten prüfen
                return null;
            }

            String title = parts[0].trim();
            String artist = parts[1].trim();
            int year = Integer.parseInt(parts[3].trim());
            long streams = Long.parseLong(parts[8].trim());

            return new Song(title, artist, year, streams);
        } catch (NumberFormatException e) {
            System.err.println("Fehler beim Parsen der Zeile: " + line);
            return null;
        }
    }

}
    
    
