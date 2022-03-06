import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsDevice;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;

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
		Scanner kb = new Scanner(System.in);
		Scanner inputStream = null;
		PrintWriter outputStream = null;
		//wouldn't it be eaasier to use an ArrayList<MusicNote> ?
		MusicNote list_of_notes[] = new MusicNote[5];// (5 only there for testing) get size of message later
		int option = 0;
		String sentence;
		
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
	
	//save sheet counter (necessary to uniquely name files
	public static int ss_counter = 0;
	
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

//JComponent for rhythm note "quarter"
URL quarter_url = SentenceIN.class.getResource("/quarter_note.png");
ImageIcon i_quarter = new ImageIcon(quarter_url);

//JComponent for rhythm note "eighth"
URL eighth_url = SentenceIN.class.getResource("/eighth_note.png");
ImageIcon i_eighth = new ImageIcon(eighth_url);


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
			MusicNote list_of_notes [] = MusicNote.getNotes(Newmessage);
		
			int x_coord = 260;
			//int tx_coord = 0;
			int y_xtra = 0;
			int [] coords;
			//randomly assigning rhythm to notes (half,whole, quarter, etc.)
			Random r = new Random();
		
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
					 */
			
					coords = getRandomRhythm(r, list_of_notes, count2, x_coord, y_xtra, notes, panel, i_whole, i_half, i_quarter, i_eighth);
					//coords = getRandomRhythm(r, list_of_notes, count2, tx_coord, y_xtra, notes, panel, i_whole, i_half, i_quarter, i_eighth);
					
					x_coord = coords [0];
					y_xtra = coords [1];
					/*
					tx_coord = coords [0];
					y_xtra = coords [1];
					*/
				}
		
	
			}
		panel.add(music_sheet);
		panel.repaint();
		
		setToJpg(frame, music_sheet, w, h); //sets buffered image
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
		
	}
});

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
		
		setToJpg(frame, music_sheet, w, h); //sets buffered image
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
				saveToJpg( b_image, file_location);
				}
		} 
		else if (fc_result == JFileChooser.CANCEL_OPTION) 
		{
		    System.out.println("Cancel was selected");
		}
		
		
	}
});

exit_button.addActionListener(new ActionListener() {
	
	public void actionPerformed(ActionEvent e)
	{
		System.exit(0); //exits program
	}
});
}

public static int [] getRandomRhythm (Random r, MusicNote [] list_of_notes, int count2, int x_coord, int y_xtra, ArrayList<JLabel> notes, JPanel panel, ImageIcon i_whole, ImageIcon i_half, ImageIcon i_quarter, ImageIcon i_eighth )
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




//used to save note populated music sheet to jpg
public static void setToJpg(JFrame frame, JLabel music_sheet, int w, int h)
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
public static void saveToJpg(BufferedImage bi, String file_location)
{
			
		//File outputfile = new File(fileNamer());
		File outputfile = new File(file_location);
			
		try {
			ImageIO.write(bi, "jpg", outputfile);
			System.out.println("image should have been created");
			} 
		catch (IOException e) 
			{
				System.out.println("file doesn't exist");
				e.printStackTrace();
			}
	
}


//used to dynamically create a string to call the saved file of music sheet
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