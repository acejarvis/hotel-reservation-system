package models;

public class RoomReserved {
    private int roomReservedId;
    private int roomId;
    private int reservationId;
    private float price;

    public RoomReserved(int roomReservedId, int roomId, int reservationId, float price) {
        this.roomReservedId = roomReservedId;
        this.roomId = roomId;
        this.reservationId = reservationId;
        this.price = price;
    }

    public int getRoomReservedId() {
        return roomReservedId;
    }

    public void setRoomReservedId(int roomReservedId) {
        this.roomReservedId = roomReservedId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
