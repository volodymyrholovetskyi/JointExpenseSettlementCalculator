package my.expense.calcuator.payment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import my.expense.calcuator.calculation.domain.Estimation;
import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.shared.jpa.BaseEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable
            (name = "payer_event",
                    joinColumns = @JoinColumn(name = "payer_id"),
                    inverseJoinColumns = @JoinColumn(name = "event_id")
            )
    @JsonIgnoreProperties("payers")
    private MeetingEvent event = new MeetingEvent();

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "payer_id")
    private List<Payment> payments = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "payer_id")
    private Set<Estimation> estimations = new HashSet<>();

    @Enumerated(EnumType.STRING)
    public PayerStatus status = PayerStatus.NEW;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    @Builder
    public Payer(String firstName, String lastName, String email, PayerStatus status) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = Optional.ofNullable(status).orElse(this.status);
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
}

