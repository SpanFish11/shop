package com.spanfish.shop.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.TemporalType.TIMESTAMP;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_customers")
public class Customer implements Serializable {

  private static final long serialVersionUID = -5196041829626708075L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "registration_date")
  @Temporal(TIMESTAMP)
  private Date registrationDate;

  @Column(name = "email_verified")
  private Boolean emailVerified = false;

  @Column(name = "is_active")
  private Boolean isActive;

  @OneToOne(
      mappedBy = "customer",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true)
  @JsonManagedReference
  private Contacts contacts = new Contacts();

  @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  @JoinTable(
      name = "l_customers_roles",
      joinColumns = {
        @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
      },
      inverseJoinColumns = {
        @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
      })
  private Set<Role> roles = new HashSet<>();

  @OneToOne(
      mappedBy = "customer",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true)
  @JsonManagedReference
  private Cart cart;
}
