package com.jpmc.tradereport.service;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jpmc.tradereport.dto.Entity;
import com.jpmc.tradereport.dto.Instruction;
import com.jpmc.tradereport.dto.TradeType;
import com.jpmc.tradereport.exception.ApplicationException;
import com.jpmc.tradereport.parser.IDataParser;
import com.jpmc.tradereport.parser.InstructionCSVParser;
import com.jpmc.tradereport.util.TradeReportUtil;

public class TradeReportService {

	private static final Logger logger = LoggerFactory.getLogger(TradeReportService.class);

	private IDataParser instructionParser;

	private List<Instruction> filteredInstructions;

	private NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));

	private String message = "";

	public void printDailyTradeAmounts(TradeType tradeType) throws ApplicationException {

		if (tradeType.name().equals(TradeType.BUY.name())) {
			message = "Outgoing trade amount on ";
		} else {
			message = "Incoming trade amount on ";
		}

		filteredInstructions = getFilteredInstructions(tradeType);

		Map<LocalDate, Double> dailyTradeAmountsMap = TradeReportUtil.calculateDailyTradeAmounts(filteredInstructions);

		dailyTradeAmountsMap.entrySet().stream().forEach(instruction -> logger
				.info(message + instruction.getKey() + " is " + currencyFormatter.format(instruction.getValue())));
	}

	public void printEntitiesRanking(TradeType tradeType) throws ApplicationException {

		if (tradeType.name().equals(TradeType.BUY.name())) {
			message = "Outgoing entities based on ranking ...";
		} else {
			message = "Incoming entities based on ranking ...";
		}

		filteredInstructions = getFilteredInstructions(tradeType);

		List<Entity> entities = TradeReportUtil.getEntityRankings(filteredInstructions);

		logger.info(message);
		logger.info("Entity Name \tTrade Amount");
		logger.info("*********** \t************");
		entities.stream().forEach(
				entity -> logger.info(entity.getName() + "\t" + currencyFormatter.format(entity.getTradeAmount())));
	}

	private List<Instruction> getFilteredInstructions(TradeType tradeType) throws ApplicationException {

		instructionParser = new InstructionCSVParser();

		List<Instruction> instructions = instructionParser.parseInstructions();

		filteredInstructions = instructions.stream()
				.filter(instruction -> tradeType.name().equalsIgnoreCase(instruction.getTradeType()))
				.collect(Collectors.toList()).stream().map(TradeReportUtil.adjustSettlementDate())
				.collect(Collectors.toList());

		filteredInstructions.sort((instruction1, instruction2) -> instruction1.getSettlementDate()
				.compareTo(instruction2.getSettlementDate()));

		return filteredInstructions;
	}
}
