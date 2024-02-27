package com.spring.ocr.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BillData {
@Id
    private String billNumber;
private Double noOfUnits;   // electricity units used
    private String name;
    private String date;
    private String billAmount;

}
