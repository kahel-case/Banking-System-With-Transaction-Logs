package sys.collection;

import javafx.application.Application;
import javafx.scene.image.Image;
import sys.main.Main;

import java.util.Objects;

public class ImageCollection {
    public static final Image frameIcon = new Image(Objects.requireNonNull(ImageCollection.class.getResourceAsStream("/sys/images/Logo/Logo_FrameIcon.png")));
}
