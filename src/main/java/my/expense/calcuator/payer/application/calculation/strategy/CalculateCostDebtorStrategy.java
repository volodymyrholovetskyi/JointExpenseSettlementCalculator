package my.expense.calcuator.payer.application.calculation.strategy;

import my.expense.calcuator.payer.application.calculation.Debtor;

import java.math.BigDecimal;
import java.util.List;

public class CalculateCostDebtorStrategy implements CalculateExpenseStrategy {


    @Override
    public void calculate(List<SettlementPayer> settlementPayers) {
        for (SettlementPayer settlementPayer : settlementPayers) {
            for (int i = 0; i < settlementPayers.size(); i++) {
                if (settlementPayer.getBalance().compareTo(BigDecimal.ZERO) < 0
                        && settlementPayers.get(i).getBalance().compareTo(BigDecimal.ZERO) > 0) {

                    BigDecimal balanceDifference = subtract(settlementPayers.get(i), settlementPayer);
                    if (balanceDifference.compareTo(BigDecimal.ZERO) > 0) {
                        reverse(balanceDifference, settlementPayers.get(i), settlementPayer);
                    } else {
                        Debtor result = toDebtor(settlementPayers.get(i), settlementPayers.get(i).getBalance());
                        settlementPayer.addDebtor(result);
                        settlementPayers.get(i).setBalance(BigDecimal.ZERO);
                    }
                }
            }
        }
    }

    private BigDecimal subtract(SettlementPayer otherPayer, SettlementPayer actualPayer) {
        BigDecimal subtract = otherPayer.getBalance().add(actualPayer.getBalance());
        actualPayer.setBalance(subtract);
        return subtract;
    }

    private void reverse(BigDecimal balanceDifference, SettlementPayer otherPayer, SettlementPayer actualPayer) {
        actualPayer.setBalance(actualPayer.getBalance().subtract(balanceDifference));
        BigDecimal balanceToByPickedUp = otherPayer.getBalance().subtract(balanceDifference);
        BigDecimal subtractBalance = otherPayer.getBalance().subtract(balanceToByPickedUp);
        Debtor debtor = toDebtor(otherPayer, balanceToByPickedUp);
        actualPayer.addDebtor(debtor);
        otherPayer.setBalance(subtractBalance);
    }


    private Debtor toDebtor(SettlementPayer settlementPayer, BigDecimal subtract) {
        return new Debtor(settlementPayer.getFirstName(),
                settlementPayer.getLastName(), subtract
        );
    }
}
