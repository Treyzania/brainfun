package com.treyzania.bfncc;

import java.io.File;
import java.io.IOException;

public class FunScript {

	public final String filename;
	public final File compiling;
	
	public FunScript(String filename) {
		
		this.filename = filename;
		this.compiling = new File(this.filename);
		
		if (!compiling.exists()) {
			
			try {
				//compiling.getParentFile().mkdirs();
				compiling.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
}
