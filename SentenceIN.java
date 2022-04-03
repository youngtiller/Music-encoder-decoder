import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
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

//import sun.audio.AudioPlayer;

/*Notes:
 * encoding table 
 * divide into bars (frames)
 * 
 * 
 * 
 */
public class SentenceIN {
	
	public static void main(String[] args) {
		/*
		Scanner kb = new Scanner(System.in);
		Scanner inputStream = null;
		PrintWriter outputStream = null;
		//wouldn't it be eaasier to use an ArrayList<MusicNote> ?
		MusicNote list_of_notes[] = new MusicNote[5];// (5 only there for testing) get size of message later
		int option = 0;
		String sentence;
		*/
		//only here for testing
		JFrame f = new JFrame();
		JPanel p = new JPanel();
		startGraphics(f,p);
		
		
		
		/*
		for( // This needs to be moved to main function
				MusicNote note:list_of_notes)
				{
					playSound(note.getPath());
				}
				*/
	}
	//changed to static
	public static ArrayList<Character> Newmessage = new ArrayList<Character>();
	
	//for appending buffered images
	public static ArrayList<BufferedImage> bi_measures = new ArrayList<BufferedImage>();
	//save sheet counter (necessary to uniquely name files
	public static int ss_counter = 0;
	
	//needed for clearing music
	public static JPanel measures_panel = new JPanel();
	
