package my.expense.calcuator.payer.application.calculation.strategy;

import lombok.Builder;
import lombok.Data;
import my.expense.calcuator.payer.application.calculation.Debt;
import my.expense.calcuator.payer.application.calculation.Debtor;
import my.expense.calcuator.payment.domain.Payment;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class SettlementPayer {

    String lastName;
    String firstName;
    List<Payment> payments;
    List<Debt> debts;
    List<Debtor> debtors;
    BigDecimal totalCost;
    BigDecimal balance;
    BigDecimal average;
    BigDecimal totalCostOfOnePerson;
    BigDecimal totalCostForAllPeople;


}



