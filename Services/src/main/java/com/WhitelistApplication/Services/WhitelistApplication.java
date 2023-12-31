package com.WhitelistApplication.Services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class WhitelistApplication {

    private static final String IPv6v4Address = null;
	@Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/check_ip")
    public WhitelistResponse checkIP(@RequestParam("ip") InetAddress inputIP) {
        List<String> whitelistedIPRanges = jdbcTemplate.queryForList(
                "SELECT * FROM Whitelisted_DB_1 WHERE Whitelisted_IPs = ?", String.class);

        if (isIPInWhitelist(inputIP) || isIPInCIDRRanges(inputIP, whitelistedIPRanges)) {
            return new WhitelistResponse(true, whitelistedIPRanges.contains(inputIP));
        }
        return new WhitelistResponse(false, false);
    }


	@GetMapping("/check_uid")
    public WhitelistResponse checkUID(@RequestParam("user_id") String userId) {
        int count = jdbcTemplate.queryForObject(
                "SELECT * FROM Whitelisted_DB_1 WHERE Whitelisted_UIDs = ?",
                Integer.class,
                userId);

        boolean isWhitelisted = count > 0;
        return new WhitelistResponse(isWhitelisted, false); 
    }
 
    
    @GetMapping("/check_device_id")
    public WhitelistResponse checkDeviceId(@RequestParam("device_id") String deviceId) {
        int count = jdbcTemplate.queryForObject(
                "SELECT COUNT * FROM Whitelisted_DB_1 WHERE Whitelisted_Device_IDs = ?",
                Integer.class,
                deviceId);

        boolean isWhitelisted = count > 0;
        return new WhitelistResponse(isWhitelisted, false); // Assuming no CIDR range check for device_id
    }


    private boolean isIPInWhitelist(InetAddress inputIP) {
        // Check if the input IP is in the Whitelisted_IPs column
        int count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM Whitelisted_DB_1 WHERE Whitelisted_IPs = ?",
                Integer.class,
                inputIP);

        return count > 0;
    }

    private boolean isIPInCIDRRanges(InetAddress inputIP, List<String> whitelistedIPRanges) {
        for (String cidrRange : whitelistedIPRanges) {
            if (isIPInCIDR(inputIP, cidrRange)) {
                return true;
            }
        }
        return false;
    }

    private boolean isIPInCIDR(InetAddress ip, String cidrAddress) {
        try {
            InetAddress networkAddr = InetAddress.getByName(cidrAddress.split("/")[0]);
            int prefixLength = Integer.parseInt(cidrAddress.split("/")[1]);

            if (ip instanceof java.net.Inet4Address && networkAddr instanceof java.net.Inet4Address) {
                return isIPv4InCIDR((java.net.Inet4Address) ip, (java.net.Inet4Address) networkAddr, prefixLength);
            } else if (ip instanceof java.net.Inet6Address && networkAddr instanceof java.net.Inet6Address) {
                return isIPv6InCIDR((java.net.Inet6Address) ip, (java.net.Inet6Address) networkAddr, prefixLength);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isIPv4InCIDR(java.net.Inet4Address ip, java.net.Inet4Address networkAddr, int prefixLength) {
        byte[] ipBytes = ip.getAddress();
        byte[] networkBytes = networkAddr.getAddress();

        for (int i = 0; i < prefixLength / 8; i++) {
            if (ipBytes[i] != networkBytes[i]) {
                return false;
            }
        }

        int remainingBits = prefixLength % 8;
        if (remainingBits > 0) {
            int mask = 0xFF << (8 - remainingBits);
            return (ipBytes[prefixLength / 8] & mask) == (networkBytes[prefixLength / 8] & mask);
        }

        return true;
    }

    private boolean isIPv6InCIDR(java.net.Inet6Address ip, java.net.Inet6Address networkAddr, int prefixLength) {
        byte[] ipBytes = ip.getAddress();
        byte[] networkBytes = networkAddr.getAddress();

        for (int i = 0; i < prefixLength / 8; i++) {
            if (ipBytes[i] != networkBytes[i]) {
                return false;
            }
        }

        int remainingBits = prefixLength % 8;
        if (remainingBits > 0) {
            int mask = 0xFF << (8 - remainingBits);
            return (ipBytes[prefixLength / 8] & mask) == (networkBytes[prefixLength / 8] & mask);
        }

        return true;
    }
    
    public static void main(String[] args) {
        SpringApplication.run(WhitelistApplication.class, args);
    }
}

