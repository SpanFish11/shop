package com.spanfish.shop.rest;

import com.spanfish.shop.entity.Contacts;
import com.spanfish.shop.service.ContactsService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// TODO контакты можно обновить и просто вытянув пользователя
@RestController
@RequestMapping("/api/v1/{customerId}/contacts")
@RequiredArgsConstructor
public class ContactsController {

  private final ContactsService contactsService;

  // TODO переделать на Principal principal и искать по email
  @GetMapping(value = "")
  public ResponseEntity<Contacts> getOne(@PathVariable("customerId") Long customerId) {
    if (customerId == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Optional<Contacts> contactsOptional = contactsService.findContactByCustomerId(customerId);
    return contactsOptional
        .map(contacts -> new ResponseEntity<>(contacts, HttpStatus.OK))
        .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  // TODO переделать на Principal principal и искать по email
  @PutMapping
  // @ResponseBody
  public ResponseEntity<Contacts> update(
      @PathVariable("customerId") Long customerId, @RequestBody Contacts requestContacts) {

    if (requestContacts == null || customerId == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    Contacts contacts = contactsService.update(customerId, requestContacts);
    return new ResponseEntity<>(contacts, HttpStatus.OK);
  }
}
