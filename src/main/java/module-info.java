module com.example.rxchatfx {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.rxchatfx to javafx.fxml;
    exports com.example.rxchatfx;
}