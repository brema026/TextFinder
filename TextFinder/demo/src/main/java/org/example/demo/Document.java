package org.example.demo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents a document with its associated metadata and content.
 */
public class Document {
    private final String path;
    private final DocumentType type;
    private final String fileName;
    private final Long date;
    private final Long size;
    private final String content;

    /**
     * Document object constructor.
     *
     * @param path    The path to the document.
     * @param type    The type of the document.
     * @param date    The last modified date of the document in milliseconds since the epoch.
     * @param size    The size of the document in bytes.
     * @param content The content of the document.
     */
    public Document(String path, DocumentType type,
                    String fileName, Long date, Long size,
                    String content) {
        this.path = path;
        this.type = type;
        this.fileName = fileName;
        this.date = date;
        this.size = size;
        this.content = content;
    }

    public String getPath() {
        return path;
    }

    public String getFileName() {
        return fileName;
    }

    public DocumentType getType() {
        return type;
    }

    public Long getDate() {
        return date != null ? date : 0L;
    }

    public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date(date));
    }

    public Long getSize() {
        return size != null ? size : 0L;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "Document{" +
                "path='" + path + '\'' +
                ", type=" + type +
                ", fileName='" + fileName + '\'' +
                ", date=" + date +
                ", size=" + size +
                ", content='" + content + '\'' +
                '}';
    }
}
