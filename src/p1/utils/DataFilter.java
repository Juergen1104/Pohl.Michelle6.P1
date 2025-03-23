package p1.utils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import p1.model.Song;
//import p1.exception.IllegalSongException; //Diese Zeile nach Erstellung von IllegalSongException einkommentieren!


public class DataFilter {

	private List<Song> songs;           // Liste mit allen Songs

	public DataFilter(List<Song> songs) {
		this.songs = songs;
	}

	 /* ***   Aufgabenteil (2b) *** */
 	// Hier SongComparator einfügen:

 
 
	 /* ***   Aufgabenteil (2c) *** */
	 /**
	  * Gibt die Top N Songs (oder wenn n > Länge der Liste: alle Songs) basierend auf der Anzahl der Streams zurück.
	  * @return Eine Liste der Top N Songs
	  */
	 public List<Song> getTopNSongs(int n) {
		 
		  return new ArrayList<>(); // Platzhalter
	 }
	
	 
	 
	 /* ***   Aufgabenteil (2d) *** */
	 /**
	  * Filtert die Songs basierend auf einem bestimmten Künstler.
	  * @param artist Der Name des Künstlers
	  * @return Eine Liste der Songs des angegebenen Künstlers
	  */
	 public List<Song> filterSongsByArtist(String artist) {
	     
		 return new ArrayList<>(); // Platzhalter
	 }
	
	 	
	 /* ***   Aufgabenteil (2e) *** */
	 /**
	  * Sucht den, aufsummiert, am meisten gestreamten Künstler in der Liste
	  * @return Einen String des meistgestreamten Künstlers und die Gesamtanzahl an Streams
	  */
	 public String getMostStreamedArtist() {
	   
	     return "Not yet implemented"; // Platzhalter
	 }


	 /* ***   Aufgabenteil (2f) *** */	
		/**
		 * Sortiert die Songs alphabetisch nach dem Namen des Künstlers.
		 * Wenn mehrere Songs denselben Künstler haben, werden sie zusätzlich
		 * nach der Anzahl der Streams in absteigender Reihenfolge sortiert.
		 * Verwenden Sie bitte einen Lambda-Ausdruck
		 * @return Eine sortierte Liste der Songs
		 */
	 public List<Song> sortSongsByArtistAndStreams() {
	    

	     return new ArrayList<>(); // Platzhalter
	 }
	 
	 
	 /* ***   Aufgabenteil (2g) *** */	

	 /**
	  * Filtert alle Songs aus einem Stream mit den Songs, welche ab dem
	  * Jahr a und vor dem Jahr b erschienen sind. Die werden gezählt und
	  * dann wird ein String der Form "a  ->  b: count" zurückgegeben
	  */
	 private String numSongsInYears(int a, int b) {
		 
		 return " ";         
	 }
	 
	 /**
	  * Erzeugt einen Stream mit allen Songs, sortiert diesen nach der 
	  * Anzahl der Streams, beschränkt den Stream auf die ersten n
	  * Songs, die dann zu Strings umgewandelt werden. Am Ende werden
	  * die Strings dann zeilenweise in den vorgegebenen StringBuffer 
	  * eingefügt.
	  */
	 private String topNSongArtists(int n){
		 StringBuffer sBuf = new StringBuffer();
		 
		 return sBuf.toString();
	 }	

	 /**
	  * Baut einen "Text" mit ein paar Infps zu den Sons zusammmen.
	  * Hierzu wird die Methode numSongsInYears mit den Werten für
	  * Anfang und Ende der Dekaden ab 1991 (a = 1991, 2001, 2011, 2021 
	  * und b = 2001, 2011,, 2021, 2031) aufgerufen und die Ergebnisse
	  * an den Inhalt des StringBuffers sBuf angehängt. Anschließend 
	  * werden die Ergebnisse aus dem Aufruf der Methode topNSongArtists 
	  * (mit n = 10) an den Inhalt des StringBuffers angehängt.
	  * Der Inhalt des StringBuffers wird am Ende zurückgegeben.
	  */
	 public String filterStreamForInfos() {
		 StringBuffer sBuf = new StringBuffer("Number of Songs by Decades:\n\n");
		 
		 
		 int n = 10;
		 sBuf.append("\nArtists of top " + n + " streamed songs: \n\n");
		 
		 
		 return sBuf.toString();
	 }
	 
	 
	 
}