package my.expense.calcuator.payment.application.port;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import my.expense.calcuator.payment.domain.Payment;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public interface PaymentUseCase {

    Payment createPayment(CreatePaymentCommand command);

    UpdatePaymentResponse updatePayment(UpdatePaymentCommand command);


    @Value
    @Builder
    @AllArgsConstructor
    class CreatePaymentCommand {
        Long payerId;
        String title;
        BigDecimal payment;
    }

    @Value
    @Builder
    @AllArgsConstructor
    class UpdatePaymentCommand {
        Long id;
        String title;
        BigDecimal payment;
    }

    @Value
    class UpdatePaymentResponse {

        public static UpdatePaymentResponse SUCCESS = new UpdatePaymentResponse(true, Collections.emptyList());

        boolean success;
        List<String> errors;

    }
}
