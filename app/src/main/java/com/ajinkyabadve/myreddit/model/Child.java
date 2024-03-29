
package com.ajinkyabadve.myreddit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Child {

    @SerializedName("kind")
    @Expose
    private String kind;
    @SerializedName("data")
    @Expose
    private Data_ data;

    /**
     * @return The kind
     */
    public String getKind() {
        return kind;
    }

    /**
     * @param kind The kind
     */
    public void setKind(String kind) {
        this.kind = kind;
    }

    /**
     * @return The data
     */
    public Data_ getData() {
        return data;
    }

    /**
     * @param data The data
     */
    @SuppressWarnings("unused")
    public void setData(Data_ data) {
        this.data = data;
    }


    @Override
    public String toString() {
        return "Child{" +
                "kind='" + kind + '\'' +
                ", data=" + data +
                '}';
    }
}
