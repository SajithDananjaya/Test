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
	
	public UserFacebook(String username,String name){
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
