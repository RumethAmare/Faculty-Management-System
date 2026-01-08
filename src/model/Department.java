package model;

public class Department {
    private String id;
    private String name;
    private String hod;
    private int staffCount;


    public Department(String id, String name, String hod, int staffCount) {
        this.id = id;
        this.name = name;
        this.hod = hod;
        this.staffCount = staffCount;
    }


    public String getId() { return id; }
    public String getName() { return name; }
    public String getHod() { return hod; }
    public int getStaffCount() { return staffCount; }


    public void setId(String id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setHod(String hod) { this.hod = hod; }
    public void setStaffCount(int staffCount) { this.staffCount = staffCount; }
}