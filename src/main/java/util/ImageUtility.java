package util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class ImageUtility {

    private ImageUtility() {}

    public static byte[] getImageBytes(String filename) throws IOException {
        File imageFile = new File("src/main/resources/productimages/" + filename);
        return Files.readAllBytes(imageFile.toPath());
    } // end of getImageBytes

    /**
     * Copies an image file from the specified absolute path to a destination directory.
     * @param absolutePath the absolute path of the source image file
     * @param newFileName the new filename for the copied image file
     * @return the filename with the extension of the copied image file.
     */
    public static String copyImage(String absolutePath, String newFileName) {
        String resourceDirectory = "src/main/resources/productimages/";

        File file = new File(absolutePath);
        if (file.exists()) {
            try {
                Path sourcePath = file.toPath();
                Path destinationPath = Path.of(resourceDirectory + newFileName);

                Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

                return destinationPath.toString();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.err.println("File does not exist: " + absolutePath);
        }
        return null;
    } // end of copyImage

    /**
     * Deletes an image file from the resource folder
     * @param fileName the name of the image file to be deleted
     */
    public static void deleteImage(String fileName) {
        File imageFile = new File("src/main/resources/productimages/" + fileName);

        if (imageFile.exists()) {
            if (imageFile.delete()) {
                System.out.println("Image deleted successfully");
            } else {
                System.out.println("Failed to delete the image");
            }
        }
    }
} // end of FileCreator class
