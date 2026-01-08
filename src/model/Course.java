package model;

public class Course {
    private String courseId;
    private String courseName;
    private int credits;
    private String lecturerId;

    public Course(String courseId, String courseName, int credits, String lecturerId) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.credits = credits;
        this.lecturerId = lecturerId;
    }

    public String getCourseId() { return courseId; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    public String getLecturerId() { return lecturerId; }
}