package com.example.poshell.cli;

import com.example.poshell.biz.PosService;
import com.example.poshell.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class PosCommand {

    private PosService posService;

    @Autowired
    public void setPosService(PosService posService) {
        this.posService = posService;
    }

    @ShellMethod(value = "List Products", key = "p")
    public String products() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        for (Product product : posService.products()) {
            stringBuilder.append("\t").append(++i).append("\t").append(product).append("\n");
        }
        return stringBuilder.toString();
    }

    @ShellMethod(value = "New Cart", key = "n")
    public String newCart() {
        return posService.newCart() + " OK";
    }

    @ShellMethod(value = "Add a Product to Cart", key = "a")
    public String addToCart(String productId, int amount) {
        if (posService.add(productId, amount)) {
            return posService.getCart().toString();
        }
        return "ERROR";
    }

    @ShellMethod(value = "Empty Cart", key = "e")
    public String emptyCart() {
        posService.emptyCart();
        return "Empty Cart OK";
    }

    @ShellMethod(value = "Delete a Product to Cart", key = "d")
    public String deleteToCart(String productId) {
        if (posService.delete(productId)) {
            return posService.getCart().toString();
        }
        return "ERROR";
    }

    @ShellMethod(value = "Modify a Product to Cart", key = "m")
    public String modifyToCart(String productId, int amount) {
        if (posService.modify(productId, amount)) {
            return posService.getCart().toString();
        }
        return "ERROR";
    }

    @ShellMethod(value = "List Cart", key = "c")
    public String cart() {
        return posService.getCart().toString();
    }
}
