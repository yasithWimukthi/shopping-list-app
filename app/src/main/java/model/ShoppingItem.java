package model;

public class ShoppingItem {
    private int id;
    private String itemName;
    private String itemColor;
    private int quantity;
    private int size;
    private String itemAddedDate;

    public ShoppingItem() {
    }

    public ShoppingItem(String itemName, String itemColor, int quantity, int size, String itemAddedDate) {
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.quantity = quantity;
        this.size = size;
        this.itemAddedDate = itemAddedDate;
    }

    public ShoppingItem(int id, String itemName, String itemColor, int quantity, int size, String itemAddedDate) {
        this.id = id;
        this.itemName = itemName;
        this.itemColor = itemColor;
        this.quantity = quantity;
        this.size = size;
        this.itemAddedDate = itemAddedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemColor() {
        return itemColor;
    }

    public void setItemColor(String itemColor) {
        this.itemColor = itemColor;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getItemAddedDate() {
        return itemAddedDate;
    }

    public void setItemAddedDate(String itemAddedDate) {
        this.itemAddedDate = itemAddedDate;
    }
}
