package my.expense.calcuator.payer.db;

import my.expense.calcuator.payer.domain.Payer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PayerJpaRepository extends JpaRepository<Payer, Long> {

    @Query("SELECT DISTINCT p FROM Payer p LEFT JOIN FETCH p.event")
    List<Payer> getAllWithJoinFetch();

    List<Payer> findByFirstNameStartsWithIgnoreCase(String firstName);

    List<Payer> findByLastNameStartsWithIgnoreCase(String lastName);

    List<Payer> findByFirstNameAndLastNameStartsWithIgnoreCase(String firstName, String lastName);

}
