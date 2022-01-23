package my.expense.calcuator.payment.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import my.expense.calcuator.event.domain.MeetingEvent;
import my.expense.calcuator.shared.jpa.BaseEntity;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@ToString(exclude = {"payments", "event"})
@NoArgsConstructor
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

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    public Payer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

