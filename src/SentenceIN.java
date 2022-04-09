
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.lang.Double;


public class SentenceIN {
	
	//GLOBAL ATTRIBUTES
	//where user input is saved to
	public static ArrayList<Character> Newmessage = new ArrayList<Character>();
	//for appending buffered images
	public static ArrayList<BufferedImage> bi_measures = new ArrayList<BufferedImage>();
	//save sheet counter (necessary to uniquely name files
	public static int ss_counter = 0;
	//music notes are created to
	public static ArrayList<MusicNote> list_of_notes = new ArrayList<MusicNote>();
	//needed for clearing music
	public static JPanel measures_panel = new JPanel();
	//for display music sheet
	public static JScrollPane s_pane = new JScrollPane(); 
	//needed for screen capture
	public static BufferedImage b_image; 
	
	
	public static void main(String[] args) {
		
		JFrame f = new JFrame();
		JPanel p = new JPanel();
		startGraphics(f,p);
		
		}

	// plays sound of a single note for correct duration (rhythm)
	public static int playSound(MusicNote note, TimeSignature ts, boolean flag, int counter){ //plays sounds
		try{
			
			Clip clip;
			AudioInputStream ais = AudioSystem.getAudioInputStream(SentenceIN.class.getResourceAsStream(note.getpath()));
			clip = AudioSystem.getClip();
	        clip.open(ais);
	        clip.start();
	        double ts_value = ts.gettop_number() / ts.getbottom_number();
	        int whole_value = (int) (8000 * ts_value);
	        int half_value = (int) (4000 * ts_value);
	        int quarter_value = (int) (2000 * ts_value);
	        int eighth_value = (int) (1000 * ts_value);
	        switch(note.getrhythm())
	        {
	        case "whole": Thread.sleep(whole_value); counter+= whole_value; break;
	        case "half":  Thread.sleep(half_value); counter+= half_value; break;
	        case "quarter": Thread.sleep(quarter_value);counter+= quarter_value; break;
	        case "eighth":  Thread.sleep(eighth_value); counter+= eighth_value; break;
	        default: break;
	        }
	        System.out.println("Note: " + note.getlabel() + note.getoctave()+ " path: " + note.getpath() + "\n");
	        if(flag)
	        {
	        //clip.stop();
	        }
	        clip.stop();
	        return counter;
		}
		catch (Exception e) {
			System.err.println(e.getMessage() + "something wrong with sound");
		}
		
		return counter; //shouldn't happen
		
	}

//creates starting graphics 
public static void startGraphics(JFrame frame, JPanel panel)
{

frame.setName("Music Encoder");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.setSize(1000, 800);
frame.setLocationRelativeTo(null); // puts frame in center of screen upon opening
frame.setResizable(false); // necessary for absolute positioning
frame.setMinimumSize(new Dimension(500,400)); //might need to take this out
frame.setMaximumSize(new Dimension(1000,800));
panel.setLayout(null);
frame.add(panel);
panel.setBackground(Color.darkGray);

//needed to clear notes 
ArrayList<JLabel> notes = new ArrayList<JLabel>();

measures_panel.setSize(2000,300); //testing
measures_panel.setLayout(new FlowLayout(FlowLayout.LEFT));

//to avoid enclosing scope error (t_sig.get(0) = an instance of TimeSignature
ArrayList<TimeSignature> t_sig = new ArrayList<TimeSignature>();


try {
	double [] ts_xml = getTimeSignatureXml();
	TimeSignature ts = new TimeSignature(ts_xml[0],ts_xml[1]);
	t_sig.add(ts);
} catch (ParserConfigurationException e4) {
	// TODO Auto-generated catch block
	
	e4.printStackTrace();
} catch (SAXException e4) {
	// TODO Auto-generated catch block
	
	e4.printStackTrace();
} catch (IOException e4) {
	// TODO Auto-generated catch block
	
	e4.printStackTrace();
}

//record creation modal
JInternalFrame i_frame = new JInternalFrame("Record", false, true);
i_frame.setName("Music Encoder");
i_frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
i_frame.reshape(400,250,300,300); 
i_frame.setResizable(false); // necessary for absolute positioning

JPanel i_panel = new JPanel();
i_panel.setLayout(null);
i_panel.setBackground(Color.darkGray);

//Make new record
JButton new_record_button = new JButton("Create New Record");
new_record_button.setBounds(30,350,200, 30);

i_frame.add(i_panel);
i_frame.setVisible(false);

//JComponent first jlabel for measures_panel
URL ts_url = SentenceIN.class.getResource("/clef_and_time_signature.png");
ImageIcon i_ts = new ImageIcon(ts_url);
JLabel ts_l = new JLabel(i_ts); //new changed to static variable
ts_l.setSize(90,300);


//JComponent for music sheet (needs to be added first to be first layer(back layer)
URL m_sheet_url = SentenceIN.class.getResource("/empty_sheet.png");
ImageIcon i_m_sheet = new ImageIcon(m_sheet_url);
JLabel music_sheet = new JLabel(i_m_sheet); //new changed to static variable
int x = 250, y = 250, w = 700, h = 300;

music_sheet.setBounds(x,y,w,h);
//JComponent for rhythm note "whole"
URL whole_url = SentenceIN.class.getResource("/whole_note.png");
//JComponent for rhythm note "half"
URL half_url = SentenceIN.class.getResource("/half_note.png");
URL u_half_url = SentenceIN.class.getResource("/u_half_note.png");
//JComponent for rhythm note "quarter"
URL quarter_url = SentenceIN.class.getResource("/quarter_note.png");
URL u_quarter_url = SentenceIN.class.getResource("/u_quarter_note.png");
//JComponent for rhythm note "eighth"
URL eighth_url = SentenceIN.class.getResource("/eighth_note2.png");
URL u_eighth_url = SentenceIN.class.getResource("/u_eighth_note2.png");


URL whole_rest_url = SentenceIN.class.getResource("/whole_rest.png");
URL half_rest_url = SentenceIN.class.getResource("/half_rest.png");
URL quarter_rest_url = SentenceIN.class.getResource("/quarter_rest.png");
URL eighth_rest_url = SentenceIN.class.getResource("/eighth_rest.png");

//to put extra line for notes that exceed the music sheet lines (above or below)
URL extra_line_url = SentenceIN.class.getResource("/extra_line1.png");

URL ts1 = SentenceIN.class.getResource("/ts_1.png");
URL ts2 = SentenceIN.class.getResource("/ts_2.png");
URL ts3 = SentenceIN.class.getResource("/ts_3.png");
URL ts4 = SentenceIN.class.getResource("/ts_4.png");
URL ts5 = SentenceIN.class.getResource("/ts_5.png");
URL ts6 = SentenceIN.class.getResource("/ts_6.png");
URL ts7 = SentenceIN.class.getResource("/ts_7.png");
URL ts8 = SentenceIN.class.getResource("/ts_8.png");
URL ts9 = SentenceIN.class.getResource("/ts_9.png");

// JComponent to accept user input (for newmessage)
JTextField userInput = new JTextField("");
userInput.setBounds(100,200,100, 30);

//JComponent to display current message being encoded to music
JTextField userInput_display = new JTextField("");
userInput_display.setBounds(200,50,600,100);
userInput_display.setEditable(false);
userInput_display.setFont(new java.awt.Font("Default",Font.PLAIN,20));
userInput_display.setHorizontalAlignment(JTextField.CENTER);

//Make the notes appear on sheet and get ready for music to be played
JButton generate_music_button = new JButton("Generate Music");
generate_music_button.setBounds(30,300,200, 30);

//clears music being displayed
JButton clear_music_button = new JButton("Clear Music");
clear_music_button.setBounds(30,250,200, 30);

//load file
JButton load_button = new JButton("Load a File");
load_button.setBounds(30,500,200, 30);

//save music sheet
JButton save_sheet_button = new JButton("Save PNG of Music Sheet");
save_sheet_button.setBounds(30,550,200, 30);

//play music button
JButton play_button = new JButton("Play");
play_button.setBounds(500,600,200, 30);
play_button.setVisible(false); //invisible when until music generated

//exit (idk if neccessary)
JButton exit_button = new JButton("Exit");
exit_button.setForeground(Color.red);
exit_button.setBounds(30,600,200, 30);


panel.add(i_frame);
panel.add(userInput);
panel.add(userInput_display);
panel.add(generate_music_button);
panel.add(clear_music_button);
panel.add(load_button);
panel.add(save_sheet_button);
panel.add(play_button);
panel.add(exit_button);


frame.setContentPane(panel);
frame.setVisible(true);


/* creates music sheet and displays in app
 * make notes in groups of measures(only put a rhythm that can fit)
 * Put it on a JScrollpane that automatically scrolls with the music
 */
generate_music_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		String u_input = null;
		
		u_input = (String)userInput.getText(); //gets text from textfield
		
		
		if(u_input == null)
			{
				return;// exit method
			}
		
		else
			{
			
		
			userInput_display.setText(u_input);
		
			//sets Newmessage
			for(int count = 0;count < u_input.length(); count++)
				{
					Newmessage.add(u_input.charAt(count));
				}
		
			userInput.setText(null); //clears input (isn't working)
		
			
			list_of_notes = MusicNote.setNotesXml(Newmessage, scaleChooser(u_input));
			
					
					ArrayList<Measure> measures = new ArrayList<Measure>();
					
					
					MusicNote.setRhythmXml(list_of_notes, Newmessage, t_sig.get(0));
					
					double measure_beat_count = 0.0;
					double current_note_beat = 0.0;
					int measure_count = 0;
					double num_beats_per_measure = t_sig.get(0).gettop_number();
					double bottom_number = t_sig.get(0).getbottom_number(); //base rhythm that counts as a beat
					Measure m = new Measure();
					measures.add(m);
					System.out.println("Time Signature: " + num_beats_per_measure + "/" + bottom_number);
					
					for(int count2 = 0;count2 < list_of_notes.size(); count2++)
					{
						switch(list_of_notes.get(count2).getrhythm())
						{
						case "whole":
							{	
								current_note_beat = bottom_number;
								measure_beat_count = measure_beat_count + current_note_beat; // 4/4: 1
					
								break;
							}
						case "half":
							{
								current_note_beat = bottom_number/2.0;
								measure_beat_count = measure_beat_count + current_note_beat; // 4/4: 1/2
								
								break;
							}
						case "quarter":
							{
								current_note_beat = bottom_number/4.0;
								measure_beat_count = measure_beat_count + current_note_beat; // 4/4: 1/4
								
								break;
							}
						case "eighth":
							{
								current_note_beat = bottom_number/8.0;
								measure_beat_count = measure_beat_count + current_note_beat; // 4/4: 1/8
								
								break;
							}
						}
					
					if(measure_beat_count <= num_beats_per_measure)
					{
						//add note to current bar
						measures.get(measure_count).getnotes().add(list_of_notes.get(count2));
						System.out.println("Within measure: " + measure_beat_count);
					}
					else if(measures.size() == 1 && measures.get(0).getnotes().size() == 0) //first measure
					{
						//add note to first measure
						measures.get(measure_count).getnotes().add(list_of_notes.get(count2));
						//create new bar
						Measure m1 = new Measure();
						//add bar to bars
						measures.add(m1);
						//add to measure count
						measure_count++;
						//set counts
						measure_beat_count = current_note_beat; //should set to correct amount of beats the note has
						System.out.println("New first measure: " + measure_beat_count);
					}
					else //first note of measure (for any measure except first measure)
					{
						
						measure_count++;
						
						//create new bar
						Measure m1 = new Measure();
						//add bar to bars
						measures.add(m1);
						//add note to new bar
						measures.get(measure_count).getnotes().add(list_of_notes.get(count2));
						//set counts
						measure_beat_count = current_note_beat; //should set to correct amount of beats the note has
						System.out.println("New measure: " + measure_beat_count);
					}
					
					}
			
					//print out all bars
					URL e_measure_url = SentenceIN.class.getResource("/empty_measure.png");
					BufferedImage png_measure;
					BufferedImage measure_w_notes;
					
					String str ="Sheet :\n";
					BufferedImage measure_w_ts;
					BufferedImage png_ts;
					
					EmptyBorder e_border = new EmptyBorder(0,0,0,0);
					
					ImageIcon i_w_ts;
					JLabel jl_w_ts;
					try {
						png_ts = ImageIO.read(ts_url);
						try {
							
							measure_w_ts = addTSToClef(png_ts, t_sig.get(0), ts1,ts2,ts3,ts4,ts5,ts6,ts7,ts8,ts9);
							System.out.println("ts was added graphically\n");
							t_sig.get(0).setimg(measure_w_ts);
							i_w_ts = new ImageIcon(measure_w_ts);
							jl_w_ts = new JLabel(i_w_ts);
							jl_w_ts.setBorder(e_border);
							//jl_m.setBounds(0,0,2000,300);
							jl_w_ts.setSize(90,300);
							measures_panel.add(jl_w_ts);//adding clef and time signature
							
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					} catch (IOException e3) {
						// TODO Auto-generated catch block
						e3.printStackTrace();
					}
				
					ImageIcon i_m;
					JLabel jl_m;
					
			    	for (Measure m1 : measures) {
			            str = str + m1;
			            try {
			            	
							png_measure = ImageIO.read(e_measure_url);  
							//added rest urls 
							measure_w_notes = addNotesToMeasure(png_measure, m1, whole_url, half_url, u_half_url, quarter_url, u_quarter_url, eighth_url, u_eighth_url, whole_rest_url, half_rest_url, quarter_rest_url, eighth_rest_url, extra_line_url, t_sig.get(0));
							
							bi_measures.add(measure_w_notes); //may be redundant
							
							i_m = new ImageIcon(measure_w_notes);
							jl_m = new JLabel(i_m);
							jl_m.setBorder(e_border);
							//jl_m.setBounds(0,0,2000,300);
							jl_m.setSize(175,300);
							measures_panel.add(jl_m);
							
							measures_panel.setAutoscrolls(true);
							 
							System.out.println("added measure to arraylist\n");
							
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			        }
			 	   System.out.println(str);
			 	   
			 	   	measures_panel.setVisible(true);
					measures_panel.repaint();
					
					//s_pane = new JScrollPane(measures_panel);
					s_pane.setViewportView(measures_panel);
					s_pane.setBounds(x,y,700,300);
					
					s_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
					s_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
					s_pane.setVisible(true);
					
					panel.add(s_pane);
	
			}
		try {
			setToJpg(frame, music_sheet, w, h, t_sig.get(0));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //sets buffered image
		
		play_button.setVisible(true);
		System.out.println("Components in panel: " + measures_panel.getComponentCount());
		panel.repaint();
		
		
	}
	
});

// clears music sheet from application(must be called before loading music sheet (if there's a music sheet populating app) or generating new music sheet
clear_music_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		for(int count = 0; count < notes.size(); count++)
		{
			notes.get(count).setVisible(false);
		}
		notes.clear();
		Newmessage.clear();
		bi_measures.clear();
		//t_sig.clear(); //clear time signature
		measures_panel.removeAll();
		measures_panel.revalidate();
		measures_panel.repaint();
		play_button.setVisible(false);
		
	}
});

