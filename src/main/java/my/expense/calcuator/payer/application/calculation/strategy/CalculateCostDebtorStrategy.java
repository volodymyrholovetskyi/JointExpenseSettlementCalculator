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

                    BigDecimal subtract = subtractBalance(settlementPayers.get(i).getBalance(), settlementPayer.getBalance());
                    settlementPayer.setNewBalance(subtract);
                    if (subtract.compareTo(BigDecimal.ZERO) > 0) {
                        reverse(subtract, settlementPayers.get(i), settlementPayer);
                    } else {
                        Debtor result = toDebtor(settlementPayers.get(i), settlementPayers.get(i).getBalance());
                        settlementPayer.addDebtor(result);
                        settlementPayer.setZeroBalance();
                    }
                }
            }
        }
    }

    private BigDecimal subtractBalance(BigDecimal otherBalance, BigDecimal actualBalance) {
        if (actualBalance.compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal positiveBalance = BigDecimal.valueOf(0).subtract(actualBalance);
            return otherBalance.subtract(positiveBalance);
        }
        return otherBalance.subtract(actualBalance);
    }

    private void reverse(BigDecimal balanceDifference, SettlementPayer otherPayer, SettlementPayer actualPayer) {
        BigDecimal bigDecimal = subtractBalance(otherPayer.getBalance(), actualPayer.getBalance());
        actualPayer.setNewBalance(bigDecimal);
        BigDecimal balanceToByPickedUp = subtractBalance(otherPayer.getBalance(), balanceDifference);
        BigDecimal subtractBalance = subtractBalance(otherPayer.getBalance(), balanceToByPickedUp);
        Debtor debtor = toDebtor(otherPayer, balanceToByPickedUp);
        actualPayer.addDebtor(debtor);
        otherPayer.setNewBalance(subtractBalance);
    }

    private Debtor toDebtor(SettlementPayer settlementPayer, BigDecimal subtract) {
        return new Debtor(settlementPayer.getFirstName(),
                settlementPayer.getLastName(), subtract
        );
    }
}
