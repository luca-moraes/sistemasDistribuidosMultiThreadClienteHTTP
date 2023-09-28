import java.io.*;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FileDownloader {
    private String siteUrl;
    private String filePath;

    public FileDownloader(String url){
        this.siteUrl = url;
        // downloadHTML(siteUrl);
    }

    public String getFilePath(){
        return this.filePath;
    }

    public String getResponseContent(String siteUrl) throws ClassNotFoundException{
        // StringBuilder response = new StringBuilder();

        // try (Socket socket = new Socket(siteUrl, 80)) {
        //     // Enviar a solicitação ao servidor para "/pagina.html"
        //     OutputStream outputStream = socket.getOutputStream();
        //     PrintWriter out = new PrintWriter(outputStream, true);

        //     out.println("GET / HTTP/1.1");
        //     out.println("Host: " + siteUrl);
        //     out.println("User-Agent: curl/7.81.0");
        //     out.println("Accept: */*");  
        //     out.println();

        //     // Receber a resposta do servidor
        //     InputStream inputStream = socket.getInputStream();
        //     BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

        //     String inputLine;
        //     StringBuilder response = new StringBuilder();

        //     while ((inputLine = in.readLine()) != null) {
        //         response.append(inputLine).append("\n");
        //     }

        //     // Processar a resposta (HTML modificado)
        //     String processedHtml = response.toString();

        //     // Exibir o HTML processado
        //     System.out.println(processedHtml);
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // try (Socket socket = new Socket(siteUrl, 80);
        //     PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        //     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

        //     out.println("GET / HTTP/1.1");
        //     out.println("Host: " + siteUrl);
        //     out.println("User-Agent: curl/7.81.0");
        //     out.println("Accept: */*");
        //     out.println();

        //     String inputLine;
        //     while ((inputLine = in.readLine()) != null) {
        //         response.append(inputLine);
        //         response.append(System.lineSeparator());
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        // return response.toString();

        // try{
        //     InetAddress host = InetAddress.getByName(siteUrl);

        //     var socket = new Socket(host, 80);
            
        //     ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        //     ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());


        //     System.out.println("Sending request to Socket Server");

        //     oos.writeObject("GET / HTTP/1.1\n");
        //     oos.writeObject("Host: " + host + "\n");
        //     // oos.writeObject("User-Agent: curl/7.81.0\n");
        //     // oos.writeObject("Accept: */*\n");
        //     oos.writeObject("\n");
        //     oos.flush();
        //     oos.close();

        //     //read the server response message
        //     String message = ois.readObject().toString();

        //     System.out.println("Message: " + message);

        //     ois.close();

        // }catch (IOException e) {
        //     e.printStackTrace();
        // }

        // try {
        //     InetAddress host = InetAddress.getByName(siteUrl);
        //     Socket socket = new Socket(host, 80);

        //     OutputStream os = socket.getOutputStream();

        //     StringBuilder str = new StringBuilder();
        //     str.append("GET / HTTP/1.1\nHost: ")
        //     .append(siteUrl)
        //     .append("\r\n");

        //     os.write(str.toString().getBytes());
        //     os.flush();

        //     // os.close();

        //     InputStream in = socket.getInputStream();

        //     byte[] bt = in.readAllBytes();

        //     // String line;
        //     // StringBuilder response = new StringBuilder();

        //     // while ((line = ) != null) {
        //     //     response.append(line).append("\n");
        //     // }

        //     System.out.println("Resposta da página:");

        //     System.out.println(bt.toString());

        //     socket.close();
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }

        try (Socket socket = new Socket(siteUrl, 80)) {
 
            OutputStream output = socket.getOutputStream();

            StringBuilder str = new StringBuilder();
 
            str.append("GET / HTTP/1.1\nHost: ");
            str.append(siteUrl);
            str.append("\r\n");
 
            output.write(str.toString().getBytes());

            InputStream input = socket.getInputStream();
 
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            String line;
 
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }

        return null;
    }

    private void downloadHTML(String siteUrl) throws ClassNotFoundException, IOException {
        this.filePath = "./files/index.html";
        File file = new File(this.filePath);

        FileWriter writer = new FileWriter(file);

        String content = this.getResponseContent(siteUrl);

        writer.write(content.toString());
        writer.close();
    }

    public void downloadImgs(List<HtmlTag> list){
        for (HtmlTag tag : list) {
            Thread downloadThread = new Thread(() -> {
                try {
                    URL url = new URL(tag.getFileUrl());

                    String fileName = tag.getFileUrl()
                        .substring(
                            tag.getFileUrl()
                            .lastIndexOf("/") + 1
                        );

                    File imageFile = new File("./files/images/", fileName);

                    try (ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                        FileOutputStream fos = new FileOutputStream(imageFile)) {
                        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            downloadThread.start();
        }
    }

    public void downloadLinks(List<HtmlTag> list){
        for (HtmlTag tag : list) {
            Thread downloadThread = new Thread(() -> {
                try {
                    URL url = new URL(tag.getFileUrl());

                    String fileName = tag.getFileUrl()
                        .substring(
                            tag.getFileUrl()
                            .lastIndexOf("/") + 1
                        );

                    File linkFile = new File("./files/links/", fileName);

                    try (ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                        FileOutputStream fos = new FileOutputStream(linkFile)) {
                        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            downloadThread.start();
        }
    }

    public void downloadScripts(List<HtmlTag> list){
        for (HtmlTag tag : list) {
            Thread downloadThread = new Thread(() -> {
                try {
                    URL url = new URL(tag.getFileUrl());

                    String fileName = tag.getFileUrl()
                        .substring(
                            tag.getFileUrl()
                            .lastIndexOf("/") + 1
                        );

                    File scriptFile = new File("./files/scripts/", fileName);

                    try (ReadableByteChannel rbc = Channels.newChannel(url.openStream());
                        FileOutputStream fos = new FileOutputStream(scriptFile)) {
                        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

            downloadThread.start();
        }
    }
}
