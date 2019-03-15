package by.bsuir.karamach.finder;

import by.bsuir.karamach.HostChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class IpLocalFinder implements IpFinder {

    private static final Logger logger = LogManager.getLogger();

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

        CountDownLatch countDownLatch = new CountDownLatch(MAX_IP_RANGE);

        hostNameList = new ArrayList<>();

        for (int i = 1; i <= MAX_IP_RANGE; i++) {

            String host = subnetMask + DOT_SPLITTER + i;

            HostChecker hostChecker = new HostChecker(host, countDownLatch);

            Thread thread = new Thread(hostChecker);

            thread.start();

        }

        try {

            countDownLatch.await();

        } catch (InterruptedException e) {

            logger.error("Normal running was interrupted!", e);

        }

        return hostNameList;
    }

    public synchronized void addHostNameToList(String hostName) {
        hostNameList.add(hostName);
    }
}
