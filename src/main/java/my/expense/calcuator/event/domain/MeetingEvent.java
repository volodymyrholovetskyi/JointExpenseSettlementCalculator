package my.expense.calcuator.event.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import my.expense.calcuator.jpa.BaseEntity;
import my.expense.calcuator.payer.domain.Payer;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "event")
@Getter
@Setter
@ToString(exclude = "payers")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class MeetingEvent extends BaseEntity {

    private String name;

    private String location;

    @OneToMany(mappedBy = "event", cascade =
            {CascadeType.MERGE, CascadeType.PERSIST})
    @Fetch(FetchMode.SUBSELECT)
    @JsonIgnoreProperties("event")
    private List<Payer> payers = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    public MeetingEventStatus status;

    @CreatedDate
    private LocalDateTime createAt;

    @LastModifiedDate
    private LocalDateTime updateAt;

    @Builder
    public MeetingEvent(String name, String location, MeetingEventStatus status) {
        this.name = name;
        this.location = location;
        this.status = Optional.ofNullable(status).orElse(MeetingEventStatus.NEW);
    }

    public void updateStatus(UpdateStatusResult statusResult){
            this.status = statusResult.getNewStatus();
        }
    }
