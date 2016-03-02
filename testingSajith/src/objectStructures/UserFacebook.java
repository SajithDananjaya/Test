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
	
	public UserFacebook(String userName,String name,String accessToken){
		super.setUserName(userName);
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
