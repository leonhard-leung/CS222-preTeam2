package server.model.inventory;

import shared.Food;
import util.ImageUtility;

import java.io.IOException;

public class FoodEditDetailsPopupModel {
    private Food food;

    public void setFood(Food food) {
        this.food = food;
    }

    public Food getFood() {
        return food;
    }

    public Object[] processImageChosen(String absolutePath) {
        String extension = absolutePath.substring(absolutePath.lastIndexOf('.'));
        String copiedImagePath = ImageUtility.copyImage(absolutePath, food.getName() + extension);

        String imageFileName = food.getImageName();
        ImageUtility.deleteImage(imageFileName);

        Object[] image;
        try {
            assert copiedImagePath != null;
            image = new Object[]{food.getName() + extension, ImageUtility.getImageBytes(copiedImagePath.substring(copiedImagePath.lastIndexOf('\\') + 1))};
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return image;
    } // end of processSerializableImage
} // end of FoodEditDetailsPopupModel
