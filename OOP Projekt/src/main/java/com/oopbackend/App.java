package com.oopbackend;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;


/**
 * Hello world!
 */
public final class App {
    private App() {
    }

       public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost",8000), 0);
        server.createContext("/host", new GetExercise());
        server.setExecutor(null); // creates a default executor
        server.start();
    }


    /*
    * Handlers
    * */
    static class GetExercise implements HttpHandler{

        @Override
        public void handle(HttpExchange exchange) throws IOException{
            // POST -> create
            // GET -> Read
            // PUT -> Update
            // DELET -> delete
            // crud
            // example request: http://localhost:8000/host/OOP/Group1/Exercise01/exercise01.txt

            if("GET".equals(exchange.getRequestMethod())){
                // Simulates a download process. So it moves selected exercise from "host" to "local"
                // Also it gives a message to the user if the download was successful
                OutputStream outputStream = exchange.getResponseBody();
                String responseToUser = "";



                String filePath = exchange.getRequestURI().getPath();;

                try {
                    // Get the input stream for the file
                    InputStream inputStream = App.class.getResourceAsStream(filePath);
                    if (inputStream != null) {
                        // Specify the destination folder where you want to copy the file
                        Path destinationFolder = Paths.get("./src/main/resources/local");

                        // Create the destination folder if it doesn't exist
                        if (!Files.exists(destinationFolder)) {
                            Files.createDirectories(destinationFolder);
                        }

                        // Copy the file to the destination folder
                        Path destinationFile = destinationFolder.resolve(Paths.get(filePath).getFileName());
                        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);

                        responseToUser = "File copied successfully to: " + destinationFolder;
                        System.out.println("File copied successfully to: " + destinationFolder);
                        System.out.println(destinationFolder);
                    } else {
                        responseToUser = "File not found in resources.";
                        System.err.println("File not found in resources.");
                    }
                } catch (IOException e) {
                    responseToUser = "Failed to copy file: " + e.getMessage();
                    System.err.println("Failed to copy file: " + e.getMessage());
                }



                exchange.sendResponseHeaders(200, responseToUser.length());

                outputStream.write(responseToUser.getBytes());
                outputStream.flush();
                outputStream.close();
            }

        }
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "This is the response";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
