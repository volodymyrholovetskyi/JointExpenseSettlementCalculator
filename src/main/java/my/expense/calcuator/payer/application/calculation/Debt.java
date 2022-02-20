package my.expense.calcuator.payer.application.calculation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;

import java.math.BigDecimal;

@AllArgsConstructor
@Value
@Builder
public class Debt {

    private String firstName;

    private String lastName;

    private BigDecimal payment;
}
