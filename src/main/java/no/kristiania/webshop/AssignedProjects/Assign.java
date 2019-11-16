package no.kristiania.webshop.AssignedProjects;

import java.util.Objects;

public class Assign {
    private String name;
    private String pName;
    private long id;


    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }

    public String getPName() {
        return pName;
    }

    public void setPName(String pName) {
        this.pName = pName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assign assign = (Assign) o;
        return id == assign.id &&
                name.equals(assign.name) &&
                pName.equals(assign.pName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Assign{" +
                "name='" + name + '\'' +
                "pName='" + pName + '\'' +
                ", id=" + id +
                '}';
    }
}
