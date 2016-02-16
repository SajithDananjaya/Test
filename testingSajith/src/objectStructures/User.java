/**
 * 
 */
package objectStructures;


import java.util.HashMap;
/**
 * @author Sajith Dananjaya
 *
 * <Sajith.dt@hotmail.com>
 */

public abstract class User {
	
	private String userName;
	private HashMap<String,Integer> musciTaste;
	private int numberOfTags=0;
	
	
	
	
	public final void setMusicTaste(String tagName,int tagCount){
				
	}
	
	public final int getTagCount(String tageName){
		int tagCount=0;
		return 0;
	}
	
	public HashMap<String, Integer> getMusicTaste(){
		return this.musciTaste;
	}
	
	

}
