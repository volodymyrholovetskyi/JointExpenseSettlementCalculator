package my.expense.calcuator.payer.application.calculation.strategy;

import my.expense.calcuator.payer.application.calculation.Debt;
import my.expense.calcuator.payer.application.calculation.Debtor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;

public class CalculateCostDebtStrategy implements CalculateExpenseStrategy {

    @Override
    public void calculate(List<SettlementPayer> settlementPayers) {
        for (SettlementPayer settlementPayer : settlementPayers) {
            if (settlementPayer.getDebtors() != null) {

                for (Debtor debtor : settlementPayer.getDebtors()) {
                    Optional<SettlementPayer> byNameAndLastName = findByNameAndLastName(debtor.getFirstName(), debtor.getLastName(), settlementPayers);
                    if (byNameAndLastName.isPresent()) {
                        update(debtor, byNameAndLastName.get(), settlementPayer);
                    }
                }
            }
        }
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

    private void update(Debtor debtor, SettlementPayer... settlementPayer) {
        SettlementPayer actual = settlementPayer[1];
        Debt debt = new Debt(actual.getFirstName(), actual.getLastName(), debtor.getPayment());
        SettlementPayer target = settlementPayer[0];
        target.addDebt(debt);

    }
}
