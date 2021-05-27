module inventorymanagementsystem {
    requires javafx.controls;
    requires javafx.fxml;

    opens dev.nicholaskoldys.inventorymanagementsystem to javafx.fxml;
    exports dev.nicholaskoldys.inventorymanagementsystem;
}