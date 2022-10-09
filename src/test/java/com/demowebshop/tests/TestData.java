package com.demowebshop.tests;

import com.github.javafaker.Faker;

public class TestData {
    static Faker faker = new Faker();

    public String female = "#gender-female";
    public String firstName = "#FirstName";
    public String lastName = "#LastName";
    public String email = "#Email";
    public String password = "#Password";
    public String confirmPassword = "#ConfirmPassword";
    public String firstNameData = "John";
    public String lastNameData = "Main";

    public String emailData = faker.internet().emailAddress();
    public String passwordData = "123456";
    public String registerButton = "#register-button";
    public String checkTitle = ".page-title";
    public String checkBody = ".page-body";
    public String textTitle = "Register";
    public String textBody = "Your registration completed";
    public String headerLogin = ".header-links .account";
    public String customerInfo = ".page-title";
    public String textCustomerInfo = "My account - Customer info";
    public String newName = "NewNameName";
    public String saveButton = ".save-customer-info-button";


}
