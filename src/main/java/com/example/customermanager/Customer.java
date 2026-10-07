package com.example.customermanager;

/** One customer. Each Customer object becomes one row in the table. */
public class Customer {
    private final String name;
    private final String province;

    public Customer(String name, String province) {
        this.name = name;
        this.province = province;
    }

    // The table columns read the values through these public getters.
    public String getName() { return name; }
    public String getProvince() { return province; }
}
