/**
 * 
 */
package objectStructures;

/**
 * @author Sajith Dananjaya
 *
 */
public class UserFacebook extends User{
	
	private String name;
	private String accessToken;
	
	public UserFacebook(String userFacebookID,String name,String accessToken){
		super.setUserName(userFacebookID);
		this.name=name;
		this.accessToken=accessToken;
	}
	
	
	public String getName(){
		return this.name;
	}
	
	public String getAccessToken(){
		return this.accessToken;
	}
}
