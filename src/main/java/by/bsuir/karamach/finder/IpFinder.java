package by.bsuir.karamach.finder;

import java.io.IOException;
import java.util.List;

public interface IpFinder {
    List<String> getHostNames(List<String> ipAddresses) throws IOException;
}
