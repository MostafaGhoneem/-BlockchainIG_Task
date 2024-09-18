package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class AddBestProductToCartTest extends BaseTest {

    @Test
    @DisplayName("check the total price of 3 best sellers products")
    @Description("check the total price of 3 best sellers products")
    public void addBestSellerProductToCart(){
        var totalPrice = getReader().get("cart.total-price").toString();
        homePage.ClickOnShop()
             .ClickOnBestSellerProduct()
             .PickColor().AddQuantity().addToCart().openCart()
             .assertTheTotalPriceIsRight(totalPrice).ClickOnCheckOut();



    }
}
