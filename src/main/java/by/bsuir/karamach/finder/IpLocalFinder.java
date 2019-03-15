package by.bsuir.karamach.finder;

import by.bsuir.karamach.HostChecker;

import java.util.ArrayList;
import java.util.List;

public class IpLocalFinder implements IpFinder {
    public static final int TIMEOUT = 1000;
    private static final String DOT_SPLITTER = ".";
    private static final int MAX_IP_RANGE = 255;
    private static IpLocalFinder instance = new IpLocalFinder();
    private volatile List<String> hostNameList;

    private IpLocalFinder() {
    }

    public static IpLocalFinder getInstance() {
        return instance;
    }

    /**
     * @param subnetMask 3 byte mask
     * @return List of all ip-addresses in local network
     */
    @Override
    public List<String> getAllIps(String subnetMask) {

        hostNameList = new ArrayList<String>();

        for (int i = 1; i < MAX_IP_RANGE; i++) {

            String host = subnetMask + DOT_SPLITTER + i;

            HostChecker hostChecker = new HostChecker(host);

            Thread thread = new Thread(hostChecker);

            thread.run();

//            InetAddress currentInetAddress = InetAddress.getByName(host);
//
//            if (currentInetAddress.isReachable(TIMEOUT)) {
//
//                logger.info(host + IS_REACHABLE_HOST);
//
//                String hostName = currentInetAddress.getHostName();
//
//
//                logger.info(hostName);
//
//
//                hostNameList.add(hostName);
//            }

        }

        return hostNameList;
    }

    public synchronized void addHostNameToList(String hostName) {
        hostNameList.add(hostName);
    }
}
