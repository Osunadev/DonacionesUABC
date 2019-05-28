package com.example.donacionesuabc.Model;

public class User {
    private String userName;
    private String userLastNames;
    private String dateOfBirth;
    private String faculty;
    private String facultyEmailUABC;
    private String userId;
    private String studentId;

    public User() {}

    public User(String userName, String userLastNames, String dateOfBirth, String faculty, String facultyEmailUABC, String userId, String studentId) {
        this.userName = userName;
        this.userLastNames = userLastNames;
        this.dateOfBirth = dateOfBirth;
        this.faculty = faculty;
        this.facultyEmailUABC = facultyEmailUABC;
        this.userId = userId;
        this.studentId = studentId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastNames() {
        return userLastNames;
    }

    public void setUserLastNames(String userLastNames) {
        this.userLastNames = userLastNames;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getFacultyEmailUABC() {
        return facultyEmailUABC;
    }

    public void setFacultyEmailUABC(String facultyEmailUABC) {
        this.facultyEmailUABC = facultyEmailUABC;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}
