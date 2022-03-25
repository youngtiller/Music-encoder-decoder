public class BNote extends MusicNote{

	//private int note_id = 1; already declared in 'MusicNote'
    //private String label; already declared in 'MusicNote'
    //private boolean Gclef; //notes above 'middle c' (melody or not) already declared in 'MusicNote'
    // private String path = " Notes/key01.mp3"; already declared in 'MusicNote'
	public int y_coord = -2;

    public BNote(int o, String l, boolean g, String p){
        
    	super(o,l,g,p);
    	super.y_coord = 307;
    	super.setuy_coord(y_coord);
    	//super.uy_coord = 57;
    	//super.uy_coord = 0;
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

