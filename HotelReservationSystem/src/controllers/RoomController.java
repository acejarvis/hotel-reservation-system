package controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import daos.ReservationDao;
import daos.RoomDao;
import daos.RoomReservedDao;
import models.Reservation;
import models.Room;
import models.RoomReserved;
import models.RoomType;

public class RoomController {
    private RoomController() {
        throw new IllegalStateException("Room Controller class");
    }

    public static List<Room> getAvailableRoomsByDateAndType(Date date, RoomType roomType) {
        RoomDao roomDao = new RoomDao();
        RoomReservedDao roomReservedDao = new RoomReservedDao();
        ReservationDao reservationDao = new ReservationDao();
        List<Room> availableRooms = roomDao.getRoomsByType(roomType);

        // Remove rooms that are reserved
        ListIterator<Room> roomIterator = availableRooms.listIterator();
        while (roomIterator.hasNext()) {
            Room room = roomIterator.next();
            List<RoomReserved> roomReservedList = roomReservedDao.getRoomReservedsByRoomId(room.getRoomId());
            if (!roomReservedList.isEmpty()) {
                for (RoomReserved roomReserved : roomReservedList) {
                    Reservation reservation = reservationDao.getReservationById(roomReserved.getReservationId());
                    if (!reservation.getStartDate().after(date) && !reservation.getEndDate().before(date)) {
                        roomIterator.remove();
                    }
                }
            }
        }

        return availableRooms;
    }

    public static Map<Integer, String> updatePriceByRoomRange(int roomNumberFrom, int roomNumberTo, float price) {
        RoomDao roomDao = new RoomDao();
        List<Room> roomList = roomDao.getRoomsByRoomNumberRange(roomNumberFrom, roomNumberTo);
        Map<Integer, String> result = new HashMap<>();

        for (Room room : roomList) {
            // Build change result string
            String priceChange = Float.valueOf(room.getCurrentPrice()) + " -> ";
            room.setCurrentPrice(price);
            roomDao.updateRoom(room);
            priceChange += Float.valueOf(room.getCurrentPrice());
            result.put(room.getRoomNumber(), priceChange);
        }
        return result;
    }

    public static Map<Integer, String> updatePriceByRoomRange(int roomNumberFrom, int roomNumberTo, int discountPercentage) {
        RoomDao roomDao = new RoomDao();
        List<Room> roomList = roomDao.getRoomsByRoomNumberRange(roomNumberFrom, roomNumberTo);
        Map<Integer, String> result = new HashMap<>();

        for (Room room : roomList) {
            // Build change result string
            String priceChange = Float.valueOf(room.getCurrentPrice()) + " -> ";
            room.setCurrentPrice(room.getCurrentPrice() * (float)discountPercentage / 100);
            roomDao.updateRoom(room);
            priceChange += Float.valueOf(room.getCurrentPrice());
            result.put(room.getRoomNumber(), priceChange);
        }
        return result;
    }
}
