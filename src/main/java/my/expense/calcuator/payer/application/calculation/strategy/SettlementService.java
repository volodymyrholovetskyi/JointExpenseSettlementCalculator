package my.expense.calcuator.payer.application.calculation.strategy;

import lombok.AllArgsConstructor;
import my.expense.calcuator.payer.domain.Payer;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class SettlementService {

    private List<SettlementPayer> settlementPayers;

    private final List<CalculateExpenseStrategy> strategies = Arrays.asList(
            new CalculateTotalCostOfOnePerson(),
            new CalculateTheAverageForAllPeople(),
            new CalculateBalanceStrategy(),
            new CalculateCostDebtorStrategy(),
            new CalculateCostDebtStrategy()
    );

    public List<SettlementPayer> calculateExpenses(List<Payer> payers) {
        settlementPayers.clear();
        toSettlementPayer(payers);
        strategies.stream().forEach(strategy -> strategy.calculate(settlementPayers));
        return settlementPayers;
    }

    private void toSettlementPayer(List<Payer> payers) {
        for (Payer payer : payers) {
            settlementPayers.add(SettlementPayer
                    .builder()
                    .firstName(payer.getFirstName())
                    .lastName(payer.getLastName())
                    .payments(payer.getPayments())
                    .build());
        }
    }
}
