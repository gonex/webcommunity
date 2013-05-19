package com.webcommunity.server.cron;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.webcommunity.server.MailDispatchQueue;


public class CronMailDispatchHandler extends HttpServlet {

	private static final long serialVersionUID = 8861467241110318454L;

	private static final Logger log = Logger.getLogger(CronMailDispatchHandler.class.getName());
	
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)	throws IOException {
		try {
			MailDispatchQueue.dispatchPendingMails(8);
		} catch (Exception ex) {
			log.log(Level.SEVERE, "error dispaching mails in cron job", ex);
		}
	}
}
