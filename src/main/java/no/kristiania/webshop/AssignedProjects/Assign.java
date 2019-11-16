package no.kristiania.webshop.AssignedProjects;

import java.util.Objects;

public class Assign {
    private String memberName;
    private String projectName;
    private long id;


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
                memberName.equals(assign.memberName) &&
                projectName.equals(assign.projectName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberName);
    }

    @Override
    public String toString() {
        return "Assign{" +
                "name='" + memberName + '\'' +
                "pName='" + projectName + '\'' +
                ", id=" + id +
                '}';
    }
}
