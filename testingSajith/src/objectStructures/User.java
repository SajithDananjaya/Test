/**
 * 
 */
package objectStructures;


import java.util.HashMap;
/**
 * @author Sajith Dananjaya
 *
 */

public abstract class User {
	
	private String userName;
	private HashMap<Tag,Integer> musicTaste;
	
	
	public void setUserName(String userName){
		this.userName= userName;
	}
	
	public String getUserName(){
		return this.userName;
	}
	
	public final void setMusicTaste(Tag tag){
		if(this.musicTaste.containsKey(tag)){
			int tagCount = musicTaste.get(tag);
			musicTaste.replace(tag, tagCount+1);
		}else{
			musicTaste.put(tag,1);
		}
	}
		
	public final int getTagCount(String tagName){
		return musicTaste.get(tagName);
	}
	
	public final HashMap<Tag, Integer> getMusicTaste(){
		return this.musicTaste;
	}
	
	

}