//loads music from a png file that was generated from this application
load_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		JFileChooser filechooser = new JFileChooser();
		if (JFileChooser.APPROVE_OPTION == filechooser.showOpenDialog(null))
		{
			String filename = filechooser.getSelectedFile().getName();
			System.out.println(filename);
			
			if(filename.contains(".png") && filename.contains("enc"))
				
			{
				System.out.println("contains_both");
				String[] input = filename.split("\\.");
				filename = input[0];
				filename = filename.replace("enc", "");
				System.out.println("Input:" + filename);
				
				String u_input = null;
				
				u_input = filename;
				
				
				userInput_display.setText(u_input);
				
				//sets Newmessage
				for(int count = 0;count < u_input.length(); count++)
					{
						Newmessage.add(u_input.charAt(count));
					}
			
				userInput.setText(null); //clears input (isn't working)
			
				//creating note objs
				
				list_of_notes = MusicNote.setNotesXml(Newmessage,scaleChooser(u_input));
						
						ArrayList<Measure> measures = new ArrayList<Measure>();
						
						MusicNote.setRhythmXml(list_of_notes, Newmessage, t_sig.get(0));
						
						double measure_beat_count = 0.0;
						double current_note_beat = 0.0;
						int measure_count = 0;
						double num_beats_per_measure = t_sig.get(0).gettop_number();
						double bottom_number = t_sig.get(0).getbottom_number(); //base rhythm that counts as a beat
						Measure m = new Measure();
						measures.add(m);
						System.out.println("Time Signature: " + num_beats_per_measure + "/" + bottom_number);
						
						for(int count2 = 0;count2 < list_of_notes.size(); count2++)
						{
							
							switch(list_of_notes.get(count2).getrhythm())
							{
							case "whole":
								{	
									current_note_beat = bottom_number;
									measure_beat_count = measure_beat_count + current_note_beat; // 4/4: 1
						
									break;
								}
							case "half":
								{
									current_note_beat = bottom_number/2.0;
									measure_beat_count = measure_beat_count + current_note_beat; // 4/4: 1/2
									
									break;
								}
							case "quarter":
								{
									current_note_beat = bottom_number/4.0;
									measure_beat_count = measure_beat_count + current_note_beat; // 4/4: 1/4
									
									break;
								}
							case "eighth":
								{
									current_note_beat = bottom_number/8.0;
									measure_beat_count = measure_beat_count + current_note_beat; // 4/4: 1/8
									
									break;
								}
							}
						
						if(measure_beat_count <= num_beats_per_measure)
						{
							//add note to current bar
							measures.get(measure_count).getnotes().add(list_of_notes.get(count2));
							System.out.println("Within measure: " + measure_beat_count);
						}
						else if(measures.size() == 1 && measures.get(0).getnotes().size() == 0) //first measure
						{
							//add note to first measure
							measures.get(measure_count).getnotes().add(list_of_notes.get(count2));
							//create new bar
							Measure m1 = new Measure();
							//add bar to bars
							measures.add(m1);
							//add to measure count
							measure_count++;
							//set counts
							measure_beat_count = current_note_beat; //should set to correct amount of beats the note has
							System.out.println("New first measure: " + measure_beat_count);
						}
						else //first note of measure (for any measure except first measure)
						{
							
							measure_count++;
							
							//create new bar
							Measure m1 = new Measure();
							//add bar to bars
							measures.add(m1);
							//add note to new bar
							measures.get(measure_count).getnotes().add(list_of_notes.get(count2));
							//set counts
							measure_beat_count = current_note_beat; //should set to correct amount of beats the note has
							System.out.println("New measure: " + measure_beat_count);
						}
						
				
						}
				
						//print out all bars
						URL e_measure_url = SentenceIN.class.getResource("/empty_measure.png");
						BufferedImage png_measure;
						BufferedImage measure_w_notes;
						
						String str ="Sheet :\n";
						BufferedImage measure_w_ts;
						BufferedImage png_ts;
						
						EmptyBorder e_border = new EmptyBorder(0,0,0,0);
						
						ImageIcon i_w_ts;
						JLabel jl_w_ts;
						try {
							png_ts = ImageIO.read(ts_url);
							try {
								
								measure_w_ts = addTSToClef(png_ts, t_sig.get(0), ts1,ts2,ts3,ts4,ts5,ts6,ts7,ts8,ts9);
								System.out.println("ts was added graphically\n");
								t_sig.get(0).setimg(measure_w_ts);
								
								
								i_w_ts = new ImageIcon(measure_w_ts);
								jl_w_ts = new JLabel(i_w_ts);
								jl_w_ts.setBorder(e_border);
								jl_w_ts.setSize(90,300);
								measures_panel.add(jl_w_ts);//adding clef and time signature
								
							} catch (IOException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
						} catch (IOException e3) {
							// TODO Auto-generated catch block
							e3.printStackTrace();
						}
					
						ImageIcon i_m;
						JLabel jl_m;
						
				    	for (Measure m1 : measures) {
				            str = str + m1;
				            try {
				            	
								png_measure = ImageIO.read(e_measure_url);  
								//added rest urls 
								measure_w_notes = addNotesToMeasure(png_measure, m1, whole_url, half_url, u_half_url, quarter_url, u_quarter_url, eighth_url, u_eighth_url, whole_rest_url, half_rest_url, quarter_rest_url, eighth_rest_url, extra_line_url, t_sig.get(0));
								
								bi_measures.add(measure_w_notes); //may be redundant
								
								i_m = new ImageIcon(measure_w_notes);
								jl_m = new JLabel(i_m);
								jl_m.setBorder(e_border);
								jl_m.setSize(175,300);
								measures_panel.add(jl_m);
								
								measures_panel.setAutoscrolls(true);
								 
								System.out.println("added measure to arraylist\n");
								
							} catch (IOException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
				        }
				 	   System.out.println(str);
				 	   
				 	   	measures_panel.setVisible(true);
						measures_panel.repaint();
						
						s_pane.setViewportView(measures_panel);
						s_pane.setBounds(x,y,700,300);
						
						s_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
						s_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
						s_pane.setVisible(true);
						
						panel.add(s_pane);
		
				
			try {
				setToJpg(frame, music_sheet, w, h, t_sig.get(0));
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} //sets buffered image
			
			//panel.add(music_sheet);
			play_button.setVisible(true);
			System.out.println("Components in panel: " + measures_panel.getComponentCount());
			panel.repaint();
			
				
			}
			else
			{
				return;
			}
		}
		else
		{
			return;
		}
		
	}
});

