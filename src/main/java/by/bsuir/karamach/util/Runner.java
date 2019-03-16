package by.bsuir.karamach.util;


import org.apache.commons.net.util.SubnetUtils;

import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;

import static by.bsuir.karamach.informer.InfoProvider.getCurrentComputerInfo;
import static by.bsuir.karamach.informer.InfoProvider.getLocalDeviceInfo;

public class Runner {

    public static final String CIDR_NOTATION_SPLITTER = "/";
    public static final int FIRST_ELEMENT_INDEX = 0;

    public static void main(String[] args) throws Exception {

        StringBuilder myComputerInfo = getCurrentComputerInfo();
        System.out.println(myComputerInfo);


        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();

        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();


            if (!networkInterface.getInterfaceAddresses().isEmpty()) {
                InterfaceAddress ipa4Address = networkInterface.getInterfaceAddresses().get(FIRST_ELEMENT_INDEX);

                String hostAddress = ipa4Address.getAddress().getHostAddress();

                if (!hostAddress.equals("127.0.0.1")) {
                    short networkPrefixLength = ipa4Address.getNetworkPrefixLength();

                    SubnetUtils subnetUtils = new SubnetUtils(hostAddress + CIDR_NOTATION_SPLITTER + networkPrefixLength);
                    StringBuilder result = getLocalDeviceInfo(Arrays.asList(subnetUtils.getInfo().getAllAddresses()));

                    System.out.println(result);
                }
            }

        }


    }


}
