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
	private HashMap<String,Integer> musicTaste;
	
	public final void setMusicTaste(String tagName){
		if(this.musicTaste.containsKey(tagName)){
			int tagCount = musicTaste.get(tagName);
			musicTaste.replace(tagName, tagCount+1);
		}else{
			musicTaste.put(tagName,1);
		}
	}
	
	public final int getTagCount(String tagName){
		return musicTaste.get(tagName);
	}
	
	public final HashMap<String, Integer> getMusicTaste(){
		return this.musicTaste;
	}
	
	

}
