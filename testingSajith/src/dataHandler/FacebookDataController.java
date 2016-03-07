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
		//artistNameList.addAll(AccessFacebook.getRecentArtistList(user));
		User tempUser = LastFMDataController.addUserTags(user, artistNameList);
		tempUser.setUserID(LastFMDataController.getNewUserID());
		return tempUser;
	}
}
