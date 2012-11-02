import java.io.File;
import javax.swing.filechooser.*;

public class ImageFilter extends FileFilter {

    private final String jpeg = "jpeg";
    private final String jpg = "jpg";
    private final String gif = "gif";
    private final String tiff = "tiff";
    private final String tif = "tif";
    private final String png = "png";
    private final String bmp = "bmp";
	
    //aceita todos os diretorios e todos os arquivos gif, jpg, tiff, png ou bmp.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String ext = getExtension(f);
        if (ext != null) {
            if (ext.equals(tiff) ||
                ext.equals(tif) ||
                ext.equals(gif) ||
                ext.equals(jpeg) ||
                ext.equals(jpg) ||
                ext.equals(png) ||
                ext.equals(bmp)) {
                    return true;
            } else {
                return false;
            }
        }
        return false;
    }

    //retorna a extensao de um arquivo
    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
    }

	@Override
	public String getDescription() {
		return "Somente imagens";
	}
}