package com.example.bruno.cuadernodebitacora;

public class InitialOrderPost {

    private String book_id;
    private String book_title;
    private long date_milis;

    public InitialOrderPost() {
    }

    public InitialOrderPost(String book_id, String book_title, long date_milis) {
        this.book_id = book_id;
        this.book_title = book_title;
        this.date_milis = date_milis;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public long getDate_milis() {
        return date_milis;
    }

    public void setDate_milis(long date_milis) {
        this.date_milis = date_milis;
    }

    @Override
    public String toString() {
        return "InitialOrderPost{" +
                "book_id='" + book_id + '\'' +
                ", book_title='" + book_title + '\'' +
                ", date_milis=" + date_milis +
                '}';
    }

}
