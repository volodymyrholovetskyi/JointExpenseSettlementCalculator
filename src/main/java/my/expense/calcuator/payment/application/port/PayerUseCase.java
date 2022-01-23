package my.expense.calcuator.payment.application.port;

import lombok.Builder;
import lombok.Value;
import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.payment.domain.Payer;
import my.expense.calcuator.payment.domain.Payment;

import java.util.List;
import java.util.Optional;

public interface PayerUseCase {

    List<Payer> getAll();

    Optional<Payer> getById(Long id);

    Payer addPayer(CreatePayerCommand command);

    @Value
    class CreatePayerCommand {

        String lastName;
        String firstName;
        String email;
        Long eventId;
    }
}
