import org.mint.gui.ProgramEditor;

import java.util.TreeMap;

/*
 * Created on Mar 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class Mint {

    static class Person{
        Person(){}
    }

	public static void main(String[] args) {
//		new ProgramEditor();

        Person o = new Person();

        TreeMap map = new TreeMap();
        map.put(1,1);
        map.put(2,1);
        map.put(3,1);
        map.put(4,1);

//        map.put(9,1);


        System.out.println(map.firstEntry());
	}


}
