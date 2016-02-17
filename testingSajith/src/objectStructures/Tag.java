/**
 * 
 */
package objectStructures;

/**
 * @author Sajith Dananjaya
 *
 */
public class Tag {
	private int tagID;
	private String tagName;
	
	private Tag(){}
	
	public Tag(int tagID,String tagName){
		this.tagID = tagID;
		this.tagName = tagName;
	}
	
	public int getTagID(){
		return this.tagID;
	}
	
	public String getTagName(){
		return this.tagName;
	}

}
