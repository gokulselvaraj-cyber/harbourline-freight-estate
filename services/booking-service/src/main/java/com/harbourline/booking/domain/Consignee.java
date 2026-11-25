package com.harbourline.booking.domain;

import jakarta.persistence.*;
import java.time.OffsetDateTime;

/**
 * The party that receives the goods. Stored in table {@code delivery_party}; customs-docs calls it
 * {@code shipTo} and tracking-worker calls it the {@code receiver}. Holds personal data when the
 * receiver is a private person (name, phone, email, address).
 */
@Entity
@Table(name = "delivery_party")
public class Consignee {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "delivery_party_id")
    private Long id;
    @Column(name = "contact_name")
    private String contactName;
    @Column(name = "company_name")
    private String companyName;
    private String phone;
    private String email;
    @Column(name = "address_line1")
    private String addressLine1;
    private String city;
    private String postcode;
    private String country;
    private String eori;
    @Column(name = "delivered_at")
    private OffsetDateTime deliveredAt;

    protected Consignee() { }

    public Consignee(String contactName, String companyName, String phone, String email,
                     String addressLine1, String city, String postcode, String country, String eori) {
        this.contactName = contactName;
        this.companyName = companyName;
        this.phone = phone;
        this.email = email;
        this.addressLine1 = addressLine1;
        this.city = city;
        this.postcode = postcode;
        this.country = country;
        this.eori = eori;
    }

    public Long getId() { return id; }
    public String getContactName() { return contactName; }
    public String getCompanyName() { return companyName; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddressLine1() { return addressLine1; }
    public String getCity() { return city; }
    public String getPostcode() { return postcode; }
    public String getCountry() { return country; }
    public String getEori() { return eori; }
}
