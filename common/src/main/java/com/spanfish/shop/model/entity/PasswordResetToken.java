package com.spanfish.shop.model.entity;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_password_reset_token")
public class PasswordResetToken implements Serializable {

  @Serial private static final long serialVersionUID = -2322017593268661307L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  private Long id;

  @Column(name = "token")
  private String token;

  @Column(name = "expiration")
  @Temporal(TIMESTAMP)
  private Date expiration;

  @OneToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;
}
