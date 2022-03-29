package my.expense.calcuator.payer.application.calculation.strategy;

import my.expense.calcuator.payer.application.calculation.strategy.SettlementPayer.Debtor;

import java.math.BigDecimal;
import java.util.List;

class CalculateCostDebtorStrategy implements CalculateExpenseStrategy {


    @Override
    public void calculate(List<SettlementPayer> settlementPayers) {
        for (SettlementPayer settlementPayer : settlementPayers) {
            for (int i = 0; i < settlementPayers.size(); i++) {
                if (settlementPayer.getBalance().compareTo(BigDecimal.ZERO) < 0
                        && settlementPayers.get(i).getBalance().compareTo(BigDecimal.ZERO) > 0) {

                    BigDecimal subtract = subtractBalance(settlementPayers.get(i).getBalance(), settlementPayer.getBalance());
                    if (subtract.compareTo(BigDecimal.ZERO) > 0) {
                        reverse(settlementPayers.get(i), settlementPayer);
                    } else {
                        Debtor result = toDebtor(settlementPayers.get(i), settlementPayers.get(i).getBalance());
                        settlementPayer.addDebtor(result);
                        settlementPayer.newBalance(subtract);
                        settlementPayers.get(i).setZeroBalance();
                    }
                }
            }
        }
    }

    private void reverse(SettlementPayer otherPayer, SettlementPayer actualPayer) {
        BigDecimal calculateAnotherPayer = subtractBalance(otherPayer.getBalance(), actualPayer.getBalance());
        BigDecimal calculateTheDebt = subtractBalance(otherPayer.getBalance(), calculateAnotherPayer);
        BigDecimal calculateActualPayer = subtractBalance(calculateTheDebt, actualPayer.getBalance());
        Debtor debtor = toDebtor(otherPayer, calculateTheDebt);
        actualPayer.addDebtor(debtor);
        actualPayer.newBalance(calculateActualPayer);
        otherPayer.newBalance(calculateAnotherPayer);
    }

    private BigDecimal subtractBalance(BigDecimal otherBalance, BigDecimal actualBalance) {
        if (actualBalance.compareTo(BigDecimal.ZERO) < 0) {
            BigDecimal balance = BigDecimal.valueOf(0).subtract(actualBalance);
            return otherBalance.subtract(balance);
        }
        return otherBalance.subtract(actualBalance);
    }

    private Debtor toDebtor(SettlementPayer settlementPayer, BigDecimal calculateTheDebt) {
        return Debtor.builder()
                .firstName(settlementPayer.getFirstName())
                .lastName(settlementPayer.getLastName())
                .payment(calculateTheDebt)
                .build();
    }
}


