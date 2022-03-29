package my.expense.calcuator.payer.application;

import lombok.AllArgsConstructor;
import my.expense.calcuator.event.db.MeetingEventJpaRepository;
import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.payer.application.port.PayerUseCase;
import my.expense.calcuator.payer.db.PayerJpaRepository;
import my.expense.calcuator.payer.domain.Payer;
import my.expense.calcuator.payment.domain.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

@Service
@AllArgsConstructor

public class PayerServices implements PayerUseCase {

    private final PayerJpaRepository repository;
    private final MeetingEventJpaRepository eventJpaRepository;

    @Override
    @Transactional
    public Payer addPayer(CreatePayerCommand command) {
        Payer payer = toPayer(command);
        return repository.save(payer);
    }

    private Payer toPayer(CreatePayerCommand command) {
        Payer payer = Payer
                .builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .email(command.getEmail())
                .build();
        toUpdate(command.getEventId(), payer);
        return payer;
    }

    private void toUpdate(Long id, Payer payer) {
        MeetingEvent meetingEvent = fetchMeetingEventById(id);
        payer.removeEvent();
        payer.addEvent(meetingEvent);
    }

    @Override
    @Transactional
    public UpdatePayerResponse updatePayer(UpdatePayerCommand command) {
        return repository
                .findById(command.getId())
                .map(payer -> {
                    updateFields(command, payer);
                    return UpdatePayerResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdatePayerResponse(false,
                        Collections.singletonList("Payer not fond with id: " + command.getId())));
    }

    @Override
    public void remoteById(Long id) {
        repository.deleteById(id);
    }


    private Payer updateFields(UpdatePayerCommand command, Payer payer) {
        if (command.getFirstName() != null) {
            payer.setFirstName(command.getFirstName());
        }
        if (command.getLastName() != null) {
            payer.setLastName(command.getLastName());
        }
        if (command.getEmail() != null) {
            payer.setEmail(command.getEmail());
        }
        return payer;
    }

    private MeetingEvent fetchMeetingEventById(Long eventId) {
        Optional<MeetingEvent> payer = eventJpaRepository.findById(eventId);
        return payer.orElseThrow(() -> new IllegalArgumentException("Unable to find event with id: " + eventId));
    }
}
