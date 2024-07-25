package bll.validators;

import model.Product;

/**
 * Validator pentru validarea cantității unui produs.
 */
public class ProductQuantityValidator implements Validator<Product> {

    /** Cantitatea minimă acceptată pentru un produs. */
    private static final int CANTITATE_MINIMA = 0;

    /**
     * Validează cantitatea unui produs.
     *
     * @param product Produsul de validat.
     * @throws IllegalArgumentException Dacă cantitatea produsului este mai mică decât limita minimă.
     */
    public void validate(Product product) {
        if (product.getQuantity() < CANTITATE_MINIMA) {
            throw new IllegalArgumentException("Limita cantității produsului nu este respectată!");
        }
    }
}
