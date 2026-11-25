package com.harbourline.booking.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "shipper")
public class Shipper {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "shipper_id")
    private Long id;
    @Column(name = "account_no")
    private String accountNo;
    @Column(name = "company_name")
    private String companyName;
    private String eori;

    public Long getId() { return id; }
    public String getAccountNo() { return accountNo; }
    public String getCompanyName() { return companyName; }
    public String getEori() { return eori; }
}
