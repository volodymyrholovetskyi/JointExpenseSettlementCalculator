package my.expense.calcuator.payer.application;

import lombok.Builder;
import lombok.Value;
import my.expense.calcuator.payer.domain.Debt;
import my.expense.calcuator.payer.domain.Debtor;
import my.expense.calcuator.payment.domain.Payment;

import java.math.BigDecimal;
import java.util.List;

@Value
@Builder
public class ExtendedPayer {

    Long id;
    List<Payment> payments;
    List<Debt> debts;
    List<Debtor> debtors;
    BigDecimal totalCost;

    }
