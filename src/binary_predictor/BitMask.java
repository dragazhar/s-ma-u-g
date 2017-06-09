package binary_predictor;


public class BitMask {
    String mask = new String();

    public BitMask(String mask) {
	super();
	this.mask = mask;
    }
    
    public int getMaskLength(){
	return mask.length();
    }
    
    public String generateWordWithLength(int length){
	String word="";
	while(word.length()<=length){
	    word+=mask;
	}
	
	
	
	return word.substring(0, length);
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }
    
    public static void main(String[] args)  {
 	BitMask mask001=new BitMask("001");
 	String word=mask001.generateWordWithLength(6);
 	System.out.println(word);
 	System.out.println(word.substring(0, word.length()-1));
 	System.out.println(word.substring(1,word.length()));
 	/*BitMask mask001111=new BitMask("001111");
 	System.out.println(mask001111.generateWordWithLength(12));*/

 	
     }

    @Override
    public String toString() {
	return "BitMask [mask=" + mask + "]";
    }
    
}
