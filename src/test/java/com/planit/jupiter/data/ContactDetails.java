package com.planit.jupiter.data;

import net.datafaker.Faker;

public record ContactDetails(String forename, String email, String message) {
    private static final Faker FAKER = new Faker();

    /** Random but valid details, so every run submits something different. */
    public static ContactDetails random() {
        return new ContactDetails(
                FAKER.name().firstName(),
                FAKER.internet().emailAddress(),
                FAKER.lorem().sentence());
    }
}
