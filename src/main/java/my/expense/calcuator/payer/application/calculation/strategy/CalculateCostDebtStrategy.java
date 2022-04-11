package my.expense.calcuator.payer.application.calculation.strategy;

import my.expense.calcuator.payer.application.calculation.strategy.SettlementPayer.Debt;
import my.expense.calcuator.payer.application.calculation.strategy.SettlementPayer.Debtor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

class CalculateCostDebtStrategy implements CalculateExpenseStrategy {

    @Override
    public List<SettlementPayer> calculate(List<SettlementPayer> settlementPayers) {
        for (SettlementPayer settlementPayer : settlementPayers) {
            if (settlementPayer.getDebtors() != null) {

                for (Debtor debtor : settlementPayer.getDebtors()) {
                    Optional<SettlementPayer> byNameAndLastName = findByNameAndLastName(debtor.getFirstName(),
                            debtor.getLastName(), settlementPayers);
                    if (byNameAndLastName.isPresent()) {
                        updateSettlementPayer(debtor, byNameAndLastName.get(), settlementPayer);
                    }
                }
            }
        }
        return settlementPayers;
    }

    private Optional<SettlementPayer> findByNameAndLastName(String firstName, String lastName,
                                                            List<SettlementPayer> settlementPayers) {
        for (SettlementPayer settlementPayer : settlementPayers) {
            if (StringUtils.equalsIgnoreCase(settlementPayer.getFirstName(), firstName)
                    && StringUtils.equalsIgnoreCase(settlementPayer.getLastName(), lastName)) {
                return Optional.ofNullable(settlementPayer);
            }
        }
        return Optional.empty();
    }

    private void updateSettlementPayer(Debtor debtor, SettlementPayer... settlementPayer) {
        SettlementPayer actual = settlementPayer[1];
        Debt debt = Debt.builder()
                .firstName(actual.getFirstName())
                .lastName(actual.getLastName())
                .payment(debtor.getPayment())
                .build();
        SettlementPayer target = settlementPayer[0];
        target.addDebt(debt);

    }
}
