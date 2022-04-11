package my.expense.calcuator.payer.application.calculation.strategy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

class CalculateTheAverageForAllPeople implements CalculateExpenseStrategy {

    @Override
    public List<SettlementPayer> calculate(List<SettlementPayer> settlementPayers) {

        for (SettlementPayer payer : settlementPayers) {
            BigDecimal reduce = settlementPayers.stream()
                    .map(settlementPayer -> settlementPayer.getTotalCost())
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            payer.setAverage(getAverage(reduce, settlementPayers.size()));
        }
        return settlementPayers;
    }

    private BigDecimal getAverage(BigDecimal reduce, int size) {
      return reduce.divide(BigDecimal.valueOf(size), 2, RoundingMode.HALF_UP);
    }
}
