package com.oopbackend;

//Properties Class represents the comman properties of files & folders
public class Properties {

    String name;
    Folder parent;
    String path;

    //Constructor to initialize name and path
    Properties(String name) {
        this.name = name;
        this.path = name.replaceAll("\\s", "");
    }

    //Method to update path
    void updatePath(String parent_path) {
        this.path = parent_path + "/" + this.path;
    }
}
