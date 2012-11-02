import java.io.File;
import javax.swing.filechooser.*;

public class MapFilter extends FileFilter {

    private final String map = "map";
	
    //aceita todos os diretorios e todos os arquivos map.
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String ext = getExtension(f);
        if (ext != null) {
            if (ext.equals(map)) {
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
		return "Somente mapas";
	}
}