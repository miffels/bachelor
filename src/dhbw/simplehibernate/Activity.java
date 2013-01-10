package dhbw.simplehibernate;

import org.hibernate.annotations.Entity;
import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


@Root
@Entity
public class Activity {
	
	@Attribute
	private int id = 0;
	
	@Element
	private String name = null;
	
	public Activity() {
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "Activity " + this.name + ", " + this.id;
	}

}
