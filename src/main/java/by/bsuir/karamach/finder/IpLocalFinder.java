package by.bsuir.karamach.finder;

import by.bsuir.karamach.util.HostChecker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class IpLocalFinder implements IpFinder {

    private static final Logger logger = LogManager.getLogger();

    private static IpLocalFinder instance = new IpLocalFinder();
    
    private volatile List<String> hostNameList;

    private IpLocalFinder() {
    }

    public static IpLocalFinder getInstance() {
        return instance;
    }

    /**
     * @param ipAddresses 3 byte mask
     * @return List of all ip-addresses in local network
     */
    @Override
    public List<String> getHostNames(List<String> ipAddresses) {

        CountDownLatch countDownLatch = new CountDownLatch(ipAddresses.size());

        hostNameList = new ArrayList<>();

        for (String ipAddress : ipAddresses) {
            HostChecker hostChecker = new HostChecker(ipAddress, countDownLatch);

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
