package com.example.fastmart3.models;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String userId, email, name, address, phone, country, dateOfBirth, gender, accountType;
    private long timestamp;

    public User() {}
    public User(String email, String name, String address, String phone, String country, 
                String dateOfBirth, String gender, String accountType) {
        this.email = email; this.name = name; this.address = address; this.phone = phone;
        this.country = country; this.dateOfBirth = dateOfBirth; this.gender = gender;
        this.accountType = accountType; this.timestamp = System.currentTimeMillis();
    }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getEmail() { return email; }
    public String getName() { return name; }
    public String getAddress() { return address; }
    public String getPhone() { return phone; }
    public String getCountry() { return country; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getAccountType() { return accountType; }
    public long getTimestamp() { return timestamp; }
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId); map.put("email", email); map.put("name", name);
        map.put("address", address); map.put("phone", phone); map.put("country", country);
        map.put("dateOfBirth", dateOfBirth); map.put("gender", gender);
        map.put("accountType", accountType); map.put("timestamp", timestamp);
        return map;
    }
}
