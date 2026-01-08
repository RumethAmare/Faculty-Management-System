package model;

public class Degree {
    private String id;
    private String name;
    private String deptId;

    public Degree(String id, String name, String deptId) {
        this.id = id;
        this.name = name;
        this.deptId = deptId;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDeptId() { return deptId; }

    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setDeptId(String deptId) { this.deptId = deptId; }
}