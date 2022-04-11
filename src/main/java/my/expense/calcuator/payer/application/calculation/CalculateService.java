package my.expense.calcuator.payer.application.calculation;

import lombok.AllArgsConstructor;
import my.expense.calcuator.event.db.MeetingEventJpaRepository;
import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.event.domain.UpdateStatusResult;
import my.expense.calcuator.payer.application.calculation.strategy.SettlementPayer;
import my.expense.calcuator.payer.application.calculation.strategy.SettlementFacade;
import my.expense.calcuator.payer.domain.Payer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static my.expense.calcuator.event.domain.MeetingEventStatus.DURING_SETTLEMENT;
import static my.expense.calcuator.event.domain.MeetingEventStatus.NEW;

@Service
@AllArgsConstructor
public class CalculateService {

    private final MeetingEventJpaRepository jpaRepository;
    private final SettlementFacade settlementFacade;

    private List<RichPayer> calculatedPayments;


    @Transactional
    public List<RichPayer> getCalculatedExpenses(Long id) {
        List<Payer> payers = getEventById(id);
        List<SettlementPayer> settlementPayers = settlementFacade.calculateExpenses(payers);
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
                .map(meetingEvent -> {
                    updateStatus(meetingEvent);
                    List<Payer> payers = meetingEvent.getPayers();
                    return payers;
                })
                .orElseThrow(() -> new IllegalArgumentException("Not found event by id: " + id));
    }

    private void updateStatus(MeetingEvent event) {
        UpdateStatusResult updateStatusResult = DURING_SETTLEMENT.updateStatus(event.getStatus());
        if (updateStatusResult.getNewStatus() != NEW && updateStatusResult.getNewStatus() != DURING_SETTLEMENT)
            event.updateStatus(updateStatusResult);
    }
}
