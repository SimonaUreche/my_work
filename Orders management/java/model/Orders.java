package model;

/**
 * Clasa care reprezintă o comandă.
 */
public class Orders {

    private int id;
    private int clientID;
    private int productID;
    private int quantity;
    private int total_price;

    /**
     * Constructorul fără parametri al clasei Orders.
     */
    public Orders(){

    }

    /**
     * Constructorul clasei Orders.
     * @param id ID-ul comenzii.
     * @param clientID ID-ul clientului care a plasat comanda.
     * @param productID ID-ul produsului comandat.
     * @param quantity Cantitatea comandată.
     * @param total_price Prețul total al comenzii.
     */
    public Orders(int id, int clientID, int productID, int quantity, int total_price)
    {
        super();
        this.id = id;
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
        this.total_price = total_price;
    }

    /**
     * Constructorul clasei Orders.
     * @param clientID ID-ul clientului care a plasat comanda.
     * @param productID ID-ul produsului comandat.
     * @param quantity Cantitatea comandată.
     * @param total_price Prețul total al comenzii.
     */
    public Orders(int clientID, int productID, int quantity, int total_price)
    {
        super();
        this.clientID = clientID;
        this.productID = productID;
        this.quantity = quantity;
        this.total_price = total_price;
    }


    /**
     * Metoda care returnează ID-ul comenzii.
     * @return ID-ul comenzii.
     */
    public int getId() {
        return id;
    }

    /**
     * Metoda care setează ID-ul comenzii.
     * @param orderID ID-ul comenzii.
     */
    public void setId(int orderID) {
        this.id = orderID;
    }

    /**
     * Metoda care returnează cantitatea comandată.
     * @return Cantitatea comandată.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Metoda care returnează prețul total al comenzii.
     * @return Prețul total al comenzii.
     */
    public int getTotal_price() {
        return total_price;
    }

    /**
     * Metoda care returnează ID-ul produsului comandat.
     * @return ID-ul produsului comandat.
     */
    public int getProductID() {
        return productID;
    }

    /**
     * Metoda care returnează ID-ul clientului care a plasat comanda.
     * @return ID-ul clientului care a plasat comanda.
     */
    public int getClientID() {
        return clientID;
    }

    /**
     * Metoda care setează prețul total al comenzii.
     * @param total_price Prețul total al comenzii.
     */
    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    /**
     * Metoda care setează ID-ul produsului comandat.
     * @param productID ID-ul produsului comandat.
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     * Metoda care setează ID-ul clientului care a plasat comanda.
     * @param clientID ID-ul clientului care a plasat comanda.
     */
    public void setClientID(int clientID) {
        this.clientID = clientID;
    }
    /**
     * Metoda care setează cantitatea produsului din comanda.
     * @param quantity cantitatea produsului din comanda.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
