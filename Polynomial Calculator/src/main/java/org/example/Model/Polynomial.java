package org.example.Model;

import org.example.Logic.Divade;
import org.example.Logic.Operations;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    private Map<Integer, Monomial> polynomial;

    public Polynomial(Map<Integer, Monomial> myPolynom) {
        this.polynomial = myPolynom;
    }

    public Polynomial() {
        this.polynomial = new HashMap<>();
    }

    public Polynomial(String inputValue) {
        Map<Integer, Monomial> result = new HashMap<>();

        Pattern p = Pattern.compile("((-?\\d+(?=x))?(-?[x])(\\^(-?\\d+))?)|((-?)[x])|(-?\\d+)");
        inputValue = inputValue.replaceAll("\\s", "");
        inputValue = inputValue.replaceAll("\\*", "");
        Matcher m = p.matcher(inputValue);
        double coefficient = 0;
        int exponent = 0;

        //search for each monomial potential using the pattern
        while (m.find()) {
            if (m.group(3) != null && m.group(2) != null) { //the monomial of the form ax^b

                exponent = (m.group(5) != null ? Integer.parseInt(m.group(5)) : 1);
                coefficient = Integer.parseInt(m.group(2));

            } else if (m.group(3) != null && m.group(2) == null) { //the monomial of the form +/-x^b or +/-x
                if (m.group(3).equals("-x")) {
                    coefficient = -1;
                } else {
                    coefficient = 1;
                }
                exponent = (m.group(5) != null ? Integer.parseInt(m.group(5)) : 1);
            } else if (m.group(3) == null && m.group(2) == null) { //the constant monomial, without x
                coefficient = Integer.parseInt(m.group());
            }
            result.put(exponent, new Monomial(exponent, coefficient));
            coefficient = 0;
            exponent = 0;
        }
        this.polynomial = result;
    }

    public Map<Integer, Monomial> getMyPolynomial() {
        return this.polynomial;
    }

    public void setPolynomial(Map<Integer, Monomial> polynomial) {
        this.polynomial = polynomial;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        List<Integer> degrees = new ArrayList<>(this.polynomial.keySet());
        Collections.sort(degrees, Collections.reverseOrder());

        for (Integer degree : degrees) {
            //myPolynom.get(degree) -> value from myPolynom with degree "degree"
            Monomial monomial = this.polynomial.get(degree);
            double coefficient = monomial.getCoefficient();

            if (coefficient == 0) {
                continue;
            }

            if (sb.length() > 0) {
                sb.append(coefficient >= 0 ? " + " : " - ");
            } else {
                sb.append(coefficient >= 0 ? "" : "-");
            }


            if (coefficient != 0 || degree == 0) {
                sb.append(String.format("%.1f", Math.abs(coefficient)));
            }

            if (degree == 1) {
                sb.append("x");
            } else if (degree > 1) {
                sb.append("x^").append(degree);
            }
        }
        return sb.length() == 0 ? "0" : sb.toString();
    }
}

