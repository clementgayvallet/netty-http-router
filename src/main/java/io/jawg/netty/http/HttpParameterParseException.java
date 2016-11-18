package io.jawg.netty.http;

/**
 * @author Clement Gayvallet
 * @since 18/11/16
 */
class HttpParameterParseException extends RuntimeException {

  HttpParameterParseException(String message) {
    super(message);
  }
}
