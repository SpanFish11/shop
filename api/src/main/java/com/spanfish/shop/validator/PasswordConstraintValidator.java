package com.spanfish.shop.validator;

import static java.lang.String.join;
import static java.util.Arrays.asList;
import static org.passay.EnglishCharacterData.Digit;
import static org.passay.EnglishCharacterData.LowerCase;
import static org.passay.EnglishCharacterData.Special;
import static org.passay.EnglishCharacterData.UpperCase;
import static org.passay.EnglishSequenceData.Alphabetical;
import static org.passay.EnglishSequenceData.Numerical;
import static org.passay.EnglishSequenceData.USQwerty;

import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.SneakyThrows;
import org.passay.CharacterRule;
import org.passay.IllegalSequenceRule;
import org.passay.LengthRule;
import org.passay.MessageResolver;
import org.passay.PasswordData;
import org.passay.PasswordValidator;
import org.passay.PropertiesMessageResolver;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;

public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

  private static final int ILLEGAL_SEQUENCES = 5;
  private static final int COUNT_CHARACTERS = 1;

  @Override
  public void initialize(ValidPassword arg0) {}

  @SneakyThrows
  @Override
  public boolean isValid(final String password, final ConstraintValidatorContext context) {

    final Properties props = new Properties();
    final InputStream inputStream =
        getClass().getClassLoader().getResourceAsStream("passay.properties");
    props.load(inputStream);
    final MessageResolver resolver = new PropertiesMessageResolver(props);

    PasswordValidator validator =
        new PasswordValidator(
            resolver,
            asList(

                // length between 8 and 84 characters
                new LengthRule(8, 84),

                // at least one upper-case character
                new CharacterRule(UpperCase, COUNT_CHARACTERS),

                // at least one lower-case character
                new CharacterRule(LowerCase, COUNT_CHARACTERS),

                // at least one digit character
                new CharacterRule(Digit, COUNT_CHARACTERS),

                // at least one symbol (special character)
                new CharacterRule(Special, COUNT_CHARACTERS),

                // define some illegal sequences that will fail when >= 5 chars long
                // alphabetical is of the form 'abcde', numerical is '34567', qwery is 'asdfg'
                // the false parameter indicates that wrapped sequences are allowed; e.g. 'xyzabc'
                new IllegalSequenceRule(Alphabetical, ILLEGAL_SEQUENCES, false),
                new IllegalSequenceRule(Numerical, ILLEGAL_SEQUENCES, false),
                new IllegalSequenceRule(USQwerty, ILLEGAL_SEQUENCES, false),

                // no whitespace
                new WhitespaceRule()));

    final RuleResult result = validator.validate(new PasswordData(password));
    if (result.isValid()) {
      return true;
    }
    final List<String> messages = validator.getMessages(result);
    final String messageTemplate = join(",", messages);
    context
        .buildConstraintViolationWithTemplate(messageTemplate)
        .addConstraintViolation()
        .disableDefaultConstraintViolation();
    return false;
  }
}
