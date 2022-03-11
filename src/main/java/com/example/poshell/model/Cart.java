package com.example.poshell.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
public class Cart {

    private List<Item> items = new ArrayList<>();

    public boolean addItem(Item item) {
        Optional<Item> existItem = items.stream().filter(
                item1 -> {return item.getProduct().getId().equals(item1.getProduct().getId());}).findAny();
        if(existItem.isPresent()){
            existItem.get().setAmount(existItem.get().getAmount()+item.getAmount());
            return true;
        }
        return items.add(item);
    }

    public boolean deleteItem(Item item) {
        return items.removeIf(item1 -> {return item.getProduct().getId().equals(item1.getProduct().getId());});
    }

    public boolean modifyItem(Item item) {
        Optional<Item> existItem = items.stream().filter(
                item1 -> {return item.getProduct().getId().equals(item1.getProduct().getId());}).findAny();
        if(existItem.isPresent()){
            existItem.get().setAmount(item.getAmount());
            return true;
        }
        return false;
    }

    public Cart empty(){
        items.clear();
        return this;
    }

    @Override
    public String toString() {
        if (items.size() ==0){
            return "Empty Cart";
        }
        double total = 0;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cart -----------------\n"  );

        for (int i = 0; i < items.size(); i++) {
            stringBuilder.append(items.get(i).toString()).append("\n");
            total += items.get(i).getAmount() * items.get(i).getProduct().getPrice();
        }
        stringBuilder.append("----------------------\n"  );

        stringBuilder.append("Total...\t\t\t" + total );

        return stringBuilder.toString();
    }
}
