package com.sososhopping.customer.common.types;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessDays{
    String day;
    Boolean isOpen;
    String openTime;
    String closeTime;
}

