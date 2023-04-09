package vn.edu.hcmuaf.st.chuyendeweb.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private Long id;
    @Column
    @Getter
    @Setter
    @CreatedBy
    private String createBy;
    @Column
    @Getter
    @Setter
    @CreatedDate
    private Date createdDate;
    @Column
    @Getter
    @Setter
    @LastModifiedBy
    private String modifiedBy;
    @Column
    @Getter
    @Setter
    @LastModifiedDate
    private Date modifiedDate;
}
