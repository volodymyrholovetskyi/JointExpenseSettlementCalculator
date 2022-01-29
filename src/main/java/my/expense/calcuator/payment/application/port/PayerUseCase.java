package my.expense.calcuator.payment.application.port;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import my.expense.calcuator.payment.domain.Payer;
import my.expense.calcuator.payment.domain.PayerStatus;

import java.util.List;
import java.util.Optional;

public interface PayerUseCase {

    Payer addPayer(CreatePayerCommand command);

    @Value
    @Builder
    @AllArgsConstructor
   class CreatePayerCommand {

        String firstName;
        String lastName;
        String email;
        Long eventId;
    }
}
