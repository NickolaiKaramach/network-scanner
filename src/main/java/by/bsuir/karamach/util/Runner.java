package by.bsuir.karamach.util;

import static by.bsuir.karamach.informer.InfoProvider.getCurrentComputerInfo;
import static by.bsuir.karamach.informer.InfoProvider.getLocalDeviceInfo;

public class Runner {

    private static final String SUBNET = "192.168.33";

    public static void main(String[] args) throws Exception {

        StringBuilder myComputerInfo = getCurrentComputerInfo();
        System.out.println(myComputerInfo);


        StringBuilder result = getLocalDeviceInfo(SUBNET);

        System.out.println(result);

    }


}
