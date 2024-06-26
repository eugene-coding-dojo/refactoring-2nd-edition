package ru.ewc.learning.chapter01;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TheaterBillingTest {
    @Test
    void bigCoCharacteriationTest() {
        TheaterBilling.Invoice invoice = new TheaterBilling.Invoice("BigCo", new TheaterBilling.Performance[]{
            new TheaterBilling.Performance("hamlet", 55),
            new TheaterBilling.Performance("as-like", 35),
            new TheaterBilling.Performance("othello", 40)
        });
        TheaterBilling.Plays plays = new TheaterBilling.Plays(new TheaterBilling.Play[]{
            new TheaterBilling.Play("hamlet", "Hamlet", "tragedy"),
            new TheaterBilling.Play("as-like", "As You Like It", "comedy"),
            new TheaterBilling.Play("othello", "Othello", "tragedy")
        });

        String expected = "Statement for BigCo\n" +
                          "  Hamlet: $650.00 (55 seats)\n" +
                          "  As You Like It: $580.00 (35 seats)\n" +
                          "  Othello: $500.00 (40 seats)\n" +
                          "Amount owed is $1,730.00\n" +
                          "You earned 47 credits\n";
        assertEquals(expected, new TheaterBilling(plays).statement(invoice));
    }
}