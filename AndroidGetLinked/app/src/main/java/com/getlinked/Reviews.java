package com.getlinked;

public class Reviews {

    private Long id;

    private String username;

    private Long professionalid;

    private int rating;

    private String date;

    private String comment;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getProfessionalid() {
        return professionalid;
    }

    public void setProfessionalid(Long professionalid) {
        this.professionalid = professionalid;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
