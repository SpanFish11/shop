package com.spanfish.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"category", "products"})
@Entity
@Table(name = "m_subcategories")
public class SubCategory implements Serializable {

  private static final long serialVersionUID = 3664615081521795379L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @ManyToOne
  @JoinColumn(name = "category_id")
  @JsonBackReference
  private Category category;

  @OneToMany(
      mappedBy = "subCategory",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY,
      orphanRemoval = true) // orphanRemoval = true ?????
  @JsonIgnore
  Set<Product> products = new HashSet<>();
}
