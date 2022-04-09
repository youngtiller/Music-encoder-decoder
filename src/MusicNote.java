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


public class MusicNote {
    
	// Attributes

	private int octave; //this is gonna mean octave 
    private String label; //What type the note is (ex. 'A' OR 'G' or Rest)
    private String path;// = " Notes/key01.mp3";
    private String rhythm; //whole,half,quarter, eighth

    protected int uy_coord; //for putting coordinates on music sheet (relative to measure)
    
    //Constructor
    public MusicNote (int o, String l, String p)
    {
    	octave = o;
    	label = l;
    	path = p;
    	setuy_coord(sety_coord(l)); // needed for location on music sheet
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
       
    public String getrhythm(){
        return rhythm;
    }
    public int getuy_coord()
    {
    	return uy_coord;
    }
    
    //setter
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
    
    // updates y-coordinate of note based on melody or bass note and octave
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
    	}
    	
    }
    
 // sets default y-coordinate of note 
    public int sety_coord(String label)
    {
    	int y_coord = 0;
    	switch(label)
    	{
    	case "c": y_coord = 48;return y_coord;
    	case "d": y_coord = 40;return y_coord;
    	case "e": y_coord = 31;return y_coord;
    	case "f": y_coord = 21;return y_coord;
    	case "g": y_coord = 14;return y_coord;
    	case "a": y_coord = 7;return y_coord;
    	case "b": y_coord = -2;return y_coord;
    	case "rest": y_coord = 31;return y_coord;
    	default: return y_coord;
    	
    	}
    }
    
    // loads what characters convert to what notes from XML conversion table
    public static ArrayList<String> getNotesXml() throws ParserConfigurationException, SAXException, IOException
    {
    	ArrayList<String> notes = new ArrayList<String>(); 
    	 
    	File file = new File("res/presets.xml");  
    	
    	 
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();  
    	
    	 
    	DocumentBuilder db = dbf.newDocumentBuilder();  
    	Document doc = db.parse(file);  
    	doc.getDocumentElement().normalize();  
    	NodeList nodeList = doc.getElementsByTagName("MelodyNotes");  
    	// nodeList is not iterable, so we are using for loop  
    	for (int itr = 0; itr < nodeList.getLength(); itr++)   
    	{  
    		Node node = nodeList.item(itr);   
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
		
    			notes.add(eElement.getElementsByTagName("Rest").item(0).getTextContent());
    		}  
    	}  
    	
    	return notes;
    }
    
    //converts the userinput characters into notes
    public static ArrayList <MusicNote> setNotesXml(ArrayList<Character> Newmessage, String [] sound_files)
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
    	/*
    	String C2;String D2;String E2;String F2;String G2;String A2;String  B2;
    	
    	String C3;String D3;String  E3;String  F3;String G3;String  A3;String  B3;
    	*/
    	
    // makes arraylist<MusicNote>
    ArrayList <MusicNote> list_of_notes = new ArrayList <MusicNote> ();
    
    for (int i = 0; i < Newmessage.size(); i++)
    {
        if(C4.indexOf(Newmessage.get(i)) != -1)
        	{
            list_of_notes.add(new MusicNote(4,"c",sound_files[0]));
        	}
        else if(D4.indexOf(Newmessage.get(i)) != -1)
        	{
        	list_of_notes.add(new MusicNote(4,"d",sound_files[1]));
        	}
        else if(E4.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes.add(new MusicNote(4,"e",sound_files[2]));
    		}
        else if(F4.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes.add( new MusicNote(4,"f",sound_files[3]));
    		}
        else if(G4.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes.add(new MusicNote(4,"g",sound_files[4]));
    		}
        else if(A4.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes.add( new MusicNote(4,"a",sound_files[5]));
    		}
        else if(B4.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes.add(new MusicNote(4,"b",sound_files[6]));
    		}
        else if(C5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes.add( new MusicNote(5,"c",sound_files[7]));
    		}
        else if(D5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes.add( new MusicNote(5,"d",sound_files[8]));
    		}
        else if(E5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes.add( new MusicNote(5,"e",sound_files[9]));
    		}
        else if(F5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes.add( new MusicNote(5,"f",sound_files[10]));
    		}
        else if(G5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes.add( new MusicNote(5,"g",sound_files[11]));
    		}
        else if(A5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes.add( new MusicNote(5,"a",sound_files[12]));
    		}
        else if(B5.indexOf(Newmessage.get(i)) != -1)
    		{
        	list_of_notes.add( new MusicNote(5,"b",sound_files[13]));
    		}
        else if(rest.indexOf(Newmessage.get(i)) != -1)
			{
        	list_of_notes.add( new MusicNote(4,"rest","rest.wav"));
			}
        else
        {	System.out.println("undefined character"); 
        list_of_notes.add( new MusicNote(4,"rest","rest.wav"));
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
    	/*MusicNote [] list = new MusicNote[0];
    	return list;
    	*/
    	ArrayList <MusicNote> notes = new ArrayList <MusicNote> ();
    	return notes;
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
    	NodeList nodeList = doc.getElementsByTagName("Rhythm");  
    	// nodeList is not iterable, so we are using for loop  
    	for (int itr = 0; itr < nodeList.getLength(); itr++)   
    	{  
    		Node node = nodeList.item(itr);    
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
    public static void setRhythmXml(ArrayList <MusicNote> list_of_notes, ArrayList <Character> Newmessage, TimeSignature ts)
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
	            		list_of_notes.get(i).setrhythm("whole");
	            		measure_size_c += 8;
	            		}
	            	else if(c_diff >= 4)
	            		{
	            		list_of_notes.get(i).setrhythm("half");
	            		measure_size_c += 4;
	            		}
	            	else if(c_diff >= 2)
            			{
	            		list_of_notes.get(i).setrhythm("quarter");
	            		measure_size_c += 2;
            			}
	            	else //c_diff == 1
            			{
	            		list_of_notes.get(i).setrhythm("eighth");
	            		measure_size_c += 1;
            			}
	            	}
	            else if(half.indexOf(Newmessage.get(i2)) != -1)
	            	{
	            	if(c_diff >= 4)
            			{
	            		list_of_notes.get(i).setrhythm("half");
	            		measure_size_c += 4;
            			}
	            	else if(c_diff >= 2)
        				{
	            		list_of_notes.get(i).setrhythm("quarter");
	            		measure_size_c += 2;
        				}
	            	else //c_diff == 1
        				{
	            		list_of_notes.get(i).setrhythm("eighth");
	            		measure_size_c += 1;
        				}
	            	}
	            else if(quarter.indexOf(Newmessage.get(i2)) != -1)
            		{
	            	if(c_diff >= 2)
    					{
	            		list_of_notes.get(i).setrhythm("quarter");
	            		measure_size_c += 2;
    					}
	            	else //c_diff == 1
    					{
	            		list_of_notes.get(i).setrhythm("eighth");
	            		measure_size_c += 1;
    					}
            		}
	            else if(eighth.indexOf(Newmessage.get(i2)) != -1)
	            	{
	            	list_of_notes.get(i).setrhythm("eighth");
	            	measure_size_c += 1;
	            	}
	            else //for an undefined character
	            	{
	            	list_of_notes.get(i).setrhythm("eighth");
	            	measure_size_c += 1;
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