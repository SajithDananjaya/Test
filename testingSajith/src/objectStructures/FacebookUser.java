/**
 * 
 */
package objectStructures;

/**
 * @author Sajith Dananjaya
 *
 */
public class FacebookUser extends User{
	
	private String name;
	
	public FacebookUser(String username,String name){
		super.setUserName(username);
		this.name=name;
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public String getName(){
		return this.name;
	}
}
