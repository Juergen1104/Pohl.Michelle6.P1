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

        
        
        return null; //Platzhalter
    }
    
}