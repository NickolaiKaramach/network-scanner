package by.bsuir.karamach;

import by.bsuir.karamach.finder.IpLocalFinder;
import by.bsuir.karamach.process.ProcessRunner;

import java.util.List;

public class Runner {

    private static final String ARP_CONSOLE_COMMAND = "arp ";

    public static void main(String[] args) throws Exception {

        String subnet = "192.168.33";

        IpLocalFinder hostFinder = new IpLocalFinder();
        List<String> hostNames = hostFinder.getAllIps(subnet);

        System.out.println(hostNames);


        for (String currentName : hostNames) {
            System.out.println("******************************************************************************");
            System.out.println(ProcessRunner.executeCommand(ARP_CONSOLE_COMMAND + currentName));
            System.out.println("******************************************************************************");
        }
    }


//        try {
//            Enumeration networkInterfaces = NetworkInterface.getNetworkInterfaces();
//
//            while(networkInterfaces.hasMoreElements()) {
//
//                NetworkInterface networkInterface = (NetworkInterface) networkInterfaces.nextElement();
//                Enumeration inetAddresses = networkInterface.getInetAddresses();
//
//                System.out.println("***********************************************");
//                System.out.println("Host name: " + networkInterface.getDisplayName());
//
//                while (inetAddresses.hasMoreElements()) {
//
//                    InetAddress inetAddress = (InetAddress) inetAddresses.nextElement();
//
//                    System.out.println("Host address: " + inetAddress.getHostAddress());
//                    System.out.println(inetAddress.getHostName());
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
//                    System.out.println("Mac: " + sb.toString());
//                }
//
//                System.out.println("***********************************************\n");
//
//
//            }
//        } catch (SocketException ex) {
//            System.out.println(ex.getMessage());
//        }
//
//    }
}
