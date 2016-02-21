package dataHandler;

/**
 * @author Sajith Dananjaya
 *
 */

import java.net.URL;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import org.w3c.dom.Document;

import com.sun.prism.TextureMap;

import objectStructures.Tag;
import objectStructures.User;
import objectStructures.Song;
import objectStructures.Artist;
import objectStructures.UserLastFM;


public class LastFMDataController {
	
	private static int currentTagID=1;
	private static List<Tag> newTags = new ArrayList<>();
	private static List<User> initialUsers = new ArrayList<>();
	private static HashMap<String, Tag> initailTags = new HashMap<>();
	private static HashMap<String, Artist> learnedArtists= new HashMap<>();
	
	
	public static void initiateUsers(){
		URL url = AccessLastFM.getURL("user.getFriends&user=sajithdr&limit=6");
		Document userListXML = AccessLastFM.grabXML(url);
		List<String> userList = AccessLastFM.extractPattern("<name>(.*?)</name>", userListXML, 4);
		for(String userName:userList){
			//System.err.println("Learning of user "+userName);
			User tempUser = setUserTaste(userName);
			initialUsers.add(tempUser);
		}
	}
	
	public static User setUserTaste(String userName){
		User tempUser = new UserLastFM(userName);
		URL url = AccessLastFM.getURL("user.getTopArtists&user="+userName+"&limit=20");
		Document userArtitsList = AccessLastFM.grabXML(url);
		List<String> artistNameList = AccessLastFM.extractPattern("<name>(.*?)</name>", userArtitsList, 4);
		for(String artistName:artistNameList){
			List<Tag> artistTags = getArtistInformation(artistName);
			for(Tag tag:artistTags){
				tempUser.setMusicTaste(tag);
			}
		}
		return tempUser;
	}
	
	public static List<Tag> getArtistInformation(String artistName){
		if(!learnedArtists.containsKey(artistName)){
			//System.out.println("Learning about "+artistName);
			URL url = AccessLastFM.getURL("artist.getTopTags&artist="+artistName);
			Document artistTagListXML = AccessLastFM.grabXML(url);
			List<String> tagList = AccessLastFM.extractPattern("<name>(.*?)</name>", artistTagListXML, 4);
			Artist tempArtist = new Artist(artistName);
			for(String tagName:tagList){
				if(!initailTags.containsKey(tagName)){
					Tag tempTag = new Tag(currentTagID, tagName);
					initailTags.put(tagName, tempTag);
					currentTagID++;
				}
				tempArtist.addArtistTag(initailTags.get(tagName));
			}
			learnedArtists.put(artistName, tempArtist);
		}
		return learnedArtists.get(artistName).getArtistTags();
	}
	
	
	public static void setUserSongList(){
		
	}
	
	
	
	
	
	public static int getTagCount(){
		return (currentTagID-1);
	}
	
	public static HashMap<String,Artist> getKnownArtist(){
		return learnedArtists;
	}
	
	public static HashMap<String,Tag> getKnownTags(){
		return initailTags;
	}
	
	public static List<User> getUserList(){
		return initialUsers;
	}
	

}
