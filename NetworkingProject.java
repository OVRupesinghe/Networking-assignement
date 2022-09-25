package networkingproject;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class NetworkingProject {

    
    public static void main(String[] args) throws Exception {
       //start receiving messages
        try (ServerSocket serverSocket = new ServerSocket(8080)){ //create a socket and open up a server in the local machine
            System.out.println("Server started.\nListening for messages");
            while (true) {                
                //Handle incoming messages
                try(Socket client = serverSocket.accept()) { //accepting messagse from browser
                    System.out.println("Debug: got new message "+ client.toString());
                    InputStreamReader isr = new InputStreamReader(client.getInputStream()); //get all the messages or in other words the input stream from the browser
                    
                    
                   //Read the first request from the client
                    BufferedReader br =new BufferedReader(isr);
                    StringBuilder requestBuilder = new StringBuilder();
                    String line; //temporary variable that holds one line at a time of our message
                    line = br.readLine();
                    
                    while(!(line==null || line.trim().isEmpty())){ //THe http reponse always ends with a blank line so we get the input stream
                        requestBuilder.append(line).append("\r\n");
                        line = br.readLine();
                    }
                    System.out.println("--REQUEST--");
                    System.out.println(requestBuilder);
                    //Decide how we'd like to respond to the browser
                    //just send back a simple "Hello World"
                    //OutputStream clientOutput =  client.getOutputStream(); //request ability to send responses
                   // clientOutput.write(("HTTP/1/1 200 OK\r\n".getBytes()));//Send responses as BYTES
                   // clientOutput.write(("\r\n".getBytes()));//Send a blank line to signal the end of the HTTp response
                   // clientOutput.write(("Hello World").getBytes()); //actual content we want to publish
                   // clientOutput.flush();
                    
                    //send back an image
                    
                    //load the image from the file system
                    FileInputStream image = new FileInputStream("D:\\NetBeansProjects\\NetworkingProject\\src\\networkingproject\\fav.jpg");
                    //turn the image into bytes
                    
                    //set the contentType
                    OutputStream clientOutput =  client.getOutputStream(); //request ability to send responses
                    clientOutput.write(("HTTP/1/1 200 OK\r\n".getBytes()));//Send responses as BYTES
                    clientOutput.write(("\r\n".getBytes()));//Send a blank line to signal the end of the HTTp response
                    clientOutput.write(image.read()); //actual content we want to publish
                    clientOutput.flush();
                    
                    
                    
                    
                    
                    client.close();
                } 
            }
        } 
    }
    
}
