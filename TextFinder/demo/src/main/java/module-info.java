module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires org.apache.pdfbox;
    requires org.apache.poi.ooxml;
    requires java.desktop;

    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
    exports dataStructures;
}