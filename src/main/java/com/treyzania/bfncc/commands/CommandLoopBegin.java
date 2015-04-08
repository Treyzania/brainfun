package com.treyzania.bfncc.commands;

import java.io.InputStream;

import com.treyzania.bfncc.FunCompiler;

public class CommandLoopBegin extends Command {

	@Override
	public char[] getChars() {
		return new char[] { '[' };
	}

	@Override
	public void add(FunCompiler comp, char c, InputStream script) {
		if (!(comp.lastCommand instanceof CommandLoopEnd)) comp.addEmptyLine();
		comp.appendFile("snips/while_begin.csnip");
		comp.currentTabLevel++;
	}

}
