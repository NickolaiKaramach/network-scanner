package by.bsuir.karamach.informer;

import by.bsuir.karamach.finder.IpLocalFinder;
import by.bsuir.karamach.process.ProcessRunner;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;

public final class InfoProvider {
    private static final String ARP_CONSOLE_COMMAND = "arp ";
    private static final String HEX_FORMAT = "%02X%s";
    private static final String EMPTY_STRING = "";
    private static final String MINUS = "-";
    private static final String DEVICES_SPLITTER = "\n**********************************************************";
    private static final String TOOLTIP = "host_name (ip_address) at MAC_address on network_interface";

    private InfoProvider() {
    }

    public static StringBuilder getLocalDeviceInfo(List<String> subnet) throws IOException {
        StringBuilder result = new StringBuilder();

        List<String> hostNames = IpLocalFinder.getInstance().getHostNames(subnet);


        if (!hostNames.isEmpty()) {
            StringBuilder otherDevicesInfo = getNetworkInfo(hostNames);
            result.append(otherDevicesInfo);
        }
        return result;
    }

    public static StringBuilder getCurrentComputerInfo() throws UnknownHostException, SocketException {
        InetAddress localHost = InetAddress.getLocalHost();
        byte[] macBytes = NetworkInterface.getByInetAddress(localHost).getHardwareAddress();

        String hostName = localHost.getHostName();
        String macAddress = getFormatMacAddress(macBytes).toString();

        StringBuilder myComputerInfo = new StringBuilder();

        myComputerInfo.append("My computer:")
                .append("\nHost name: ")
                .append(hostName)
                .append("\nMac: ")
                .append(macAddress)
                .append("\n")
                .append("Others:");
        return myComputerInfo;
    }

    private static StringBuilder getNetworkInfo(List<String> hostNames) throws IOException {
        StringBuilder otherDevicesInfo = new StringBuilder();
        for (String currentName : hostNames) {
            otherDevicesInfo//.append(DEVICES_SPLITTER)
                    //.append("\n")
                    //.append(TOOLTIP)
                    //.append("\n\n")
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
}