	//for display music sheet
	public static JScrollPane s_pane = new JScrollPane(); 
	//needed for screen capture
	public static BufferedImage b_image; 
	/*{

		try {
			Scanner inputStream = new Scanner(new FileInputStream("message.txt"));
			/* Notes/key01.mp3  // took this out for now was messing up code
			while (inputStream.hasNextLine()) {
				// message.remove(0);
				String temp = inputStream.next();
				int n = temp.length();
				Newmessage.add(temp.charAt(0));
				Newmessage.add(temp.charAt(n - 1));
				System.out.println("file opened, just no content");
			}
			inputStream.close();
		} catch (FileNotFoundException f) {
			System.out.println(f);
		}
		
		
	}
	*/
/*
	public static void playSound(String path){ //plays sounds
		try{
			InputStream in = new FileInputStream(path);
			AudioStream sound = new AudioStream(in);
			AudioPlayer.player.start(sound);
		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
	}
}
*/


/*
 * System.out.println("Which Record would you like to update?"); for (int i = 0;
 * i < message.size(); i++) { System.out.printf("%d %s\n", i,
 * message.get(i).toString());
 */ // THIS IS WHERE WE PUT THE GRAPHICS PART IN ORDER TO START MAKIN
//
//
// }

//creates starting graphics 

public static void startGraphics(JFrame frame, JPanel panel)
{

Thread play_stop = Thread.currentThread();

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

int tempo = 1; // used for playback speed of music sheet (currently hard-coded)

measures_panel.setSize(2000,300); //testing
measures_panel.setLayout(new FlowLayout(FlowLayout.LEFT));

//to avoid enclosing scope error (t_sig.get(0) = an instance of TimeSignature
ArrayList<TimeSignature> t_sig = new ArrayList<TimeSignature>();

//TimeSignature ts = new TimeSignature(4.0,4.0);
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
//TimeSignature ts = new TimeSignature(ts_xml[0],ts_xml[1]);
//TimeSignature ts = new TimeSignature(4.0,4.0);
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
//music_sheet.setSize(w,h);
music_sheet.setBounds(x,y,w,h);
//JComponent for rhythm note "whole"
URL whole_url = SentenceIN.class.getResource("/whole_note.png");
ImageIcon i_whole = new ImageIcon(whole_url);

//JComponent for rhythm note "half"
URL half_url = SentenceIN.class.getResource("/half_note.png");
ImageIcon i_half = new ImageIcon(half_url);

URL u_half_url = SentenceIN.class.getResource("/u_half_note.png");
//JComponent for rhythm note "quarter"
URL quarter_url = SentenceIN.class.getResource("/quarter_note.png");
ImageIcon i_quarter = new ImageIcon(quarter_url);

URL u_quarter_url = SentenceIN.class.getResource("/u_quarter_note.png");
//JComponent for rhythm note "eighth"
URL eighth_url = SentenceIN.class.getResource("/eighth_note2.png");
ImageIcon i_eighth = new ImageIcon(eighth_url);

URL u_eighth_url = SentenceIN.class.getResource("/u_eighth_note2.png");
/*
URL eighth_url = SentenceIN.class.getResource("/eighth_note.png");
ImageIcon i_eighth = new ImageIcon(eighth_url);

URL u_eighth_url = SentenceIN.class.getResource("/u_eighth_note.png");
*/

URL whole_rest_url = SentenceIN.class.getResource("/whole_rest.png");
URL half_rest_url = SentenceIN.class.getResource("/half_rest.png");
URL quarter_rest_url = SentenceIN.class.getResource("/quarter_rest.png");
URL eighth_rest_url = SentenceIN.class.getResource("/eighth_rest.png");

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

//update existing record
JButton update_record_button = new JButton("Update Record"); 
update_record_button.setBounds(30,400,200, 30);

//save to file
JButton save_button = new JButton("Save Record to File");
save_button.setForeground(Color.green);
save_button.setBounds(30,450,200, 30);

//load file
JButton load_button = new JButton("Load a File");
load_button.setBounds(30,500,200, 30);

//save music sheet
JButton save_sheet_button = new JButton("Save JPEG of Music Sheet");
save_sheet_button.setBounds(30,550,200, 30);

//play music button
JButton play_button = new JButton("Play");
play_button.setBounds(350,600,200, 30);
play_button.setVisible(false); //invisible when until music generated

//stop music button
JButton stop_button = new JButton("Stop");
stop_button.setBounds(600,600,200, 30);
stop_button.setVisible(false); //invisible when until music generated
//exit (idk if neccessary)
JButton exit_button = new JButton("Exit");
exit_button.setForeground(Color.red);
exit_button.setBounds(30,600,200, 30);

/*new
JScrollPane s_pane = new JScrollPane(music_sheet);
s_pane.setBounds(x,y,w,h);

JViewport vp = new JViewport();
Dimension vp_size = new Dimension(w/2, h);
vp.setViewSize(vp_size);

//s_pane.setViewport(vp);
s_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
s_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
s_pane.setVisible(false);
//panel.add(s_pane);
*/

//add JComponents to panel
//panel.add(s_pane);
//panel.add(music_sheet);
panel.add(i_frame);
panel.add(userInput);
panel.add(userInput_display);
panel.add(generate_music_button);
panel.add(clear_music_button);
//panel.add(new_record_button);
panel.add(update_record_button);
//panel.add(save_button);
panel.add(load_button);
panel.add(save_sheet_button);
panel.add(play_button);
panel.add(stop_button);
panel.add(exit_button);


frame.setContentPane(panel);
frame.setVisible(true);


// currently only generates melody and rhythm is randomly assigned
/*TODO
 * make notes in groups of frames(only put a rhythm that can fit)
 * add rests as an option
 * Put it on a jscrollpane that automatically scrolls with the tempo
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
		
			//creating note objs
			//MusicNote list_of_notes [] = MusicNote.getNotes(Newmessage);
			MusicNote list_of_notes [] = MusicNote.setNotesXml(Newmessage);
			int x_coord = 260;
			//int tx_coord = 0;
			int y_xtra = 0;
			int [] coords;
			//randomly assigning rhythm to notes (half,whole, quarter, etc.)
			Random r = new Random();
			/*
			for(int count2 = 0;count2 < list_of_notes.length; count2++)
				{
					//needed for when the first 4 bars/frames are filled
				
					if(x_coord >= 950)
						{
							x_coord = 350;
							y_xtra = 170;
				
						}
					/*
			
					if(tx_coord >= 700)
						{
							tx_coord = 0;
							y_xtra = 170;
						}
					 
			
					coords = getRandomRhythm(r, list_of_notes, count2, x_coord, y_xtra, notes, panel, i_whole, i_half, i_quarter, i_eighth);
					//coords = getRandomRhythm(r, list_of_notes, count2, tx_coord, y_xtra, notes, panel, i_whole, i_half, i_quarter, i_eighth);
					
					x_coord = coords [0];
					y_xtra = coords [1];
					
					//tx_coord = coords [0];
					//y_xtra = coords [1];
					
				}
				*/
					
					ArrayList<Measure> measures = new ArrayList<Measure>();
					
					//sets all rhythms
					/*
					for(int count2 = 0;count2 < list_of_notes.length; count2++)
					{
						getRandomRhythm(r,list_of_notes,count2, ts);
					}
					*/
					MusicNote.setRhythmXml(list_of_notes, Newmessage, t_sig.get(0));
					//MusicNote.setRhythmXml(list_of_notes, Newmessage, ts);
					
					double measure_beat_count = 0.0;
					double current_note_beat = 0.0;
					int measure_count = 0;
					double num_beats_per_measure = t_sig.get(0).gettop_number();
					double bottom_number = t_sig.get(0).getbottom_number(); //base rhythm that counts as a beat
					Measure m = new Measure();
					measures.add(m);
					System.out.println("Time Signature: " + num_beats_per_measure + "/" + bottom_number);
					for(int count2 = 0;count2 < list_of_notes.length; count2++)
					{
						
					switch(list_of_notes[count2].getrhythm())
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
						measures.get(measure_count).getnotes().add(list_of_notes[count2]);
						System.out.println("Within measure: " + measure_beat_count);
					}
					else if(measures.size() == 1 && measures.get(0).getnotes().size() == 0) //first measure
					{
						//add note to first measure
						measures.get(measure_count).getnotes().add(list_of_notes[count2]);
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
						measures.get(measure_count).getnotes().add(list_of_notes[count2]);
						//set counts
						measure_beat_count = current_note_beat; //should set to correct amount of beats the note has
						System.out.println("New measure: " + measure_beat_count);
					}
					
					
					
						//if(x_coord >= 950)
							//{
							//	x_coord = 350;
							//	y_xtra = 170;
					
							//}
						/*
				
						if(tx_coord >= 700)
							{
								tx_coord = 0;
								y_xtra = 170;
							}
						 */
				
						//coords = get_setRandomRhythm(r, list_of_notes, count2, x_coord, y_xtra, notes, panel, i_whole, i_half, i_quarter, i_eighth);
						//coords = getRandomRhythm(r, list_of_notes, count2, tx_coord, y_xtra, notes, panel, i_whole, i_half, i_quarter, i_eighth);
						
						//x_coord = coords [0];
						//y_xtra = coords [1];
						/*
						tx_coord = coords [0];
						y_xtra = coords [1];
						*/
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
							measure_w_notes = addNotesToMeasure(png_measure, m1, whole_url, half_url, u_half_url, quarter_url, u_quarter_url, eighth_url, u_eighth_url, whole_rest_url, half_rest_url, quarter_rest_url, eighth_rest_url,t_sig.get(0));

							//ImageIO.write(png_measure, "jpg", new File(e_measure_url.toString()));
							
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
		/*
		//b_image
		//File file = new File("pics/generated_music_sheet.png");
		File file = new File("pics/generated_music_sheet.png");
		try {
			ImageIO.write(b_image, "png", file);
			System.out.println("Image written when generated");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		URL p_m_sheet_url = SentenceIN.class.getResource("/generated_music_sheet.png");
		
		ImageIcon p_m_sheet;
		try {
			p_m_sheet = new ImageIcon(file.toURI().toURL());
			//ImageIcon p_m_sheet = new ImageIcon(p_m_sheet_url);
			JLabel p_music_sheet_l = new JLabel(p_m_sheet);
			//i may have to set size (i can do ths based upon the amount of measures)
			
	        
			JPanel p = new JPanel();
			p.setSize(1000,300);
			
			p_music_sheet_l.setBounds(0,0,2000,300);
			p.add(p_music_sheet_l);
			/*
			JLabel music_sheet2 = new JLabel(i_m_sheet);
			music_sheet2.setBounds(0,0,700,300);
			p.add(music_sheet2);
			JLabel music_sheet3 = new JLabel(i_m_sheet);
			music_sheet3.setBounds(700,0,700,300);
			p.add(music_sheet3);
			
			
			
			
			JScrollPane s_pane = new JScrollPane(p);
			s_pane.setBounds(x,y,700,300);
			//s_pane.setBounds(x,y,w,h);
			//s_pane.setLocation(x,y);
/*
			JViewport vp = new JViewport();
			//Dimension vp_size = new Dimension(w/2, h);
			Dimension vp_size = new Dimension(200, 300);
			vp.setViewSize(vp_size);
			vp.setViewPosition(new Point(0, 0));
			
			s_pane.setViewport(vp);
			
			s_pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
			s_pane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
			s_pane.setVisible(true);
			panel.add(s_pane);
			
		} catch (MalformedURLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		*/
		//panel.add(music_sheet);
		play_button.setVisible(true);
		stop_button.setVisible(true);
		System.out.println("Components in panel: " + measures_panel.getComponentCount());
		panel.repaint();
		
		
		//frame.setVisible(true);
	}
	
});

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
		measures_panel.removeAll();
		measures_panel.revalidate();
		measures_panel.repaint();
		play_button.setVisible(false);
		stop_button.setVisible(false);
		
	}
});

