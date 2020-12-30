package com.spanfish.shop.model.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.TemporalType.TIMESTAMP;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "customer")
@Entity
@Table(name = "m_orders")
public class Order implements Serializable {

  @Serial private static final long serialVersionUID = -5927255191402153227L;

  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @Column(name = "number")
  private String orderNumber;

  @OneToMany(mappedBy = "order", cascade = ALL, fetch = LAZY, orphanRemoval = true)
  @JsonManagedReference
  private List<OrderProduct> orderProductList = new ArrayList<>();

  @Column(name = "date")
  @Temporal(TIMESTAMP)
  private Date date;

  @Column(name = "total_price")
  private BigDecimal totalPrice;

  @Column(name = "status")
  @Enumerated(STRING)
  private OrderStatus status;
}
