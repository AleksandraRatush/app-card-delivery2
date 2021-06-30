package ru.netology.patterns;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class CardDeliveryInfo {
    private final String city;
    private final String name;
    private final String phone;
    private final LocalDate date;
}
