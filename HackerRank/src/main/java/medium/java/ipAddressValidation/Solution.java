package medium.java.ipAddressValidation;

import static java.util.stream.Collectors.toList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.IntStream;

class Result {
	public static final String IPv4 = "IPv4";
	public static final String IPv6 = "IPv6";
	public static final String NEITHER = "Neither";

	/**
	 * Check if given string is a valid IPv4
	 *
	 * @param ip
	 * @return boolean
	 */
	public static boolean isIpv4(String ip) {
		try {
			// dot isn't allowed in the beginning or end of the IP
			if (ip.startsWith(".") || ip.endsWith(".")) {
				return false;
			}
			String[] ipArray = ip.split("\\.");
			// a valid IP has exactly 4 tokens
			if (ipArray.length != 4) {
				return false;
			}
			// Iterate over each token
			for (int i = 0; i < 4; i++) {
				if (ipArray[i].startsWith("0") && ipArray[i].length() > 1) {
					// Handle octal token
					Integer val = Integer.parseInt(ipArray[i].substring(1, ipArray[i].length()));
					if (val >= 8) {
						return false;
					}
				} else {
					// Handle decimal token
					Integer val = Integer.parseInt(ipArray[i]);
					if (val < 0 || val > 255) {
						return false;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * Check if given string is a valid IPv6
	 *
	 * @param ip
	 * @return
	 */
	public static boolean isIpv6(String ip) {
		// First try to un-abbreviate the given IP
		if (ip.contains("::")) {
			String ip1 = ip.substring(0, ip.indexOf("::"));
			String ip2 = ip.substring(ip.indexOf("::") + 2, ip.length());

			String[] ip1Array = ip1.split(":");
			String[] ip2Array = ip2.split(":");

			int tokenLen = 0;
			if (!ip1.isEmpty()) {
				tokenLen += ip1Array.length;
			}
			if (!ip2.isEmpty()) {
				tokenLen += ip2Array.length;
			}

			if (tokenLen >= 8) {
				return false;
			}
			StringBuilder sb = new StringBuilder();
			sb.append(":");
			for (int i = 0; i < (8 - tokenLen); i++) {
				sb.append("0000:");
			}
			String newIp = ip.replace("::", sb.toString());
			if (newIp.startsWith(":")) {
				newIp = newIp.substring(1);
			}
			if (newIp.endsWith(":")) {
				newIp = newIp.substring(0, newIp.length() - 1);
			}
			// Un-abbreviated IP
			ip = newIp;
		}

		// Validate un-abbreviated IP
		String[] tokens = ip.split(":");
		if (tokens.length != 8) {
			// IPv6 should have exactly 8 tokens
			return false;
		}
		for (int i = 0; i < 8; i++) {
			// Each token must be less than 4 character long
			if (tokens[i].length() > 4) {
				return false;
			}

			// Try parsing hexadecimal integer
			try {
				Integer.parseInt(tokens[i], 16);
			} catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Pass each IP through a chain of validators.
	 *
	 * @param addresses
	 * @return List of output strings
	 */
	public static List<String> validateAddresses(List<String> addresses) {
		List<String> output = new LinkedList<>();
		// Iterate over each input line
		for (String ip : addresses) {
			// Trim to remove any extra spaces
			ip = ip.trim();
			if (isIpv4(ip)) {
				output.add(IPv4);
			} else if (isIpv6(ip)) {
				output.add(IPv6);
			} else {
				output.add(NEITHER);
			}
		}
		return output;
	}

}

/**
 * @author sjafari
 *
 */
public class Solution {
	public static void main(String[] args) throws IOException {
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
		int addressesCount = Integer.parseInt(bufferedReader.readLine().trim());

		// Java 8
		List<String> addresses = IntStream.range(0, addressesCount).mapToObj(i -> {
			try {
				return bufferedReader.readLine();
			} catch (IOException ex) {
				throw new RuntimeException(ex);
			}
		}).collect(toList());

		// Java 7
		/*
		 * List<String> addresses = new LinkedList<String>(); for(int i=0; i<
		 * addressesCount; i++) { addresses.add(bufferedReader.readLine()); }
		 */
		List<String> result = Result.validateAddresses(addresses);
		// Java 8
		result.stream().forEach(System.out::println);

		// Java 7
		/*
		 * for(String resultStr : result) { System.out.println(resultStr); }
		 */
		bufferedReader.close();
	}
}
