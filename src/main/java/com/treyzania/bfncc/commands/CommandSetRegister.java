package com.treyzania.bfncc.commands;

import java.io.InputStream;

import com.treyzania.bfncc.BrainfunCC;
import com.treyzania.bfncc.FunCompiler;

public class CommandSetRegister extends Command {

	public final char register; 
	
	public CommandSetRegister(char reg) {
		this.register = Character.toLowerCase(reg);
	}
	
	@Override
	public char[] getChars() {
		return new char[] { register };
	}

	@Override
	public void add(FunCompiler comp, char c, InputStream script) {
		
		String rawStatement = BrainfunCC.downloadURL(ClassLoader.getSystemResource("snips/reg_set.csnip"));
		
		comp.appendLine(rawStatement.replaceAll("\\{REGISTER\\}", Character.toString(this.register).toLowerCase()));
		
	}

}
