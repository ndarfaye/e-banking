package org.sid.ebankingbachend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sid.ebankingbachend.enums.OperattionType;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private Date OperationDate;
    private double amount;
    @Enumerated(EnumType.STRING)
    private OperattionType type;
    @ManyToOne //une operation concerne un compte
    private BankAccount bankAccount;
    private String description;


}
