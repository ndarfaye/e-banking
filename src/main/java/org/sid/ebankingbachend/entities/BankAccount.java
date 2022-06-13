package org.sid.ebankingbachend.entities;

import com.sun.deploy.security.ValidationState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.engine.internal.Cascade;
import org.sid.ebankingbachend.enums.AccountStatus;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "TYPE", length = 4)
@Data @NoArgsConstructor @AllArgsConstructor
public abstract class BankAccount {
    @Id
    private String id;
    private double balance;
    private Date dateAt;
    @Enumerated(EnumType.STRING)
    private AccountStatus status;
    @ManyToOne(cascade = CascadeType.ALL) //Un compte concerne un seul client
    private Customer customer;
    @OneToMany(mappedBy = "bankAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL) //
    private List<AccountOperation> accountOperation;
}
