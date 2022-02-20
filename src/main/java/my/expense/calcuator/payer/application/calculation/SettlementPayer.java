package my.expense.calcuator.payer.application.calculation;

import lombok.Builder;
import lombok.Getter;
import lombok.Value;
import my.expense.calcuator.payer.domain.Debt;
import my.expense.calcuator.payer.domain.Debtor;
import my.expense.calcuator.payment.domain.Payment;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
@Getter
public class SettlementPayer {

    Long id;
    List<Payment> payments;
    List<Debt> debts;
    List<Debtor> debtors;
    BigDecimal balance;
    BigDecimal totalCostOfOnePerson;
    BigDecimal totalCostForAllPeople;


}



