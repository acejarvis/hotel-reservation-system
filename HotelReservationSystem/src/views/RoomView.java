package views;

import java.util.List;
import java.util.Map;

import models.Room;

public class RoomView {
    private RoomView() {
        throw new IllegalStateException("Room View class");
      }

    public static void printRoom(Room room) {
        System.out.println("Room ID: " + room.getRoomId());
        System.out.println("Room Number: " + room.getRoomNumber());
        System.out.println("Room Description: " + room.getDescription());
        System.out.println("Room Type: " + room.getRoomType());
        System.out.println("Room Price: " + room.getCurrentPrice());
    }


    public static void printRoomList(List<Room> roomList) {
        for (Room room : roomList) {
            printRoom(room);
        }
    }

    public static void printChangePriceResult(Map<Integer, String> result) {
        for (var room : result.entrySet()) {
            System.out.println("Room " + room.getKey() + ": " + room.getValue());
        }
    }
}
