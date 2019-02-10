package by.bsuir.karamach.finder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class HostNameLocalFinder implements HostNameFinder {

    private static final int timeout = 10000;

    private static final Logger logger = LogManager.getLogger();
    private static final String DOT_SPLITTER = ".";
    private static final String IS_REACHABLE_HOST = " is reachable";
    private static final int MAX_IP_RANGE = 255;

    /**
     * @param subnetMask 3 byte mask
     * @return List of all ip-addresses in local network
     */
    @Override
    public List<String> getAllHostNames(String subnetMask) throws IOException {

        List<String> hostNameList = new ArrayList<String>();

        for (int i = 1; i < MAX_IP_RANGE; i++) {

            String host = subnetMask + DOT_SPLITTER + i;

            InetAddress currentInetAddress = InetAddress.getByName(host);

            if (currentInetAddress.isReachable(timeout)) {

                logger.info(host + IS_REACHABLE_HOST);

                logger.info(currentInetAddress.getHostName());


                hostNameList.add(host);
            }

        }

        return hostNameList;
    }
}
