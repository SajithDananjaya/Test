/**
 * 
 */
package mainO;

import java.net.URL;
import org.w3c.dom.Document;
import java.util.List;

/**
 * @author Sajith Dananjaya
 *
 */
public class MainExecuter {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		String url ="http://ws.audioscrobbler.com/2.0/?method=user.getFriends&user=sajithdr&api_key=";
		URL tempUrl = new URL(url);

		Document tempXml = lastFM.AccessLastFM.grabXML(tempUrl);
		List<String> usersNames =lastFM.AccessLastFM.extractPattern("<name>(.*?)</name>", tempXml,4);

		for(String name:usersNames){
			System.out.println(name);
		}

	}

}
