package com.gucci.entities;

import com.gucci.enums.Country;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User {
    String title;
    String name;
    String email;
    String password;
    String dateOfBirth;
    String firstName;
    String lastName;
    String company;
    String address1;
    String address2;
    Country country;
    String state;
    String city;
    String zipcode;
    String mobileNumber;
}