//saves music sheet to png using a JFileChooser
save_sheet_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		
		try {
			setToJpg(frame, music_sheet, w, h, t_sig.get(0));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //sets buffered image
		
		JFileChooser save_file = new JFileChooser();
		save_file.setBounds(frame.getX() + 300, frame.getY() + 200,400,400);
		
		save_file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		/*new *///save_file.setSelectedFile(new File("fileToSave.png"));
		
		panel.add(save_file);
		panel.repaint();
		
		int fc_result = save_file.showSaveDialog(i_frame);
		if (fc_result == JFileChooser.APPROVE_OPTION) 
		{
			String name_input = null;
			name_input = (String)userInput.getText(); //gets text from textfield
			
			
			if(name_input == null)
				{
					// do nothing
				}
			else
				{
				System.out.println("Saving file");
				String enc_name = fileNamer();
				String file_location = save_file.getSelectedFile().getAbsolutePath()/*+ enc_name name_input*/;
				String filename = save_file.getSelectedFile().getName();
				file_location = file_location.replace(filename, "");
				file_location = file_location + enc_name;
				saveToPNG(b_image, file_location);
				}
		} 
		else if (fc_result == JFileChooser.CANCEL_OPTION) 
		{
		    System.out.println("Cancel was selected");
		}
		
		
	}
});
//plays music on current music sheet and automatically scrolls with playback
play_button.addActionListener(new ActionListener() {

	public void actionPerformed(ActionEvent e)
	{
		
		Rectangle r;
		
		int starting_x = (int) s_pane.getViewport().getViewRect().getWidth();
		int position = 0; //to autoscroll
		int counter = 0; //to keep track of what notes are being played
		for(int i = 0; i < Newmessage.size(); i++)
		{
			//if a measure is past
			if(counter >= t_sig.get(0).gettop_number() / t_sig.get(0).gettop_number() * 8000)
			{
				counter = 0;
				r = new Rectangle(starting_x +(position*175),0, 1, 1);
				measures_panel.scrollRectToVisible(r);
				position++;
			}
			if(i == (Newmessage.size() - 1))
			{
				counter = playSound(list_of_notes.get(i), t_sig.get(0),true, counter );
			}
			else
			{
				counter = playSound(list_of_notes.get(i),t_sig.get(0),false, counter );
			}
		}
	}
});


