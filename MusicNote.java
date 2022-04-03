import java.util.*;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
  
import java.io.File;
import java.io.IOException; 

//why is this an abstract class
abstract class MusicNote implements Comparable<MusicNote>{
    
	// Attributes

	private int octave; //this is gonna mean octave 
    private String label; //What type the note is (ex. 'A' OR 'G' or Rest)
    private boolean Gclef; //notes above 'middle c' (melody or not)
    private String path;// = " Notes/key01.mp3";
    static int id = 0; //what's the difference between this and "note_id"
    private String rhythm; //whole,half,quarter, eighth
    
    protected int y_coord; //for putting coordinates on music sheet (relative to JFrame)
    
    protected int uy_coord; //for putting coordinates on music sheet (relative to measure)
    //Constructor
    public MusicNote (int o, String l, boolean g, String p)
    {
    	octave = o;
    	label = l;
    	Gclef = g;
    	path = p;
    }
    
    //Methods
    
    //getter
    public int getoctave(){
        return octave;
    }
    
    public String getpath(){
        return path;
    }
    
    public String getlabel(){
        return label;
    }
    
    public boolean getGclef(){
        return Gclef;
    }
    
    public int getid(){
        return id;
    }
    public String getrhythm(){
        return rhythm;
    }
    public int gety_coord()
    {
    	return y_coord;
    }
    public int getuy_coord()
    {
    	return uy_coord;
    }
    
    //setter
    public void setid(int id){
        this.id= id;
    }
    public void setoctave(int o){
        this.octave= o;
    }
    public void setlabel(String label){
        this.label= label;

    }
    public void setrhythm(String rhythm){
    	if(rhythm == "whole"|| rhythm == "half" || rhythm == "quarter" || rhythm == "eighth")
    	{
        this.rhythm= rhythm;
    	}
    }
    
    public void setuy_coord(int u){
    	
    	int octave_ylength = 61;
    	switch(this.octave)
    	{
    	//only for bass notes
    	case 2: this.uy_coord = u + (octave_ylength*3);break;
    	case 3: this.uy_coord = u + (octave_ylength*2);break;
    	case 4: this.uy_coord = u + octave_ylength;break;
    	//can be both
    	case 5: this.uy_coord = u;break;
    	//only for melody notes
    	case 6: this.uy_coord = u - octave_ylength;break; //(only A6 and B6)
    	}
    	
    }
    
