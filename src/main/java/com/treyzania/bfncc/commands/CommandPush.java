package com.treyzania.bfncc.commands;

import java.io.InputStream;

import com.treyzania.bfncc.FunCompiler;

public class CommandPush extends Command {

	@Override
	public char[] getChars() {
		return new char[] { 's' };
	}

	@Override
	public void add(FunCompiler comp, char c, InputStream script) {
		comp.appendLine("push_stack(*dp);");
	}

}
