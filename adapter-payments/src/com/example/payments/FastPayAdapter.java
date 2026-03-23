package com.example.payments;

/**
 * Adapts FastPayClient to the PaymentGateway interface.
 * Maps charge() -> payNow().
 */
public class FastPayAdapter implements PaymentGateway {

    private final FastPayClient client;

    public FastPayAdapter(FastPayClient client) {
        this.client = client;
    }

    @Override
    public String charge(String customerId, int amountCents) {
        return client.payNow(customerId, amountCents);
    }
}
