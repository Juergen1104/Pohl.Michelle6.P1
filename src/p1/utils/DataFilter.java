package p1.utils;

import p1.gui.GUI;
import p1.model.Song;

import java.util.*;
import java.util.stream.Collectors;

public class DataFilter {

    private List<Song> songs;           // Liste mit allen Songs

    public DataFilter(List<Song> songs) {
        this.songs = songs;
    }

    /* ***   Aufgabenteil (2b) *** */
    // Hier SongComparator einfügen:

    /**
     * Gibt die Top N Songs (oder wenn n > Länge der Liste: alle Songs) basierend auf der Anzahl der Streams zurück.
     *
     * @return Eine Liste der Top N Songs
     */
    public List<Song> getTopNSongs(int n) {
        songs.sort(new SongComparator("streams")); // Sortierung nach Streams
        n = Math.min(n, songs.size()); // Begrenzung auf maximale Listengröße
        return new ArrayList<>(songs.subList(0, n)); // Top-n Songs zurückgeben
    }



    /* ***   Aufgabenteil (2c) *** */

    /**
     * Filtert die Songs basierend auf einem bestimmten Künstler.
     *
     * @param artist Der Name des Künstlers
     * @return Eine Liste der Songs des angegebenen Künstlers
     */
    public List<Song> filterSongsByArtist(String artist) {

        List<Song> filteredSongs = new ArrayList<>();
        String artistLower = artist.toLowerCase();

        for (Song song : songs) {
            if (song.getArtist().toLowerCase().contains(artistLower)) {
                filteredSongs.add(song);
            }
        }
        return filteredSongs;

    }



    /* ***   Aufgabenteil (2d) *** */

    /**
     * Sucht den, aufsummiert, am meisten gestreamten Künstler in der Liste
     *
     * @return Einen String des meistgestreamten Künstlers und die Gesamtanzahl an Streams
     */

    /* ***   Aufgabenteil (2e) *** */
    public String getMostStreamedArtist() {

        Map<String, Long> artistStreamsMap = new HashMap<>();

        for (Song song : songs) {
            artistStreamsMap.put(song.getArtist(),  (artistStreamsMap.getOrDefault((Object) song.getArtist(), (long) 0) + song.getStreams()));
        }

        Map.Entry<String, Long> topArtist = null;
        for (Map.Entry<String, Long> entry : artistStreamsMap.entrySet()) {
            if (topArtist == null || entry.getValue() > topArtist.getValue()) {
                topArtist = entry;
            }
        }

        if (topArtist != null) {
            return String.format("%s with %s streams", topArtist.getKey(), GUI.dfStreams.format(topArtist.getValue()));
        }
        return "No artist found";
    }

    /**
     * Sortiert die Songs alphabetisch nach dem Namen des Künstlers.
     * Wenn mehrere Songs denselben Künstler haben, werden sie zusätzlich
     * nach der Anzahl der Streams in absteigender Reihenfolge sortiert.
     * Verwenden Sie bitte einen Lambda-Ausdruck
     *
     * @return Eine sortierte Liste der Songs
     */
    public List<Song> sortSongsByArtistAndStreams() {
        /* ***   Aufgabenteil (2f) *** */
        Comparator<Song> artistComparator = new SongComparator("artist");
        Comparator<Song> streamsComparator = new SongComparator("streams");

        List<Song> sortedSongs = new ArrayList<>(songs);
        sortedSongs.sort((s1, s2) -> {
            int artistComparison = artistComparator.compare(s1, s2);
            return (artistComparison != 0) ? artistComparison : streamsComparator.compare(s2, s1);
        });

        return sortedSongs;
    }

    /**
     * Filtert alle Songs aus einem Stream mit den Songs, welche ab dem
     * Jahr a und vor dem Jahr b erschienen sind. Die werden gezählt und
     * dann wird ein String der Form "a  ->  b: count" zurückgegeben
     */
    private String numSongsInYears(int a, int b) {

        long count = songs.stream()
                .filter(song -> song.getYear() >= a && song.getYear() < b)
                .count();
        return a + " -> " + (b - 1) + ": " + count + " songs";
    }


    /* ***   Aufgabenteil (2g) *** */

    /**
     * Erzeugt einen Stream mit allen Songs, sortiert diesen nach der
     * Anzahl der Streams, beschränkt den Stream auf die ersten n
     * Songs, die dann zu Strings umgewandelt werden. Am Ende werden
     * die Strings dann zeilenweise in den vorgegebenen StringBuffer
     * eingefügt.
     */
    private String topNSongArtists(int n) {
        StringBuffer sBuf = new StringBuffer();
        return songs.stream()
                .sorted((s1, s2) -> Long.compare(s2.getStreams(), s1.getStreams()))
                .limit(n)
                .map(song -> song.getArtist() + " (" + song.getTitle() + ")")
                .collect(Collectors.joining("\n"));
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

        int[][] yearIntervals = {{1991, 2001}, {2001, 2011}, {2011, 2021}, {2021, 2031}};

        for (int[] interval : yearIntervals) {
            sBuf.append(numSongsInYears(interval[0], interval[1])).append("\n");
        }

        sBuf.append("\nTop Artists:\n").append(topNSongArtists(n));

        sBuf.append("\nArtists of top " + n + " streamed songs: \n\n");

        return sBuf.toString();
    }

    private static class SongComparator implements Comparator<Song> {
        private final String criteria;

        public SongComparator(String criteria) {
            this.criteria = criteria.toLowerCase();
        }

        public static List<Song> getTopNSongs(List<Song> songs, int n) {
            songs.sort(new SongComparator("streams")); // Sortierung nach Streams
            n = Math.min(n, songs.size()); // Begrenzung auf maximale Listengröße
            return new ArrayList<>(songs.subList(0, n)); // Top-n Songs zurückgeben
        }

        @Override
        public int compare(Song s1, Song s2) {
            switch (criteria) {
                case "title":
                    return s1.getTitle().compareToIgnoreCase(s2.getTitle());
                case "artist":
                    return s1.getArtist().compareToIgnoreCase(s2.getArtist());
                case "year":
                    return Integer.compare(s1.getYear(), s2.getYear());
                case "streams":
                    return Double.compare(s2.getStreams(), s1.getStreams()); // Absteigende Reihenfolge
                //return Integer.compare((int) s2.getStreams(), (int) s1.getStreams()); // Absteigende Reihenfolge
                default:
                    throw new IllegalArgumentException("Ungültiges Sortierkriterium: " + criteria);
            }
        }
    }


}