package dhbw.simplehibernate;

import java.io.StringWriter;

import org.simpleframework.xml.core.Persister;

public class SimpleHibernate_Execute {
	
	public static void main(String[] args) throws Exception {
		Persister p = new Persister();
		StringWriter s = new StringWriter();
		Activity a = new Activity();
		a.setId(17);
		a.setName("Paule");
		
		p.write(a, s);
		System.out.println(s.toString());
		
		Activity b = p.read(Activity.class, "<activity id=\"17\"><name>Paule</name></activity>");
		System.out.println(b);
	}

}
