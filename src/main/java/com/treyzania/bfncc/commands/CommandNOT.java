package com.treyzania.bfncc.commands;

import java.io.InputStream;

import com.treyzania.bfncc.FunCompiler;

public class CommandNOT extends Command {

	@Override
	public char[] getChars() {
		return new char[] { 'N' };
	}

	@Override
	public void add(FunCompiler comp, char c, InputStream script) {
		comp.appendLine("*dp = ~*dp");
	}

}
