package my.expense.calcuator.payer.application.calculation.strategy;

import my.expense.calcuator.payer.domain.Payer;

import java.math.BigDecimal;
import java.util.List;

public interface CalculateExpenseStrategy {

    void calculate(List<SettlementPayer> settlementPayers);
}
