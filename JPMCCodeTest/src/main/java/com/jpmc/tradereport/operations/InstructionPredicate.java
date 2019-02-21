package com.jpmc.tradereport.operations;

import java.time.LocalDate;
import java.util.function.Predicate;

import com.jpmc.tradereport.dto.Instruction;

public class InstructionPredicate {
	
	public static Predicate<Instruction> isForSettlement(LocalDate currentDate) {
		return instruction -> instruction.getSettlementDate().equals(currentDate);
	}
}
