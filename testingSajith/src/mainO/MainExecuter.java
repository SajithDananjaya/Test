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
 		
		LastFMDataController.createDataSheet();
		LastFMDataController.saveTagInforamtion();
		LastFMDataController.saveArtistInforamtion();
//		
//		
//		String userID="";
//		String name = "Sajith Dananjaya";
//		String accessToken ="";
//		
//		User facebookUser = FacebookDataController
//				.setUserTaste(new UserFacebook(userID, name, accessToken));
//		facebookUser.filterTaste();
//		LastFMDataController.saveTagInforamtion();
//		
//		for(Tag t: facebookUser.getMusicTaste().keySet()){
//			System.err.println(t.getTagName()+" : "+facebookUser.getMusicTaste().get(t));
//		}
//		
//		System.out.println(facebookUser.getTasteString());
	}

}
