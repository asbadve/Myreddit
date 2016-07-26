
package com.ajinkyabadve.myreddit.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("modhash")
    @Expose
    private String modhash;
    @SerializedName("children")
    @Expose
    private List<Child> children = new ArrayList<>();
    @SerializedName("after")
    @Expose
    private String after;
    @SerializedName("before")
    @Expose
    private Object before;

    /**
     * @return The modhash
     */
    @SuppressWarnings("unused")
    public String getModhash() {
        return modhash;
    }

    /**
     * @param modhash The modhash
     */
    @SuppressWarnings("unused")
    public void setModhash(String modhash) {
        this.modhash = modhash;
    }

    /**
     * @return The children
     */
    public List<Child> getChildren() {
        return children;
    }

    /**
     * @param children The children
     */
    @SuppressWarnings("unused")
    public void setChildren(List<Child> children) {
        this.children = children;
    }

    /**
     * @return The after
     */
    @SuppressWarnings("unused")
    public String getAfter() {
        return after;
    }

    /**
     * @param after The after
     */
    @SuppressWarnings("unused")
    public void setAfter(String after) {
        this.after = after;
    }

    /**
     * @return The before
     */
    @SuppressWarnings("unused")
    public Object getBefore() {
        return before;
    }

    /**
     * @param before The before
     */
    @SuppressWarnings("unused")
    public void setBefore(Object before) {
        this.before = before;
    }


    @Override
    public String toString() {
        return "Data{" +
                "modhash='" + modhash + '\'' +
                ", children=" + children +
                ", after='" + after + '\'' +
                ", before=" + before +
                '}';
    }
}
