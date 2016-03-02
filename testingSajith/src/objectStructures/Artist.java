/**
 * 
 */
package objectStructures;

import java.util.List;
import java.util.ArrayList;;
/**
 * @author Sajith Dananjaya
 *
 */
public class Artist {
	
	private String artistName;
	private List<Tag> artistTagList;
	
	private Artist(){}
	
	public Artist(String artistName){
		this.artistName=artistName;
		this.artistTagList = new ArrayList<>();
	}
	
	public String getArtistName(){
		return this.artistName; 
	}
	
	public void addArtistTag(Tag tag){
		this.artistTagList.add(tag);
	}
	
	public void setArtistTags(List<Tag> artistTagList){
		this.artistTagList=artistTagList;
	}

	public List<Tag> getArtistTags(){
		return this.artistTagList;
	}
	
	public String getTagListString(){
		String stringTagList = "";
		for(Tag t : artistTagList){
			stringTagList=stringTagList+","+t.getTagName();
		}
		return stringTagList;
	}

}
