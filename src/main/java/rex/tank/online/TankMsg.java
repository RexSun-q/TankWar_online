package rex.tank.online;

import rex.tank.Dir;
import rex.tank.Group;
import rex.tank.Tank;
import rex.tank.TankFrame;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.UUID;

public class TankMsg {
    public int x;
    public int y;
    public Dir dir;
    public boolean moving;
    public Group group;
    public UUID uuid;

    public TankMsg() {
    }

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
        }
        return bytes;
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
