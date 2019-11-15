package no.kristiania.webshop;
import java.util.Objects;

public class Product {
    private String name;
    private String lName;
    private long id;

    //==================================
    // Getter and Setters
    //==================================

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                name.equals(product.name) &&
                lName.equals(product.lName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                "lName='" + lName + '\'' +
                ", id=" + id +
                '}';
    }
}
