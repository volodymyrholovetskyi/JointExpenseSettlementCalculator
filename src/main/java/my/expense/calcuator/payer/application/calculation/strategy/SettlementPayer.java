package my.expense.calcuator.payer.application.calculation.strategy;

import lombok.Builder;
import lombok.Data;
import my.expense.calcuator.payer.application.calculation.Debt;
import my.expense.calcuator.payer.application.calculation.Debtor;
import my.expense.calcuator.payment.domain.Payment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SettlementPayer {

    private String lastName;
    private String firstName;
    private List<Payment> payments;
    private List<Debt> debts;
    private List<Debtor> debtors;
    private BigDecimal totalCost;
    private BigDecimal balance;
    private BigDecimal average;
    private BigDecimal totalCostOfOnePerson;
    private BigDecimal totalCostForAllPeople;


    public void addDebtor(Debtor debtor) {
        if (debtors == null) {
            debtors = new ArrayList<>();
        }
        debtors.add(debtor);
    }

    public void addDebt(Debt debt) {
        if (debts == null) {
            debts = new ArrayList<>();
        }
        debts.add(debt);
    }
}



