/**
 * 
 */
package mainO;

import java.net.URL;
import org.w3c.dom.Document;

import dataHandler.AccessLastFM;
import dataHandler.LastFMDataController;
import dataHandler.FacebookDataController;

import java.util.List;

import objectStructures.*;
import java.util.*;
/**
 * @author Sajith Dananjaya
 *
 */
public class MainExecuter {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		String userID="";
		String name = "Sajith Dananjaya";
		String accessToken ="";
		
		User facebookUser = FacebookDataController
				.setUserTaste(new UserFacebook(userID, name, accessToken));
		
		for(Tag t: facebookUser.getMusicTaste().keySet()){
			System.err.println(t.getTagName()+" : "+facebookUser.getMusicTaste().get(t));
		}
	}

}
