package util;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class PushNotification {

    public static void toastError(String title, String errorMessage) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(errorMessage)
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);
        Platform.runLater(notificationBuilder::showError);
    } // end of toastError

    public static void toastSuccess(String title, String successMessage) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(successMessage)
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);
        Platform.runLater(notificationBuilder::showInformation);
    } // end of toastSuccess

    public static void toastWarn(String title, String warningMessage) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(warningMessage)
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT);
        Platform.runLater(notificationBuilder::showWarning);
    } // end of toastWarn
} // end of PushNotification class
