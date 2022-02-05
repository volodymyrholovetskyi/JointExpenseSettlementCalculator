package my.expense.calcuator.payment.db;

import my.expense.calcuator.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {


    List<Payment> findByTitleStartsWithIgnoreCase(String title);
}
