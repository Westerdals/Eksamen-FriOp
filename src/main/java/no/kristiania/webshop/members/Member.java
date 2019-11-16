package no.kristiania.webshop.members;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Member {
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
        Member member = (Member) o;
        return id == member.id &&
                name.equals(member.name) &&
                lName.equals(member.lName);
    }

    public static String decodeValue(String value) {
        try {
            return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException ex) {
            throw new RuntimeException(ex.getCause());
        }
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
        return "Member{" +
                "name='" + name + '\'' +
                "lName='" + lName + '\'' +
                ", id=" + id +
                '}';
    }
}
