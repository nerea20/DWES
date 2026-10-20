package es.dwes.dentista;

public class Patient {
    private final int id;
    private final String name;
    private final String surname;
    private final String dni;
    private final String birthDate;
    private final String phone;
    private final String email;
    private final String address;
    private final String allergies;
    private final int appointmentCount;

    public Patient(int id, String name, String surname, String dni, String birthDate, String phone,
                   String email, String address, String allergies, int appointmentCount) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.dni = dni;
        this.birthDate = birthDate;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.allergies = allergies;
        this.appointmentCount = appointmentCount;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getDni() { return dni; }
    public String getBirthDate() { return birthDate; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getAllergies() { return allergies; }
    public int getAppointmentCount() { return appointmentCount; }
}