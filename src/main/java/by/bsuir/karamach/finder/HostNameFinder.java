package by.bsuir.karamach.finder;

import java.io.IOException;
import java.util.List;

public interface HostNameFinder {
    List<String> getAllHostNames(String subnetMask) throws IOException;
}
