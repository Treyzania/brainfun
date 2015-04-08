package com.treyzania.bfncc.commands;

import java.io.IOException;
import java.io.InputStream;

import com.treyzania.bfncc.FunCompiler;

public class CommandString extends Command {

	@Override
	public char[] getChars() {
		return new char[] { '\"' };
	}

	@Override
	public void add(FunCompiler comp, char c, InputStream script) {
		
		StringBuilder str = new StringBuilder();
		
		String lastThing = "";
		boolean stringEnded = false;
		
		while (!stringEnded) {
			
			try {
				
				int in = script.read();
				
				String test = Character.toString((char) in);
				
				if (test.equals("\n")) test = "";
				
				lastThing = test;
				
				//System.out.println("blah" + test);
				
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
			if (lastThing.equals("\"")) {
				stringEnded = true;
			} else {
				str.append(lastThing);
			}
			
		}
		
		// I'm not entirely sure how mashing strings into arrays in C works.  But I'm assuming this is the right way.
		// This was the old line.  It doesn't work because C.
		//comp.appendLine("*dp = \"" + str.toString() + "\";");
		
		// This one does work.
		comp.appendLine("strcpy(dp, \"" + str.toString() + "\");");
		
	}

}
