package lastFM;

/**
 * @author Sajith Dananjaya
 *
 */

import java.net.URL;
import java.util.List;
import java.util.HashMap;
import org.w3c.dom.Document;


import objectStructures.Tag;
import objectStructures.User;
import objectStructures.Song;
import objectStructures.Artist;
import objectStructures.UserLastFM;


public class LastFMDataController {
	
	private static int currentTagID=1;
	private static List<Tag> newTags;
	private static List<User> initialUsers;
	private static HashMap<String, Tag> initailTags = new HashMap<>();
	private static HashMap<String, Artist> learnedArtists= new HashMap<>();
	
	
	public static void initiateUsers(){
		URL url = AccessLastFM.getURL("user.getFriends&user=sajithdr");
		Document userListXML = AccessLastFM.grabXML(url);
		List<String> userList = AccessLastFM.extractPattern("<name>(.*?)</name>", userListXML, 4);
		for(String userName:userList){
			User tempUser = new UserLastFM(userName);
		}
	}
	
	public static void getUserTaste(User user){
		URL url = AccessLastFM.getURL("user.getTopArtists&user="+user.getUserName()+"&limit=20");
		Document userArtitsList = AccessLastFM.grabXML(url);
		List<String> artistNameList = AccessLastFM.extractPattern("<name>(.*?)</name>", userArtitsList, 4);
		for(String artistName:artistNameList){
			
		}
	}
	
	public static List<Tag> getArtistInformation(String artistName){
		if(!learnedArtists.containsKey(artistName)){
			URL url = AccessLastFM.getURL("artist.getTopTags&artist="+artistName);
			Document artistTagListXML = AccessLastFM.grabXML(url);
			List<String> tagList = AccessLastFM.extractPattern("<name>(.*?)</name>", artistTagListXML, 4);
			Artist tempArtist = new Artist(artistName);
			for(String tagName:tagList){
				//if()
				
			}
			
		}
		return learnedArtists.get(artistName).getArtistTags();
	} 
	

}
