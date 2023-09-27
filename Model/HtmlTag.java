public class HtmlTag{
    private String tag;
    private String fileUrl;

    public String getTag(){
        return this.tag;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public HtmlTag(String tag, String fileUrl) {
        this.tag = tag;
        this.fileUrl = fileUrl;
    }
}