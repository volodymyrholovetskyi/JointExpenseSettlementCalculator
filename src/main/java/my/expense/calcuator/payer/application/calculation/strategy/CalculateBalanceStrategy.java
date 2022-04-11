package my.expense.calcuator.payer.application.calculation.strategy;

import java.math.BigDecimal;
import java.util.List;

class CalculateBalanceStrategy implements CalculateExpenseStrategy {

    @Override
    public List<SettlementPayer> calculate(List<SettlementPayer> settlementPayers) {
        for (SettlementPayer payer : settlementPayers) {
            BigDecimal subtract = getSubtract(payer.getAverage(), payer.getTotalCost());
            payer.setBalance(subtract);
        }
        return settlementPayers;
    }

    private BigDecimal getSubtract(BigDecimal average, BigDecimal totalCost) {
        return average.subtract(totalCost);
    }
}
