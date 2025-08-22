package com.gucci.entities;

import com.gucci.enums.Country;
import net.datafaker.Faker;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class UserGenerated {

    private static final Faker faker = new Faker(new Locale("en"));

    public static User randomUser() {

        String formattedBirthday = new SimpleDateFormat("dd/MMMM/yyyy", Locale.ENGLISH)
                .format(faker.date().birthday(18, 65));

        return User.builder()
                .title(faker.options().option("Mr", "Mrs"))
                .firstName(faker.name().firstName())
                .lastName(faker.name().lastName())
                .name(faker.name().fullName())
                .email(faker.internet().emailAddress())
                .password(faker.internet().password(8, 16))
                .dateOfBirth(formattedBirthday)
                .company(faker.company().name())
                .address1(faker.address().streetAddress())
                .address2(faker.address().secondaryAddress())
                .country(randomCountry())
                .state(faker.address().state())
                .city(faker.address().city())
                .zipcode(faker.address().zipCode())
                .mobileNumber(faker.phoneNumber().cellPhone())
                .build();
    }

    private static Country randomCountry() {
        Country[] countries = Country.values();
        int randomIndex = ThreadLocalRandom.current().nextInt(countries.length);
        return countries[randomIndex];
    }
}
