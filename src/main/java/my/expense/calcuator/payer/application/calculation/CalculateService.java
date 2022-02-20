package my.expense.calcuator.payer.application.calculation;

import lombok.AllArgsConstructor;
import my.expense.calcuator.payer.domain.Payer;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class CalculateService {

    private final List<CalculateExpenseStrategy> strategies = Arrays.asList(
            new CalculateTotalCostOfOnePerson(),
            new CalculateTheTotalCostForAllPeople()
    );

    public SettlementPayer costExpense(BigDecimal averages, List<Payer> payers) {

        for (Payer payer : payers) {
            SettlementPayer settlementPayer = new SettlementPayer()
        }

        SettlementPayer settlement = new SettlementPayer(payer.getDebts(), payer.getDebtors());
        calculate(averages, payers);
        return settlement;
    }

    public void calculate(BigDecimal averages, Payer payer) {


        strategies.forEach(s -> s.calculate(averages, payer));
    }
}
