package io.jawg.netty.http;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Clement Gayvallet
 * @since 18/11/16
 */
public class QueryParameters {

  private Map<String, List<String>> params;

  QueryParameters(Map<String, List<String>> queryParams) {
    this.params = queryParams;
  }

  public List<String> getStrings(String param) {
    return params.get(param);
  }

  public String getString(String param) {
    List<String> values = params.get(param);
    if (values == null || values.isEmpty()) {
      return null;
    }
    return values.get(0);
  }

  public List<Integer> getIntegers(String param) {
    List<String> values = params.get(param);
    if (values == null) {
      return null;
    }
    try {
      return values
          .stream()
          .map(Integer::valueOf)
          .collect(Collectors.toList());
    } catch (NumberFormatException e) {
      throw new HttpParameterParseException(param + " is not an integer array");
    }
  }

  public Integer getInteger(String param) {
    List<String> values = params.get(param);
    if (values == null || values.isEmpty()) {
      return null;
    }
    try {
      return Integer.valueOf(values.get(0));
    } catch (NumberFormatException e) {
      throw new HttpParameterParseException(param + " is not an integer");
    }
  }

  public List<Float> getFloats(String param) {
    List<String> values = params.get(param);
    if (values == null) {
      return null;
    }
    try {
      return values
          .stream()
          .map(Float::valueOf)
          .collect(Collectors.toList());
    } catch (NumberFormatException e) {
      throw new HttpParameterParseException(param + " is not a float array");
    }
  }

  public Float getFloat(String param) {
    List<String> values = params.get(param);
    if (values == null || values.isEmpty()) {
      return null;
    }
    try {
      return Float.valueOf(values.get(0));
    } catch (NumberFormatException e) {
      throw new HttpParameterParseException(param + " is not a float");
    }
  }

  public List<Boolean> getBooleans(String param) {
    List<String> values = params.get(param);
    if (values == null) {
      return null;
    }
    return values
        .stream()
        .map(Boolean::valueOf)
        .collect(Collectors.toList());
  }

  public Boolean getBoolean(String param) {
    List<String> values = params.get(param);
    if (values == null || values.isEmpty()) {
      return null;
    }
    return Boolean.valueOf(values.get(0));
  }
}
