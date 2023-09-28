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

    public FileDownloader(String url) throws ClassNotFoundException, IOException{
        this.siteUrl = url;
        this.downloadHTML(siteUrl);
    }

    public String getFilePath(){
        return this.filePath;
    }

    public String getResponseContent(String siteUrl) throws ClassNotFoundException{
        StringBuilder response = new StringBuilder();

        try (Socket socket = new Socket(siteUrl, 80)) {
 
            OutputStream output = socket.getOutputStream();

            StringBuilder str = new StringBuilder();
 
            str.append("GET / HTTP/1.1\r\nHost: ");
            str.append(siteUrl);
            str.append("\r\nUser-Agent: ChromeOficial\r\n");
            str.append("Accept: */*\r\n");
            str.append("\r\n");
 
            output.write(str.toString().getBytes());
            output.flush();

            InputStream input = socket.getInputStream();
 
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
 
            String line;
            int lineBreakCounter = 0;
            int endDoc = 0;

            while ((line = reader.readLine()) != null) {
                lineBreakCounter += line.contains("<!DOCTYPE html>") ? 1 : 0;

                if(lineBreakCounter > 0 && endDoc < 1){
                    response.append(line);
                }

                endDoc += line.contains("</html>") ? 1 : 0;
            }

            socket.close();
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
        

        return response.toString();
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
