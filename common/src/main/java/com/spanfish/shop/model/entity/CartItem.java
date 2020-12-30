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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"cart", "product"})
@Entity
@Table(name = "m_cart_items")
public class CartItem implements Serializable {

  @Serial private static final long serialVersionUID = 3317640467893102163L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "cart_id")
  @JsonBackReference
  private Cart cart;

  @ManyToOne
  @JoinColumn(name = "product_id")
  private Product product;

  @Column(name = "amount")
  private Integer amount;
}
