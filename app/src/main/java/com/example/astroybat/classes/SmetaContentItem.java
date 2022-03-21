package com.example.astroybat.classes;

public class SmetaContentItem {

    public String title;
    public String unit;
    public int price;
    public int amount;
    public boolean isMaterial;

    public SmetaContentItem(String title, String unit, int price, boolean isMaterial) {
        this.title = title;
        this.unit = unit;
        this.price = price;
        this.amount = 0;
        this.isMaterial = isMaterial;
    }

}
