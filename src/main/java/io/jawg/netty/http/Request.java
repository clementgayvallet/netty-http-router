package io.jawg.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderResult;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

/**
 * @author Clement Gayvallet
 * @since 17/11/16
 */
public class Request implements FullHttpRequest {

  private FullHttpRequest fullHttpRequest;
  private PathVariables pathVariables;
  private QueryParameters queryParameters;


  public Request(FullHttpRequest fullHttpRequest, PathVariables pathVariables, QueryParameters queryParameters) {
    this.fullHttpRequest = fullHttpRequest;
    this.pathVariables = pathVariables;
    this.queryParameters = queryParameters;
  }

  public PathVariables pathVariables() {
    return pathVariables;
  }

  public QueryParameters queryParameters() {
    return queryParameters;
  }

  @Override
  public FullHttpRequest copy() {
    return fullHttpRequest.copy();
  }

  @Override
  public FullHttpRequest duplicate() {
    return fullHttpRequest.duplicate();
  }

  @Override
  public FullHttpRequest retainedDuplicate() {
    return fullHttpRequest.retainedDuplicate();
  }

  @Override
  public FullHttpRequest replace(ByteBuf content) {
    return fullHttpRequest.replace(content);
  }

  @Override
  public FullHttpRequest retain(int increment) {
    return fullHttpRequest.retain(increment);
  }

  @Override
  public FullHttpRequest retain() {
    return fullHttpRequest.retain();
  }

  @Override
  public FullHttpRequest touch() {
    return fullHttpRequest.touch();
  }

  @Override
  public FullHttpRequest touch(Object hint) {
    return fullHttpRequest.touch(hint);
  }

  @Override
  public FullHttpRequest setProtocolVersion(HttpVersion version) {
    return fullHttpRequest.setProtocolVersion(version);
  }

  @Override
  public FullHttpRequest setMethod(HttpMethod method) {
    return fullHttpRequest.setMethod(method);
  }

  @Override
  public FullHttpRequest setUri(String uri) {
    return fullHttpRequest.setUri(uri);
  }

  @Override
  @Deprecated
  public HttpMethod getMethod() {
    return fullHttpRequest.getMethod();
  }

  @Override
  public HttpMethod method() {
    return fullHttpRequest.method();
  }

  @Override
  @Deprecated
  public String getUri() {
    return fullHttpRequest.getUri();
  }

  @Override
  public String uri() {
    return fullHttpRequest.uri();
  }

  @Override
  @Deprecated
  public HttpVersion getProtocolVersion() {
    return fullHttpRequest.getProtocolVersion();
  }

  @Override
  public HttpVersion protocolVersion() {
    return fullHttpRequest.protocolVersion();
  }

  @Override
  public HttpHeaders headers() {
    return fullHttpRequest.headers();
  }

  @Override
  @Deprecated
  public DecoderResult getDecoderResult() {
    return fullHttpRequest.getDecoderResult();
  }

  @Override
  public DecoderResult decoderResult() {
    return fullHttpRequest.decoderResult();
  }

  @Override
  public void setDecoderResult(DecoderResult result) {
    fullHttpRequest.setDecoderResult(result);
  }

  @Override
  public HttpHeaders trailingHeaders() {
    return fullHttpRequest.trailingHeaders();
  }

  @Override
  public ByteBuf content() {
    return fullHttpRequest.content();
  }

  @Override
  public int refCnt() {
    return fullHttpRequest.refCnt();
  }

  @Override
  public boolean release() {
    return fullHttpRequest.release();
  }

  @Override
  public boolean release(int decrement) {
    return fullHttpRequest.release(decrement);
  }
}
