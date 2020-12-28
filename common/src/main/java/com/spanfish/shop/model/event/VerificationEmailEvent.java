package com.spanfish.shop.model.event;

import com.spanfish.shop.model.entity.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class VerificationEmailEvent extends ApplicationEvent {

  private static final long serialVersionUID = 1159091094318500870L;

  private Customer customer;
  private String token;

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   *     associated (never {@code null})
   */
  public VerificationEmailEvent(Object source, Customer customer, String token) {
    super(source);
    this.customer = customer;
    this.token = token;
  }
}
