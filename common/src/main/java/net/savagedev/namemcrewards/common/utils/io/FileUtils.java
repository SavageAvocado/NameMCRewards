package net.savagedev.namemcrewards.common.utils.io;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.Optional;

public class FileUtils {
    private FileUtils() {
        throw new UnsupportedOperationException("This class cannot be instantiated!");
    }

    public static Optional<InputStream> getStream(Path path) {
        URL resourceStream = FileUtils.class.getClassLoader().getResource(path.getFileName().toString());
        if (resourceStream != null) {
            try {
                URLConnection connection = resourceStream.openConnection();
                connection.setUseCaches(false);

                return Optional.ofNullable(connection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Optional.empty();
    }
}
