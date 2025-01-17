package project.board.common.constant;

import java.util.HashSet;
import java.util.Set;

public class FileConstant {
    public static final String WIN_FILE_DIR = "C:/Users/user/Downloads/";
    public static final String MAC_FILE_DIR = "/tmp/";

    public static final String IMAGE_FILE = "image";
    public static final String ATTACH_FILE = "attach";
    public static final Set<String> IMAGE_EXTENTIONS = new HashSet<>();

    static {
        IMAGE_EXTENTIONS.add("jpg");
        IMAGE_EXTENTIONS.add("jpeg");
        IMAGE_EXTENTIONS.add("png");
        IMAGE_EXTENTIONS.add("gif");
        IMAGE_EXTENTIONS.add("bmp");
        IMAGE_EXTENTIONS.add("tiff");
        IMAGE_EXTENTIONS.add("webp");
    }

}
