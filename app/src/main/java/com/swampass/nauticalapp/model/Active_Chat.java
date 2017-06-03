package com.swampass.nauticalapp.model;

/**
 * Created by Peter on 6/2/2017.
 */

public class Active_Chat {
    private String Email;
    private String image;
    private String Name;

    public Active_Chat() {
    }

    public Active_Chat(String email, String image, String name) {
        Email = email;
        this.image = image;
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
