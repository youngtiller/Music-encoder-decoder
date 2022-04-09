import java.util.ArrayList;

public class Measure {
	
		// Attributes
		private ArrayList <MusicNote> notes = new ArrayList <MusicNote>(); // notes that fit in one measure
		    
		//Constructors
		
		public Measure ()
		  {
			    //do nothing
		  }
		public Measure (ArrayList <MusicNote> n)
		  {
		    notes = n;
		  }
		//getter
	    public ArrayList <MusicNote> getnotes(){
	        return notes;
	    }
	    
	    //setter
	    public void setnotes(ArrayList <MusicNote> n){
	        this.notes= n;
	    }
	   
	  //necessary for testing
	    public String toString()
	    {
	    		String str ="Measure :\n";
	    	for (MusicNote note : notes) {
	            str = str + note;
	        }
	 	   return str;
	    }
	    
	}

