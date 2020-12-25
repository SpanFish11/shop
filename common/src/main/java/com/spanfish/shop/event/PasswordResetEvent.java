package com.spanfish.shop.event;

import com.spanfish.shop.entity.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class PasswordResetEvent extends ApplicationEvent {

  private static final long serialVersionUID = -1954334327811143810L;

  private Customer customer;
  private String token;

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   *     associated (never {@code null})
   */
  public PasswordResetEvent(Object source, Customer customer, String token) {
    super(source);
    this.customer = customer;
    this.token = token;
  }
}
