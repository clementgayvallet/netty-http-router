package io.jawg.netty.http;

import java.util.Map;

/**
 * @author Clement Gayvallet
 * @since 17/11/16
 */
public class PathVariables {

  private Map<String, String> params;

  PathVariables(Map<String, String> params) {
    this.params = params;
  }

  public String getString(String param) {
    return params.get(param);
  }

  public Integer getInteger(String param) {
    String value = params.get(param);
    if (value == null) {
      return null;
    }
    try {
      return Integer.valueOf(value);
    } catch (NumberFormatException e) {
      throw new HttpParameterParseException(param + " is not an integer");
    }
  }

  public Float getFloat(String param) {
    String value = params.get(param);
    if (value == null) {
      return null;
    }
    try {
      return Float.valueOf(value);
    } catch (NumberFormatException e) {
      throw new HttpParameterParseException(param + " is not a float");
    }
  }

  public Boolean getBoolean(String param) {
    String value = params.get(param);
    if (value == null) {
      return null;
    }
    try {
      return Boolean.valueOf(value);
    } catch (NumberFormatException e) {
      throw new HttpParameterParseException(param + " is not a boolean");
    }
  }
}
