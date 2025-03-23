package p1.xml;

import java.io.File;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.io.FileNotFoundException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import p1.model.Song;

public class XML_PushParser {
	private File file;
	private int year;
	
	public XML_PushParser(String xmlFile, int year) {
		this.file = new File(xmlFile);
		this.year = year;
	}
	
	public List<Song> parseFile() {
		List<Song> result = null;
		try {
			SAXParserFactory parserFactory = SAXParserFactory.newInstance();
			parserFactory.setNamespaceAware(true);
			SAXParser parser;
			parser = parserFactory.newSAXParser();
			org.xml.sax.XMLReader xmlReader = parser.getXMLReader();
			
			FileReader reader = new FileReader(this.file);
			InputSource inputSource = new InputSource(reader);
			
			
			PlaylistContentHandler handler = new PlaylistContentHandler(year);
			xmlReader.setContentHandler(handler);
			xmlReader.parse(inputSource);			
		    result = handler.getResult();						
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