//generate music + save file will be sufficient (don't need this button)
new_record_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		i_panel.removeAll();
		
		JTextField record_name = new JTextField("Name: ");
		record_name.setBounds(50,0,200,30);


		i_panel.add(record_name);
		
		i_panel.repaint();
		
		i_frame.setVisible(true);
		
	/*
	if(option == 1) {
		 * System.out.println("Enter first name"); first = kb.next();
		 * System.out.println("Enter last name"); last = kb.next();
		 * System.out.println("Enter birthday in format mm/dd/yyyy"); bday = kb.next();			
		 * message.add(new Record(first,last,bday));
	*/
		
	}
});

update_record_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		i_panel.removeAll();
		
		JFileChooser record_file = new JFileChooser();
		record_file.setBounds(25,0,250,250);
		
		i_panel.add(record_file);
		i_panel.repaint();
		
		i_frame.setVisible(true);
	}
});
//what components of the song are being saved? audio file? music sheet? both? 
save_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		
	}
});

//(id imagine it would be the save as save button) what components of the song are being loaded? audio file? music sheet? both? 
load_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		i_frame.setVisible(true);
	}
});

save_sheet_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		
		try {
			setToJpg(frame, music_sheet, w, h, t_sig.get(0));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //sets buffered image
		/*
		i_panel.removeAll();
		
		JTextField record_name = new JTextField();
		record_name.setBounds(50,0,200,30);
		*/
		JFileChooser save_file = new JFileChooser();
		save_file.setBounds(frame.getX() + 300, frame.getY() + 200,400,400);
		
		save_file.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		panel.add(save_file);
		panel.repaint();
		/*
		i_panel.add(record_name);
		i_panel.add(save_file);
		i_panel.repaint();
		
		i_frame.setVisible(true);
		*/
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
				String file_location = save_file.getSelectedFile().getAbsolutePath()+ name_input;
				//String filename = save_file.getSelectedFile().getName();
				saveToJpg(b_image, file_location);
				}
		} 
		else if (fc_result == JFileChooser.CANCEL_OPTION) 
		{
		    System.out.println("Cancel was selected");
		}
		
		
	}
});
//autoscrolls jscrollpane from what the current view is (not the best solution)
play_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		Rectangle r;
		
		int end = 90 + (bi_measures.size()*175);
		for(int x = (int)s_pane.getViewport().getViewRect().getX(); x < end; x += tempo)
				{
			 r = new Rectangle(x,0, 1, 1);
			measures_panel.scrollRectToVisible(r);
				}
	}
});

