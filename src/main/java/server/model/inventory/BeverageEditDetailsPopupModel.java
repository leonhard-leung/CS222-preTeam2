package server.model.inventory;

import shared.Beverage;
import util.ImageUtility;

import java.io.IOException;

public class BeverageEditDetailsPopupModel {
    public Beverage beverage;

    public void setBeverage(Beverage beverage) {
        this.beverage = beverage;
    }

    public Beverage getBeverage() {
        return beverage;
    }

    public Object[] processImageChosen(String absolutePath) {
        String extension = absolutePath.substring(absolutePath.lastIndexOf('.'));
        String copiedImagePath = ImageUtility.copyImage(absolutePath, beverage.getName() + extension);

        String imageFileName = beverage.getImageName();
        ImageUtility.deleteImage(imageFileName);

        Object[] image;
        try {
            assert copiedImagePath != null;
            image = new Object[]{beverage.getName() + extension, ImageUtility.getImageBytes(copiedImagePath.substring(copiedImagePath.lastIndexOf('\\') + 1))};
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    } // end of processSerializableImage
} // end of BeverageEditDetailsPopupModel
