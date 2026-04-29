package com.example.fastmart3.utils;

import android.util.Patterns;

public class ValidationUtils {
    public static boolean isValidEmail(String email) { return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches(); }
    public static boolean isValidPassword(String password) { return password != null && password.length() >= 6; }
    public static boolean isValidPhone(String phone) { return phone != null && phone.length() >= 10; }
    public static boolean isValidName(String name) { return name != null && !name.trim().isEmpty(); }
    public static boolean isValidAddress(String address) { return address != null && !address.trim().isEmpty(); }
    public static boolean isValidCountry(String country) { return country != null && !country.trim().isEmpty(); }
    public static boolean isValidDOB(String dob) { return dob != null && dob.matches("\\d{2}/\\d{2}/\\d{4}"); }
    public static boolean isValidPrice(double price) { return price > 0; }
}