//can't be called during a play_button actionperformed method
stop_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		int x = (int)s_pane.getViewport().getViewRect().getX();
		Rectangle r = new Rectangle(x,0, 1, 1);
		measures_panel.scrollRectToVisible(r);
	}
});



exit_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		System.exit(0); //exits program
	}
});
}
//not in current use
public static int [] get_setRandomRhythm (Random r, MusicNote [] list_of_notes, int count2, int x_coord, int y_xtra, ArrayList<JLabel> notes, JPanel panel, ImageIcon i_whole, ImageIcon i_half, ImageIcon i_quarter, ImageIcon i_eighth )
{
	
	int random = r.nextInt(4);//0-3(inclusive)
	switch(random)
	{
	case 0: 
	{
		list_of_notes [count2].setrhythm("whole");
		System.out.println(list_of_notes[count2]);
		
		//tx_coord = tx_coord + 82;
		
		x_coord = x_coord + 82;
		JLabel whole_label = new JLabel(i_whole); //new changed to static variable
		whole_label.setBounds(x_coord,list_of_notes [count2].gety_coord()+ y_xtra,20,20);
		//whole_label.setBounds(tx_coord,((list_of_notes [count2].gety_coord() - 250 )+ y_xtra -40),40,60);
		
		x_coord = x_coord + 82;
		
		//tx_coord = tx_coord + 82;
		
		notes.add(whole_label);
		panel.add(whole_label);
		//s_pane.add(whole_label);
		int [] coords= {x_coord, y_xtra};
		return coords;
		//break;
	}
	
	case 1: 
	{
		list_of_notes[count2].setrhythm("half");
		System.out.println(list_of_notes[count2]);
		
		x_coord = x_coord + 36;
		//tx_coord = tx_coord + 36;
		
		JLabel half_label = new JLabel(i_half); //new changed to static variable
		half_label.setBounds(x_coord,(list_of_notes [count2].gety_coord() + y_xtra -40),40,60);
		//half_label.setBounds(tx_coord,((list_of_notes [count2].gety_coord() - 250 )+ y_xtra -40),40,60);
		
		x_coord = x_coord + 36;
		
		//tx_coord = tx_coord + 36;
		
		notes.add(half_label);
		panel.add(half_label);
		//s_pane.add(half_label);
		
		int [] coords= {x_coord, y_xtra};
		return coords;
		//break;
	}
	case 2: 
	{
		list_of_notes[count2].setrhythm("quarter");
		System.out.println(list_of_notes[count2]);
		x_coord = x_coord + 17;
		//tx_coord = tx_coord + 17;
		
		JLabel quarter_label = new JLabel(i_quarter); //new changed to static variable
		quarter_label.setBounds(x_coord,(list_of_notes [count2].gety_coord()+ y_xtra -40),20,60);
		//quarter_label.setBounds(tx_coord,((list_of_notes [count2].gety_coord() - 250 )+ y_xtra -40),40,60);
		
		x_coord = x_coord + 17;
		//tx_coord = tx_coord + 17;
		
		notes.add(quarter_label);
		panel.add(quarter_label);
		//s_pane.add(quarter_label);
		int [] coords= {x_coord, y_xtra};
		return coords;
		//break;
	}
		
	case 3: 
	{
		list_of_notes [count2].setrhythm("eighth");
		System.out.println(list_of_notes[count2]);
		
		x_coord = x_coord + 9;
		//tx_coord = tx_coord + 9;
		JLabel eighth_label = new JLabel(i_eighth); //new changed to static variable
		eighth_label.setBounds(x_coord,(list_of_notes [count2].gety_coord()+ y_xtra -40),40,60);
		//eighth_label.setBounds(tx_coord,((list_of_notes [count2].gety_coord() - 250 )+ y_xtra -40),40,60);
		//tx_coord = tx_coord + 9;
		x_coord = x_coord + 9;
		notes.add(eighth_label);
		panel.add(eighth_label);
		
		//s_pane.add(eighth_label);
		int [] coords= {x_coord, y_xtra};
		return coords;
		//break;
		
	}
	default: { int [] coords= {x_coord, y_xtra}; return coords; } //case shouldn't happen
 }

}

