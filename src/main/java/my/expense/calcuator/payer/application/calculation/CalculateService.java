package my.expense.calcuator.payer.application.calculation;

import lombok.AllArgsConstructor;
import my.expense.calcuator.event.db.MeetingEventJpaRepository;
import my.expense.calcuator.payer.application.calculation.strategy.SettlementPayer;
import my.expense.calcuator.payer.application.calculation.strategy.SettlementService;
import my.expense.calcuator.payer.domain.Payer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CalculateService {

    private final MeetingEventJpaRepository jpaRepository;
    private final SettlementService settlementService;

    private List<RichPayer> calculatedPayments;


    public List<RichPayer> getCalculatedExpenses(Long id) {
        List<Payer> payers = getEventById(id);
        List<SettlementPayer> settlementPayers = settlementService.calculateExpenses(payers);
        toExtendedPayer(settlementPayers);
        return calculatedPayments;
    }

    private void toExtendedPayer(List<SettlementPayer> settlementPayers) {
        calculatedPayments.clear();
        for (SettlementPayer settlementPayer : settlementPayers) {
            calculatedPayments.add(new RichPayer(
                    settlementPayer.getFirstName(),
                    settlementPayer.getLastName(),
                    settlementPayer.getPayments(),
                    settlementPayer.getDebts(),
                    settlementPayer.getDebtors(),
                    settlementPayer.getTotalCost(),
                    settlementPayer.getAverage(),
                    settlementPayer.getBalance()
            ));
        }
    }

    private List<Payer> getEventById(Long id) {
        return jpaRepository.findById(id)
                .map(meetingEvent -> meetingEvent.getPayers())
                .orElseThrow(() -> new IllegalArgumentException("Not found event by id: " + id));
    }
}
