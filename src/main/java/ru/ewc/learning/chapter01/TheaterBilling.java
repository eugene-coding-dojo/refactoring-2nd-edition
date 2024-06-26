package ru.ewc.learning.chapter01;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.function.Function;

public class TheaterBilling {
    private final Plays plays;

    public TheaterBilling(Plays plays) {
        this.plays = plays;
    }

    public String statement(Invoice invoice) {
        int totalAmount = 0;
        int volumeCredits = 0;
        String result = "Statement for " + invoice.customer() + "\n";
        final Function<Integer, String> format = (amount) -> {
            final Locale locale = new Locale("en", "US");
            final NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
            return nf.format(amount);
        };

        for (Performance perf : invoice.performances()) {
            Play play = plays.get(perf.playID());
            int thisAmount = amountFor(play, perf);

            // add volume credits
            volumeCredits += Math.max(perf.audience() - 30, 0);
            // add extra credit for every ten comedy attendees
            if ("comedy".equals(play.type())) {
                volumeCredits += Math.floorDiv(perf.audience(), 5);
            }

            // print line for this order
            result += "  " + play.name() + ": " + format.apply(thisAmount / 100) + " (" + perf.audience() + " " +
                      "seats)\n";
            totalAmount += thisAmount;
        }

        result += "Amount owed is " + format.apply(totalAmount / 100) + "\n";
        result += "You earned " + volumeCredits + " credits\n";
        return result;
    }

    private static int amountFor(Play play, Performance aPerformance) {
        int result = 0;
        switch (play.type()) {
            case "tragedy":
                result = 40000;
                if (aPerformance.audience() > 30) {
                    result += 1000 * (aPerformance.audience() - 30);
                }
                break;
            case "comedy":
                result = 30000;
                if (aPerformance.audience() > 20) {
                    result += 10000 + 500 * (aPerformance.audience() - 20);
                }
                result += 300 * aPerformance.audience();
                break;
            default:
                throw new IllegalArgumentException("Unknown type: " + play.type());
        }
        return result;
    }

    public record Invoice(String customer, Performance[] performances) {
    }

    public record Performance(String playID, int audience) {
    }

    public record Plays(Play[] plays) {
        public Play get(String playID) {
            for (Play play : plays) {
                if (play.id().equals(playID)) {
                    return play;
                }
            }
            return null;
        }
    }

    public record Play(String id, String name, String type) {
    }
}
