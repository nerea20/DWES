package es.dwes.dentista;

public class Appointment {
    private final int id;
    private final String date;
    private final String time;
    private final String patientName;
    private final String reason;
    private final String dentistName;

    public Appointment(int id, String date, String time, String patientName, String reason, String dentistName) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.patientName = patientName;
        this.reason = reason;
        this.dentistName = dentistName;
    }

    public int getId() { return id; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public String getPatientName() { return patientName; }
    public String getReason() { return reason; }
    public String getDentistName() { return dentistName; }
}