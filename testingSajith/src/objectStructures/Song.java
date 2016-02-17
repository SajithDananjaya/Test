/**
 * 
 */
package objectStructures;

/**
 * @author Sajith Dananjaya
 *
 */
public class Song {
	
	private String songMD;
	private String songName;
	
	private Song(){}
	
	private Song(String songMD,String songName){
		this.songMD=songMD;
		this.songName=songName;
	}
	
	public String getSongName(){
		return this.songName;
	}
	
	public String getSongMD(){
		return this.songMD;
	}
}
