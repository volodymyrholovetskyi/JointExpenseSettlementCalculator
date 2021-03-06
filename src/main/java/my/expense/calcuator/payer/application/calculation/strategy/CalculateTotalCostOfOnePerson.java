package my.expense.calcuator.payer.application.calculation.strategy;

import my.expense.calcuator.payment.domain.Payment;

import java.math.BigDecimal;
import java.util.List;

class CalculateTotalCostOfOnePerson implements CalculateExpenseStrategy {

    @Override
    public List<SettlementPayer> calculate(List<SettlementPayer> settlementPayers) {

        for (SettlementPayer payer : settlementPayers) {
            BigDecimal totalCost = payer.getPayments().stream()
                    .map(Payment::getPayment)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (payer.getPayments().size() >= 1) {
                payer.setTotalCost(totalCost);
            } else payer.setTotalCost(BigDecimal.ZERO);
        }
        return settlementPayers;
    }
}
