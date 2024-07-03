package com.br.webshop.purchase.utils;

import com.br.webshop.purchase.domain.*;

public class DomainUtils {
    public static Product getProduct1() {
        return Product
                .builder()
                .code("1")
                .type("Tinto")
                .price(121.5)
                .harvest("2019")
                .year(2020)
                .build();
    }
    public static Product getProduct2() {
        return Product
                .builder()
                .code("2")
                .type("RosÊ")
                .price(430.3)
                .harvest("2009")
                .year(2020)
                .build();
    }
    public static Product getProduct3() {
        return Product
                .builder()
                .code("3")
                .type("RosÊ")
                .price(1020.2)
                .harvest("2010")
                .year(2019)
                .build();
    }
    public static Product getProduct4() {
        return Product
                .builder()
                .code("4")
                .type("RosÊ")
                .price(860.0)
                .harvest("2010")
                .year(2019)
                .build();
    }
    public static Purchase getPurchase1() {
        return Purchase
                .builder()
                .code("1")
                .quantity(2)
                .totalCost(243.0)
                .product(DomainUtils.getProduct1())
                .build();
    }
    public static Purchase getPurchase2() {
        return Purchase
                .builder()
                .code("2")
                .quantity(1)
                .totalCost(430.3)
                .product(getProduct2())
                .build();
    }
    public static Purchase getPurchase3() {
        return Purchase
                .builder()
                .code("3")
                .quantity(3)
                .totalCost(3060.6)
                .product(DomainUtils.getProduct3())
                .build();
    }
    public static Purchase getPurchase4() {
        return Purchase
                .builder()
                .code("4")
                .quantity(1)
                .totalCost(860.0)
                .product(getProduct4())
                .build();
    }
    public static Purchase getPurchase5() {
        return Purchase
                .builder()
                .code("4")
                .quantity(4)
                .totalCost(1721.2)
                .product(getProduct2())
                .build();
    }
    public static PurchaseSummary getPurchaseSummary1() {
        return PurchaseSummary
                .builder()
                .count(1)
                .totalCost(243.0)
                .build();
    }
    public static PurchaseSummary getPurchaseSummary2() {
        return PurchaseSummary
                .builder()
                .count(1)
                .totalCost(430.3)
                .build();
    }
    public static PurchaseSummary getPurchaseSummary3(){
        return PurchaseSummary
                .builder()
                .count(0)
                .totalCost(0d)
                .build();
    }

    public static PurchaseSummary getPurchaseSummary4(){
        return PurchaseSummary
                .builder()
                .count(2)
                .totalCost(673.3)
                .build();
    }

    public static PurchaseSummary getPurchaseSummary5(){
        return PurchaseSummary
                .builder()
                .count(1)
                .totalCost(1721.2)
                .build();
    }
    public static Customer getCustomer1 () {
        return Customer
                .builder()
                .name("John Doe")
                .citizenId("999888877733")
                .purchases(new Purchase[]{DomainUtils.getPurchase1()})
                .purchaseSummary(DomainUtils.getPurchaseSummary1())
                .build();
    }
    public static Customer getCustomer2() {
        return Customer
                .builder()
                .name("Jane Smith")
                .citizenId("199888877733")
                .purchases(new Purchase[]{ DomainUtils.getPurchase2() })
                .purchaseSummary(DomainUtils.getPurchaseSummary2())
                .build();
    }
    public static Customer getCustomer3() {
        return Customer
                .builder()
                .name("Bob Johnson")
                .citizenId("299888877733")
                .purchases(new Purchase[]{})
                .purchaseSummary(DomainUtils.getPurchaseSummary3())
                .build();
    }
    public static Customer getCustomer4() {
        return Customer
                .builder()
                .name("Michael Jackson")
                .citizenId("299884477113")
                .purchases(new Purchase[]{
                        getPurchase1(),
                        getPurchase2(),
                })
                .purchaseSummary(DomainUtils.getPurchaseSummary4())
                .build();
    }
    public static Customer getCustomer5(){
        return Customer
                .builder()
                .name("Other Name")
                .citizenId("221418877223")
                .purchases(new Purchase[]{
                        getPurchase5(),
                })
                .purchaseSummary(DomainUtils.getPurchaseSummary5())
                .build();
    }
    public static Purchase getNotEnrichedPurchase1() {
        return Purchase
                .builder()
                .code("1")
                .quantity(1)
                .build();
    }
    public static Purchase getNotEnrichedPurchase2() {
        return Purchase
                .builder()
                .code("4")
                .quantity(2)
                .build();
    }
    public static Customer getNotEnrichedCustomer1() {
        return Customer
                .builder()
                .name("John Doe")
                .citizenId("999888877733")
                .purchases(new Purchase[]{DomainUtils.getNotEnrichedPurchase1()})
                .build();
    }

    public static Customer getNotEnrichedCustomer2() {
        return Customer
                .builder()
                .name("Jane Smith")
                .citizenId("199888877733")
                .purchases(new Purchase[]{DomainUtils.getNotEnrichedPurchase2()})
                .build();
    }

    public static Product getProductRecommendation1(){
        return DomainUtils.getProduct1();
    }
    public static Product getProductRecommendation2(){
        return  Product
                .builder()
                .code("2")
                .type("Tinto")
                .price(140.0)
                .harvest("2019")
                .year(2021)
                .build();
    }
    public static Product getProductRecommendation3(){
        return  Product
                .builder()
                .code("3")
                .type("Tinto")
                .price(430.2)
                .harvest("2007")
                .year(2019)
                .build();
    }
    public static Recommendation getRecommendation1(){
        return Recommendation
                .builder()
                .customer(DomainUtils.getCustomer1())
                .productType("Tinto")
                .products(new Product[]{
                        DomainUtils.getProductRecommendation1(),
                        DomainUtils.getProductRecommendation2(),
                        DomainUtils.getProductRecommendation3(),

                })
                .build();
    }
}
