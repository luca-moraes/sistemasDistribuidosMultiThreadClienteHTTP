public class Main{
    public static void main(String args[]) throws ClassNotFoundException{
        String siteUrl = "www.ufba.br";

        FileDownloader fDownloader = new FileDownloader(siteUrl);

        // JsoupParser jParser = new JsoupParser(fDownloader.getFilePath());

        // HtmlParser hParser = new HtmlParser(fDownloader.getFilePath());

        // fDownloader.downloadImgs(jParser.getImageList());
        // fDownloader.downloadLinks(jParser.getLinkList());
        // fDownloader.downloadScripts(jParser.getScriptList());

        System.out.println(fDownloader.getResponseContent(siteUrl));
    }
}