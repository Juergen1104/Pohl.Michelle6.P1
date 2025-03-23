package p1.xml;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import p1.model.Song;


public class XMLWriter {

	
	/* ***   Aufgabe (3b) *** */
	/**
	 * Schreibt eine Playlist in ein XML-Dokument und speichert diese in einer Datei.
	 * @param filePath     Der Pfad zur XML-Datei, in die die Playlist geschrieben wird.
	 * @param playlistName Der Name der Playlist.
	 * @param playlist     Die Liste der Songs, die in die Playlist geschrieben werden sollen.
	 * @throws IOException Wenn ein Fehler beim Schreiben der Datei auftritt.
	 */
    public void writePlaylistToXML(String filePath, String playlistName, List<Song> playlist) throws IOException {
    	 Element rootElement = new Element("playlist");
         rootElement.setAttribute("name", playlistName);
         //Hier implementieren:
         
    	

         
         
        //Ab hier nichts mehr ändern
        Document document = new Document(rootElement);
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            XMLOutputter outputter = new XMLOutputter(Format.getPrettyFormat());
            outputter.output(document, fos);
        }	
    }
    
}