package p1.xml;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import p1.model.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Liest eine Playlist aus einer XML-Datei.
 */
public class XMLReader {

    /*  ***   Aufgabe (3a) *** */

    /**
     * Parst eine XML-Datei und erstellt eine Playlist.
     *
     * @param filePath Pfad zur XML-Datei
     * @return Liste der Songs in der Playlist
     * @throws Exception Wenn ein Fehler beim Lesen der Datei auftritt
     */
    public List<Song> readPlaylistFromXML(String filePath) throws Exception {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(new File(filePath));

        //Hier implementieren:
        List<Song> songs = new ArrayList<>();
        try {

            // XML-Datei mit SAXBuilder einlesen
            File xmlFile = new File(filePath);
            if (!xmlFile.exists()) {
                throw new IllegalArgumentException("Die Datei existiert nicht: " + filePath);
            }
            document = saxBuilder.build(xmlFile);

            // Wurzelelement holen
            Element rootElement = document.getRootElement();

            // Alle "song"-Elemente durchlaufen
            List<Element> songElements = rootElement.getChildren("song");
            for (Element songElement : songElements) {
                // Werte aus Unterelementen extrahieren
                String title = songElement.getChildText("title");
                String artist = songElement.getChildText("artist");
                int year = Integer.parseInt(songElement.getChildText("year"));
                long streams = Long.parseLong(songElement.getChildText("streams"));

                // Song-Objekt erstellen und zur Liste hinzufügen
                songs.add(new Song(title, artist, year, streams));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return songs;
    }

}