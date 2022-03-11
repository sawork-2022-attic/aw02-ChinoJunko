package com.example.poshell.db;

import com.example.poshell.model.Cart;
import com.example.poshell.model.Product;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PosInMemoryDB implements PosDB {

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/pos?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";
    static final String PASS = "191220002";

    private List<Product> products;

    private Connection conn;

    private Cart cart;

    public boolean updateProducts(){
        ArrayList<Product> productArrayList = new ArrayList<>();
        try (Statement stmt = conn.createStatement();ResultSet rs = stmt.executeQuery("SELECT * FROM product")){
            while(rs.next()){
                String id  = rs.getString("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                productArrayList.add(new Product(id,name,price));
            }
        }catch (SQLException se){
            return false;
        }
        products = productArrayList;
        return true;
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Cart saveCart(Cart cart) {
        this.cart = cart;
        return this.cart;
    }

    @Override
    public Cart getCart() {
        return this.cart;
    }

    @Override
    public Product getProduct(String productId) {
        for (Product p : getProducts()) {
            if (p.getId().equals(productId)) {
                return p;
            }
        }
        return null;
    }

    private PosInMemoryDB() {
        try {
            Class.forName(JDBC_DRIVER);

            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
            System.out.printf("数据库连接成功："+DB_URL);
            updateProducts();
        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
    }

}
