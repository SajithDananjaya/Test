package dataHandler;

/**
 * @author Sajith Dananjaya
 *
 */

import java.net.URL;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import org.w3c.dom.Document;
import objectStructures.Tag;
import objectStructures.User;
import objectStructures.Song;
import objectStructures.Artist;
import objectStructures.UserLastFM;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LastFMDataController {

	private static int currentTagID = 1;
	private static int currentUserID = 1;
	private static List<Tag> newTags = new ArrayList<>();
	private static List<User> initialUsers = new ArrayList<>();
	private static HashMap<String, Tag> initailTags = new HashMap<>();
	private static HashMap<String, Artist> learnedArtists = new HashMap<>();

	public static void initiateUsers() {
		URL url = AccessLastFM.getURL("user.getFriends&user=sajithdr&limit=5");
		Document userListXML = AccessLastFM.grabXML(url);
		List<String> userList = AccessLastFM.extractPattern("<name>(.*?)</name>", userListXML, 4);
		for (String userName : userList) {
			// System.err.println("Learning of user "+userName);
			User tempUser = setUserTaste(userName);
			tempUser.setUserID(currentUserID);
			tempUser.filterTaste();
			currentUserID++;
			initialUsers.add(tempUser);
		}
	}

	public static User setUserTaste(String userName) {
		User tempUser = new UserLastFM(userName);
		URL url = AccessLastFM.getURL("user.getTopArtists&user=" + userName + "&limit=20");
		Document userArtitsList = AccessLastFM.grabXML(url);
		List<String> artistNameList = AccessLastFM.extractPattern("<name>(.*?)</name>", userArtitsList, 4);
		return addUserTags(tempUser, artistNameList);
	}

	public static User addUserTags(User user, List<String> artistNameList) {
		for (String artistName : artistNameList) {
			System.err.println(artistName);
			List<Tag> artistTags = getArtistInformation(artistName);
			for (Tag tag : artistTags) {
				user.setMusicTaste(tag);
			}
		}
		return user;
	}

	public static List<Tag> getArtistInformation(String artistName) {
		if (!learnedArtists.containsKey(artistName)) {
			// System.out.println("Learning about "+artistName);
			URL url = AccessLastFM.getURL("artist.getTopTags&artist=" + artistName);
			Document artistTagListXML = AccessLastFM.grabXML(url);
			List<String> tagList = AccessLastFM.extractPattern("<name>(.*?)</name>", artistTagListXML, 4);
			Artist tempArtist = new Artist(artistName);
			for (String tagName : tagList) {
				if (!initailTags.containsKey(tagName)) {
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

	public static int getNewUserID() {
		int tempID = currentUserID;
		currentUserID++;
		return tempID;
	}

	public static int getTagCount() {
		return (currentTagID - 1);
	}

	public static HashMap<String, Artist> getKnownArtist() {
		return learnedArtists;
	}

	public static HashMap<String, Tag> getKnownTags() {
		return initailTags;
	}

	public static List<User> getUserList() {
		return initialUsers;
	}

	public static void saveTagInforamtion() {
		BufferedWriter tempWriter = getWriter("./learnedTags.txt");
		try {
			for (String tagName : initailTags.keySet()) {
				Tag t = initailTags.get(tagName);
				String data = t.getTagID() + "," + t.getTagName();
				// System.err.println(data);
				tempWriter.write(data);
				tempWriter.newLine();
			}
			tempWriter.close();
		} catch (IOException e) {
			System.err.println("ERROR@LastFMDataController_saveTagInforamtion");
			System.err.println(e.toString());
		}
	}
	
	
	public static void saveUserInformation(){
		BufferedWriter tempWriter = getWriter("./learnedUsers.txt");
		try{
			for(User user: initialUsers){
				String data = user.getUserID()+","+user.getUserName()+user.getTasteString();
				//System.err.println(data);
				tempWriter.write(data);
				tempWriter.newLine();
			}
			tempWriter.close();
		}catch(IOException e){
			System.err.println("ERROR@LastFMDataController_saveUserInformation");
			System.err.println(e.toString());
		}
	}
	
	public static void saveArtistInforamtion(){
		BufferedWriter tempWriter = getWriter("./learnedArtists.txt");
		try{
			for(String artistName: learnedArtists.keySet()){
				Artist artist = learnedArtists.get(artistName);
				String data = artist.getArtistName()+artist.getTagListString();
				System.err.println(data);
				tempWriter.write(data);
				tempWriter.newLine();
			}
			tempWriter.close();
		}catch(IOException e){
			System.err.println("ERROR@LastFMDataController_saveUserInformation");
			System.err.println(e.toString());
		}
	}
	
	
	public static void loadPreviousData(){
		
	}
	
	private static BufferedWriter getWriter(String filePath) {
		File tempFile = new File(filePath);
		BufferedWriter bufferedWriter = null;
		try {
			if (!tempFile.exists()) {
				tempFile.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(tempFile.getAbsoluteFile());
			bufferedWriter = new BufferedWriter(fileWriter);
		} catch (IOException e) {
			System.err.println("ERROR@LastFMDataController_getWriter");
			System.err.println(e.toString());
		}
		return bufferedWriter;
	}
	
	private static void getReader(String filePath){
		File tempFile = new File(filePath);
	}

}
