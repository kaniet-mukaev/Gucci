package com.gucci.entities;

import com.gucci.enums.Country;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class User {
    // Lombok автоматически создаст геттеры и сеттеры для всех полей
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
