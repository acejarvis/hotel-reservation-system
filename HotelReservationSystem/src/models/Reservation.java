package models;

import java.util.Date;
import java.util.List;

public class Reservation {
    private int reservationId;
    private List<Integer> roomIds;
    private Date startDate;
    private Date endDate;
    private Float totalPrice;
    private int guestId;

    public Reservation(int reservationId, List<Integer> roomIds, Date startDate, Date endDate, float totalPrice, int guestId) {
        this.reservationId = reservationId;
        this.roomIds = roomIds;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.guestId = guestId;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public List<Integer> getRoomIds() {
        return roomIds;
    }

    public void setRoomIds(List<Integer> roomIds) {
        this.roomIds = roomIds;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(int guestId) {
        this.guestId = guestId;
    }
}