//exits application
exit_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		System.exit(0); //exits program
	}
});
}


/* return: 
 * int array should always be of size 5: 
 * arr [0] (type of note)
 * arr [1] (x_coord)
 * arr [2] (y_coord)
 * arr [3] (width)
 * arr [4] (length)
 */
// calculates the spacing of a not on a measure's BufferedImage
public static int [] setMeasureBufferedImage(MusicNote note, int x_coord, double ts_condition)
{
	if(note.getlabel() == "rest")
	{
		
	}
	
	switch(note.getrhythm())
	{
	case "whole": 
	{	
		x_coord = (int) (x_coord + 35 * ts_condition);
		if(note.getlabel() == "rest")
		{
			int [] info = {1,x_coord,(note.getuy_coord() - 45),40,20}; //change
			return info;
		}
		else
		{
			int [] info = {1,x_coord,note.getuy_coord(),20,20};
			return info;
		}
		
	}
	
	case "half": 
	{
		x_coord = (int) (x_coord + 16 * ts_condition);
		if(note.getlabel() == "rest")
		{
			int [] info= {2,x_coord,(note.getuy_coord() - 36),40,20};
			return info;
		}
		else
		{
			int [] info= {2,x_coord,(note.getuy_coord() - 40),20,60};
			return info;
		}
	
	}
	case "quarter": 
	{
		x_coord = (int) (x_coord + 5 * ts_condition); 
		if(note.getlabel() == "rest")
		{
			int [] info = {3, x_coord, (note.getuy_coord() - 50),20,60}; 
			return info;
		}
		else
		{
			int [] info = {3, x_coord, (note.getuy_coord() - 40),20,60};
			return info;
		}
	}
		
	case "eighth": 
	{
		x_coord = (int) (x_coord + 0.5 * ts_condition);
		if(note.getlabel() == "rest")
		{
			int [] info= {4, x_coord,(note.getuy_coord() - 45),20,40};
			return info;
		}
		else
		{
			int [] info= {4, x_coord,(note.getuy_coord() - 40),20,60};
			return info;
		}
		
	}
	default: { int [] info= {1,2,3,4,5}; return info; } //case shouldn't happen
    }
  

}


