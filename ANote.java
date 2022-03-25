
public class ANote extends MusicNote {
	
	/*Notes:
	- do we need a separate class for each note? (A thru G)
	- location (third space from top.
	
	
	*/
	public int y_coord = 7;
	//private int note_id = 1; already declared in 'MusicNote'
    //private String label; already declared in 'MusicNote'
    //private boolean Gclef; //notes above 'middle c' (melody or not) already declared in 'MusicNote'
    // private String path = " Notes/key01.mp3"; already declared in 'MusicNote'

    public ANote(int o, String l, boolean g, String p){
        
    	super(o,l,g,p);
    	super.y_coord = 315;
    	super.setuy_coord(y_coord);
    	//super.uy_coord = 6;
    	//super.uy_coord = 65;
    	//supersetlabel("C")
        //super.id++;
        //this.Gclef = g;

        //Newsound = getSound(Gclef);

    }
    
    
    /*
    public getSound(boolean gclef){
        if (gclef){
            return sound1 + sound2;
        }
        else{
            return sound2;
        }
    }
    */
    public String getpath(){
        //return path;
    	return getpath();
    }

    //Notes/key01.mp3

	@Override
	public int compareTo(MusicNote o) {
		// TODO Auto-generated method stub
		return 0;
	}
}
