package my.expense.calcuator.payment.application.port;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import my.expense.calcuator.payment.domain.Payer;
import my.expense.calcuator.payment.domain.PayerStatus;
import org.hibernate.mapping.Collection;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public interface PayerUseCase {

    Payer addPayer(CreatePayerCommand command);

    UpdatePayerResponse updatePayer(UpdatePayerCommand command);

    @Value
    @Builder
    @AllArgsConstructor
   class CreatePayerCommand {
        Long eventId;
        String firstName;
        String lastName;
        String email;
    }

    @Value
    @Builder
    @AllArgsConstructor
    class UpdatePayerCommand{
        Long id;
        String firstName;
        String lastName;
        String email;
    }

    @Value
   class UpdatePayerResponse{
        public static UpdatePayerResponse SUCCESS = new UpdatePayerResponse(true, Collections.emptyList());

    boolean success;
    List<String> errors;
    }

}
