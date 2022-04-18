package daos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import models.Reservation;
import utils.CSV;

public class ReservationDao {

    String rootPath = System.getProperty("user.dir").replace("HotelReservationSystem", "");
    String filePath = rootPath + "\\database\\reservations.csv";

    public Reservation getReservationById(int reservationId) {
        CSV reservations = new CSV(filePath);
        Map<Integer, String[]> reservationMap = reservations.search("id", String.valueOf(reservationId));
        String[] reservationValues = reservationMap.get(0);
        Date startDate = null;
        Date endDate = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            startDate = dateFormat.parse(reservationValues[1]);
            endDate = dateFormat.parse(reservationValues[2]);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new Reservation(reservationId, null, startDate, endDate, Float.parseFloat(reservationValues[3]),
                Integer.parseInt(reservationValues[4]));
    }

    public List<Reservation> getAllReservations() {
        CSV reservations = new CSV(filePath);
        List<Reservation> reservationList = new ArrayList<>();
        Map<Integer, String[]> reservationMap = reservations.getAll();
        for (Map.Entry<Integer, String[]> entry : reservationMap.entrySet()) {
            String[] reservationValues = entry.getValue();
            Date startDate = null;
            Date endDate = null;
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                startDate = dateFormat.parse(reservationValues[1]);
                endDate = dateFormat.parse(reservationValues[2]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            reservationList.add(
                    new Reservation(entry.getKey(), null, startDate, endDate, Float.parseFloat(reservationValues[3]),
                            Integer.parseInt(reservationValues[4])));
        }
        return reservationList;
    }

    public Boolean addReservation(Reservation reservation) {
        CSV reservations = new CSV(filePath);
        String[] values = {
                String.valueOf(reservation.getStartDate()),
                String.valueOf(reservation.getEndDate()),
                String.valueOf(reservation.getTotalPrice()),
                String.valueOf(reservation.getGuestId()),
                new Date().toString(),
                new Date().toString()
        };
        return reservations.insert(values);
    }

    public Boolean updateReservation(Reservation reservation) {
        CSV reservations = new CSV(filePath);
        String[] values = {
                String.valueOf(reservation.getStartDate()),
                String.valueOf(reservation.getEndDate()),
                String.valueOf(reservation.getTotalPrice()),
                String.valueOf(reservation.getGuestId()),
                new Date().toString(),
                new Date().toString()
        };
        return reservations.update(reservation.getReservationId(), values);
    }

    public Boolean deleteReservation(int reservationId) {
        CSV reservations = new CSV(filePath);
        return reservations.delete(reservationId);
    }

}