//used to save note populated music sheet to jpg
/*image file should be made in the format of n amount of rows with 
 * 4 columns (bufferedImage measures) each. If not given an amount 
 * of measures divisible by 4, populate the rest of the row with an empty
 * measure bufferedimage.
 * had to surround all function calls with try/catch
 */
public static void setToJpg(JFrame frame, JLabel music_sheet, int w, int h, TimeSignature ts) throws IOException
{
	URL e_measure_url = SentenceIN.class.getResource("/empty_measure.png");
	BufferedImage empty_measure = ImageIO.read(e_measure_url);
	
	URL e_ts_url = SentenceIN.class.getResource("/clef_and_time_signature.png");
	BufferedImage empty_ts = ImageIO.read(e_ts_url);
	
	ArrayList<BufferedImage> phrases = new ArrayList<BufferedImage>();
		b_image = ts.getimg();
		int measure_count = bi_measures.size();
		System.out.println("measure size " + measure_count + "\n");
		
		int phrase_count = measure_count/4; //remainder is truncated	
			
		System.out.println("Phrase count: " + phrase_count);
		//should group all measures into phrases
		for(int count = 0; count < phrase_count; count++)
		{
			if(count == 0)
			{
				b_image = ts.getimg();
				System.out.println("Setting ts image to beginning of phrase");
			}
			else
			{
				b_image = empty_ts; 
				System.out.println("Setting empty ts image to beginning of phrase");
			}
			
			for(int count2 = 0; count2 < 4; count2 ++)
			{
				
				b_image = joinBufferedImageHorizontally(b_image, bi_measures.get(count2 + 4*count));
			}
			phrases.add(b_image);
			b_image = null;
		}
		//adding measures in the last phrase
		if(measure_count != phrase_count*4) //bc truncated
		{
			int xtra_measures = measure_count - phrase_count*4;
			
			if(phrase_count == 0)
			{
				b_image = ts.getimg();
			}
			else
			{
				b_image = empty_ts;
			}
			
			
			
			for(int count3 = 0; count3 < xtra_measures; count3 ++) //iterates max twice  
				{
					
					b_image = joinBufferedImageHorizontally(b_image, bi_measures.get(measure_count-xtra_measures+count3));
				}
		
			for(int count4 = 0; count4 < Math.abs(xtra_measures - 4); count4 ++) //iterates max thrice  
			{
				
				b_image = joinBufferedImageHorizontally(b_image, empty_measure);
			}
			phrases.add(b_image);
		}
		//testing
		b_image = phrases.get(0);
		for(int count5 = 1; count5 < phrases.size(); count5++)
		{
			b_image = joinBufferedImageVertically(b_image, phrases.get(count5));
			System.out.println("adding phrase " + count5+ " and " + (count5 + 1) + "\n");
		}
		
}

