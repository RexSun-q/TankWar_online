package rex.tank.online;

import io.netty.channel.ChannelHandlerContext;
import rex.tank.*;

import java.io.*;
import java.util.UUID;

public class TankMsg extends Msg {
    public int x;
    public int y;
    public Dir dir;
    public boolean moving;
    public Group group;
    public UUID uuid;
    private MsgType type = MsgType.TankMsg;

    public TankMsg() { }

    public TankMsg(int x, int y, Dir dir, boolean moving, Group group, UUID uuid) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = moving;
        this.group = group;
        this.uuid = uuid;
    }

    public TankMsg(Tank tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
        this.moving = tank.getMoving();
        this.group = tank.getGroup();
        this.uuid = tank.getUuid();
    }

    @Override
    public void handle() {
        if (this.uuid.equals(TankFrame.getINSTANCE().myTank.getUuid()) ||
                TankFrame.getINSTANCE().findByUUID(uuid) != null) return;

        TankFrame.getINSTANCE().enemyTanks.put(uuid, new Tank(this, TankFrame.getINSTANCE()));
        TankMsg myTankMsg = new TankMsg(TankFrame.getINSTANCE().myTank);
        Client.getInstance().channel.writeAndFlush(myTankMsg);
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream bos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            bos = new ByteArrayOutputStream();
            dos = new DataOutputStream(bos);
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeBoolean(moving);
            dos.writeInt(group.ordinal());
            dos.writeLong(uuid.getMostSignificantBits());
            dos.writeLong(uuid.getLeastSignificantBits());
            dos.flush();
            bytes = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            x = dis.readInt();
            y = dis.readInt();
            dir = Dir.values()[dis.readInt()];
            moving = dis.readBoolean();
            group = Group.values()[dis.readInt()];
            uuid = new UUID(dis.readLong(), dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public MsgType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "TankMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", moving=" + moving +
                ", group=" + group +
                ", uuid=" + uuid +
                '}';
    }


}
