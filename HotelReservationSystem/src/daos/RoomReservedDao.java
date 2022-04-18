package daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import models.RoomReserved;
import utils.CSV;

public class RoomReservedDao {
    
    String rootPath = System.getProperty("user.dir").replace("HotelReservationSystem", "");
    String filePath = rootPath + "\\database\\room_reserveds.csv";

    public RoomReserved getRoomReservedById(int roomReservedId) {
        CSV roomReserveds = new CSV(filePath);
        Map<Integer, String[]> roomReservedMap = roomReserveds.search("id", String.valueOf(roomReservedId));
        String[] reservationValues = roomReservedMap.get(roomReservedId);

        return new RoomReserved(roomReservedId, Integer.parseInt(reservationValues[1]), Integer.parseInt(reservationValues[2]), Float.parseFloat(reservationValues[3]));
    }

    public List<RoomReserved> getRoomReservedsByReservationId(int reservationId) {
        CSV roomReserveds = new CSV(filePath);
        Map<Integer, String[]> roomReservedMap = roomReserveds.search("reservation_id", String.valueOf(reservationId));
        List<RoomReserved> roomReservedList = new ArrayList<>();
        for(String[] roomReserved : roomReservedMap.values()) {
            RoomReserved reservationObj = new RoomReserved(Integer.parseInt(roomReserved[0]), Integer.parseInt(roomReserved[1]), Integer.parseInt(roomReserved[2]), Float.parseFloat(roomReserved[3]));
            System.out.println(reservationObj);
            roomReservedList.add(reservationObj);
        }
        return roomReservedList;
    }

    public List<RoomReserved> getRoomReservedsByRoomId(int roomId) {
        CSV roomReserveds = new CSV(filePath);
        Map<Integer, String[]> roomReservedMap = roomReserveds.search("room_id", String.valueOf(roomId));
        List<RoomReserved> roomReservedList = new ArrayList<>();
        for(String[] roomReserved : roomReservedMap.values()) {
            RoomReserved reservationObj = new RoomReserved(Integer.parseInt(roomReserved[0]), Integer.parseInt(roomReserved[1]), Integer.parseInt(roomReserved[2]), Float.parseFloat(roomReserved[3]));
            roomReservedList.add(reservationObj);
        }
        return roomReservedList;
    }

    public Boolean addRoomReserved(RoomReserved roomReserved) {
        CSV roomReserveds = new CSV(filePath);
        String[] values = {
                String.valueOf(roomReserved.getRoomId()),
                String.valueOf(roomReserved.getReservationId()),
                String.valueOf(roomReserved.getPrice())
        };
        return roomReserveds.insert(values);
    }

    public Boolean updateRoomReserved(RoomReserved roomReserved) {
        CSV roomReserveds = new CSV(filePath);
        String[] values = {
                String.valueOf(roomReserved.getReservationId()),
                String.valueOf(roomReserved.getPrice())
        };
        return roomReserveds.update(roomReserved.getRoomReservedId(), values);
    }

    public Boolean deleteRoomReserved(int roomReservedId) {
        CSV roomReserveds = new CSV(filePath);
        return roomReserveds.delete(roomReservedId);
    }
    
}
