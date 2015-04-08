package com.treyzania.bfncc.commands;

import java.io.InputStream;

import com.treyzania.bfncc.FunCompiler;

public abstract class Command {

	public Command() {
		
		// Something.
		
	}
	
	public abstract char[] getChars();
	public abstract void add(FunCompiler comp, char c, InputStream script);
	
}
