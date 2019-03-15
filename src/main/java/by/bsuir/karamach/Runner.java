package by.bsuir.karamach;

import static by.bsuir.karamach.informer.InfoProvider.getCurrentComputerInfo;
import static by.bsuir.karamach.informer.InfoProvider.getLocalDeviceInfo;

public class Runner {

    private static final String SUBNET = "10.6.103";

    public static void main(String[] args) throws Exception {

        StringBuilder myComputerInfo = getCurrentComputerInfo();
        System.out.println(myComputerInfo);


        StringBuilder result = getLocalDeviceInfo(SUBNET);

        System.out.println(result);

    }


}
