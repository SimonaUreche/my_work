package model;

/**
 * Clasa care reprezintă o factură pentru o comandă.
 */
public class Bill {
    private int orderId;
    private int clientId;
    private int productId;
    private int quantity;
    private int totalPrice;
    private String email;

    /**
     * Constructorul clasei Bill.
     * @param idOrder ID-ul comenzii.
     * @param clientId ID-ul clientului.
     * @param productId ID-ul produsului.
     * @param quantity Cantitatea de produse.
     * @param totalPrice Prețul total al comenzii.
     * @param email Adresa de email asociată facturii.
     */
    public Bill(int idOrder, int clientId, int productId, int quantity, int totalPrice, String email){
        this.orderId = idOrder;
        this.clientId = clientId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.email = email;
    }

    /**
     * Metoda care returnează ID-ul comenzii asociate facturii.
     * @return ID-ul comenzii.
     */
    public int getId() {
        return orderId;
    }

    /**
     * Metoda care returnează cantitatea de produse asociată facturii.
     * @return Cantitatea de produse.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Metoda care returnează prețul total al comenzii asociate facturii.
     * @return Prețul total al comenzii.
     */
    public int getTotalPrice() {
        return totalPrice;
    }

    /**
     * Metoda care returnează ID-ul clientului asociat facturii.
     * @return ID-ul clientului.
     */
    public int getClientId() {
        return clientId;
    }

    /**
     * Metoda care returnează ID-ul produsului asociat facturii.
     * @return ID-ul produsului.
     */
    public int getProductId() {
        return productId;
    }

    /**
     * Metoda care setează cantitatea de produse asociată facturii.
     * @param quantity Cantitatea de produse.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Metoda care setează ID-ul comenzii asociate facturii.
     * @param id ID-ul comenzii.
     */
    public void setId(int id) {
        this.orderId = id;
    }

    /**
     * Metoda care setează ID-ul clientului asociat facturii.
     * @param clientId ID-ul clientului.
     */
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    /**
     * Metoda care setează ID-ul produsului asociat facturii.
     * @param productId ID-ul produsului.
     */
    public void setProductId(int productId) {
        this.productId = productId;
    }

    /**
     * Metoda care setează prețul total al comenzii asociate facturii.
     * @param totalPrice Prețul total al comenzii.
     */
    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Metoda care returnează adresa de email asociată facturii.
     * @return Adresa de email asociată facturii.
     */
    public String getEmail() {
        return email;
    }

}
