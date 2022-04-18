package models;

public class Room {
    private int roomId;
    private int roomNumber;
    private String description;
    private RoomType roomType;
    private float currentPrice;

    public Room(int roomId, int roomNumber, String description, RoomType roomType, float currentPrice) {
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.description = description;
        this.roomId = roomId;
        this.currentPrice = currentPrice;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(float currentPrice) {
        this.currentPrice = currentPrice;
    }
}
