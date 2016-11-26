package sk.mlp.util;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateEditor extends PropertyEditorSupport {
  private static final String FORMAT = "yyyy-MM-dd";

  public String getAsText() {
    return getValue() != null
             ? new SimpleDateFormat(FORMAT).format(getValue())
             : null;
  }

  public void setAsText(final String value) {
    try {
      setValue(new SimpleDateFormat(FORMAT).parse(value));
    }
    catch (final ParseException e) {
      // Log error.
    }
  }
}