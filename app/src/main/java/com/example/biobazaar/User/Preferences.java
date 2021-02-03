package com.example.biobazaar.User;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.biobazaar.Filters.Product;

public class Preferences {
    private static SharedPreferences SharedPref;

    public static void init(Context context) {
        if (SharedPref == null) {
            SharedPref = context.getSharedPreferences("BIO_BAZAAR", Activity.MODE_PRIVATE);
        }
    }

    public static void Logout() {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.clear();
        editor.commit();
    }

    //USERNAME USER LOGGADO
    public static void saveName(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("userName");
        editor.putString("userName", value);
        editor.commit();
    }

    public static String readUserName() {
        return SharedPref.getString("userName", null);
    }

    //EMAIL USER LOGGADO
    public static void saveUserEmail(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("userEmail");
        editor.putString("userEmail", value);
        editor.commit();
    }

    public static String readEmail() {
        return SharedPref.getString("userEmail", null);
    }

    //PONTOS USER LOGGADO
    public static void saveCoins(int value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("userCoins");
        editor.putInt("userCoins", value);
        editor.commit();
    }

    public static int getCoins() {
        return SharedPref.getInt("userCoins", 0);
    }


    //PAIS USER LOGGADO
    public static void saveCountry(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("userCountry");
        editor.putString("userCountry", value);
        editor.commit();
    }

    public static String getCountry() {
        return SharedPref.getString("userCountry", null);
    }

    //CODIGOPOSTAL USER LOGGADO
    public static void saveZipCode(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("zipcode");
        editor.putString("zipcode", value);
        editor.commit();
    }

    public static String getZipcode() {
        return SharedPref.getString("zipcode", null);
    }

    //MORADA USER LOGGADO
    public static void saveAdress(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("adress");
        editor.putString("adress", value);
        editor.commit();
    }

    public static String getAdress() {
        return SharedPref.getString("adress", null);
    }

    //CIDADE USER LOGGADO
    public static void saveCity(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("city");
        editor.putString("city", value);
        editor.commit();
    }

    public static String getCity() {
        return SharedPref.getString("city", null);
    }

    //PASSWORD USER LOGGADO
    public static void savePass(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("password");
        editor.putString("password", value);
        editor.commit();
    }

    public static String getPass() {
        return SharedPref.getString("password", null);
    }
    //COMPANHIA NOME USER LOGADO

    public static void saveCompanyName(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("companyName");
        editor.putString("companyName", value);
        editor.commit();

    }

    public static String getCompanyName() {
        return SharedPref.getString("companyName", null);
    }

    //nif USER LOGADO

    public static void saveNif(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("nif");
        editor.putString("nif", value);
        editor.commit();

    }

    public static String getNif() {
        return SharedPref.getString("nif", null);
    }

    public static void savePhone(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("phone");
        editor.putString("phone", value);
        editor.commit();

    }

    public static String getPhone() {
        return SharedPref.getString("phone", null);
    }


    //PRODUTOS FAVORITOS USER LOGGADO
    public static void saveProductId(int value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("productId");
        editor.putInt("productId", value);
        editor.commit();
    }

    public static int getProductId() {
        return SharedPref.getInt("productId", 0);
    }

    public static void removeProductId() {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("productId");
        editor.commit();
    }

    //USERNAME DOS PAGAMENTO
    public static void saveOrderName(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("userName");
        editor.putString("userName", value);
        editor.commit();
    }

    public static String readOrderName() {
        return SharedPref.getString("userName", null);
    }

    //EMAIL USER LOGGADO PAGAMENTO
    public static void saveOrderEmail(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("userEmail");
        editor.putString("userEmail", value);
        editor.commit();
    }

    public static String readOrderEmail() {
        return SharedPref.getString("userEmail", null);
    }

    //PAIS DO PAGAMENTO
    public static void saveOrderCountry(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("userCountry");
        editor.putString("userCountry", value);
        editor.commit();
    }

    public static String getOrderCountry() {
        return SharedPref.getString("userCountry", null);
    }

    //PAIS DO PAGAMENTO
    public static void saveOrderPayment(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("payment");
        editor.putString("payment", value);
        editor.commit();
    }

    public static String getOrderPayment() {
        return SharedPref.getString("payment", null);
    }

    //CODIGOPOSTAL DO PAGAMENTO
    public static void saveOrderZipCode(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("zipcode");
        editor.putString("zipcode", value);
        editor.commit();
    }

    //PRODUTOS PARA ENCOMENDAS
    public static void saveOrderProducts(Product product) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("products");
        editor.putString("productsName", product.getTitle());
        editor.putInt("productsPrice", product.getPrice());
        editor.putString("productsImg", product.getImg());
        editor.commit();

    }
    public static String retrieveOrderProductName()  {
        return SharedPref.getString("productsName", null);
    }
    public static int retrieveOrderProductPrice()  {
        return SharedPref.getInt("productsPrice", 0);
    }
    public static String retrieveOrderProductImg()  {
        return SharedPref.getString("productsImg", null);
    }
    public static void saveOrderProductsPrice(int product) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("products");
        editor.putInt("products", product);
        editor.commit();

    }

    public static int retrieveOrderProducts() {
        return SharedPref.getInt("products", 0);
    }

    public static String getOrderZipcode() {
        return SharedPref.getString("zipcode", null);
    }

    //MORADA DO PAGAMENTO
    public static void saveOrderAdress(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("adress");
        editor.putString("adress", value);
        editor.commit();
    }

    public static String getOrderAdress() {
        return SharedPref.getString("adress", null);
    }

    //CIDADE DO PAGAMENTO
    public static void saveOrderCity(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("city");
        editor.putString("city", value);
        editor.commit();
    }

    public static String getOrderCity() {
        return SharedPref.getString("city", null);
    }

    //EMPRESA DO PAGAMENTO

    public static void saveOrderCompanyName(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("companyName");
        editor.putString("companyName", value);
        editor.commit();

    }

    public static String getOrderCompanyName() {
        return SharedPref.getString("companyName", null);
    }

    //NIF DO PAGAMENTO

    public static void saveOrderNif(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("nif");
        editor.putString("nif", value);
        editor.commit();

    }

    public static String getOrderNif() {
        return SharedPref.getString("nif", null);
    }

    //CONTACTO TELEFÓNICO DO PAGAMENTO
    public static void saveOrderPhone(String value) {
        SharedPreferences.Editor editor = SharedPref.edit();
        editor.remove("phone");
        editor.putString("phone", value);
        editor.commit();

    }

    public static String getOrderPhone() {
        return SharedPref.getString("phone", null);
    }

}