//used to save note populated music sheet of type BufferedImage to png
public static void saveToPNG(BufferedImage bi, String file_location)
{
			
		File outputfile = new File(file_location);
			
		try {
			ImageIO.write(bi, "png", outputfile);
			System.out.println("image should have been created");
			} 
		catch (IOException e) 
			{
				System.out.println("file doesn't exist");
				e.printStackTrace();
			}
	
}


// horizontally joins measures in a phrase
public static BufferedImage joinBufferedImageHorizontally(BufferedImage img1, BufferedImage img2) 
{
	    int offset = 0;
	    int width = img1.getWidth() + img2.getWidth() + offset;
	    int height = Math.max(img1.getHeight(), img2.getHeight()) + offset;
	    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = newImage.createGraphics();
	    Color oldColor = g.getColor();
	    g.setPaint(Color.BLACK);
	    g.fillRect(0, 0, width, height);
	    g.setColor(oldColor);
	    g.drawImage(img1, null, 0, 0);
	    g.drawImage(img2, null, img1.getWidth() + offset, 0);
	    g.dispose();
	    return newImage;
	  }
//vertically joins phrases on a music sheet
public static BufferedImage joinBufferedImageVertically(BufferedImage img1, BufferedImage img2) 
{
	    int offset = 0;
	    int height = img1.getHeight() + img2.getHeight() + offset;
	    int width = Math.max(img1.getWidth(), img2.getWidth()) + offset;
	    BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = newImage.createGraphics();
	    Color oldColor = g.getColor();
	    g.setPaint(Color.BLACK);
	    g.fillRect(0, 0, width, height);
	    g.setColor(oldColor);
	    g.drawImage(img1, null, 0, 0); 
	    g.drawImage(img2, null, 0, img1.getHeight() + offset);
	    g.dispose();
	    return newImage;
	  }
