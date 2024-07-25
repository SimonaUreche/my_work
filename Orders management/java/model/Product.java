package model;

/**
 * Clasa care reprezintă un produs.
 */
public class Product {
    private int id;
    private int quantity;
    private int price;
    private String name;

    /**
     * Constructorul fără parametri al clasei Product.
     */
    public Product(){
    }

    /**
     * Constructorul clasei Product.
     * @param id ID-ul produsului.
     * @param quantity Cantitatea disponibilă a produsului.
     * @param price Prețul produsului.
     * @param name Numele produsului.
     */
    public Product(int id, int quantity, int price, String name){
        this.id = id;
        this.quantity = quantity;
        this.price = price;
        this.name = name;
    }

    /**
     * Constructorul clasei Product.
     * @param quantity Cantitatea disponibilă a produsului.
     * @param price Prețul produsului.
     * @param name Numele produsului.
     */
    public Product(int quantity, int price,String name){
        this.quantity = quantity;
        this.price = price;
        this.name = name;
    }

    /**
     * Metoda care returnează cantitatea disponibilă a produsului.
     * @return Cantitatea disponibilă a produsului.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Metoda care returnează ID-ul produsului.
     * @return ID-ul produsului.
     */
    public int getId() {
        return id;
    }

    /**
     * Metoda care setează cantitatea disponibilă a produsului.
     * @param quantity Cantitatea disponibilă a produsului.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Metoda care setează ID-ul produsului.
     * @param id ID-ul produsului.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Metoda care setează numele produsului.
     * @param name Numele produsului.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metoda care setează prețul produsului.
     * @param price Prețul produsului.
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * Metoda care returnează numele produsului.
     * @return Numele produsului.
     */
    public String getName() {
        return name;
    }

    /**
     * Metoda care returnează prețul produsului.
     * @return Prețul produsului.
     */
    public int getPrice() {
        return price;
    }
}
