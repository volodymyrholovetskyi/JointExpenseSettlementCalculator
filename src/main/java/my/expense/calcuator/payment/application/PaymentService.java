package my.expense.calcuator.payment.application;

import lombok.AllArgsConstructor;
import my.expense.calcuator.payer.db.PayerJpaRepository;
import my.expense.calcuator.payer.domain.Payer;
import my.expense.calcuator.payment.application.port.PaymentUseCase;
import my.expense.calcuator.payment.db.PaymentJpaRepository;
import my.expense.calcuator.payment.domain.Payment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class PaymentService implements PaymentUseCase {

    private final PaymentJpaRepository repository;
    private final PayerJpaRepository payerJpaRepository;

    @Override
    @Transactional
    public Payment createPayment(CreatePaymentCommand command) {

        Payment payment = toPayment(command);
        return repository.save(payment);
    }

    private Payment toPayment(CreatePaymentCommand command) {
        Payment payment = Payment
                .builder()
                .title(command.getTitle())
                .payment(command.getPayment())
                .build();
        toUpdate(command.getPayerId(), payment);
        return payment;
    }

    private void toUpdate(Long id, Payment payment) {
        Payer payer = fetchPayerById(id);
        payer.addPayment(payment);
    }

    @Override
    @Transactional
    public UpdatePaymentResponse updatePayment(UpdatePaymentCommand command) {
        return repository.findById(command.getId())
                .map(payment -> {
                    updateFields(command, payment);
                    return UpdatePaymentResponse.SUCCESS;
                }).orElseGet(() -> new UpdatePaymentResponse(false,
                        Collections.singletonList("Payment not fond with id: " + command.getId())));
    }

    private Payment updateFields(UpdatePaymentCommand command, Payment payment) {
        if (command.getTitle() != null) {
            payment.setTitle(command.getTitle());
        }

        if (command.getPayment() != null) {
            payment.setPayment(command.getPayment());
        }

        return payment;
    }

    private Payer fetchPayerById(Long id) {
        Optional<Payer> payer = payerJpaRepository.findById(id);
        return payer.orElseThrow(() -> new IllegalArgumentException("Unable to find payer by id: " + id));
    }
}
