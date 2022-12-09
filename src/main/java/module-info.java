module com.example.lopputuo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lopputuo to javafx.fxml;
    exports com.example.lopputuo;
}