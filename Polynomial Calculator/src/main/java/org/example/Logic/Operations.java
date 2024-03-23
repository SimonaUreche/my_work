package org.example.Logic;

import org.example.Model.Polynomial;
import org.example.Model.Monomial;

import java.util.*;

public class Operations {
    public Polynomial add(Polynomial polynomial1, Polynomial polynomial2) {
        Map<Integer, Monomial> result = new HashMap<>();

        for (Map.Entry<Integer, Monomial> entry : polynomial1.getMyPolynomial().entrySet()) {
            int degree = entry.getKey();
            Monomial monomPart1 = entry.getValue();
            result.put(degree, monomPart1);
        }

        for (Map.Entry<Integer, Monomial> entry : polynomial2.getMyPolynomial().entrySet()) {
            int degree = entry.getKey();
            Monomial monomPart2 = entry.getValue();

            Monomial existingDegree = result.get(degree);
            if (existingDegree != null) {
                Monomial resPart = new Monomial(degree, existingDegree.getCoefficient() + monomPart2.getCoefficient());
                result.put(degree, resPart);
            } else {
                result.put(degree, monomPart2);
            }
        }
        return new Polynomial(result);
    }
    public Polynomial negate(Polynomial polynomial) {
        Map<Integer, Monomial> result = new HashMap<>();

        for (Map.Entry<Integer, Monomial> entry : polynomial.getMyPolynomial().entrySet()) {
            int degree = entry.getKey();
            double coefficient = (-1) * entry.getValue().getCoefficient();
            result.put(degree, new Monomial(degree, coefficient));
        }
        return new Polynomial(result);
    }

    public Polynomial subtract(Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial result = negate(polynomial2);
        return add(polynomial1, result);
    }
    public Polynomial multiply(Polynomial polynomial1, Polynomial polynomial2) {
        Map<Integer, Monomial> result = new HashMap<>();

        for (Map.Entry<Integer, Monomial> entry1 : polynomial1.getMyPolynomial().entrySet()) {
            Monomial m1 = entry1.getValue();
            int degree1 = entry1.getKey();

            for (Map.Entry<Integer, Monomial> entry2 : polynomial2.getMyPolynomial().entrySet()) {
                Monomial m2 = entry2.getValue();
                int degree2 = entry2.getKey();

                int newDegree = degree1 + degree2;
                double newCoefficient = m1.getCoefficient() * m2.getCoefficient();
                //verify if 'newDegree' existing; if existing add new degree coefficient with old degree coeffieciet; if no create new monomial with new degree
                Monomial newMonomial = result.get(newDegree);
                if (newMonomial != null) {
                    double sumCoefficient = newMonomial.getCoefficient() + newCoefficient;
                    newMonomial.setCoefficient(sumCoefficient);
                } else {
                    newMonomial = new Monomial(newDegree, newCoefficient);
                }
                result.put(newDegree, newMonomial);
            }
        }
        return new Polynomial(result);
    }
    public Divade divide(Polynomial polynomial1, Polynomial polynomial2) {
        Polynomial quotient = new Polynomial();
        //initialize the remainder with the first polynomial so that the division algorithm starts with the integer polynomial from which the division is desired
        Polynomial remainder = new Polynomial(polynomial1.getMyPolynomial());

        List<Integer> degrees = new ArrayList<>(remainder.getMyPolynomial().keySet());
        degrees.sort(Comparator.reverseOrder());
        List<Integer> degrees2 = new ArrayList<>(polynomial2.getMyPolynomial().keySet());
        degrees2.sort(Comparator.reverseOrder());

        while (!remainder.getMyPolynomial().isEmpty() && !polynomial2.getMyPolynomial().isEmpty() && degrees.get(0) >= degrees2.get(0)) {
            //the largest monomials in the remainder and polynomial2
            Monomial highestMonomial1 = remainder.getMyPolynomial().get(degrees.get(0));
            Monomial highestMonomial2 = polynomial2.getMyPolynomial().get(degrees2.get(0));

            //the coefficient and degree of a monomial that will be added to the quotient polinomyal and add new monomial at quotient
            double coefficient = highestMonomial1.getCoefficient() / highestMonomial2.getCoefficient();
            int degree = highestMonomial1.getDegree() - highestMonomial2.getDegree();
            Polynomial term = new Polynomial();
            term.getMyPolynomial().put(degree, new Monomial(degree, coefficient));
            quotient = add(quotient, term);

            //the product of 'polynomial2' and new monom added at quotient
            Polynomial product = multiply(polynomial2, term);

            //subtract the result of the multiplication from P obtaining the remainder of the division
            remainder = subtract(remainder, product);

            //correctly updating the maximum degree of the remainder and thus avoids entering infinite cycles
            Map<Integer, Monomial> eliminate = new HashMap<>();
            for (Map.Entry<Integer, Monomial> entry : remainder.getMyPolynomial().entrySet()) {
                if (Math.abs(entry.getValue().getCoefficient()) > 1e-10) {
                    eliminate.put(entry.getKey(), entry.getValue());
                }
            }
            remainder = new Polynomial(eliminate);

            //updating the lists of maximum degrees
            degrees = new ArrayList<>(remainder.getMyPolynomial().keySet());
            degrees.sort(Comparator.reverseOrder());
            degrees2 = new ArrayList<>(polynomial2.getMyPolynomial().keySet());
            degrees2.sort(Comparator.reverseOrder());
        }
        return new Divade(quotient, remainder);
    }
    public Polynomial derivate(Polynomial polynomial) {
        Map<Integer, Monomial> result = new HashMap<>();

        for (Map.Entry<Integer, Monomial> entry : polynomial.getMyPolynomial().entrySet()) {
            int degree = entry.getKey();

            double coefficient = entry.getValue().getCoefficient() * degree;
            if (degree > 0) {
                result.put(degree - 1, new Monomial(degree - 1, coefficient));
            }
        }
        return new Polynomial((result));
    }

    public Polynomial integrate(Polynomial polynomial) {
        Map<Integer, Monomial> result = new HashMap<>();

        for (Map.Entry<Integer, Monomial> entry : polynomial.getMyPolynomial().entrySet()) {
            int degree = entry.getKey();
            double coefficient = entry.getValue().getCoefficient() / (degree + 1);

            result.put(degree + 1, new Monomial(degree + 1, coefficient));
        }
        return new Polynomial(new HashMap<>(result));
    }
}
