/**
 * 
 */
package objectStructures;

import java.util.List;


/**
 * @author Sajith Dananjaya
 *
 */
public class Song {
	
	private String songMD;
	private String songName;
	private List<Tag> songTags;
	private Artist songArtist;
	
	
	private Song(){}
	
	public Song(String songName){
		this.songName=songName;
	}
	
	
	
	
	public String getSongName(){
		return this.songName;
	}
	
	public String getSongMD(){
		return this.songMD;
	}
}
