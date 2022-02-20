package my.expense.calcuator.payment.application;

import lombok.AllArgsConstructor;
import my.expense.calcuator.payer.application.calculation.strategy.SettlementService;
import my.expense.calcuator.payment.application.port.QueryPaymentUseCase;
import my.expense.calcuator.payment.db.PaymentJpaRepository;
import my.expense.calcuator.payment.domain.Payment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QueryPaymentService implements QueryPaymentUseCase {

    private final PaymentJpaRepository paymentJpaRepository;
    private final SettlementService settlementService;

    @Override
    public List<Payment> findByTitle(String title) {
       return paymentJpaRepository.findByTitleStartsWithIgnoreCase(title);
    }

    @Override
    public List<Payment> findAll() {
        return paymentJpaRepository.findAll();
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentJpaRepository.findById(id);
    }
}
