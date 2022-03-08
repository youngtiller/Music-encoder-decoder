import java.util.*;

//why is this an abstract class
abstract class MusicNote implements Comparable<MusicNote>{
    
	// Attributes

	private int octave; //this is gonna mean octave 
    private String label; //What type the note is (ex. 'A' OR 'G')
    private boolean Gclef; //notes above 'middle c' (melody or not)
    private String path;// = " Notes/key01.mp3";
    static int id = 0; //what's the difference between this and "note_id"
    private String rhythm; //whole,half,quarter, eighth
    
    protected int y_coord; //new (for putting coordinates on music sheet)
    
    protected int uy_coord; //new (for putting coordinates on music sheet)
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
    
    //(made static)might be easier to eventuallty change 'list_of_notes' to ArrayList<MusicNote>
    // spaces in message will be caught by else statement
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
   //necessary for testing
   public String toString()
   {
	   return "Note_id: " + this.octave + " Octave: " + this.label + " Rhythm: " + this.rhythm + "\n";
   }
    
}