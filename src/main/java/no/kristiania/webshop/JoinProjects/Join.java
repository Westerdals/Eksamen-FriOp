package no.kristiania.webshop.JoinProjects;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class Join {
    private String memberName;
    private String projectName;


    public void setMemberName(String memberName){
        this.memberName = memberName;
    }
    public String getMemberName(){
        return memberName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String pName) {
        this.projectName = pName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Join join = (Join) o;
        return Objects.equals(memberName, join.memberName) &&
                Objects.equals(projectName, join.projectName);
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
        return Objects.hash(memberName);
    }

    @Override
    public String toString() {
        return "Assign{" +
                "memberName='" + memberName + '\'' +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}
