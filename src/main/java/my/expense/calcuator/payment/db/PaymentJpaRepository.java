package my.expense.calcuator.payment.db;

import my.expense.calcuator.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentJpaRepository extends JpaRepository<Payment, Long> {


    List<Payment> findByTitleStartsWithIgnoreCase(String title);

    @Query("SELECT AVG(p) FROM Payment p")
    BigDecimal getAveragesFromPayment();

}
