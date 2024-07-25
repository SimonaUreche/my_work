package bll;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import bll.validators.ProductQuantityValidator;
import bll.validators.Validator;
import dao.ProductDAO;
import model.Product;

/**
 * Această clasă gestionează logica legată de produse.
 */
public class ProductBLL {

    private List<Validator<Product>> validators;
    private ProductDAO productDAO;

    /**
     * Constructorul clasei ProductBLL.
     * Inițializează lista de validatori și obiectul ProductDAO.
     */
    public ProductBLL() {
        validators = new ArrayList<>();
        validators.add(new ProductQuantityValidator());
        productDAO = new ProductDAO();
    }

    /**
     * Găsește un produs după id.
     * @param id Id-ul produsului căutat.
     * @return Produsul cu id-ul specificat.
     * @throws NoSuchElementException Dacă produsul nu există în baza de date.
     */
    public Product findProductById(int id) {
        Product st = productDAO.findById(id);
        if (st == null) {
            throw new NoSuchElementException("The product with id =" + id + " was not found!");
        }
        return st;
    }

    /**
     * Returnează o listă cu toate produsele.
     * @return Lista de produse.
     */
    public List<Product> findAllProducts() {
        return productDAO.findAll();
    }

    /**
     * Inserează un produs în baza de date.
     * @param product Produsul de inserat.
     */
    public void insertProduct(Product product) {
        for (Validator<Product> validator : validators) {
            validator.validate(product);
        }
        productDAO.insert(product);
    }

    /**
     * Actualizează un produs în baza de date.
     * @param product Produsul de actualizat.
     */
    public void updateProduct(Product product) {
        for (Validator<Product> validator : validators) {
            validator.validate(product);
        }
        productDAO.update(product);
    }

    /**
     * Șterge un produs din baza de date după id.
     * @param id Id-ul produsului de șters.
     * @throws NoSuchElementException Dacă produsul nu există în baza de date.
     */
    public void deleteProduct(int id) {
        try {
            productDAO.delete("id", id);
        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Product with id '" + id + "' not found.");
        }
    }

    /**
     * Actualizează cantitatea unui produs în baza de date.
     * @param productId Id-ul produsului căruia i se actualizează cantitatea.
     * @param newQuantity Noua cantitate a produsului.
     */
    public void updateProductQuantity(int productId, int newQuantity) {
        Product product = findProductById(productId);
        product.setQuantity(newQuantity);

        for (Validator<Product> validator : validators) {
            validator.validate(product);
        }
        productDAO.update(product);
    }

    /**
     * Returnează obiectul ProductDAO.
     * @return Obiectul ProductDAO.
     */
    public ProductDAO getProductDAO() {
        return productDAO;
    }

    /**
     * Setează obiectul ProductDAO.
     * @param productDAO Obiectul ProductDAO.
     */
    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
}
