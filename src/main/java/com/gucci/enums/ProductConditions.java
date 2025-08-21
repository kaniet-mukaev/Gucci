package com.gucci.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum ProductConditions {
    AVAILABILITY("In Stock"),
    CONDITIONS("New"),
    BRAND("Polo");

    final String text;
}
