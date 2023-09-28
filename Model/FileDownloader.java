import java.io.*;
import java.net.HttpURLConnection;
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
        downloadHTML(siteUrl);
    }

    public String getFilePath(){
        return this.filePath;
    }

    private void downloadHTML(String siteUrl) {
        try (Socket socket = new Socket(siteUrl, 80)) {
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter out = new PrintWriter(outputStream, true);
            out.println("GET / HTTP/1.1");
            out.println("Host: " + siteUrl);
            out.println();

            InputStream inputStream = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine).append("\n");
            }

            String processedHtml = response.toString();

            System.out.println(processedHtml);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            URL url = new URL(siteUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder content = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    content.append(line);
                    content.append(System.lineSeparator());
                }
                in.close();

                this.filePath = "./files/index.html";
                File file = new File(this.filePath);

                FileWriter writer = new FileWriter(file);
                writer.write(content.toString());
                writer.close();
            } else {
                System.exit(responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
