package io.jawg.netty.example;

import io.jawg.netty.http.HttpRouterHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author Clement Gayvallet
 * @since 18/11/16
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

  private HttpRouterHandler httpRouterHandler = new HttpRouterHandler(HelloRoutes.ROUTER, TileRoutes.ROUTER, HeavyRoutes.ROUTER);

  @Override
  protected void initChannel(SocketChannel ch) throws Exception {
    ChannelPipeline p = ch.pipeline();

    p.addLast("codec", new HttpServerCodec())
        .addLast("aggregator", new HttpObjectAggregator(65536))
        .addLast("router", httpRouterHandler);
  }
}