//add notes to a bufferimage of an empty measure
public static BufferedImage addNotesToMeasure(BufferedImage original, Measure m, URL whole_url, URL half_url,URL u_half_url, URL quarter_url, URL u_quarter_url,URL eighth_url, URL u_eighth_url, URL whole_rest_url, URL half_rest_url, URL quarter_rest_url, URL eighth_rest_url, URL extra_line_url, TimeSignature ts) throws IOException
{
	BufferedImage img_result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = (Graphics2D) img_result.getGraphics();
	g.drawImage(original, 0, 0, null);
	
	//int [] coords;
	int x_coord = 0;
	int prev_x_coord;
	BufferedImage bi_note;
	BufferedImage x_line;

	double ts_condition; 
	
	if( ts.gettop_number() / ts.getbottom_number() ==  1) // ex. 2/2, 4/4
	{
		ts_condition = 2;
		System.out.println("ts = 2\n");
	}
	else if(ts.gettop_number() / ts.getbottom_number() ==  0.75) // ex. 3/4, 6/8
	{
		ts_condition = 3;
		System.out.println("ts = 3\n");
	}
	else if(ts.gettop_number() / ts.getbottom_number() ==  0.5) // ex. 2/4, 4/8
	{
		ts_condition = 4;
		System.out.println("ts = 4\n");
	}
	else if(ts.gettop_number() / ts.getbottom_number() ==  2) // ex. 4/2, 8/4
	{
		ts_condition = 1;
		System.out.println("ts = 1\n");
	}
	else //ts.gettop_number() / ts.getbottom_number() ==  1.5
	{
		ts_condition = 1.5; 
		System.out.println("ts = 1.5\n");
	}
	
	for (MusicNote note : m.getnotes()) 
	{ 		      
        //function to find out where the location of the note need to be and 
		
	int [] result = setMeasureBufferedImage(note, x_coord, ts_condition);
	
	
	if(note.getlabel() == "rest")
	{
		switch(result [0])
		{
			case 1:
			{
			bi_note = ImageIO.read(whole_rest_url);
			
			break;
			}
			case 2:
			{
			bi_note = ImageIO.read(half_rest_url);
			
			break;
			}
			case 3:
			{
			bi_note = ImageIO.read(quarter_rest_url);
			
			break;
			}
			case 4:
			{
			bi_note = ImageIO.read(eighth_rest_url);
			break;
			}
			default: bi_note = ImageIO.read(eighth_rest_url); prev_x_coord = result[1] - x_coord; break; //shouldn't happen
			
			
	}
	}
	else
	{
	switch(result [0])
	{
		case 1:
		{
		bi_note = ImageIO.read(whole_url);
		break;
		}
		case 2:
		{
		if(note.uy_coord <= 48)
		
		{
			bi_note = ImageIO.read(u_half_url);
			result[2] = result[2] + 40; //when image is flipped, ycoord needs to be adjusted
		}
		else
		{
			bi_note = ImageIO.read(half_url);
		}
		
		break;
		}
		case 3:
		{
		if(note.uy_coord <= 48)
		{
			bi_note = ImageIO.read(u_quarter_url);
			result[2] = result[2] + 40; //when image is flipped, ycoord needs to be adjusted
		}
		else
		{
			bi_note = ImageIO.read(quarter_url);
		}
		break;
		}
		case 4:
		{
		if(note.uy_coord <= 48)
		{
			bi_note = ImageIO.read(u_eighth_url);
			result[2] = result[2] + 40; //when image is flipped, ycoord needs to be adjusted
		
		}
		else
		{
			bi_note = ImageIO.read(eighth_url);
		}
		break;
		}
		default: bi_note = ImageIO.read(eighth_url); prev_x_coord = result[1] - x_coord; break; //shouldn't happen
	}
	}
	
	prev_x_coord = result[1] - x_coord;
	x_coord = result[1];
	
	
	
	if((note.getlabel() == "c" || note.getlabel() == "d") && note.getoctave() == 4)
	{
		x_line = ImageIO.read(extra_line_url);
		g.drawImage(x_line, x_coord - 2, 118, 24, 4, null); //will have to chnage x-coord to a set value
	}
	if((note.getlabel() == "a" || note.getlabel() == "b") && note.getoctave() == 5)
	{
		x_line = ImageIO.read(extra_line_url);
		g.drawImage(x_line, x_coord - 2, 13, 24, 4, null); //will have to chnage x-coord to a set value
	}
	
	g.drawImage(bi_note, x_coord, result[2], result[3], result[4], null);
	
	x_coord = x_coord + prev_x_coord + 20; //additional 20 is to account for the width of the note

	}
	
	File outputfile = new File("img_result.png"); //idk if i like this
	ImageIO.write(img_result, "png", outputfile);
	img_result = ImageIO.read(outputfile);
	g.dispose();
	return img_result;
}

//add time signature to a bufferimage of clef notes at the beginning of the music sheet
public static BufferedImage addTSToClef(BufferedImage original, TimeSignature ts, URL ts1, URL ts2, URL ts3,URL ts4,URL ts5,URL ts6,URL ts7,URL ts8,URL ts9) throws IOException
{
	BufferedImage img_result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = (Graphics2D) img_result.getGraphics();
	g.drawImage(original, 0, 0, null);
	
	int x_coord = 50;
	int t_ycoord = 30;
	int b_ycoord = 75;
	int width = 40;
	BufferedImage top_number;
	BufferedImage bottom_number;
	//add top number
	int t_num = (int) ts.gettop_number();
	
	if(t_num == 1)
	{
		width = 20;
	}
	top_number = bufImgSetter(t_num, ts1, ts2, ts3, ts4, ts5, ts6, ts7, ts8, ts9);

	g.drawImage(top_number, x_coord, t_ycoord, width, 40, null);
	//add line (divisor line)
	
	//add bottom number if we want
	
	width = 40;
	
	int b_num = (int) ts.getbottom_number();
	
	if(b_num == 1)
	{
		width = 20;
	}
	bottom_number = bufImgSetter(b_num, ts1, ts2, ts3, ts4, ts5, ts6, ts7, ts8, ts9);
	
	g.drawImage(bottom_number, x_coord, b_ycoord, width, 40, null);
	
	File outputfile = new File("ts_img_result.png"); //idk if i like this
	ImageIO.write(img_result, "png", outputfile);
	img_result = ImageIO.read(outputfile);
	g.dispose();
	return img_result;
}

