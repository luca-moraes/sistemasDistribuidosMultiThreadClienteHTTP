import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsoupParser {
    private List<HtmlTag> imageTags = new ArrayList<>();
    private List<HtmlTag> linkTags = new ArrayList<>();
    private List<HtmlTag> scriptTags = new ArrayList<>();
    private File htmlFile;

    public JsoupParser(String filePath){
        this.htmlFile = new File(filePath);
        this.parseTags();
    }

    private void parseTags() {
        try {
            Document doc = Jsoup.parse(this.htmlFile, "UTF-8");
            
            Elements imgTags = doc.select("img");
            Elements scriptTags = doc.select("script");
            Elements linkTags = doc.select("link");

            for (Element imgTag : imgTags) {
                String imageUrl = imgTag.attr("src");
                if (!imageUrl.isEmpty()) {
                    HtmlTag imagem = new HtmlTag("img",imageUrl);
                    this.imageTags.add(imagem);
                }
            }

            for (Element srciptTag : scriptTags) {
                String scriptUrl = srciptTag.attr("src");
                if (!scriptUrl.isEmpty()) {
                    HtmlTag script = new HtmlTag("script",scriptUrl);
                    this.linkTags.add(script);
                }
            }

            for (Element linkTag : linkTags) {
                String linkUrl = linkTag.attr("href");
                if (!linkUrl.isEmpty()) {
                    HtmlTag link = new HtmlTag("img",linkUrl);
                    this.scriptTags.add(link);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<HtmlTag> getImageList(){
        return this.imageTags;
    }

    public List<HtmlTag> getLinkList(){
        return this.linkTags;
    }

    public List<HtmlTag> getScriptList(){
        return this.scriptTags;
    }
}
