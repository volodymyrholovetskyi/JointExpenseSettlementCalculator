package my.expense.calcuator.payment.db;

import my.expense.calcuator.payment.domain.Payer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PayerJpaRepository extends JpaRepository<Payer, Long> {

    @Query("SELECT DISTINCT p FROM Payer p LEFT JOIN FETCH p.event")
    List<Payer> getAllWithJoinFetch();
}
