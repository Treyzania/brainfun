package com.treyzania.bfncc.commands;

import java.io.IOException;
import java.io.InputStream;

import com.treyzania.bfncc.FunCompiler;

public class SpecialHandler extends Command {

	private final char first, last;
	
	public SpecialHandler(char first, char last) {
		
		this.first = first;
		this.last = last;
		
	}
	
	@Override
	public void add(FunCompiler comp, char c, InputStream script) {
		
		boolean skipped = false;
		
		// TODO Add support the compiler flags.
		
		while (!skipped) {
			
			try {
				
				char read = (char) script.read(); 
				
				skipped = (read == last);
				
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
			
		}
		
	}

	@Override
	public char[] getChars() {
		return new char[] { first };
	}

}
