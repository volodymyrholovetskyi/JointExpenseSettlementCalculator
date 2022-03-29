package my.expense.calcuator.payer.application.calculation.strategy;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
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

    public void setZeroBalance() {
        this.balance = BigDecimal.ZERO;
    }

    public void newBalance(BigDecimal newBalance) {
        if (newBalance == null) {
            this.balance = BigDecimal.ZERO;
        }
        this.balance = newBalance;
    }

    @Value
    @Builder
    public static class Debtor {

        String firstName;
        String lastName;
        BigDecimal payment;
    }

    @Value
    @Builder
    public static class Debt {

        String firstName;
        String lastName;
        BigDecimal payment;
    }
}



