/**
 * 
 */
package mainO;

import java.net.URL;
import org.w3c.dom.Document;

import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;

import dataHandler.AccessLastFM;
import dataHandler.LastFMDataController;
import jdk.management.resource.internal.inst.SocketOutputStreamRMHooks;
import dataHandler.FacebookDataController;

import java.util.List;

import objectStructures.*;
import java.util.*;

import clusterHandler.*;
/**
 * @author Sajith Dananjaya
 *
 */
public class MainExecuter {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		LastFMDataController.loadPreviousData();
		LastFMDataController.initiateUsers();
		String userFacebookID="";
		String name = "Sajith Dananjaya";
		String accessToken ="";
		
		User facebookUser = FacebookDataController
				.setUserTaste(new UserFacebook(userFacebookID, name, accessToken));
		facebookUser.setUserID(LastFMDataController.getNewUserID());
		facebookUser.filterTaste();
		
		LastFMDataController.createDataSheet();
		ClusterProcess temp = new ClusterProcess();
		temp.buildGraph("./trainDataSet.arff");
		
		String[] userIDList = temp.getRelatedUsers(facebookUser);
		
		for(String s: userIDList){
			System.out.println(s);
		}
		
		List<User> users= LastFMDataController.toUsers(temp.getRelatedUsers(facebookUser));
		for(User user: users){
			System.out.println(user.getUserName());
		}
		
		
//		LastFMDataController.saveTagInforamtion();
//		
//		for(Tag t: facebookUser.getMusicTaste().keySet()){
//			System.out.println(t.getTagName()+" : "+facebookUser.getMusicTaste().get(t));
//		}
//		
//		System.out.println(facebookUser.getTasteString());
	}

}
