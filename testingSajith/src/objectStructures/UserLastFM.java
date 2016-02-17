/**
 * 
 */
package objectStructures;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Sajith Dananjaya
 *
 */
public class UserLastFM extends User{
	
	List<Song> favoriteSongs;
	
	public UserLastFM(){
		favoriteSongs= new ArrayList<>();
	}
	
	public void addSong(Song song){
		this.favoriteSongs.add(song);
	}
	
	public List<Song> getSongsList(){
		return favoriteSongs;
	}
	
}