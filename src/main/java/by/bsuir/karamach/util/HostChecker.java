package by.bsuir.karamach.util;

import by.bsuir.karamach.finder.IpLocalFinder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.util.concurrent.CountDownLatch;

public class HostChecker implements Runnable {

    private static final Logger logger = LogManager.getLogger();

    private static final String IS_REACHABLE_HOST = " is reachable";
    private static final int TIMEOUT = 3000;

    private String host;
    private CountDownLatch latch;

    public HostChecker(String host, CountDownLatch latch) {
        this.host = host;
        this.latch = latch;
    }

    @Override
    public void run() {

        try {
            InetAddress currentInetAddress = InetAddress.getByName(host);

            logger.info("Started: " + host);


            if (currentInetAddress.isReachable(TIMEOUT)) {

                logger.info(host + IS_REACHABLE_HOST);

                String hostName = currentInetAddress.getHostName();

                logger.info(hostName);

                IpLocalFinder.getInstance().addHostNameToList(hostName);

            }

        } catch (IOException e) {

            logger.error(e);

        } finally {

            latch.countDown();

        }

    }
}
