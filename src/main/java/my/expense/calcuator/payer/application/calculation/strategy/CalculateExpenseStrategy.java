package my.expense.calcuator.payer.application.calculation.strategy;

import java.util.List;

interface CalculateExpenseStrategy {
    List<SettlementPayer> calculate(List<SettlementPayer> settlementPayers);
}
