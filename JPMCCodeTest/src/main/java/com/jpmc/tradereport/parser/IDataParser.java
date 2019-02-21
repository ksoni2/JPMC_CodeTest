package com.jpmc.tradereport.parser;

import java.util.List;

import com.jpmc.tradereport.dto.Instruction;
import com.jpmc.tradereport.exception.ApplicationException;

public interface IDataParser {

	public List<Instruction> parseInstructions() throws ApplicationException;
}
