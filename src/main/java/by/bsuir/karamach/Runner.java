package by.bsuir.karamach;

import by.bsuir.karamach.finder.IpLocalFinder;
import by.bsuir.karamach.process.ProcessRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.List;

public class Runner {

    private static final String ARP_CONSOLE_COMMAND = "arp ";
    private static final Logger logger = LogManager.getLogger();
    private static final String subnet = "192.168.33";
    private static final String HEX_FORMAT = "%02X%s";
    private static final String EMPTY_STRING = "";
    private static final String MINUS = "-";
    private static final String DEVICES_SPLITTER = "\n**********************************************************";
    private static final String TOOLTIP = "host_name (ip_address) at MAC_address on network_interface";

    public static void main(String[] args) throws Exception {

        InetAddress localHost = InetAddress.getLocalHost();
        byte[] macBytes = NetworkInterface.getByInetAddress(localHost).getHardwareAddress();

        String hostName = localHost.getHostName();
        String macAddress = getFormatMacAddress(macBytes).toString();

        StringBuilder result = new StringBuilder();

        result.append("My computer:")
                .append("\nHost name: ")
                .append(hostName)
                .append("\nMac: ")
                .append(macAddress)
                .append("\n")
                .append("Others:");


        IpLocalFinder hostFinder = new IpLocalFinder();
        List<String> hostNames = hostFinder.getAllIps(subnet);

        StringBuilder otherDevicesInfo = getNetworkInfo(hostNames);
        result.append(otherDevicesInfo);

        System.out.println(result);

    }

    private static StringBuilder getNetworkInfo(List<String> hostNames) throws IOException {
        StringBuilder otherDevicesInfo = new StringBuilder();
        for (String currentName : hostNames) {
            otherDevicesInfo.append(DEVICES_SPLITTER)
                    .append("\n")
                    .append(TOOLTIP)
                    .append("\n\n")
                    .append(ProcessRunner.executeCommand(ARP_CONSOLE_COMMAND + currentName));
        }
        return otherDevicesInfo;
    }

    private static StringBuilder getFormatMacAddress(byte[] macBytes) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < macBytes.length; i++) {
            sb.append(String.format(HEX_FORMAT, macBytes[i], (i < macBytes.length - 1) ? MINUS : EMPTY_STRING));
        }

        return sb;
    }


//        try {
//            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
//
//            while(networkInterfaces.hasMoreElements()) {
//
//                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
//                Enumeration inetAddresses = networkInterface.getInetAddresses();
//
//                logger.info("***********************************************");
//                logger.info("Host name: " + networkInterface.getDisplayName());
//
//                while (inetAddresses.hasMoreElements()) {
//
//                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
//
//                    logger.info("Host address: " + inetAddress.getHostAddress());
//                    logger.info(inetAddress.getHostName());
//
//                }
//
//                if(networkInterface.getHardwareAddress() != null) {
//                    byte[] mac = networkInterface.getHardwareAddress();
//
//                    StringBuilder sb = new StringBuilder();
//                    for (int i = 0; i < mac.length; i++) {
//                        sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
//                    }
//
//                    logger.info("Mac: " + sb.toString());
//                }
//
//                logger.info("***********************************************\n");
//
//
//            }
//        } catch (SocketException ex) {
//            logger.info(ex.getMessage());
//        }
//
//    }
}
