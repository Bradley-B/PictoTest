import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class NetworkHelper {
    
	private String[] getActiveIPv4Addresses() throws SocketException {
    	ArrayList<String> addresses = new ArrayList<>();
    	Enumeration<NetworkInterface> nEnumeration = NetworkInterface.getNetworkInterfaces();
    	while(nEnumeration.hasMoreElements()) {
    		NetworkInterface nInterface = nEnumeration.nextElement();
    		if(!nInterface.isLoopback() && !nInterface.isVirtual() && nInterface.isUp()) {
    			for (InterfaceAddress address : nInterface.getInterfaceAddresses()) {
    				if(validateIP(address.getAddress().getHostAddress())) {
    					addresses.add(address.getAddress().getHostAddress());
    				}
    			}    			
    		}
    	}
    	return Arrays.copyOf(addresses.toArray(), addresses.toArray().length, String[].class);
    }
    
	private boolean validateIP(final String ip) {
	    return Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$").matcher(ip).matches();
	}
	
}
