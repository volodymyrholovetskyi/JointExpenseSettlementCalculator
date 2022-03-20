package my.expense.calcuator.payer.application.calculation;

import lombok.AllArgsConstructor;
import my.expense.calcuator.payer.application.QueryPayerService;
import my.expense.calcuator.payer.application.calculation.strategy.SettlementPayer;
import my.expense.calcuator.payer.application.calculation.strategy.SettlementService;
import my.expense.calcuator.payer.domain.Payer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CalculateService {

    private final QueryPayerService payerService;
    private final SettlementService settlementService;

    private List<ExtendedPayer> extendedPayers;


    public List<ExtendedPayer> getAllCalculations() {
        List<Payer> payers = payerService.getAll();
        List<SettlementPayer> settlementPayers = settlementService.costExpense(payers);
        toExtendedPayer(settlementPayers);
        return extendedPayers;
    }

    private void toExtendedPayer(List<SettlementPayer> settlementPayers) {
        for (SettlementPayer settlementPayer : settlementPayers) {
            extendedPayers.add(new ExtendedPayer(
                    settlementPayer.getFirstName(),
                    settlementPayer.getLastName(),
                    settlementPayer.getPayments(),
                    settlementPayer.getDebts(),
                    settlementPayer.getDebtors(),
                    settlementPayer.getTotalCost(),
                    settlementPayer.getAverage()
            ));
        }
    }
}
