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

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;
import objectStructures.Tag;
import objectStructures.User;
import objectStructures.Song;
import objectStructures.Artist;
import objectStructures.UserLastFM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LastFMDataController {

	private static int currentTagID = 1;
	private static int currentUserID = 1;
	private static List<Tag> newTags = new ArrayList<>();
	private static List<User> initialUsers = new ArrayList<>();
	private static HashMap<Integer, User> initialUsersInfo = new HashMap<>();
	private static HashMap<String, Tag> initailTags = new HashMap<>();
	private static HashMap<String, Artist> learnedArtists = new HashMap<>();

	public static void initiateUsers() {
		URL url = AccessLastFM.getURL("user.getFriends&user=sajithdr&limit=5");
		Document userListXML = AccessLastFM.grabXML(url);
		List<String> userList = AccessLastFM.extractPattern("<name>(.*?)</name>", userListXML, 4);
		for (String userName : userList) {
			System.out.println("Learning of user " + userName);
			User tempUser = setUserTaste(userName);
			tempUser.setUserName(userName);
			tempUser.setUserID(currentUserID);
			tempUser.filterTaste();
			currentUserID++;
			initialUsers.add(tempUser);
			initialUsersInfo.put(tempUser.getUserID(), tempUser);
		}
		System.out.println("Done");
	}

	public static User setUserTaste(String userName) {
		User tempUser = new UserLastFM(userName);
		URL url = AccessLastFM.getURL("user.getTopArtists&user=" + userName + "&limit=100");
		Document userArtitsList = AccessLastFM.grabXML(url);
		List<String> artistNameList = AccessLastFM.extractPattern("<name>(.*?)</name>", userArtitsList, 4);
		return addUserTags(tempUser, artistNameList);
	}

	public static User addUserTags(User user, List<String> artistNameList) {
		for (String artistName : artistNameList) {
			// System.out.println(artistName);
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
			URL url = AccessLastFM.getURL("artist.getTopTags&artist=" + artistName + "&limit=5");
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

	public static void saveUserInformation() {
		BufferedWriter tempWriter = getWriter("./learnedUsers.txt");
		try {
			for (User user : initialUsers) {
				String data = user.getUserID() + "," + user.getUserName() + user.getTasteString();
				// System.err.println(data);
				tempWriter.write(data);
				tempWriter.newLine();
			}
			tempWriter.close();
		} catch (IOException e) {
			System.err.println("ERROR@LastFMDataController_saveUserInformation");
			System.err.println(e.toString());
		}
	}

	public static void saveArtistInforamtion() {
		BufferedWriter tempWriter = getWriter("./learnedArtists.txt");
		try {
			for (String artistName : learnedArtists.keySet()) {
				try {
					Artist artist = learnedArtists.get(artistName);
					String data = artist.getArtistName() + artist.getTagListString();
					// System.err.println(data);
					tempWriter.write(data);
					tempWriter.newLine();
				} catch (Exception e) {
					System.err.println("Error on saving " + artistName);
				}
			}
			tempWriter.close();
		} catch (IOException e) {
			System.err.println("ERROR@LastFMDataController_saveArtistInformation");
			System.err.println(e.toString());
		}
	}

	public static void createDataSheet() {
		BufferedWriter tempWriter = getWriter("./trainDataSet.arff");
		try {
			tempWriter.write("@relation dataSet");
			tempWriter.newLine();
			tempWriter.newLine();

			tempWriter.write("@attribute userID numeric");
			tempWriter.newLine();

			for (int index = 0; index < currentTagID - 1; index++) {
				tempWriter.write("@attribute tag" + index + " numeric");
				tempWriter.newLine();
			}

			tempWriter.newLine();
			tempWriter.write("@data");
			tempWriter.newLine();

			for (User user : initialUsers) {
				tempWriter.write(user.getUserID() + user.getTasteString());
				tempWriter.newLine();
			}
			tempWriter.close();

		} catch (IOException e) {
			System.err.println("asdfasdfasdf");
		}
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

	public static void loadPreviousData() {
		currentTagID = 1;
		initailTags = loadLearnedTag();
		currentTagID = initailTags.size() + 1;
		learnedArtists = loadLearnedArtist();

	}

	private static HashMap<String, Tag> loadLearnedTag() {
		String filePath = "./learnedTags.txt";
		HashMap<String, Tag> tempMap = new HashMap<>();
		BufferedReader dataReader = getReader(filePath);

		try {
			String dataLine = "";

			while ((dataLine = dataReader.readLine()) != null) {
				String[] data = dataLine.split(",");
				// System.out.println(dataLine);
				int tagID = Integer.parseInt(data[0]);
				String tagName = data[1];
				Tag tempTag = new Tag(tagID, tagName);
				// System.out.println(tempTag.getTagID());
				tempMap.put(tagName, tempTag);
			}

		} catch (IOException e) {
			System.err.println("ERROR@LastFMDataController_loadLearnedTag");
			System.err.println(e.toString());
		}

		return tempMap;

	}

	private static HashMap<String, Artist> loadLearnedArtist() {
		String filePath = "./learnedArtists.txt";
		HashMap<String, Artist> tempMap = new HashMap<>();
		BufferedReader dataReader = getReader(filePath);

		try {
			String dataLine = "";

			while ((dataLine = dataReader.readLine()) != null) {
				String[] data = dataLine.split(",");
				Artist tempArtist = new Artist(data[0]);
				// System.out.println(dataLine);
				if (data.length > 1) {
					for (int index = 1; index < data.length; index++) {
						if (initailTags.containsKey(data[index])) {
							Tag tag = initailTags.get(data[index]);
							tempArtist.addArtistTag(tag);
						}
					}
				}
				tempMap.put(tempArtist.getArtistName(), tempArtist);
			}

		} catch (IOException e) {
			System.err.println("ERROR@LastFMDataController_loadLearnedTag");
			System.err.println(e.toString());
		}

		return tempMap;

	}

	private static BufferedReader getReader(String filePath) {
		File tempFile = new File(filePath);
		FileReader fileReader = null;
		try {
			fileReader = new FileReader(tempFile);
		} catch (IOException e) {
			System.err.println("ERROR@LastFMDataController_getReader");
			System.err.println(e.toString());
		}
		return new BufferedReader(fileReader);
	}

	public static List<User> toUsers(String[] userIDList) {
		List<User> users = new ArrayList<>();
		for (String userID : userIDList) {
			users.add(initialUsersInfo.get(userID));
		}
		return users;
	}

}
