package com.technocrats.jobportal;

public class model
{
    String title,description,email,purl;
     model()
     {

     }

    public model(String title, String description, String email, String purl) {
        this.title = title;
        this.description = description;
        this.email = email;
        this.purl = purl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPurl() {
        return purl;
    }

    public void setPurl(String purl) {
        this.purl = purl;
    }
}
