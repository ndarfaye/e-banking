package org.sid.ebankingbachend.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "SA")
@Data @NoArgsConstructor @AllArgsConstructor
public class SavinAccount extends BankAccount{
    private double interestRate;
}
