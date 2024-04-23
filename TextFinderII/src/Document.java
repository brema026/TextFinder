import java.text.SimpleDateFormat;
import java.util.Date;

public class Document {
    private final String path;
    private final DocumentType type;
    private final Long date;
    private final Long size;
    private final String content;

    public Document(String path, DocumentType type,
                    Long date, Long size, String content) {
        this.path = path;
        this.type = type;
        this.date = date;
        this.size = size;
        this.content = content;
    }

    /**
     * Gets the document path.
     *
     * @return The document path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Gets the document type.
     *
     * @return The document type.
     */
    public DocumentType getType() {
        return type;
    }

    /**
     * Gets the document last modified date in a readable format.
     *
     * @return The document last modified date in a readable format.
     */
    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(date));
    }

    /**
     * Gets the document size in bytes.
     *
     * @return The document size.
     */
    public Long getSize() {
        return size;
    }

    /**
     * Gets the document content.
     *
     * @return The document content.
     */
    public String getContent() {
        return content;
    }
}
