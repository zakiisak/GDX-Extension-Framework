package game.net;

public class EntityPacket {

    public long netId;
    public long roomId;

    public EntityPacket() {}

    public EntityPacket(long netId, long roomId)
    {
        this.netId = netId;
        this.roomId = roomId;
    }

}
