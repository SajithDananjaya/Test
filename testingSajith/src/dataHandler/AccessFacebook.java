/**
 * 
 */
package dataHandler;

import java.util.List;
import com.restfb.Version;
import java.util.ArrayList;
import com.restfb.Connection;
import com.restfb.types.Post;
import com.restfb.types.Page;
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

}
