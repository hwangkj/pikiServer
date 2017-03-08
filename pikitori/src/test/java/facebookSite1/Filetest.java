package facebookSite1;

import java.io.File;

public class Filetest {

	public static void main(String[] args) {
		File f = new File("/piki");
		System.out.println("exist? : " + f.exists());
		
		File f1= new File("/piki/yeonfolder");
		f1.mkdir();
		
		System.out.println("exist? : " + f1.exists());	
	}

}
