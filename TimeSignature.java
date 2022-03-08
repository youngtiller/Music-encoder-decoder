import java.awt.image.BufferedImage;

public class TimeSignature {

	// Attributes
	private double top_number;
	private double bottom_number;

	
	//for saving as image
	private BufferedImage img;
			    
	//Constructors
			
	public TimeSignature ()
		{
				//do nothing
		}
			
	public TimeSignature (double t, double b)
		{
			top_number = t;
			bottom_number = b; 	
		}
			
	//getter
	public double gettop_number(){
		 return top_number;
	}
	
	public double getbottom_number(){
		 return bottom_number;
	}
	
	public BufferedImage getimg(){
		 return img;
	}
	
	//setter
	public void setbottom_number(double b){
		   this.bottom_number= b;
	}
	
	public void settop_number(double t){
		   this.top_number= t;
	}
	public void setimg(BufferedImage bi){
		   this.img= bi;
	}
}
