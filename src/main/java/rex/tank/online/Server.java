package rex.tank.online;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;

public class Server {
    protected static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public void connect() {
        EventLoopGroup bossgroup = new NioEventLoopGroup(1);
        EventLoopGroup workergroup = new NioEventLoopGroup(4);
        ServerBootstrap sbs = new ServerBootstrap();
        try {
            ChannelFuture future = sbs.group(bossgroup, workergroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ch.pipeline().addLast(new ServerChannelHandler());
                        }
                    })
                    .bind(8888)
                    .sync();
            System.out.println("Server started ...");

            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workergroup.shutdownGracefully();
            bossgroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        Server server = new Server();
        server.connect();
    }
}

class ServerChannelHandler extends SimpleChannelInboundHandler<TankMsg> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().id()+"connected");
        Server.channels.add(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TankMsg msg) throws Exception {
        System.out.println(msg);
        Server.channels.writeAndFlush(msg);
    }
}