/* return: 
 * int array should always be of size 5: 
 * arr [0] (type of note)
 * arr [1] (x_coord)
 * arr [2] (y_coord)
 * arr [3] (width)
 * arr [4] (length)
 */
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
		//x_coord = x_coord + 17 * ts_condition;
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
		//x_coord = x_coord + 7 * ts_condition;	
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
		//x_coord = x_coord + 1 * ts_condition;
		//int [] info= {4, x_coord,(note.getuy_coord() - 40),40,60};
		if(note.getlabel() == "rest")
		{
			int [] info= {4, x_coord,(note.getuy_coord() - 45),20,40};
			return info;
		}
		else
		{
			int [] info= {4, x_coord,(note.getuy_coord() - 40),20,60};
			return info;
		//break;
		}
		
	}
	default: { int [] info= {1,2,3,4,5}; return info; } //case shouldn't happen
    }
  

}


public static void getRandomRhythm (Random r, MusicNote [] list_of_notes, int count2, TimeSignature ts)
{
	int ts_condition;
	if(ts.gettop_number() * 2 == ts.getbottom_number()) // means no whole notes
	{
		ts_condition = 3;
	}
	else
	{
		ts_condition = 4;
	}
	int random = r.nextInt(ts_condition);//0- (ts_condition -1) (inclusive)
	switch(random)
	{
	case 0: 
		{
		list_of_notes [count2].setrhythm("eighth");
		System.out.println(list_of_notes[count2]);
		break;
		}
	
	case 1: 
		{
		list_of_notes[count2].setrhythm("quarter");
		System.out.println(list_of_notes[count2]);
		break;
		}
	case 2: 
		{
		list_of_notes[count2].setrhythm("half");
		System.out.println(list_of_notes[count2]);	
		break;
		}
		
	case 3: 
		{
		list_of_notes [count2].setrhythm("whole");
		System.out.println(list_of_notes[count2]);
		break;	
		}
	default: //case shouldn't happen
		{ 
		break;
	
		} 
	}

}




