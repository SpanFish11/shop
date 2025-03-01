package com.spanfish.shop.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serial;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_customers_contacts")
public class Contacts implements Serializable {

  @Serial private static final long serialVersionUID = -1550444710845029616L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "phone")
  private String phone;

  @Column(name = "zip")
  private Integer zip;

  @Column(name = "city")
  private String city;

  @Column(name = "address")
  private String address;

  @OneToOne
  @JoinColumn(name = "customer_id")
  @JsonBackReference
  private Customer customer;
}
