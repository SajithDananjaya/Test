/**
 * 
 */
package mainO;

import java.net.URL;
import org.w3c.dom.Document;

import dataHandler.AccessLastFM;
import dataHandler.LastFMDataController;

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
		
		LastFMDataController.initiateUsers();
		
		System.out.println(LastFMDataController.getTagCount());
		
		List<User> userList = LastFMDataController.getUserList();
		for(User u:userList){
			System.err.println(u.getUserName());
			for(Tag tag: u.getMusicTaste().keySet()){
				System.out.print(tag.getTagName()+" : "+u.getMusicTaste().get(tag)+", ");
			}
			System.err.println("");
		}
		System.out.println("##### DONE");

	}

}
