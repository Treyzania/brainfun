package com.treyzania.bfncc.commands;

import java.io.InputStream;

import com.treyzania.bfncc.FunCompiler;

public class CommandLoopEnd extends Command {

	@Override
	public char[] getChars() {
		return new char[] { ']' };
	}

	@Override
	public void add(FunCompiler comp, char c, InputStream script) {
		comp.currentTabLevel--;
		comp.appendFile("snips/while_end.csnip");
		comp.addEmptyLine();
	}

}
