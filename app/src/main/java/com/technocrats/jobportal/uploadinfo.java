package com.technocrats.jobportal;

public class uploadinfo {

    public String description;
    public String title;
    public String email;
    public String purl;

    public uploadinfo(String description, String title, String email, String purl) {
        this.description = description;
        this.title = title;
        this.email = email;
        this.purl = purl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
