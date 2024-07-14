package com.capstone.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Data
public class MedicineDetails {


    @Id
    private String medicineName;

    @Enumerated(EnumType.STRING)
    private MedicineType medicineType;

    private int totalQuantity;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Temporal(TemporalType.DATE)
    private Date expiryDate;

    private int price;


    public MedicineDetails() {

    }
}
