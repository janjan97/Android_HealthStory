package com.example.HealthStory.API;

public class Product {

    private String image;
    private String productId;
    private String title;
    private String price;
    private String brand;

    public Product(){}

    public String getTitle() { return title;  }

    public void setTitle(String title) { this.title = title;  }

    public String getPrice() { return price;  }

    public void setPrice(String price) {this.price = price;  }

    public String getBrand() { return brand;  }

    public void setBrand(String brand) { this.brand = brand;    }

    public String getImage() { return image;  }

    public void setImage(String image) { this.image = image; }

    public String getProductId() {return productId; }

    public void setProductId(String productId) {this.productId = productId;}
}