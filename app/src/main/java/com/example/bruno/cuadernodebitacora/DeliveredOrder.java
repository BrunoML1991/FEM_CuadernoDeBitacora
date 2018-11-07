package com.example.bruno.cuadernodebitacora;

public class DeliveredOrder {

    private String book_id;
    private String order_id;
    private String book_title;
    private long order_started_date_milis;
    private long order_delivered_date_milis;
    private String issue_description;
    private String issue_photo_url;

    public DeliveredOrder() {
    }

    public DeliveredOrder(String book_id, String order_id, String book_title, long order_started_date_milis, long order_delivered_date_milis, String issue_description, String issue_photo_url) {
        this.book_id = book_id;
        this.order_id = order_id;
        this.book_title = book_title;
        this.order_started_date_milis = order_started_date_milis;
        this.order_delivered_date_milis = order_delivered_date_milis;
        this.issue_description = issue_description;
        this.issue_photo_url = issue_photo_url;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getBook_title() {
        return book_title;
    }

    public void setBook_title(String book_title) {
        this.book_title = book_title;
    }

    public long getOrder_started_date_milis() {
        return order_started_date_milis;
    }

    public void setOrder_started_date_milis(long order_started_date_milis) {
        this.order_started_date_milis = order_started_date_milis;
    }

    public long getOrder_delivered_date_milis() {
        return order_delivered_date_milis;
    }

    public void setOrder_delivered_date_milis(long order_delivered_date_milis) {
        this.order_delivered_date_milis = order_delivered_date_milis;
    }

    public String getIssue_description() {
        return issue_description;
    }

    public void setIssue_description(String issue_description) {
        this.issue_description = issue_description;
    }

    public String getIssue_photo_url() {
        return issue_photo_url;
    }

    public void setIssue_photo_url(String issue_photo_url) {
        this.issue_photo_url = issue_photo_url;
    }

    @Override
    public String toString() {
        return "DeliveredOrder{" +
                "book_id='" + book_id + '\'' +
                ", order_id='" + order_id + '\'' +
                ", book_title='" + book_title + '\'' +
                ", order_started_date_milis=" + order_started_date_milis +
                ", order_delivered_date_milis=" + order_delivered_date_milis +
                ", issue_description='" + issue_description + '\'' +
                ", issue_photo_url='" + issue_photo_url + '\'' +
                '}';
    }

}
