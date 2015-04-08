package com.treyzania.bfncc.commands;

import java.io.InputStream;

import com.treyzania.bfncc.FunCompiler;

public class CommandNullify extends Command {

	@Override
	public char[] getChars() {
		return new char[] { '_' }; // Dat face.
	}

	@Override
	public void add(FunCompiler comp, char c, InputStream script) {
		comp.appendLine("*dp = 0;");
	}

}
