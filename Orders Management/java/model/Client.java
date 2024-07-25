package model;

/**
 * Clasa care reprezintă un client.
 */
public class Client {
    private int id;
    private String name;
    private String address;
    private String email;
    private int age;

    /**
     * Constructorul fără parametri al clasei Client.
     */
    public Client() {
    }

    /**
     * Constructorul clasei Client.
     * @param id ID-ul clientului.
     * @param name Numele clientului.
     * @param address Adresa clientului.
     * @param email Adresa de email a clientului.
     * @param age Vârsta clientului.
     */
    public Client(int id, String name, String address, String email, int age) {
        super();
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }

    /**
     * Constructorul clasei Client.
     * @param name Numele clientului.
     * @param address Adresa clientului.
     * @param email Adresa de email a clientului.
     * @param age Vârsta clientului.
     */
    public Client(String name, String address, String email, int age) {
        super();
        this.name = name;
        this.address = address;
        this.email = email;
        this.age = age;
    }

    /**
     * Metoda care returnează ID-ul clientului.
     * @return ID-ul clientului.
     */
    public int getId() {
        return id;
    }

    /**
     * Metoda care setează ID-ul clientului.
     * @param id ID-ul clientului.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metoda care returnează vârsta clientului.
     * @return Vârsta clientului.
     */
    public int getAge() {
        return age;
    }

    /**
     * Metoda care returnează adresa de email a clientului.
     * @return Adresa de email a clientului.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Metoda care setează numele clientului.
     * @param name Numele clientului.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metoda care setează adresa de email a clientului.
     * @param email Adresa de email a clientului.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Metoda care setează adresa clientului.
     * @param address Adresa clientului.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Metoda care setează vârsta clientului.
     * @param age Vârsta clientului.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Metoda care returnează numele clientului.
     * @return Numele clientului.
     */
    public String getName() {
        return name;
    }

    /**
     * Metoda care returnează adresa clientului.
     * @return Adresa clientului.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Override pentru metoda toString pentru a afișa informații despre client.
     * @return Informații despre client sub formă de șir de caractere.
     */
    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email + ", age=" + age
                + "]";
    }

}
