package p1.gui;

import p1.model.Song;
import p1.utils.CSVReader;
import p1.utils.DataFilter;
import p1.xml.XMLReader;
import p1.xml.XMLWriter;
import p1.xml.XML_PushParser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class GUI {
	public final static DecimalFormat dfStreams = new DecimalFormat("###,###,###,###");
	private static Font font1 = new Font("Arial", Font.PLAIN, 14);
	private JFrame frame;
	private JPanel mainPanel;
	private JPanel sidePanel;
	private CardLayout cardLayout;
	private DataFilter filterData;
	private Set<Integer> selectedSongIndices = new HashSet<>();
	   
	    public GUI() {
	        List<Song> songs = null;
	        try {
	            CSVReader reader = new CSVReader();
				songs = reader.parseFile("data/spotify_songs.csv");
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "Error while reading from CSV file: " + e.getMessage(),
	                    "File Error", JOptionPane.ERROR_MESSAGE);
	            System.exit(1);
	        }
	        filterData = new DataFilter(songs);
	        initialize();
	        writeAllSongsToXMLFile(songs);
	    }
	    
	    private void writeAllSongsToXMLFile(List<Song> songs) {
	    	XMLWriter writer = new XMLWriter();
	    	try {
	    		writer.writePlaylistToXML("data/all_songs.xml", "TOP list", songs);
	    	} catch (IOException ex) {
	    		JOptionPane.showMessageDialog(null, "Error saving all songs: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	    	}
	    }


	    private void initialize() {
	        frame = new JFrame("P1 Spotify");
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        frame.setSize(1000, 700);
	        frame.setLayout(new BorderLayout());

	        sidePanel = new JPanel();
	        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
	        sidePanel.setBackground(new Color(25, 20, 20));
	        sidePanel.setBorder(new EmptyBorder(10, 10, 10, 10));

	        addNavigationButton("Top N Songs", e -> showPanel("topN"));
	        addNavigationButton("Filter by Artist", e -> showPanel("filterArtist"));
	        addNavigationButton("Most Streamed Artist", e -> showPanel("mostStreamed"));
	        addNavigationButton("Sort by Artist and Streams", e -> showPanel("sortArtist"));
	        addNavigationButton("Filter Stream", e -> showPanel("someMoreInfos"));
	        addNavigationButton("Load Playlist from XML", e -> showPanel("xmlReader"));
	        addNavigationButton("Select Songs for Playlist", e -> showPanel("selectSongs"));
	        addNavigationButton("Get Songs of Year", e -> showPanel("filterByYears"));

	        frame.add(sidePanel, BorderLayout.WEST);
	        ImageIcon icon = new ImageIcon("data/logo3.png");
	        frame.setIconImage(icon.getImage());

	        mainPanel = new JPanel();
	        cardLayout = new CardLayout();
	        mainPanel.setLayout(cardLayout);

	        mainPanel.add(createTopNSongsPanel(), "topN");
	        mainPanel.add(createFilterByArtistPanel(), "filterArtist");
	        mainPanel.add(createMostStreamedArtistPanel(), "mostStreamed");
	        mainPanel.add(createSortByArtistPanel(), "sortArtist");
	        mainPanel.add(createMoreInfoPanel(),"someMoreInfos");
	        mainPanel.add(createXMLReaderPanel(), "xmlReader");
	        mainPanel.add(createSelectSongsPanel(), "selectSongs");
	        mainPanel.add(createXMLFilterPanel(), "filterByYears");
	        frame.add(mainPanel, BorderLayout.CENTER);

	        showPanel("topN");
	        frame.pack();
	        frame.setVisible(true);
	    }

	    private void addNavigationButton(String text, java.awt.event.ActionListener action) {
	        JButton button = new JButton(text);
	        button.setAlignmentX(Component.CENTER_ALIGNMENT);
	        button.setMaximumSize(new Dimension(200, 40));
	        button.setBackground(new Color(30, 215, 96));
	        button.setForeground(Color.BLACK);
	        button.setFont(font1);
	        button.setFocusPainted(false);
	        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        button.addActionListener(action);
	        
	        sidePanel.add(Box.createRigidArea(new Dimension(0, 10)));
	        sidePanel.add(button);
	    }
	 
	    //Panel für top N Songs
	    private JPanel createTopNSongsPanel() {
	        JPanel panel = new JPanel(new BorderLayout());
	        panel.setBackground(new Color(25, 20, 20));
	        JPanel inputPanel = new JPanel();
	        inputPanel.setBackground(new Color(25, 20, 20));
	        JLabel label = new JLabel("Number of Songs: ");
	        label.setForeground(Color.WHITE);
	        JTextField textField = new JTextField(5);
	        JButton showButton = new JButton("Submit");
	        inputPanel.add(label);
	        inputPanel.add(textField);
	        inputPanel.add(showButton);

	        DefaultListModel<String> songListModel = new DefaultListModel<>();
	        JList<String> songList = new JList<>(songListModel);
	        JScrollPane scrollPane = new JScrollPane(songList);

	        songList.setBackground(new Color(25, 20, 20));
	        songList.setForeground(Color.WHITE);
	        songList.setFont(font1);
	        showButton.addActionListener(e -> {
	            String input = textField.getText().trim();
	            try {
	                int n = Integer.parseInt(input);
	                if (n <= 0) {
	                    throw new NumberFormatException("Only numbers > 0 possible.");
	                }
	                List<Song> topNSongs = filterData.getTopNSongs(n);
	                songListModel.clear();
	                for (Song song : topNSongs) {
	                    songListModel.addElement(song.getTitle() + " - " + dfStreams.format(song.getStreams()) + " Streams");
	                }
	            } catch (NumberFormatException nfe) {
	                JOptionPane.showMessageDialog(frame, "Please specify a positive number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
	            } catch (Exception e1) {
					e1.printStackTrace();
				}
	        });

	        JLabel titleLabel = new JLabel("Top N Songs", JLabel.CENTER);
	        titleLabel.setForeground(new Color(30, 215, 96));
	        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

	        panel.add(titleLabel, BorderLayout.NORTH);
	        panel.add(inputPanel, BorderLayout.SOUTH);
	        panel.add(scrollPane, BorderLayout.CENTER);

	        return panel;
	    }

	    //Panel für nach Künstler filtern
	    private JPanel createFilterByArtistPanel() {
	        JPanel panel = new JPanel(new BorderLayout());
	        panel.setBackground(new Color(25, 20, 20));
	
	        JLabel artistLabel = new JLabel("  Artist (substring): " );
	        artistLabel.setBackground(new Color(50, 50, 50));
	        artistLabel.setHorizontalAlignment(SwingConstants.RIGHT);
	        artistLabel.setFont(font1);
	        
	        JButton filterButton = new JButton("Filter");
	
	        DefaultListModel<String> filteredModel = new DefaultListModel<>();
	        JList<String> filteredList = new JList<>(filteredModel);
	        JScrollPane scrollPane = new JScrollPane(filteredList);
	        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	
	        JTextField artistField = new JTextField();
	        artistField.setBackground(new Color(50, 50, 50));
	        artistField.setForeground(Color.WHITE);
	        artistField.setFont(font1);
	        artistField.setCaretColor(Color.white);
	       
	        filterButton.setBackground(new Color(30, 215, 96));
	        filterButton.setForeground(Color.BLACK);
	        filterButton.setFont(font1);
	
	        filterButton.addActionListener(e -> {
	            String artist = artistField.getText().trim();
	            filteredModel.clear();
	
	            List<Song> filteredSongs = filterData.filterSongsByArtist(artist);
	            for (Song song : filteredSongs) {
	                filteredModel.addElement(song.getTitle() + " - " + song.getArtist());
	            }
	        });
	
	        JPanel nPanel = new JPanel(new GridLayout(1,2,5,5));
	        nPanel.setBackground(new Color(30, 215, 96));
	        nPanel.add(artistLabel);
	        nPanel.add(artistField);
	        nPanel.setBorder(BorderFactory.createCompoundBorder(
	                BorderFactory.createLineBorder(new Color(30, 215, 96)),
	                BorderFactory.createEmptyBorder(5, 10, 5, 10)
	        ));
	
	        
	        panel.add(nPanel, BorderLayout.NORTH);
	        panel.add(filterButton, BorderLayout.SOUTH);
	        panel.add(scrollPane, BorderLayout.CENTER);
	        return panel;
	    }
	
	    // Panel für den meistgestreamten Künstler
	    private JPanel createMostStreamedArtistPanel() {
	        JPanel panel = new JPanel(new BorderLayout());
	        panel.setBackground(new Color(25, 20, 20));
	
	        JLabel resultLabel = new JLabel("", JLabel.CENTER);
	        resultLabel.setFont(new Font("Arial", Font.BOLD, 16));
	        resultLabel.setForeground(new Color(30, 215, 96));
	
	        try {
	            String maxStreamedArtist = filterData.getMostStreamedArtist();
			    String result =  "<html>The most streamed artist is: <br><br>" + maxStreamedArtist + " streams.</html>";

	            resultLabel.setText(result);
	        } catch (RuntimeException e) {
	            JOptionPane.showMessageDialog(frame, "Unexüpected Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	
	        panel.add(resultLabel, BorderLayout.CENTER);
	        return panel;
	    }
	
	    //Panel für Sortierung nach Künstler (alphabetisch) und Streams
	    private JPanel createSortByArtistPanel() {
	        JPanel panel = new JPanel(new BorderLayout());
	        panel.setBackground(new Color(25, 20, 20));
	
	        String[] columnNames = {"Artist", "Title", "Year", "Streams"};
	        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
	        JTable table = new JTable(tableModel);
	        JScrollPane scrollPane = new JScrollPane(table);
	
	        table.setBackground(new Color(25, 20, 20));
	        table.setForeground(Color.WHITE);
	        table.setFont(font1);
	        table.setGridColor(new Color(30, 215, 96));
	
	        try {
	            List<Song> sortedSongs = filterData.sortSongsByArtistAndStreams();
	            for (Song song : sortedSongs) {
	                Object[] rowData = {song.getArtist(), song.getTitle(), song.getYear(), song.getStreams()};
	                tableModel.addRow(rowData);
	            }
	        } catch (RuntimeException e) {
	            JOptionPane.showMessageDialog(frame, "Unexpected error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	
	        JLabel titleLabel = new JLabel("Songs Sorted by Artist and Streams", JLabel.CENTER);
	        titleLabel.setForeground(new Color(30, 215, 96));
	        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
	
	        panel.add(titleLabel, BorderLayout.NORTH);
	        panel.add(scrollPane, BorderLayout.CENTER);
	        return panel;
	    }
	   
	    private JPanel createSelectSongsPanel() {
	        JPanel panel = new JPanel(new BorderLayout());
	        String[] columnNames = {"Title", "Artist", "Year", "Streams"};
	        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
				private static final long serialVersionUID = 1L;
				@Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        JTable songTable = new JTable(tableModel) {
				private static final long serialVersionUID = 1L;
				@Override
	            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int column) {
	                Component component = super.prepareRenderer(renderer, row, column);
	                if (selectedSongIndices.contains(row)) {
	                    component.setBackground(new Color(30, 215, 96));
	                } else {
	                    component.setBackground(Color.WHITE);
	                }
	                return component;
	            }
	        };
	        songTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        songTable.setFillsViewportHeight(true);
	        loadSongsIntoTableModel(tableModel);
	        songTable.addMouseListener(new java.awt.event.MouseAdapter() {
	            @Override
	            public void mouseClicked(java.awt.event.MouseEvent e) {
	                int row = songTable.rowAtPoint(e.getPoint());
	                if (row != -1) {
	                    if (selectedSongIndices.contains(row)) {
	                        selectedSongIndices.remove(row);
	                    } else {
	                        selectedSongIndices.add(row);
	                    }
	                    songTable.repaint();
	                }
	            }
	        });
	        JScrollPane scrollPane = new JScrollPane(songTable);
	        panel.add(scrollPane, BorderLayout.CENTER);

	        JButton saveButton = new JButton("Save Playlist (in file my_own_playlist.xml)");
	        saveButton.setFont(font1);
	        saveButton.setBackground(new Color(30, 215, 96));
	        saveButton.setForeground(Color.BLACK);
	        saveButton.addActionListener(e -> {
	            List<Song> selectedSongs = new ArrayList<>();
	            for (int rowIndex : selectedSongIndices) {
	                String title = (String) tableModel.getValueAt(rowIndex, 0);
	                String artist = (String) tableModel.getValueAt(rowIndex, 1);
	                int year = (int) tableModel.getValueAt(rowIndex, 2);
	                long streams = (long) tableModel.getValueAt(rowIndex, 3);
	                selectedSongs.add(new Song(title, artist, year, streams));
	            }

	            try {
	                XMLWriter writer = new XMLWriter();
	                writer.writePlaylistToXML("data/my_own_playlist.xml", "My Own Playlist", selectedSongs);
	                JOptionPane.showMessageDialog(panel, "Playlist saved as my_own_playlist.xml! Check your implementation by checking if your selected songs are correctly saved in my_own_playlist.xml!", "Saved", JOptionPane.INFORMATION_MESSAGE);
	            } catch (IOException ex) {
	                JOptionPane.showMessageDialog(panel, "Error saving playlist: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        });

	        JPanel buttonPanel = new JPanel();
	        buttonPanel.add(saveButton);
	        panel.add(buttonPanel, BorderLayout.SOUTH);

	        return panel;
	    }
	    
	    // create Panel for songs in a given year
	    private JPanel createXMLFilterPanel() {
	    	JPanel panel = new JPanel(new BorderLayout());
	        panel.setBackground(new Color(25, 20, 20));
	        JPanel inputPanel = new JPanel();
	        inputPanel.setBackground(new Color(25, 20, 20));
	        JLabel label = new JLabel("Jahr: ");
	        label.setForeground(Color.WHITE);
	        JTextField textField = new JTextField(5);
	        JButton showButton = new JButton("Submit");
	        inputPanel.add(label);
	        inputPanel.add(textField);
	        inputPanel.add(showButton);

	        DefaultListModel<String> songListModel = new DefaultListModel<>();
	        JList<String> songList = new JList<>(songListModel);
	        JScrollPane scrollPane = new JScrollPane(songList);

	        songList.setBackground(new Color(25, 20, 20));
	        songList.setForeground(Color.WHITE);
	        songList.setFont(font1);
	        showButton.addActionListener(e -> {
	            String input = textField.getText().trim();
	            try {
	                int n = Integer.parseInt(input);
	                if ((n <= 1990) || (n > 2025)) {
	                    throw new IllegalArgumentException("Only years after 1990 (and before 2026).");
	                }
	                // **********
	                XML_PushParser parser = new XML_PushParser("data/all_songs.xml",n);
	                List<Song> songsOfYear = parser.parseFile();
	                songListModel.clear();
	                if ((songsOfYear != null) && (songsOfYear.size() > 0)) {
	                	for (Song song : songsOfYear) {
	                		songListModel.addElement(song.getTitle() + " - " + dfStreams.format(song.getStreams()) + " Streams");
	                	}
	                } else {
	                	songListModel.addElement("No songs found for " + n);
	                }
	                // ************
	            } catch (IllegalArgumentException nfe) {
	                JOptionPane.showMessageDialog(frame, "Please specify  a valid number.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
	            } catch (Exception e1) {
					e1.printStackTrace();
				}
	        });

	        JLabel titleLabel = new JLabel("Filter Songs by Year", JLabel.CENTER);
	        titleLabel.setForeground(new Color(30, 215, 96));
	        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

	        panel.add(titleLabel, BorderLayout.NORTH);
	        panel.add(inputPanel, BorderLayout.SOUTH);
	        panel.add(scrollPane, BorderLayout.CENTER);

	        return panel;
	    }
	    
	    // weitere Infos, die mit Treamverarbeitung generiert werden
	    private JPanel createMoreInfoPanel() {
	        JPanel panel = new JPanel(new BorderLayout());
	        panel.setBackground(new Color(25, 20, 20));
	               
	        JTextArea tArea = new JTextArea();
	        tArea.setFont(font1.deriveFont(16));
	        tArea.setBackground(new Color(30, 215, 96));
	        
	        tArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
	        
	        try {
	            String result = filterData.filterStreamForInfos();
	            // resultLabel.setText(result);
	            tArea.setText(result);
	        } catch (RuntimeException e) {
	            JOptionPane.showMessageDialog(frame, "Unexpected Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	
	        panel.add(tArea, BorderLayout.CENTER);
	        return panel;
	    }
	    
	    // Panel für Validierung der XMLReader-Aufgabe
	    private JPanel createXMLReaderPanel() {
	        JPanel panel = new JPanel(new BorderLayout());
	        String[] columnNames = {"Title", "Artist", "Year", "Streams"};
	        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
				private static final long serialVersionUID = 1L;
				@Override
	            public boolean isCellEditable(int row, int column) {
	                return false;
	            }
	        };
	        JTable songTable = new JTable(tableModel);
	        JScrollPane scrollPane = new JScrollPane(songTable);
	        panel.add(scrollPane, BorderLayout.CENTER);
	        JButton testButton = new JButton("Test XMLReader with Example File");
	        testButton.setFont(font1);
	        testButton.setBackground(new Color(30, 215, 96));
	        testButton.setForeground(Color.BLACK);
	        testButton.addActionListener(e -> {
	            String testFilePath = "data/test_playlist.xml";
	            File testFile = new File(testFilePath);
	            if (!testFile.exists()) {
	                JOptionPane.showMessageDialog(panel, "Test file not found: " + testFilePath, "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	            try {
	                XMLReader reader = new XMLReader();
	                List<Song> songs = reader.readPlaylistFromXML(testFilePath);
	                tableModel.setRowCount(0);
	                for (Song song : songs) {
	                    tableModel.addRow(new Object[]{
	                            song.getTitle(),
	                            song.getArtist(),
	                            song.getYear(),
	                            song.getStreams()
	                    });
	                }
	                JOptionPane.showMessageDialog(panel, "Test file loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
	            } catch (Exception ex) {
	                JOptionPane.showMessageDialog(panel, "Error validating XMLReader: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	            }
	        });

	        JPanel buttonPanel = new JPanel();
	        buttonPanel.add(testButton);
	        panel.add(buttonPanel, BorderLayout.SOUTH);

	        return panel;
	    }
	    

	    // Hilfsmethode, die die angewählten Songs speichert
	    private void loadSongsIntoTableModel(DefaultTableModel tableModel) {
	        try {
	            CSVReader reader = new CSVReader();
				List<Song> songs = reader.parseFile("data/spotify_songs.csv");
	            for (Song song : songs) {
	                tableModel.addRow(new Object[]{
	                        song.getTitle(),
	                        song.getArtist(),
	                        song.getYear(),
	                        song.getStreams()
	                });
	            }
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(frame, "Error when loading Songs: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
	        }
	    }


	    // Ansicht wechseln
	    private void showPanel(String panelName) {
	        cardLayout.show(mainPanel, panelName); 
	    }
	
	    // GUI starten
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> new GUI());
	    }
}