    //(made static)might be easier to eventuallty change 'list_of_notes' to ArrayList<MusicNote>
    // spaces in message will be caught by else statement
    /* currently not in use
    public static MusicNote [] getNotes(ArrayList<Character> Newmessage)
    {
    // makes arraylist<MusicNote>
    int length = Newmessage.size();
    
    MusicNote [] list_of_notes = new MusicNote[length]; 
    
    for (int i = 0; i < Newmessage.size(); i++)
    {
        if(Newmessage.get(i) == 'a' || Newmessage.get(i) ==  'A'){
            list_of_notes[i]= new ANote(5,"a",true," Notes/key01.mp3");
        }
        else if(Newmessage.get(i) == 'b' || Newmessage.get(i) == 'B'){
            list_of_notes[i]= new BNote(5,"b", true," Notes/key01.mp3");
        }
        else if(Newmessage.get(i) == 'c' || Newmessage.get(i) == 'C'){
            list_of_notes[i]= new CNote(4,"c",true," Notes/key01.mp3");

        }
        else if(Newmessage.get(i) == 'd' || Newmessage.get(i) ==  'D'){
            list_of_notes[i]= new DNote(4,"d",true," Notes/key01.mp3");

        }
        else if(Newmessage.get(i) == 'e' || Newmessage.get(i) ==  'E'){
            list_of_notes[i]= new ENote(4,"e",true," Notes/key01.mp3");

        }
        else if(Newmessage.get(i) ==  'f' || Newmessage.get(i) == 'F'){
            list_of_notes[i]= new FNote(4,"f",true," Notes/key01.mp3");
        }
        else if(Newmessage.get(i) == 'g' || Newmessage.get(i) == 'G'){
            list_of_notes[i]= new GNote(5,"g",true," Notes/key01.mp3");

        }
        else if(Newmessage.get(i) ==  'h' || Newmessage.get(i) == 'H'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
           // list_of_notes[i]= new ANote(False)

        }
        else if(Newmessage.get(i) == 'i' || Newmessage.get(i) == 'I'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
           // list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'j' || Newmessage.get(i) == 'J'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
            //list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'k' || Newmessage.get(i) == 'K'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
            //list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'l' || Newmessage.get(i) == 'L'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
           // list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'm' || Newmessage.get(i) == 'M'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
            //list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'n' || Newmessage.get(i) == 'N'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
           // list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'o' || Newmessage.get(i) == 'O'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
            //list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'p' || Newmessage.get(i) == 'P'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
           // list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'q' || Newmessage.get(i) == 'Q'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
          //  list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'r' || Newmessage.get(i) == 'R'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
           // list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 's' || Newmessage.get(i) == 'S'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
          //  list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 't' || Newmessage.get(i) == 'T'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
          //  list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'u' || Newmessage.get(i) == 'U'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
           // list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'v' || Newmessage.get(i) == 'V'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
          //  list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'w' || Newmessage.get(i) == 'W'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
          //  list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'x' || Newmessage.get(i) == 'X'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
          //  list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'y' || Newmessage.get(i) == 'Y'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
           // list_of_notes[i]= new ANote(False);

        }
        else if(Newmessage.get(i) == 'z' || Newmessage.get(i) == 'Z'){
        	list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
            //list_of_notes[i]= new ANote(False);
        }
        else
        {	System.out.println("idk");//this is temporary fix idk man
        list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
        	//return list_of_notes;
        }
        
    }
    
    return list_of_notes;
    }
    */
    public static ArrayList<String> getNotesXml() throws ParserConfigurationException, SAXException, IOException
    {
    	ArrayList<String> notes = new ArrayList<String>(); 
    	 
    	File file = new File("res/presets.xml");  
    	
    	 
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
    	
    	 
    	DocumentBuilder db = dbf.newDocumentBuilder();  
    	Document doc = db.parse(file);  
    	doc.getDocumentElement().normalize();  
    	//System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
    	NodeList nodeList = doc.getElementsByTagName("MelodyNotes");  
    	// nodeList is not iterable, so we are using for loop  
    	for (int itr = 0; itr < nodeList.getLength(); itr++)   
    	{  
    		Node node = nodeList.item(itr);  
    		//System.out.println("\nNode Name :" + node.getNodeName());  
    		if (node.getNodeType() == Node.ELEMENT_NODE)   
    		{  
    			Element eElement = (Element) node;  
    			notes.add(eElement.getElementsByTagName("C4").item(0).getTextContent()); 
    			notes.add(eElement.getElementsByTagName("D4").item(0).getTextContent());
    			notes.add(eElement.getElementsByTagName("E4").item(0).getTextContent());
    			notes.add(eElement.getElementsByTagName("F4").item(0).getTextContent());
    			notes.add(eElement.getElementsByTagName("G4").item(0).getTextContent());
    			notes.add(eElement.getElementsByTagName("A4").item(0).getTextContent());
    			notes.add(eElement.getElementsByTagName("B4").item(0).getTextContent());
    			notes.add(eElement.getElementsByTagName("C5").item(0).getTextContent());
    			notes.add(eElement.getElementsByTagName("D5").item(0).getTextContent());
    			notes.add(eElement.getElementsByTagName("E5").item(0).getTextContent());
    			notes.add(eElement.getElementsByTagName("F5").item(0).getTextContent());
    			notes.add(eElement.getElementsByTagName("G5").item(0).getTextContent());
    			notes.add(eElement.getElementsByTagName("A5").item(0).getTextContent());
    			notes.add(eElement.getElementsByTagName("B5").item(0).getTextContent());
<<<<<<< Updated upstream
=======
    			
    			notes.add(eElement.getElementsByTagName("Rest").item(0).getTextContent());
>>>>>>> Stashed changes
    	
    		}  
    	}  
    	
    	return notes;
    	
    }
    
    
    public static MusicNote [] setNotesXml(ArrayList<Character> Newmessage)
    {
    	try {
			ArrayList <String> xml_notes = getNotesXml();
			
    	//melody notes
    	String C4 = xml_notes.get(0); 
    	String D4 = xml_notes.get(1);
    	String E4 = xml_notes.get(2);
    	String F4 = xml_notes.get(3);
    	String G4 = xml_notes.get(4);
    	String A4 = xml_notes.get(5);
    	String B4 = xml_notes.get(6);
    	
    	String C5 = xml_notes.get(7);
    	String D5 = xml_notes.get(8);
    	String E5 = xml_notes.get(9);
    	String F5 = xml_notes.get(10);
    	String G5 = xml_notes.get(11);
    	String A5 = xml_notes.get(12);
    	String B5 = xml_notes.get(13);
    	String rest = xml_notes.get(14);
    	
    	//bass notes (not implemented)
    	String C2;String D2;String E2;String F2;String G2;String A2;String  B2;
    	
    	String C3;String D3;String  E3;String  F3;String G3;String  A3;String  B3;
    	
    	
    // makes arraylist<MusicNote>
    int length = Newmessage.size();
    
    MusicNote [] list_of_notes = new MusicNote[length]; 
    
    for (int i = 0; i < Newmessage.size(); i++)
    {
        if(C4.indexOf(Newmessage.get(i)) != -1)
        	{
            list_of_notes[i]= new CNote(4,"c",true," Notes/key01.mp3");
        	}
        else if(D4.indexOf(Newmessage.get(i)) != -1)
        	{
        	list_of_notes[i]= new DNote(4,"d",true," Notes/key01.mp3");
        	}
        else if(E4.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes[i]= new ENote(4,"e",true," Notes/key01.mp3");
    		}
        else if(F4.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes[i]= new FNote(4,"f",true," Notes/key01.mp3");
    		}
        else if(G4.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes[i]= new GNote(4,"g",true," Notes/key01.mp3");
    		}
        else if(A4.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes[i]= new ANote(4,"a",true," Notes/key01.mp3");
    		}
        else if(B4.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes[i]= new BNote(4,"b",true," Notes/key01.mp3");
    		}
        else if(C5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes[i]= new CNote(5,"c",true," Notes/key01.mp3");
    		}
        else if(D5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes[i]= new DNote(5,"d",true," Notes/key01.mp3");
    		}
        else if(E5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes[i]= new ENote(5,"e",true," Notes/key01.mp3");
    		}
        else if(F5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes[i]= new FNote(5,"f",true," Notes/key01.mp3");
    		}
        else if(G5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes[i]= new GNote(5,"g",true," Notes/key01.mp3");
    		}
        else if(A5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes[i]= new ANote(5,"a",true," Notes/key01.mp3");
    		}
        else if(B5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes[i]= new BNote(5,"b",true," Notes/key01.mp3");
    		}
        else if(rest.indexOf(Newmessage.get(i)) != -1)
			{
        	list_of_notes[i]= new Rest(4,"rest",true," Notes/key01.mp3");
			}
        else
        {	System.out.println("idk");//this is temporary fix idk man
        list_of_notes[i]= new ANote(1,"a",true," Notes/key01.mp3");
        	//return list_of_notes;
        }
        
    }
    
    return list_of_notes;
    
    
    } catch (ParserConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (SAXException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    	MusicNote [] list = new MusicNote[0];
    	return list;
    }
    
    //gets rhythm conversion from XML file
    public static ArrayList<String> getRhythmXml() throws ParserConfigurationException, SAXException, IOException
    {
    	ArrayList<String> conversions = new ArrayList<String>(); 
    	 
    	File file = new File("res/presets.xml");  
    	
    	 
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
    	
    	 
    	DocumentBuilder db = dbf.newDocumentBuilder();  
    	Document doc = db.parse(file);  
    	doc.getDocumentElement().normalize();  
    	//System.out.println("Root element: " + doc.getDocumentElement().getNodeName());  
    	NodeList nodeList = doc.getElementsByTagName("Rhythm");  
    	// nodeList is not iterable, so we are using for loop  
    	for (int itr = 0; itr < nodeList.getLength(); itr++)   
    	{  
    		Node node = nodeList.item(itr);  
    		//System.out.println("\nNode Name :" + node.getNodeName());  
    		if (node.getNodeType() == Node.ELEMENT_NODE)   
    		{  
    			Element eElement = (Element) node;  
    			conversions.add(eElement.getElementsByTagName("whole").item(0).getTextContent()); 
    			conversions.add(eElement.getElementsByTagName("half").item(0).getTextContent());
    			conversions.add(eElement.getElementsByTagName("quarter").item(0).getTextContent());
    			conversions.add(eElement.getElementsByTagName("eighth").item(0).getTextContent());
    			
    		}  
    	}  
    	
    	return conversions;
    	
    }
    // sets rhythms to fully populate each measure 
    public static void setRhythmXml(MusicNote [] list_of_notes, ArrayList <Character> Newmessage, TimeSignature ts)
    {
    	ArrayList<String> xml_rhythms;
		try {
			xml_rhythms = getRhythmXml();
			
			//rhythm conversions
	    	String whole = xml_rhythms.get(0); 
	    	String half = xml_rhythms.get(1);
	    	String quarter = xml_rhythms.get(2);
	    	String eighth = xml_rhythms.get(3);
	        
	        int i2 = 1;
	        double ms = (ts.gettop_number() / ts.getbottom_number()) * 8.00;
	        int measure_size = (int) ms; //default (4/4) measure size is 8.
	        int measure_size_c = 0;
	        int c_diff = 0;
	        for (int i = 0; i < Newmessage.size(); i++)
	        {
	        	if(i == Newmessage.size() - 1)
	        	{
	        		i2 = 0;
	        	}
	        	if(measure_size_c == measure_size)
	        	{
	        		measure_size_c = 0;
	        	}
	        	c_diff = measure_size - measure_size_c;
	            if(whole.indexOf(Newmessage.get(i2)) != -1)
	            	{
	            	if(c_diff >= 8)
	            		{
	            		list_of_notes[i].setrhythm("whole");
	            		measure_size_c += 8;
	            		}
	            	else if(c_diff >= 4)
	            		{
	            		list_of_notes[i].setrhythm("half");
	            		measure_size_c += 4;
	            		}
	            	else if(c_diff >= 2)
            			{
	            		list_of_notes[i].setrhythm("quarter");
	            		measure_size_c += 2;
            			}
	            	else //c_diff == 1
            			{
	            		list_of_notes[i].setrhythm("eighth");
	            		measure_size_c += 1;
            			}
	            	}
	            else if(half.indexOf(Newmessage.get(i2)) != -1)
	            	{
	            	if(c_diff >= 4)
            			{
	            		list_of_notes[i].setrhythm("half");
	            		measure_size_c += 4;
            			}
	            	else if(c_diff >= 2)
        				{
	            		list_of_notes[i].setrhythm("quarter");
	            		measure_size_c += 2;
        				}
	            	else //c_diff == 1
        				{
	            		list_of_notes[i].setrhythm("eighth");
	            		measure_size_c += 1;
        				}
	            	}
	            else if(quarter.indexOf(Newmessage.get(i2)) != -1)
            		{
	            	if(c_diff >= 2)
    					{
	            		list_of_notes[i].setrhythm("quarter");
	            		measure_size_c += 2;
    					}
	            	else //c_diff == 1
    					{
	            		list_of_notes[i].setrhythm("eighth");
	            		measure_size_c += 1;
    					}
            		}
	            else if(eighth.indexOf(Newmessage.get(i2)) != -1)
	            	{
	            	list_of_notes[i].setrhythm("eighth");
	            	measure_size_c += 1;
	            	}
	            else
	            	{
	            		// do nothing
	            	} 
	        i2++;   
	        }
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
   }

    

    
   //necessary for testing
   public String toString()
   {
	   return "Type: " + this.label + " Octave: " + this.octave + " Rhythm: " + this.rhythm + "\n";
   }
    
}