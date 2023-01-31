module com.example.rxchatfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires io.reactivex.rxjava3;


    opens com.example.rxchatfx to javafx.fxml;
    exports com.example.rxchatfx;
}