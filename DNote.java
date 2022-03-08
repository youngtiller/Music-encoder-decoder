public class DNote extends MusicNote{

	//private int note_id = 1; already declared in 'MusicNote'
    //private String label; already declared in 'MusicNote'
    //private boolean Gclef; //notes above 'middle c' (melody or not) already declared in 'MusicNote'
    // private String path = " Notes/key01.mp3"; already declared in 'MusicNote'

    public DNote(int n, String l, boolean g, String p){
        
    	super(n,l,g,p);
    	super.y_coord = 290;
    	super.uy_coord = 40;
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

