package com.oopbackend;
import java.util.ArrayList;
import java.util.List;

//Folder Class represents the folders containing sub-folders & .txt files
public class Folder extends Properties {
    List<Folder> sub_folders;
    List<TextFile> sub_files;

    //Constructors for naming folders and initializing sub-folders & .txt files
    Folder(String name) {
        super(name);
        sub_folders = new ArrayList<>();
        sub_files = new ArrayList<>();
    }

    //Method to add sub-folder and set parent & path
    void addFolder(Folder sub_folder) {
        sub_folder.updatePath(this.path);
        this.sub_folders.add(sub_folder);
        sub_folder.parent = this;
    }

    //Method to add .txt file and set parent & path
    void addFile(TextFile sub_file) {
        sub_file.updatePath(this.path);
        this.sub_files.add(sub_file);
        sub_file.parent = this;
    }
}
