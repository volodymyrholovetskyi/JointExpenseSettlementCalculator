package my.expense.calcuator.payer.application.calculation.strategy;

import lombok.AllArgsConstructor;
import my.expense.calcuator.payer.application.PayerServices;
import my.expense.calcuator.payer.domain.Payer;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class SettlementService {

    private List<SettlementPayer> settlementPayers = new ArrayList<>();

//    private final List<CalculateExpenseStrategy> strategies = Arrays.asList(
//            new CalculateTotalCostOfOnePerson(),
//            new CalculateTheAverageForAllPeople()
//            );


    public void costExpense(List<Payer> payers) {
        CalculateExpenseStrategy average = new CalculateTheAverageForAllPeople();
        toSettlementPayer(payers);

        average.calculate(settlementPayers);
        settlementPayers.forEach(System.out::println);
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
