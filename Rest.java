
public class Rest extends MusicNote{
	
	/*Notes:
	
	Rests can be a different rhythms (whole, half, quater, or eighth)
	y_coord will be the middle of the measure
	
	octave doesn't matter (int o)
	label is 'rest' (String )
	*/
	public int y_coord = 31;
	
    public Rest(int o, String l, boolean g, String p){
        
    	super(o,l,g,p);
    	super.y_coord = 281;
    	//super.uy_coord = 31;
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

    //Notes/key01.mp3

	@Override
	public int compareTo(MusicNote o) {
		// TODO Auto-generated method stub
		return 0;
	}
}

