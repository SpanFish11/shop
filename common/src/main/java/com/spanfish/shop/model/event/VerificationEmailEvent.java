package com.spanfish.shop.model.event;

import com.spanfish.shop.model.entity.Customer;
import java.io.Serial;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class VerificationEmailEvent extends ApplicationEvent {

  @Serial private static final long serialVersionUID = 1159091094318500870L;

  private Customer customer;
  private String token;

  /**
   * Create a new {@code ApplicationEvent}.
   *
   * @param source the object on which the event initially occurred or with which the event is
   *     associated (never {@code null})
   */
  public VerificationEmailEvent(final Object source, final Customer customer, final String token) {
    super(source);
    this.customer = customer;
    this.token = token;
  }
}
