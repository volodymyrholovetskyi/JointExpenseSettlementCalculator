package my.expense.calcuator.payer.application.calculation.strategy;

import java.util.List;

public interface CalculateExpenseStrategy {

    void calculate(List<SettlementPayer> settlementPayers);
}