// not in current use
public static void setToSSJpg(JFrame frame, JLabel music_sheet, int w, int h)
{
	System.out.println("load button pressed");
	Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
	int screen_height = (int)screen_size.getHeight();
	int screen_width = (int)screen_size.getWidth();
	int rx = frame.getX() + music_sheet.getX();
	int ry = frame.getY() + music_sheet.getY();
	//int rx = ((screen_width - 1000) / 2) + x;
	//int ry = ((screen_height - 800) / 2) + y;
	
	if((screen_width - rx) < w || (screen_height - ry) < h) // to make sure music_sheet component is fully visible b4 screenshot
	{
		return ; // doesn't create image 
	}
	Rectangle rec = new Rectangle(rx,ry,w,h);
	try {
		Robot r = new Robot();
		BufferedImage bi = r.createScreenCapture(rec);
		b_image = bi;
		
	} catch (AWTException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		System.out.println("exception caught");
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
		
		//should group all measures into phrases
		for(int count = 0; count < phrase_count; count ++)
		{
			if(count == 0)
			{
				b_image = ts.getimg();
			}
			else
			{
				b_image = empty_ts; 
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
			b_image = empty_ts;
			/*
			b_image = bi_measures.get(phrase_count);
			if(measure_count - phrase_count != 1)
			{
				for(int count3 = 1; count3 < xtra_measures; count3 ++) //iterates max twice  
				{
					
					b_image = joinBufferedImage(b_image, bi_measures.get(phrase_count+count3),1);
				}
			}
			*/
			for(int count3 = 0; count3 < xtra_measures; count3 ++) //iterates max twice  
				{
					
					b_image = joinBufferedImageHorizontally(b_image, bi_measures.get(measure_count-xtra_measures+count3));
				}
		
			for(int count4 = 0; count4 < Math.abs(xtra_measures - 4); count4 ++) //iterates max thrice  
			{
				
				b_image = joinBufferedImageHorizontally(b_image, empty_measure);
			}
			phrases.add(b_image);
			//b_image = null;
		}
		//testing
		b_image = phrases.get(0);
		for(int count5 = 1; count5 < phrases.size(); count5++)
		{
			b_image = joinBufferedImageVertically(b_image, phrases.get(count5));
			System.out.println("adding phrase " + count5+ " and " + (count5 + 1) + "\n");
		}
		
		/*
		for (BufferedImage buf_image : bi_measures) { 		      
	           b_image = joinBufferedImage(b_image, buf_image);
		}
		*/
}

//used to save note populated music sheet to jpg
public static void saveToJpg(BufferedImage bi, String file_location)
{
			
		//File outputfile = new File(fileNamer());
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
public static BufferedImage addNotesToMeasure(BufferedImage original, Measure m, URL whole_url, URL half_url,URL u_half_url, URL quarter_url, URL u_quarter_url,URL eighth_url, URL u_eighth_url, URL whole_rest_url, URL half_rest_url, URL quarter_rest_url, URL eighth_rest_url, TimeSignature ts) throws IOException
{
	BufferedImage img_result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
	Graphics2D g = (Graphics2D) img_result.getGraphics();
	g.drawImage(original, 0, 0, null);
	
	int [] coords;
	int x_coord = 0;
	int prev_x_coord;
	BufferedImage bi_note;

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
	/*
	int ts_condition; 
	
	if( ts.gettop_number() / ts.getbottom_number() ==  0.5)
	{
		ts_condition = 4;
		System.out.println("ts = 4\n");
	}
	else if( ts.gettop_number() / ts.getbottom_number() ==  0.5 && (int)ts.gettop_number() % (int)ts.getbottom_number() == 0.0)
	{
		ts_condition = 1;
		System.out.println("ts = 1\n");
	}
	else
	{
		ts_condition = 2; 
		System.out.println("ts = 2\n");
	}
	*/
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
			//prev_x_coord = result[1] - x_coord;
			break;
			}
			case 2:
			{
			bi_note = ImageIO.read(half_rest_url);
			//prev_x_coord = result[1] - x_coord;
			break;
			}
			case 3:
			{
			bi_note = ImageIO.read(quarter_rest_url);
			//prev_x_coord = result[1] - x_coord;
			break;
			}
			case 4:
			{
			bi_note = ImageIO.read(eighth_rest_url);
			//prev_x_coord = result[1] - x_coord;
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
		//prev_x_coord = result[1] - x_coord;
		break;
		}
		case 2:
		{
		if(note.uy_coord <= 48)
		//if(note.getoctave() < 5)
		{
			bi_note = ImageIO.read(u_half_url);
			result[2] = result[2] + 40; //when image is flipped, ycoord needs to be adjusted
		}
		else
		{
			bi_note = ImageIO.read(half_url);
		}
		//prev_x_coord = result[1] - x_coord;
		break;
		}
		case 3:
		{
		if(note.uy_coord <= 48)
		//if(note.getoctave() < 5)
		{
			bi_note = ImageIO.read(u_quarter_url);
			result[2] = result[2] + 40; //when image is flipped, ycoord needs to be adjusted
		}
		else
		{
			bi_note = ImageIO.read(quarter_url);
		}
		//prev_x_coord = result[1] - x_coord;
		break;
		}
		case 4:
		{
		if(note.uy_coord <= 48)
		//if(note.getoctave() < 5)
		{
			bi_note = ImageIO.read(u_eighth_url);
			result[2] = result[2] + 40; //when image is flipped, ycoord needs to be adjusted
			//result[1] = result[1] - 20; //cuz 8th note is 40 wide (double other notes width)
			//prev_x_coord = result[1] - x_coord +20;
		}
		else
		{
			bi_note = ImageIO.read(eighth_url);
			//prev_x_coord = result[1] - x_coord;
		}
		break;
		}
		default: bi_note = ImageIO.read(eighth_url); prev_x_coord = result[1] - x_coord; break; //shouldn't happen
	}
	}
	
	prev_x_coord = result[1] - x_coord;
	x_coord = result[1];
	
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
	//BufferedImage line;
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
//used to dynamically create a string to call the saved file of music sheet
//not in current use

//gets rhythm conversion from XML file
public static double [] getTimeSignatureXml() throws ParserConfigurationException, SAXException, IOException
{
	
	 
	File file = new File("res/presets.xml");  
	
	 
	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
	
	 
	DocumentBuilder db = dbf.newDocumentBuilder();  
	Document doc = db.parse(file);  
	doc.getDocumentElement().normalize();  
	//System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
	NodeList nodeList = doc.getElementsByTagName("TimeSignature");  
	// nodeList is not iterable, so we are using for loop  
	for (int itr = 0; itr < nodeList.getLength(); itr++)   
	{  
		Node node = nodeList.item(itr);  
	
		//System.out.println("\nNode Name :" + node.getNodeName());  
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

public static String fileNamer()
{
	return "music_sheet_sc" + ss_counter + ".jpg";
}



/*
 * message.remove(kb.nextInt()); kb.nextLine();
 * System.out.println("What would you like to change "); message.add(new
 * Message(kb.nextLine())); try { outputStream = new PrintWriter(new
 * FileOutputStream("musicsheet")); for(int i=0;i<message.size();i++) {
 * outputStream.println(message.get(i).toString()); } }
 * catch(FileNotFoundException f) { System.out.println(f); }
 * outputStream.close();
 * System.out.println("Items have been saved to new file"); }
 * inputStream.close(); Collections.sort(message); for(int
 * i=0;i<message.size();i++) { System.out.println(message.get(i)); } } } } }
 * 
 * class Record implements Comparable<Message>{ 
 * 
 * public String first, last, birthday; 
 * 
 * public Record(String f, String l, String bday) 
 * { 
 * first = f; 
 * last = l; 
 * birthday = bday; 
 * } 
 * public Record(String info) 
 * { 
 * String[] breaker = new String[3]; 
 * breaker = info.split(","); 
 * first = breaker[0]; 
 * last = breaker[1];
 * birthday = breaker[2]; 
 * } 
 * public int compareTo(Record r) 
 * {
 * if(last.compareTo(r.last) == 0) 
 * { 
 * return -1*first.compareTo(r.first); 
 * }
 * return -1*last.compareTo(r.last); 
 * } 
 * public String toString() 
 * { 
 * return "" + first + "," + last + "," + birthday; 
 * }
 * 
 * /* 
 * while(option != 5) 
 * { 
 * System.out.println("What would you like to do?");
 * System.out.println("1. Make a new record");
 * System.out.println("2. Update an existing record");
 * System.out.println("3. Save to a file"); 
 * System.out.println("4. Load File");
 * System.out.println("5. Exit"); 
 * option = kb.nextInt(); 
 * 
 * if(option == 1) 
 * {
 * System.out.println("Enter first name"); 
 * first = kb.next();
 * System.out.println("Enter last name"); 
 * last = kb.next();
 * System.out.println("Enter birthday in format mm/dd/yyyy"); 
 * bday = kb.next();
 * message.add(new Record(first,last,bday)); 
 * } 
 * else if(option == 2) 
 * {
 * message.clear();
 * }
 */
}