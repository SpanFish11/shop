package com.spanfish.shop.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "products")
@Entity
@Table(name = "m_manufacturers")
public class Manufacturer implements Serializable {

  private static final long serialVersionUID = 4920050002360993182L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "description", nullable = false)
  private String description;

  // TODO нужен ли мапинг
  @OneToMany(
      mappedBy = "manufacturer",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true) // orphanRemoval = true ?????
  @JsonIgnore
  Set<Product> products = new HashSet<>();
}
