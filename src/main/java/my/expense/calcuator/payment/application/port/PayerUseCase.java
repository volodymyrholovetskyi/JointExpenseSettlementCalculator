package my.expense.calcuator.payment.application.port;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import my.expense.calcuator.payment.domain.Payer;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;

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
    class UpdatePayerCommand {
        Long id;
        String firstName;
        String lastName;
        String email;
    }

    @Value
    @Builder
    @AllArgsConstructor
    class CreatePaymentCommand {
        Long payerId;
        String whatFor;
        BigDecimal payment;
    }

    @Value
    class UpdatePaymentToThePayerResponse {
        public static UpdatePaymentToThePayerResponse SUCCESS = new UpdatePaymentToThePayerResponse(true, emptyList());

        boolean success;
        List<String> errors;
    }

    @Value
    class UpdatePayerResponse {
        public static UpdatePayerResponse SUCCESS = new UpdatePayerResponse(true, emptyList());

        boolean success;
        List<String> errors;
    }

    @Value
    class UpdatePayerMeetingEventResponse {
        public static UpdatePayerMeetingEventResponse SUCCESS = new UpdatePayerMeetingEventResponse(true, emptyList());

        boolean success;
        List<String> errors;
    }
}
