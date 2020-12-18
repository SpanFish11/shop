package com.spanfish.shop.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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

import static javax.persistence.TemporalType.TIMESTAMP;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "customer")
@Entity
@Table(name = "m_orders")
public class Order implements Serializable {

  private static final long serialVersionUID = -5927255191402153227L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "customer_id")
  private Customer customer;

  @OneToMany(
      mappedBy = "order",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true)
  @JsonManagedReference
  private List<OrderProduct> orderProductList = new ArrayList<>();

  @Column(name = "date")
  @Temporal(TIMESTAMP)
  private Date date;

  @Column(name = "total_price")
  private BigDecimal totalPrice;

  @Column(name = "delivery_included")
  private Boolean deliveryIncluded;

  @Column(name = "confirmed")
  private Boolean confirmed = false;
}
