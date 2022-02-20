package my.expense.calcuator.payer.application.calculation;

import my.expense.calcuator.payer.domain.Payer;

import java.math.BigDecimal;
import java.util.List;

public class CalculateCostDebtorStrategy implements CalculateExpenseStrategy {

    @Override
    public void calculate(BigDecimal averages, List<Payer> payers) {

        for (Payer payer : payers) {
            for (int j = 1; j < payers.size(); j++) {
                if (payer.totalCost().compareTo(get(payers, j)) == -1){

                }
            }
        }

    }

    private BigDecimal getVal(List<Payer> payers, int j) {
        return payers.get(j).totalCost();
    }
}
