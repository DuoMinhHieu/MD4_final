package com.codegym.model;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import jakarta.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "customer")
@Data
@Component
public class Customer implements Validator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String typeofservice;
    private String daytrading;
    private String price;
    private String acreage;

    @Override
    public boolean supports(Class<?> clazz) {
        return Customer.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customer customer = (Customer) target;

        ValidationUtils.rejectIfEmpty(errors, "firstName", "firstName.empty", "First name cannot be empty");
        ValidationUtils.rejectIfEmpty(errors, "typeofservice", "typeofservice.empty", "Type of service cannot be empty");
        ValidationUtils.rejectIfEmpty(errors, "daytrading", "daytrading.empty", "Day trading cannot be empty");
        ValidationUtils.rejectIfEmpty(errors, "price", "price.empty", "Price cannot be empty");
        ValidationUtils.rejectIfEmpty(errors, "acreage", "acreage.empty", "Acreage cannot be empty");


        if (customer.getDaytrading() != null && !customer.getDaytrading().isEmpty()) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                dateFormat.setLenient(false);
                dateFormat.parse(customer.getDaytrading());
            } catch (ParseException e) {
                errors.rejectValue("daytrading", "daytrading.format", "Day trading must be a valid date in dd/MM/yyyy format");
            }
        }


        if (customer.getPrice() != null && !customer.getPrice().isEmpty()) {
            try {
                Double.parseDouble(customer.getPrice());
            } catch (NumberFormatException e) {
                errors.rejectValue("price", "price.format", "Price must be a numeric value");
            }
        }


        if (customer.getAcreage() != null && !customer.getAcreage().isEmpty()) {
            try {
                Double.parseDouble(customer.getAcreage());
            } catch (NumberFormatException e) {
                errors.rejectValue("acreage", "acreage.format", "Acreage must be a numeric value");
            }
        }
    }
}
