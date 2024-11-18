package com.oopbackend;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.swing.*;

//Main Class represents the UserInterface
public class Main {

    JPanel panel;  //Panel to hold UI components
    JLabel locationlable;  //To display current location

    //Method to initialize UserInterface
    public void initializeUI() {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.panel = new JPanel(new GridLayout(3, 1));
        frame.setLayout(null);
        frame.setLayout(new BorderLayout());
        panel.setBounds(0, 0,800, 600);
        panel.setLayout(null);
        frame.setSize(800, 600);
        frame.setVisible(true);
        frame.add(this.panel);
        locationlable = new JLabel("Host");
        frame.add(locationlable, BorderLayout.NORTH);
        frame.add(this.panel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    //Method to render each buttons
    public void renderButton(Folder parent) {

        //Refreshing panel
        this.panel.removeAll();
        this.panel.revalidate();
        this.panel.repaint();

        locationlable.setText(parent.path);  //Update locationlable

        //Creating a back button
        JButton back = new JButton("←");
        JLabel label = new JLabel(parent.path);
        label.setBounds(50, 0, 0, 0);
        back.setBounds(0, 0, 50, 50);
        panel.add(label);

        //Action Listener for the back button
        back.addActionListener(e -> {
            renderButton(parent.parent);
        });

        //Add a back button if parent exist
        if( parent.parent != null ) {
            this.panel.add( back );
        }

        //Loop to create buttons for sub-folders
        for (int i = 0; i < parent.sub_folders.size(); i++){
            Folder child = parent.sub_folders.get(i);
            JButton btn = new JButton(child.name);
            btn.setBounds(70 + 200  * i, 20, 125, 100);
            this.panel.add( btn );
            btn.addActionListener(e -> {
                renderButton(child);
            });
        }

        //Loop to create buttons for .txt files
        for (int i = 0; i < parent.sub_files.size(); i++){
            TextFile child = parent.sub_files.get(i);
            JButton btn = new JButton(child.name);
            btn.setBounds(70 + 200 * i, 20, 135, 100);
            this.panel.add( btn );
            btn.addActionListener(e -> {
                fetchData("http://localhost:8000/host/" + child.path);
            });
        }
    }

    //Method to fetch data from the server
    void fetchData(String urlString) {
        try{
            // create URL object
            URL url = new URL(urlString);

            //create HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            //Set request mode to GET
            connection.setRequestMethod("GET");

            //get response code
            int responseCode = connection.getResponseCode();

            //Read response from input stream
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                connection.getInputStream()
            ));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("Response code: " + responseCode + "\n" + response);

            // Disconnect the connection
            connection.disconnect();

            //Handling file copying and deleting
            if(responseCode == 200){
                String[] tempstr = urlString.split("/");
                String currPath = "";
                String currFileName = "";
                Boolean flag = false;

                //Loop through each part of URL
                for (String word : tempstr) {
                    if (word.contains(".")){
                        flag = false;
                        currFileName = word;
                    }
                    if(flag) {
                        currPath += "/" + word; //Add word to current path if flag is true
                    }
                    if (word.contains("OOP")) {
                        flag = true;  //Start adding the path after encountering OOP
                    }
                }

                String root = "./src/main/resources/local";  //Root Directory for creating folders
                Path file_path = Paths.get(root + "/" + currFileName);  //Full path of the files
                System.out.println("File path is " + file_path);

                //Check if files already exist
                if(Files.exists(file_path)) {
                    Path destinationFolder = Paths.get(root + currPath);
                    Files.createDirectories(destinationFolder);

                    Path destinationFile = destinationFolder.resolve(currFileName);
                    // InputStream inputStream = App.class.getResourceAsStream(root);
                    Files.copy(file_path, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                    Files.delete(file_path); //Delete original file

                    JOptionPane.showMessageDialog(null,
                        "Your file is copied successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    System.out.println("Error");
                    JOptionPane.showMessageDialog(null,
                        "Error: File not copied", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        } catch(IOException ex){
            System.out.println("Error: " + ex.getMessage());
            System.out.println("Error: inside catch error");
            JOptionPane.showMessageDialog(null, "Error: File not copied", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Main Method to set up folder structure
    public static void main(String[] args) {
        Main m = new Main();

        Folder host = new Folder("Host");
        Folder oop = new Folder("OOP");
        Folder g1 = new Folder("Group 1");
        Folder g2 = new Folder("Group 2");
        Folder g1e1 = new Folder("Exercise 01");
        Folder g1e2 = new Folder("Exercise 02");
        Folder g2e1 = new Folder("Exercise 01");
        Folder g2e2 = new Folder("Exercise 02");
        TextFile g1e1ex1 = new TextFile("exercise01.txt");
        TextFile g1e1data = new TextFile("data.txt");
        TextFile g1e2ex2 = new TextFile("exercise02.txt");
        TextFile g1e2data = new TextFile("data.txt");
        TextFile g2e1ex1 = new TextFile("exercise01.txt");
        TextFile g2e1data = new TextFile("data.txt");
        TextFile g2e2ex2 = new TextFile("exercise02.txt");
        TextFile g2e2data = new TextFile("data.txt");

        //Setting OOP sub-folders
        oop.addFolder(g1);
        oop.addFolder(g2);

        //Setting Group 1 sub-folders
        g1.addFolder(g1e1);
        g1.addFolder(g1e2);

        //Setting Group 2 sub-folders
        g2.addFolder(g2e1);
        g2.addFolder(g2e2);

        //Setting Exercise 1 of Group 1 files
        g1e1.addFile(g1e1ex1);
        g1e1.addFile(g1e1data);

        //Setting Exercise 2 of Group 1 files
        g1e2.addFile(g1e2ex2);
        g1e2.addFile(g1e2data);

        //Setting Exercise 1 of Group 2 files
        g2e1.addFile(g2e1ex1);
        g2e1.addFile(g2e1data);

        //Setting Exercise 2 of Group 2 files
        g2e2.addFile(g2e2ex2);
        g2e2.addFile(g2e2data);

        host.addFolder(oop);

        m.initializeUI();
        m.renderButton(host);
    }
}
