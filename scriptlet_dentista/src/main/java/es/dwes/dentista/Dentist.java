package es.dwes.dentista;

public class Dentist {
    private final int id;
    private final String name;
    private final String specialty;

    public Dentist(int id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getSpecialty() { return specialty; }
}