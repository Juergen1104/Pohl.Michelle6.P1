package p1.model;

/**
 * Repräsentiert einen Song mit Titel, Künstler, Veröffentlichungsjahr und Streams.
 */
public class Song {
    private String title;
    private String artist;
    private int year;
    private long streams;

    /**
     * Konstruktor zum Initialisieren eines Song-Objekts.
     *
     * @param title   Titel des Songs
     * @param artist  Künstler des Songs
     * @param year    Veröffentlichungsjahr des Songs
     * @param streams Anzahl der Streams des Songs
     */
    public Song(String title, String artist, int year, long streams) {
        this.title = title;
        this.artist = artist;
        this.year = year;
        this.streams = streams;
    }

    /**
     * Gibt den Titel des Songs zurück.
     *
     * @return Titel des Songs
     */
    public String getTitle() {
        return title;
    }

    /**
     * Setzt den Titel des Songs.
     *
     * @param title Titel des Songs
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gibt den Künstler des Songs zurück.
     *
     * @return Künstler des Songs
     */
    public String getArtist() {
        return artist;
    }

    /**
     * Setzt den Künstler des Songs.
     *
     * @param artist Künstler des Songs
     */
    public void setArtist(String artist) {
        this.artist = artist;
    }

    /**
     * Gibt das Veröffentlichungsjahr des Songs zurück.
     *
     * @return Veröffentlichungsjahr des Songs
     */
    public int getYear() {
        return year;
    }

    /**
     * Setzt das Veröffentlichungsjahr des Songs.
     *
     * @param year Veröffentlichungsjahr des Songs
     */
    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Gibt die Anzahl der Streams des Songs zurück.
     *
     * @return Anzahl der Streams
     */
    public long getStreams() {
        return streams;
    }

    /**
     * Setzt die Anzahl der Streams des Songs.
     *
     * @param streams Anzahl der Streams
     */
    public void setStreams(long streams) {
        this.streams = streams;
    }

    /**
     * Überschreibt die Standardausgabe für eine lesbare Darstellung des Songs.
     *
     * @return String-Darstellung des Songs
     */
    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", year=" + year +
                ", streams=" + streams +
                '}';
    }
}
