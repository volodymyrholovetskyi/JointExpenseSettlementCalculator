package my.expense.calcuator.uploads.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import my.expense.calcuator.shared.jpa.BaseEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
public class Uploads extends BaseEntity {

    private byte[] file;
    private String contentType;
    private String filename;
    @CreatedDate
    private LocalDateTime createdAt;

    public Uploads(byte[] file, String contentType, String filename){
        this.file = file;
        this.contentType = contentType;
        this.filename = filename;
    }
}

