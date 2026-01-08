package model;

public class Student {
    private String id;
    private String name;
    private String email;
    private String degree;

    public Student(String id, String name, String email, String degree) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.degree = degree;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getDegree() { return degree; }



}