
package com.example.bruno.cuadernodebitacora.pojos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Temperature {

    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("cod")
    @Expose
    private String cod;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("list")
    @Expose
    private java.util.List<com.example.bruno.cuadernodebitacora.pojos.List> list = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public java.util.List<com.example.bruno.cuadernodebitacora.pojos.List> getList() {
        return list;
    }

    public void setList(java.util.List<com.example.bruno.cuadernodebitacora.pojos.List> list) {
        this.list = list;
    }

}
