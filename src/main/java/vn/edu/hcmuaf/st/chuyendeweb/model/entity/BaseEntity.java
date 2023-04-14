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

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    @Setter
    @CreatedBy
    private String createBy;
    @Column
    @Setter
    @CreatedDate
    private Date createdDate;
    @Column
    @Setter
    @LastModifiedBy
    private String modifiedBy;
    @Column
    @Setter
    @LastModifiedDate
    private Date modifiedDate;
}
