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
        settlementService.costExpense(payers);
//        toExtendedPayer();
//        return toExtendedPayer(payers);
        return extendedPayers;
    }


//    @Override
//    public List<ExtendedPayer> getAll() {
//        List<Payer> payers = repository.findAll();
//        List<ExtendedPayer> extendedPayers = payers.stream().map(payer -> toExtendedPayer(payer, payers))
//                .collect(Collectors.toList());
//        return extendedPayers;

    //
//    private ExtendedPayer toExtendedPayer(List<Payer> payers) {
//        List<SettlementPayer> settlementPayers = settlementService.costExpense(payers);
//
//        ExtendedPayer extendedPayer = ExtendedPayer.builder()
//
//                .debtors(settlement.getDebtors())
//                .debts(settlement.getDebts())
//                .build();
//
//        return extendedPayer;
//    }
}
