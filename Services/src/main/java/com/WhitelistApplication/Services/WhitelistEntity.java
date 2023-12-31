package com.WhitelistApplication.Services;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WhitelistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String whitelistedIPs;
    private String whitelistedIPRanges;
    private String whitelistedUIDs;
    private String whitelistedDeviceIDs;


    public WhitelistEntity() {
    }

    public WhitelistEntity(String whitelistedIPs, String whitelistedIPRanges, String whitelistedUIDs, String whitelistedDeviceIDs) {
        this.whitelistedIPs = whitelistedIPs;
        this.whitelistedIPRanges = whitelistedIPRanges;
        this.whitelistedUIDs = whitelistedUIDs;
        this.whitelistedDeviceIDs = whitelistedDeviceIDs;
    }

   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWhitelistedIPs() {
        return whitelistedIPs;
    }

    public void setWhitelistedIPs(String whitelistedIPs) {
        this.whitelistedIPs = whitelistedIPs;
    }

    public String getWhitelistedIPRanges() {
        return whitelistedIPRanges;
    }

    public void setWhitelistedIPRanges(String whitelistedIPRanges) {
        this.whitelistedIPRanges = whitelistedIPRanges;
    }

    public String getWhitelistedUIDs() {
        return whitelistedUIDs;
    }

    public void setWhitelistedUIDs(String whitelistedUIDs) {
        this.whitelistedUIDs = whitelistedUIDs;
    }

    public String getWhitelistedDeviceIDs() {
        return whitelistedDeviceIDs;
    }

    public void setWhitelistedDeviceIDs(String whitelistedDeviceIDs) {
        this.whitelistedDeviceIDs = whitelistedDeviceIDs;
    }
}