//sets BufferedImage for time signature
public static BufferedImage bufImgSetter(int condition, URL ts1, URL ts2, URL ts3,URL ts4,URL ts5,URL ts6,URL ts7,URL ts8,URL ts9) throws IOException
{
	BufferedImage img;
	
	switch(condition)
	{
	case 1:
	{
	img = ImageIO.read(ts1);return img;
	}
	case 2:
	{
	img = ImageIO.read(ts2);return img;
	}
	case 3:
	{
	img = ImageIO.read(ts3);return img;
	}
	case 4:
	{
	img = ImageIO.read(ts4);return img;
	}
	case 5:
	{
	img = ImageIO.read(ts5);return img;
	}
	case 6:
	{
	img = ImageIO.read(ts6);return img;
	}
	case 7:
	{
	img = ImageIO.read(ts7);return img;
	}
	case 8:
	{
	img = ImageIO.read(ts8);return img;
	}
	case 9:
	{
	img = ImageIO.read(ts9);return img;
	}
	
	default: img = ImageIO.read(ts1); return img; //shouldn't happen
}
}

//gets rhythm conversion from XML file
public static double [] getTimeSignatureXml() throws ParserConfigurationException, SAXException, IOException
{
	
	 
	File file = new File("res/presets.xml");  
	
	 
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
	
	 
	DocumentBuilder db = dbf.newDocumentBuilder();  
	Document doc = db.parse(file);  
	doc.getDocumentElement().normalize();    
	NodeList nodeList = doc.getElementsByTagName("TimeSignature");  
	// nodeList is not iterable, so we are using for loop  
	for (int itr = 0; itr < nodeList.getLength(); itr++)   
	{  
		Node node = nodeList.item(itr);  
	 
		if (node.getNodeType() == Node.ELEMENT_NODE)   
		{  
			Element eElement = (Element) node;  
			String n = eElement.getElementsByTagName("numerator").item(0).getTextContent(); 
			String d = eElement.getElementsByTagName("denominator").item(0).getTextContent();
			double [] ts_values = {Double.parseDouble(n),Double.parseDouble(d)};
			
			return ts_values;
		}  
	}  
	double [] list = {4.0,4.0};
	return list; //this shouldn't happen
	
}

// names png file before saving
public static String fileNamer()
{
	String name = "";
	for (Character c : Newmessage) { 		      
        name = name + c; 		
   }
	return "enc" + name + ".png";
}


//counts amount of instances of user input words that appear in a wordlist
public static int wordlistCounter(String filename, String user_input)
{
	int count =0;
	 try {
	      File myObj = new File(filename);
	      Scanner myReader = new Scanner(myObj);
	      while (myReader.hasNextLine()) {
	        String data = myReader.nextLine();
	        if(user_input.contains(data))
	        {
	        	count++;
	        }
	      }
	      myReader.close();
	    } catch (FileNotFoundException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	 return count;
}

//outputs, scale to use for music sheet 
public static String [] scaleChooser(String user_input)
{
	
	
	String [] C45major_scale = {"C4.wav","D4.wav","E4.wav","F4.wav","G4.wav","A4.wav","B4.wav","C5.wav","D5.wav","E5.wav","F5.wav","G5.wav","A5.wav","B5.wav"};
	String [] Cminor_scale = {"C4.wav","D4.wav","Eb4.wav","F4.wav","G4.wav","Ab4.wav","Bb4.wav","C5.wav","D5.wav","Eb5.wav","F5.wav","G5.wav","Ab5.wav","Bb5.wav"};
	String [] C56major_scale = {"C5.wav","D5.wav","E5.wav","F5.wav","G5.wav","A5.wav","B5.wav","C6.wav","D6.wav","E6.wav","F6.wav","G6.wav","A6.wav","B6.wav"};
	
	int negative_count = wordlistCounter("res/negative-words.txt", user_input);
	int positive_count = wordlistCounter("res/positive-words.txt", user_input);
	
	
	if(negative_count > positive_count) //negative user input
	{
		System.out.println("\n\nCminor_scale: "+ negative_count + "\n\n");
	 return Cminor_scale;
	 
	}
	else if(negative_count < positive_count) //positive user input
	{
		System.out.println("\n\n56major_scale: "+ positive_count + "\n\n");
		return C56major_scale;
		
	}
	else
	{
		System.out.println("\n\nCmajor45_scale: \n n:"+ negative_count + "\np:"+ positive_count +"\n\n");
		return C45major_scale;  //neutral user input
		
}


}

}