package daos;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import utils.CSV;
import models.Room;
import models.RoomType;

public class RoomDao {

    String rootPath = System.getProperty("user.dir").replace("HotelReservationSystem", "");
    String filePath = rootPath + "\\database\\rooms.csv";

    public Room getRoom(int roomId) {

        CSV rooms = new CSV(filePath);
        Map<Integer, String[]> roomMap = rooms.search("id", String.valueOf(roomId));
        String[] roomValues = roomMap.get(roomId);
        return new Room(roomId, Integer.parseInt(roomValues[1]), roomValues[2],
                RoomType.valueOf(Integer.parseInt(roomValues[3])),
                Float.parseFloat(roomValues[4]));
    }

    public List<Room> getAllRooms() {
        CSV rooms = new CSV(filePath);
        Map<Integer, String[]> roomMap = rooms.getAll();
        List<Room> roomList = new ArrayList<>();

        for (String[] room : roomMap.values()) {
            Room roomObj = new Room(Integer.parseInt(room[0]), Integer.parseInt(room[1]), room[2],
                    RoomType.valueOf(Integer.parseInt(room[3])), Float.parseFloat(room[4]));
            System.out.println(roomObj);
            roomList.add(roomObj);
        }
        return roomList;
    }

    public List<Room> getRoomsByRoomNumberRange(int roomNumberStart, int roomNumberEnd) {
        CSV rooms = new CSV(filePath);
        Map<Integer, String[]> roomMap = rooms.search("room_number", roomNumberStart, roomNumberEnd);
        List<Room> roomList = new ArrayList<>();
        for (String[] room : roomMap.values()) {
            Room roomObj = new Room(Integer.parseInt(room[0]), Integer.parseInt(room[1]), room[2],
                    RoomType.valueOf(Integer.parseInt(room[3])), Float.parseFloat(room[4]));
            roomList.add(roomObj);
        }
        return roomList;
    }

    public List<Room> getRoomsByType(RoomType roomType) {
        CSV rooms = new CSV(filePath);
        Map<Integer, String[]> roomMap = rooms.search("room_type_id", roomType.getRoomTypeId());
        List<Room> roomList = new ArrayList<>();

        for (String[] room : roomMap.values()) {
            Room roomObj = new Room(Integer.parseInt(room[0]), Integer.parseInt(room[1]), room[2], roomType,
                    Float.parseFloat(room[4]));
            roomList.add(roomObj);
        }
        return roomList;
    }

    public Boolean addRoom(Room room) {
        CSV rooms = new CSV(filePath);
        String[] values = {
                String.valueOf(room.getRoomId()),
                String.valueOf(room.getRoomNumber()),
                room.getDescription(),
                String.valueOf(room.getRoomType().getRoomTypeId())
        };
        return rooms.insert(values);
    }

    public Boolean updateRoom(Room room) {
        CSV rooms = new CSV(filePath);
        String[] values = {
                String.valueOf(room.getRoomNumber()),
                room.getDescription(),
                String.valueOf(room.getRoomType().getRoomTypeId()),
                String.valueOf(room.getCurrentPrice())
        };
        return rooms.update(room.getRoomId(), values);
    }

    public Boolean deleteRoom(int roomId) {
        CSV rooms = new CSV(filePath);
        return rooms.delete(roomId);
    }

}
