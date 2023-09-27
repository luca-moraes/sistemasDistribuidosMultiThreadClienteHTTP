import java.util.ArrayList;
import java.util.List;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HtmlParser {
    private List<HtmlTag> imageTags = new ArrayList<>();
    private List<HtmlTag> linkTags = new ArrayList<>();
    private List<HtmlTag> scriptTags = new ArrayList<>();
    private File htmlFile;

    public HtmlParser(String filePath){
        this.htmlFile = new File(filePath);
        this.parseTagsImg();
        this.parseTagsLink();
        this.parseTagsScript();
    }

    private void parseTagsImg() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.htmlFile));
            String line;

            Pattern imgPattern = Pattern.compile("<img[^>]*src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");

            while ((line = br.readLine()) != null) {
                Matcher imgMatcher = imgPattern.matcher(line);

                while (imgMatcher.find()) {
                    String src = imgMatcher.group(1);
                    imageTags.add(new HtmlTag("img", src));
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseTagsScript() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.htmlFile));
            String line;

            Pattern scriptPattern = Pattern.compile("<script[^>]*src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");

            while ((line = br.readLine()) != null) {
                Matcher imgMatcher = scriptPattern.matcher(line);

                while (imgMatcher.find()) {
                    String src = imgMatcher.group(1);
                    imageTags.add(new HtmlTag("img", src));
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseTagsLink() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(this.htmlFile));
            String line;

            Pattern linkPattern = Pattern.compile("<link[^>]*href\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");

            while ((line = br.readLine()) != null) {
                Matcher imgMatcher = linkPattern.matcher(line);

                while (imgMatcher.find()) {
                    String src = imgMatcher.group(1);
                    imageTags.add(new HtmlTag("img", src));
                }
            }

            br.close();
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
