package p1.xml;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import p1.model.Song;

/* Methoden des Interface ContentHandler werden vom Push Parser aufgerufen
 * und können dazu verwendet werden, gezielt Informationen aus dem XML-Dokument zu 
 * filtern.
 */
public class PlaylistContentHandler implements ContentHandler {	
	private int selYear;                 // ausgwähltes Jahr
	private List<Song> songs = new ArrayList<>(); // Liste mit den Songs aus dem Jahr selYear
	private String charSequence;         // im XML-File "gefundene" Zeichensequenzen
	
	/*  ***   Aufgabe (3c) *** */

	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
	}

	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
	}	
	
	public List<Song> getResult() {
		return this.songs;
	}
	
	public PlaylistContentHandler(int year) {
		this.selYear = year;
	}
	
	/* ********************************************************************************** */
	/*                               ab hier nichts mehr ändern                           */
	/* ********************************************************************************** */

	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		charSequence = new String(ch,start,length);	
	}
	
	@Override
	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	

	@Override
	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub
		
	}

}
