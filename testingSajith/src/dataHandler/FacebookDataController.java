/**
 * 
 */
package dataHandler;

/**
 * @author Sajith Dananjaya
 *
 */

import java.util.List;
import java.util.ArrayList;

import objectStructures.User;
import objectStructures.UserFacebook;


public class FacebookDataController {
	
	public static User setUserTaste(User user){	
		List<String> artistNameList = AccessFacebook.getArtistList(user);
		artistNameList.addAll(AccessFacebook.getRecentArtistList(user));
		return LastFMDataController.addUserTags(user, artistNameList);
		
	}
	
	
	
	
	
	
	
	
}
