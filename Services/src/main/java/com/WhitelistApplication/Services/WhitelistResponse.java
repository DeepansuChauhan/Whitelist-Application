package com.WhitelistApplication.Services;

public class WhitelistResponse {

	 private boolean whitelisted;
	    private boolean whitelistedCIDRRanges;
		public WhitelistResponse(boolean whitelisted, boolean whitelistedCIDRRanges) {
			super();
			this.whitelisted = whitelisted;
			this.whitelistedCIDRRanges = whitelistedCIDRRanges;
		}
		
		
		public boolean isWhitelisted() {
			return whitelisted;
		}
		public void setWhitelisted(boolean whitelisted) {
			this.whitelisted = whitelisted;
		}
		public boolean isWhitelistedCIDRRanges() {
			return whitelistedCIDRRanges;
		}
		public void setWhitelistedCIDRRanges(boolean whitelistedCIDRRanges) {
			this.whitelistedCIDRRanges = whitelistedCIDRRanges;
		}
		
		
		@Override
		public String toString() {
			return "WhitelistResponse [whitelisted=" + whitelisted + ", whitelistedCIDRRanges=" + whitelistedCIDRRanges
					+ "]";
		}
	    
	    
}
