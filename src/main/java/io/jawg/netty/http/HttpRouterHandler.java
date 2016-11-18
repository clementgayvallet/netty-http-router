package io.jawg.netty.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

import java.util.List;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * @author Clement Gayvallet
 * @since 17/11/16
 */
@ChannelHandler.Sharable
public class HttpRouterHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

  private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(HttpRouter.class);

  private final HttpRouter[] routers;

  public HttpRouterHandler(HttpRouter... routers) {
    this.routers = routers;
  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest httpRequest) throws Exception {
    QueryStringDecoder queryStringDecoder = new QueryStringDecoder(httpRequest.uri());
    String path = queryStringDecoder.path();

    for (HttpRouter router : routers) {
      List<HttpRoute> routes = router.routes(httpRequest.method());
      for (HttpRoute route : routes) {
        PathVariables pathVariables = route.match(path);
        if (pathVariables != null) {
          route.handler().accept(ctx, new Request(httpRequest, pathVariables, new QueryParameters(queryStringDecoder.parameters())));
          return;
        }
      }
    }

    // No routes match: send 400 Bad Request and close the connection
    sendError(ctx, BAD_REQUEST);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    HttpResponseStatus status;
    if (cause instanceof HttpParameterParseException) {
      status = BAD_REQUEST;
      LOGGER.error(cause.getMessage());
    } else {
      status = INTERNAL_SERVER_ERROR;
      LOGGER.error(cause.getMessage(), cause);
    }

    if (ctx.channel().isActive()) {
      sendError(ctx, status);
    }
    ctx.close();
  }

  /**
   * Respond to client with an error.
   *
   * @param ctx    the context
   * @param status the status
   */
  private static void sendError(ChannelHandlerContext ctx, HttpResponseStatus status) {
    FullHttpResponse response = new DefaultFullHttpResponse(
        HTTP_1_1, status, Unpooled.copiedBuffer("Error: " + status + "\r\n", CharsetUtil.UTF_8));
    response.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

    // Close the connection as soon as the error message is sent.
    ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
  }
}
