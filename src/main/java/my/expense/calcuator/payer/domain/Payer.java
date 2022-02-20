package my.expense.calcuator.payer.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.jpa.BaseEntity;
import my.expense.calcuator.payer.application.calculation.Debt;
import my.expense.calcuator.payer.application.calculation.Debtor;
import my.expense.calcuator.payment.domain.Payment;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Setter
@ToString(exclude = {"payments", "event"})
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Payer extends BaseEntity {

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(name = "message")
    private boolean isMessage;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable
            (name = "payer_event",
                    joinColumns = @JoinColumn(name = "payer_id"),
                    inverseJoinColumns = @JoinColumn(name = "event_id")
            )
    @JsonIgnoreProperties("payers")
    private MeetingEvent event = new MeetingEvent();

    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "payerId")
    @Singular
    @Fetch(FetchMode.SUBSELECT)
    private List<Payment> payments = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    public PayerStatus status = PayerStatus.NOT_SETTLED;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    @Builder
    public Payer(String firstName, String lastName, String email, PayerStatus status, boolean message) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = Optional.ofNullable(status).orElse(this.status);
        this.isMessage = message;
    }

    public void addEvent(MeetingEvent meetingEvent) {
        event = meetingEvent;
        event.getPayers().add(this);
    }

    public void removeEvent() {
        Payer self = this;
        event.getPayers().remove(self);
        event = null;
    }

    public void addPayment(Payment payment){
        payments.add(payment);
    }

    public BigDecimal totalCost(){
        return payments.stream()
                .map(Payment::getPayment)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

