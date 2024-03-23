package org.example.Logic;

import org.example.Model.Polynomial;

public class Divade {
    private Polynomial quotient;
    private Polynomial remainder;

    public Divade(Polynomial quotient, Polynomial remainder) {
        this.quotient = quotient;
        this.remainder = remainder;
    }

    public Polynomial getQuotient() {
        return quotient;
    }

    public Polynomial getRemainder() {
        return remainder;
    }
}
