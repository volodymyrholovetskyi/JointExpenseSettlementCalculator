package my.expense.calcuator.payer.application.calculation.strategy;

import my.expense.calcuator.payment.domain.Payment;

import java.math.BigDecimal;
import java.util.List;

public class CalculateTheAverageForAllPeople implements CalculateExpenseStrategy {

    @Override
    public void calculate(List<SettlementPayer> settlementPayers) {

        for (SettlementPayer payer : settlementPayers) {
            BigDecimal reduce = payer.getPayments().stream()
                    .map(Payment::getPayment)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (payer.getPayments().size() > 1){
                payer.setAverage(getAverage(reduce, payer));
            }else if (payer.getPayments().size() == 1){
                payer.setAverage(payer.getPayments().get(0).getPayment());
            }else payer.setAverage(BigDecimal.ZERO);
        }
    }

    private BigDecimal getAverage(BigDecimal reduce, SettlementPayer payer) {
        return reduce.divide(BigDecimal.valueOf(payer.getPayments().size()));
    }
}
