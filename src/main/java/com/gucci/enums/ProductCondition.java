package com.gucci.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductCondition {
    AVAILABILITY("In Stock"),
    CONDITIONS("New"),
    BRAND("Polo");

    final String text;
}
