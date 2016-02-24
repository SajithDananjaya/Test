/**
 * 
 */
package dataHandler;

import java.net.URL;
import java.util.List;
import java.util.Scanner;
import com.restfb.Version;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import com.restfb.Connection;
import com.restfb.types.Post;
import com.restfb.types.Page;
import java.net.URLConnection;
import com.restfb.FacebookClient;
import com.restfb.DefaultFacebookClient;



import objectStructures.User;
import objectStructures.UserFacebook;
/**
 * @author Sajith Dananjaya
 *
 */
public class AccessFacebook {
	
	private static final String BASE_URL = "https://graph.facebook.com/v2.5/";
	
	public static List<String> getArtistList(User user){
		UserFacebook tempUser = ((UserFacebook)user);
		FacebookClient fbClient = 
				new DefaultFacebookClient(tempUser.getAccessToken(), Version.LATEST);
		List<String> artistList = new ArrayList<>();
		Connection<Post> respons =
				fbClient.fetchConnection(tempUser.getUserName()+"/Music", Post.class);
		for(List<Post> page :respons){
			for(Post post : page){
				artistList.add(post.getName());
			}
		}
		return artistList;
	}
	
	public static List<String> getRecentArtistList(User user){
		UserFacebook tempUser = ((UserFacebook)user);
		FacebookClient fbClient = 
				new DefaultFacebookClient(tempUser.getAccessToken(), Version.LATEST);
		List<Post> musicActivities = new ArrayList<>();
		Connection<Post> respons = 
				fbClient.fetchConnection(tempUser.getUserName()+"/feed", Post.class);
		for(List<Post> page : respons){
			for(Post p : page){
				if(p.getStory() != null && p.getStory().contains(" was listening to ")){
					musicActivities.add(p);
				}
			}
		}
		return extractArtistFromPost(musicActivities, user);
	}
	
	private static List<String> extractArtistFromPost(List<Post> postList,User user){
		List<String> recentArtistList = new ArrayList<>();
		for(Post post: postList){
			String stringURL = BASE_URL+post.getId()+"/attachments?access_token="+((UserFacebook)user).getAccessToken();
			try{
				URL url = new URL(stringURL);
				URLConnection respons = url.openConnection();
				String stringRespons = responsToString(respons);
				JSONArray jArray = new JSONArray(stringRespons.substring(8, stringRespons.length()-1));
				JSONObject dataObject = jArray.getJSONObject(0);
				recentArtistList.add(dataObject.getString("description"));
			}
			catch(IOException e){
				System.err.println("ERROR@extractArtistFromPost");
				System.err.println(e.toString());
			}
		}
		return recentArtistList;
	}
	
	public static String responsToString(URLConnection respons){
		String stringRespons ="";
        try {
			stringRespons =new Scanner(respons.getInputStream(),"UTF-8").useDelimiter("\\A").next();
		} catch (IOException e) {
			System.err.println("ERROR@responsToString");
			System.err.println(e.toString());
		}
		return stringRespons;
	}
}
