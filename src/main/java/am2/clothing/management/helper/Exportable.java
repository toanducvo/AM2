package am2.clothing.management.helper;

import java.io.IOException;
import java.util.List;

public interface Exportable {
    boolean writeToFile(String fileName, List<?> data) throws IOException;
}
