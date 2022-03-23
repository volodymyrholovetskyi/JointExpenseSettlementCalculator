package my.expense.calcuator.payer.application.calculation;

import lombok.Builder;
import lombok.Value;
import my.expense.calcuator.payment.domain.Payment;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class RichPayer {

    String firstName;
    String lastName;
    List<Payment> payments;
    List<Debt> debts;
    List<Debtor> debtors;
    BigDecimal totalCost;
    BigDecimal average;

}
