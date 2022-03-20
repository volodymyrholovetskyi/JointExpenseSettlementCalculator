package my.expense.calcuator.payer.application.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@AllArgsConstructor
@Value
@Builder
public class Debtor {

    String firstName;

    String lastName;

    BigDecimal payment;
}