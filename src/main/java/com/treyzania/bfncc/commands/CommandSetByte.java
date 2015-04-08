package com.treyzania.bfncc.commands;

import java.io.IOException;
import java.io.InputStream;

import com.treyzania.bfncc.FunCompiler;

public class CommandSetByte extends Command {

	@Override
	public char[] getChars() {
		return new char[] { 'x' };
	}

	@Override
	public void add(FunCompiler comp, char c, InputStream script) {
		
		char top = 0;
		char bottom = 0;
		
		try {
			top = (char) script.read();
			bottom = (char) script.read();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Hack so that we don't have to actually parse the values.
		String hexNumb = "0x" + Character.toUpperCase(top) + Character.toUpperCase(bottom);
		
		// Finally.
		comp.appendLine("*dp = " + hexNumb + ";");
		
		
	}

}
