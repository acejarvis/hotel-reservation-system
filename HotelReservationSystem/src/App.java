import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controllers.RoomController;
import models.Room;
import models.RoomType;
import views.RoomView;

public class App {
    public static void main(String[] args) {
        System.out.println("Entering server...");
        server();
        System.out.println("Exiting server...");
    }

    public static void server() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println("Input command:");
                String command = scanner.nextLine();
                if (command.equals("exit")) {
                    return;
                } else if (command.equals("check roomtype available")) {
                    checkRoomType(scanner);
                } else if (command.equals("change price")) {
                    changePrice(scanner, false);
                } else if (command.equals("apply discount")) {
                    changePrice(scanner, true);
                } else {
                    System.out.println("Invalid command");
                }
            }
        }
    }

    private static Integer[] getRoomNumberRange(String roomNumberRange) {
        // Check if room number range is valid and parse room number range to integer array
        String[] roomNumberRangeArray = roomNumberRange.split("-");
        if (roomNumberRangeArray.length != 2) {
            System.out.println("Invalid room number range");
            return new Integer[0];
        }

        Integer[] roomRangeIntArray = new Integer[2];
        roomRangeIntArray[0] = Integer.valueOf(roomNumberRangeArray[0]);
        roomRangeIntArray[1] = Integer.valueOf(roomNumberRangeArray[1]);

        if (roomRangeIntArray[0] > roomRangeIntArray[1]) {
            System.out.println("Invalid room number range");
            return new Integer[0];
        }
        return roomRangeIntArray;
    }

    private static void changePrice(Scanner scanner, Boolean isDiscount) {
        while (true) {
            System.out.println("Input room number range, separate by '-':");
            String roomNumberRange = scanner.nextLine();
            // Check price changing method
            String notice = isDiscount ? "Input new discount percentage (without %):" : "Input new price:";
            System.out.println(notice);
            String changeValue = scanner.nextLine();

            if (roomNumberRange == null || roomNumberRange.isEmpty() || changeValue == null || changeValue.isEmpty()) {
                continue;
            }
            try {
                // Parse room number range 
                Integer[] roomRange = getRoomNumberRange(roomNumberRange);
                if (roomRange.length == 0)
                    return;

                Map<Integer, String> result = null;
                if (isDiscount) {
                    int discountPercentage = Integer.parseInt(changeValue);
                    result = RoomController.updatePriceByRoomRange(roomRange[0], roomRange[1], discountPercentage);
                } else {
                    float newPrice = Float.parseFloat(changeValue);
                    result = RoomController.updatePriceByRoomRange(roomRange[0], roomRange[1], newPrice);
                }

                if (result.isEmpty())
                    System.out.println("No room found");
                else {
                    System.out.println("Room price updated successfully");
                    RoomView.printChangePriceResult(result);
                }
            } catch (Exception e) {
                System.out.println("Invalid room number range or new price");
                e.printStackTrace();
            }
            return;
        }
    }

    public static void checkRoomType(Scanner scanner) {
        while (true) {
            Date date = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            RoomType roomType = null;
            System.out.println("Input Date (YYYY-MM-DD):");
            String dateString = scanner.nextLine();
            System.out.println("Input Room Type (SINGLE, DOUBLE, TRIPLE, QUAD):");
            String roomTypeString = scanner.nextLine();

            if (dateString == null || dateString.isEmpty() || roomTypeString == null || roomTypeString.isEmpty()) {
                continue;
            }

            try {
                date = dateFormat.parse(dateString);
                roomType = RoomType.valueOf(roomTypeString.toUpperCase());

                List<Room> availableRooms = RoomController.getAvailableRoomsByDateAndType(date,
                        roomType);

                if (availableRooms.isEmpty()) {
                    System.out.println("No available rooms!");
                } else {
                    System.out.println("Available rooms:");
                    for (Room room : availableRooms) {
                        System.out.println(room.getRoomNumber());
                    }
                }
                return;

            } catch (Exception e) {
                System.out.println("Invalid date or room type!");
                e.printStackTrace();
                return;
            }
        }
    }
}
