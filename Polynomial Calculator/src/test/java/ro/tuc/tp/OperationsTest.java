package ro.tuc.tp;

import org.example.Logic.Divade;
import org.example.Logic.Operations;
import org.example.Model.Monomial;
import org.example.Model.Polynomial;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OperationsTest {


    private Polynomial polynomial1 = new Polynomial("4x^5-3x^4+x^2-8x+1");
    private Polynomial polynomial2 = new Polynomial("3x^4-x^3+x^2+2x-1");
    private Polynomial polynomial7 = new Polynomial("x^3-2x^2+6x-5");
    private Polynomial polynomial8 = new Polynomial("x^2-1");
    String polynomialString = "-4x^3+2x^2+6x-2";
    Operations operations = new Operations();
    Divade result = null;

    @Test
    public void addTestC() {
        Monomial monom1 = new Monomial(5, 4);
        Monomial monom2 = new Monomial(4, -3);
        Monomial monom3 = new Monomial(2, 1);
        Monomial monom4 = new Monomial(1, -8);
        Monomial monom5 = new Monomial(0, 1);

        Map<Integer, Monomial> polinom = new HashMap<>();
        polinom.put(monom1.getDegree(), monom1);
        polinom.put(monom2.getDegree(), monom2);
        polinom.put(monom3.getDegree(), monom3);
        polinom.put(monom4.getDegree(), monom4);
        polinom.put(monom5.getDegree(), monom5);
        Polynomial polinomulCopy = new Polynomial(new HashMap<>(polinom));

        Monomial monom6 = new Monomial(4, 3);
        Monomial monom7 = new Monomial(3, -1);
        Monomial monom8 = new Monomial(2, 1);
        Monomial monom9 = new Monomial(1, 2);
        Monomial monom10 = new Monomial(0, -1);

        Map<Integer, Monomial> polinom1 = new HashMap<>();
        polinom1.put(monom6.getDegree(), monom6);
        polinom1.put(monom7.getDegree(), monom7);
        polinom1.put(monom8.getDegree(), monom8);
        polinom1.put(monom9.getDegree(), monom9);
        polinom1.put(monom10.getDegree(), monom10);
        Polynomial polinomul2Copy = new Polynomial(new HashMap<>(polinom1));

        assertEquals("4.0x^5 - 1.0x^3 + 2.0x^2 - 6.0x", operations.add(polinomulCopy, polinomul2Copy).toString());
    }

    @Test
    public void addTestI() {
        assertEquals("-2.0x^3 + 7.0x^2 + 3.0x + 5.0", operations.add(polynomial1, polynomial2).toString());
    }

    @Test
    public void subTestC() {
        assertEquals("4.0x^5 - 6.0x^4 + 1.0x^3 - 10.0x + 2.0", operations.subtract(polynomial1, polynomial2).toString());
    }

    @Test
    public void subTestI() {
        assertEquals("6.0x^3 + 3.0x^2 - 9.0x + 9.0", operations.subtract(polynomial1, polynomial2).toString());
    }

    @Test
    public void multiplyTestC() {
        assertEquals("12.0x^9 - 13.0x^8 + 7.0x^7 + 8.0x^6 - 35.0x^5 + 15.0x^4 - 7.0x^3 - 16.0x^2 + 10.0x - 1.0", operations.multiply(polynomial1, polynomial2).toString());
    }

    @Test
    public void multiplyTestI() {
        assertEquals("3.0x^3 - 7.0x^2 + 3.0x - 2.0", operations.multiply(polynomial1, polynomial2).toString());
    }

    @Test
    public void divideTestC() {
        Divade result = operations.divide(polynomial7, polynomial8);
        String expected = "Q: " + result.getQuotient().toString() + "    R: " + result.getRemainder().toString();
        assertEquals("Q: 1.0x - 2.0    R: 7.0x - 7.0", expected);
    }

    @Test
    public void divideTestI() {
        Divade result = operations.divide(polynomial7, polynomial8);
        String expected = "Q: " + result.getQuotient().toString() + "    R: " + result.getRemainder().toString();
        assertEquals("Q: 1.0x^2 + 1.0x + 2.0    R: 3.0", expected);
    }

    @Test
    public void derivateTestC() {
        assertEquals("20.0x^4 - 12.0x^3 + 2.0x - 8.0", operations.derivate(polynomial1).toString());
    }

    @Test
    public void derivateTestI() {
        assertEquals("6.0x - 1.0", operations.derivate(polynomial1).toString());
    }

    @Test
    public void integrateTestC() {
        assertEquals("0.7x^6 - 0.6x^5 + 0.3x^3 - 4.0x^2 + 1.0x", operations.integrate(polynomial1).toString());
    }

    @Test
    public void integrateTestI() {
        assertEquals("1.0x^3 - 0.5x^2 + 1.0x", operations.integrate(polynomial1).toString());
    }

    @Test
    public void polynomialStringTestC() {
        Polynomial expected = new Polynomial("-4x^3 + 2x^2 + 6x - 2");
        assertEquals(expected.toString(), new Polynomial(polynomialString).toString());
    }

    @Test
    public void polynomialStringTestI() {
        Polynomial expected = new Polynomial("2x^3 + 3x^2 - 5x + 7");
        assertEquals(expected.toString(), new Polynomial(polynomialString).toString());
    }
}

