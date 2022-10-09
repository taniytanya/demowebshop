package com.demowebshop.tests;


import com.demowebshop.helpers.AllureRestAssuredFilter;

import org.junit.jupiter.api.*;
import org.openqa.selenium.Cookie;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.value;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;

public class RegisterTests extends TestBase {
    TestData testData = new TestData();
    public String baseUrl = "https://demowebshop.tricentis.com";


    @Test
    @Tag("demowebshop")
    @DisplayName("Successful registration and edit user to some demowebshop (UI+API)")
    void registerTest() {
        step("Open register page", () ->
                open(baseUrl + "/register"));
        step("Put data on form and register", () ->
                $(testData.female).selectRadio("F"));
        $(testData.firstName).setValue(testData.firstNameData);
        $(testData.lastName).setValue(testData.lastNameData);
        $(testData.email).setValue(testData.emailData);
        $(testData.password).setValue(testData.passwordData);
        $(testData.confirmPassword).setValue(testData.passwordData);
        $(testData.registerButton).click();
        $(testData.checkBody).shouldHave(text(testData.textBody));
        $(testData.checkTitle).shouldHave(text(testData.textTitle));

        step("Get cookie by api and set it to browser", () -> {
            String authorizationCookie =
                    given()
                            .filter(AllureRestAssuredFilter.withCustomTemplates())
                            .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                            .formParam("Email", testData.emailData)
                            .formParam("Password", testData.passwordData)
                            .when()
                            .post(baseUrl + "/login")
                            .then()
                            .statusCode(302)
                            .extract()
                            .cookie("NOPCOMMERCE.AUTH");

            step("Open minimal content, because cookie can be set when site is opened", () ->
                    open("/Themes/DefaultClean/Content/images/logo.png"));

            step("Set cookie to to browser", () ->
                    getWebDriver().manage().addCookie(
                            new Cookie("NOPCOMMERCE.AUTH", authorizationCookie)));
        });

        step("Open main page", () ->
                open(baseUrl));

        step("Verify successful authorization", () ->
                $(testData.headerLogin).shouldHave(text(testData.emailData)));

        step("Open user page", () -> {
            $(testData.headerLogin).click();
            $(testData.customerInfo).shouldHave(text(testData.textCustomerInfo));
            $(testData.firstName).shouldHave(value(testData.firstNameData));
        });
        step("Edit first name", () -> {
            $(testData.firstName).setValue(testData.newName);
            $(testData.saveButton).click();
            $(testData.headerLogin).click();
            $(testData.firstName).shouldHave(value(testData.newName));
        });


    }


}
