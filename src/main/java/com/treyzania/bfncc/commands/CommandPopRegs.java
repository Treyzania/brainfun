package com.treyzania.bfncc.commands;

import java.io.InputStream;

import com.treyzania.bfncc.FunCompiler;

public class CommandPopRegs extends Command {

	@Override
	public char[] getChars() {
		return new char[] { 'R' };
	}

	@Override
	public void add(FunCompiler comp, char c, InputStream script) {
		comp.appendLine("popregs();");
	}

}
