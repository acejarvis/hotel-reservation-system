package models;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum RoomType {
    SINGLE(1, "Single"),
    DOUBLE(2, "Double"),
    TRIPLE(3, "Triple"),
    QUAD(4, "Quad");

    private final int roomTypeId;
    private final String roomTypeName;
    
    private RoomType(int roomTypeId, String roomTypeName) {
        this.roomTypeId = roomTypeId;
        this.roomTypeName = roomTypeName;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public String getRoomTypeName() {
        return roomTypeName;
    }

    private static final Map<Integer, RoomType> roomTypeMap = Stream.of(RoomType.values())
    .collect(Collectors.toMap(RoomType::getRoomTypeId, roomType -> roomType));

    public static RoomType valueOf(int roomTypeId) {
        return roomTypeMap.get(roomTypeId);
    }
}
