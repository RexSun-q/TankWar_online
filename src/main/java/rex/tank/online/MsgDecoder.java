package rex.tank.online;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import rex.tank.Dir;
import rex.tank.Group;

import java.util.List;
import java.util.UUID;

public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 8) return;
        in.markReaderIndex();

        MsgType msgType = MsgType.values()[in.readInt()];
        int msgLength = in.readInt();

        if (in.readableBytes() < msgLength) {
            in.resetReaderIndex();
            return;
        }

        byte[] bytes = new byte[msgLength];
        in.readBytes(bytes);

        Msg msg = null;
        switch (msgType) {
            case TankMsg:
                msg = new TankMsg();
                break;
            case TankMoveMsg:
                msg = new TankMoveMsg();
                break;
            case TankStopMsg:
                msg = new TankStopMsg();
                break;
            case NewBulletMsg:
                msg = new NewBulletMsg();
                break;
            default:
                break;
        }

        msg.parse(bytes);
        out.add(msg);
    }
}
