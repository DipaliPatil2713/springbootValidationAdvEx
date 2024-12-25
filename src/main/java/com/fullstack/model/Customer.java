package com.fullstack.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CUSTOMER")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int custId;

    @Size(min = 2,message = "Customer name should be contain at least 2 character")
    private String custName;

    @NotNull(message = "Address must be required")
    private String custAddress;


    private double custAccountBalance;

    @Range(min = 1000000000, max = 9999999999L,message = "Contact Number Must be 10 Digit")
    @Column(unique = true)
    private long custContactNumber;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date custDOB;

    @Email(message = "Email Should be in an appropriate manner")
    @Column(unique = true)
    private String custEmailId;

    @Size(min = 4,message = "Password contain at least 4 character")
    private String custPassword;
}
