package my.expense.calcuator.payment.application;

import lombok.AllArgsConstructor;
import my.expense.calcuator.event.db.MeetingEventJpaRepository;
import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.payment.application.port.PayerUseCase;
import my.expense.calcuator.payment.db.PayerJpaRepository;
import my.expense.calcuator.payment.domain.Payer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor

public class PayerServices implements PayerUseCase {

    private final PayerJpaRepository repository;
    private final MeetingEventJpaRepository eventJpaRepository;


    @Override
    public List<Payer> getAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Optional<Payer> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Payer addPayer(CreatePayerCommand command) {
        Payer payer = toPayer(command);
        return repository.save(payer);
    }

    private Payer toPayer(CreatePayerCommand command){
        Payer payer = new Payer(command.getFirstName(), command.getLastName(), command.getEmail());
        MeetingEvent meetingEvent = fetchMeetingEventById(command.getEventId());
        updatePayer(payer, meetingEvent);
        return payer;
    }

    private void updatePayer(Payer payer, MeetingEvent meetingEvent) {
        payer.removeEvent();
        payer.addEvent(meetingEvent);
    }

    private MeetingEvent fetchMeetingEventById(Long eventId) {
        Optional<MeetingEvent> meetingEvent = eventJpaRepository.findById(eventId);
        return meetingEvent.orElseThrow(() -> new IllegalArgumentException("Unable to find event with id: " + eventId));
    }
}
