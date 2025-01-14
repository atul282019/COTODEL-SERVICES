package com.cotodel.hrms.auth.server.util;

public class CaptchaSession {

	private boolean captchaValidated;

	public boolean isCaptchaValidated() {
		return captchaValidated;
	}

	public void setCaptchaValidated(boolean captchaValidated) {
		this.captchaValidated = captchaValidated;
	}
}
