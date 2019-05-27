package com.example.donacionesuabc.Model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Donacion {
    private String articleName;
    private String facultyName;
    private String category;
    private String description;
    private String email;
    private String emailAlt;
    private String facebook;
    private String celular;
    private String date;
    private String userId;
    private String imageUrl;

    public Donacion() {
    }

    public Donacion(String userId, String articleName, String facultyName, String category, String description, String email, String emailAlt, String facebook, String celular, String imageUrl) {
        this.userId = userId;
        this.articleName = articleName;
        this.facultyName = facultyName;
        this.category = category;
        this.description = description;
        this.email = email;
        this.facebook = facebook;
        this.celular = celular;
        this.imageUrl = imageUrl;

        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        this.date = formatter.format(todayDate);
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmailAlt() {
        return emailAlt;
    }

    public void setEmailAlt(String emailAlt) {
        this.emailAlt = emailAlt;
    }
}
