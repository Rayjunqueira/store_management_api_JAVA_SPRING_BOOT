package com.example.softwaresystemapi.services;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateOrderNumberService {
    public String generateRandomOrderNumber() {
        int numberDigits = 8;
        Random rand = new Random();
        StringBuilder number = new StringBuilder(numberDigits);

        for (int i = 0; i < numberDigits; i++) {
            int digit = rand.nextInt(10);
            number.append(digit);
        }

        return number.toString();
    }
}
