package com.xinnet.xa.core.utils;

import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.SchedulingTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.xinnet.xa.core.vo.MailParams;

import freemarker.template.Template;

@Component
@Lazy
public class SendMailUtil {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	private JavaMailSender javaMailSend;
	@Autowired
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Autowired
	private SchedulingTaskExecutor taskExecutor;
	@Autowired
	private SimpleMailMessage simpleMailMessage;

	public void sendMail(MailParams params) throws Exception {
		MimeMessage mimeMessage = javaMailSend.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true,
				params.getEncoding());
		helper.setFrom(simpleMailMessage.getFrom());
		params.addHelper(helper);
		javaMailSend.send(mimeMessage);
	}

	public String getHtmlTextFromFreeMarker(String freeMarkerName,
			Map<String, String> params) {
		String htmlText = null;
		try {
			Template tpl = freeMarkerConfigurer.getConfiguration().getTemplate(
					freeMarkerName);
			htmlText = FreeMarkerTemplateUtils.processTemplateIntoString(tpl,
					params);
		} catch (Exception e) {
			logger.error(freeMarkerName + " " + e.getMessage(), e);
		}
		return htmlText;
	}

	public void asynSendMails(List<MailParams> mailParamslist,
			boolean returnResult) {
		for (MailParams params : mailParamslist) {
			taskExecutor.execute(new SendMailThread(params));
		}
		if (returnResult) {
			this.checkResult(mailParamslist);
		}
	}

	private void checkResult(List<MailParams> mailParamslist) {
		boolean run = true;
		int length = mailParamslist.size();
		while (run) {
			int i = 0;
			run = false;
			for (MailParams params : mailParamslist) {
				if (params.isSendSuccess() == null) {
					 break;
				}
				i++;
			}
			if (i < length) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			} else {
				run = true;
			}
		}
	}

	class SendMailThread implements Runnable {
		private MailParams params;

		public SendMailThread(MailParams params) {
			super();
			this.params = params;
		}

		@Override
		public void run() {
			try {
				sendMail(params);
				params.setSendSuccess(true);
			} catch (Exception e) {
				params.setSendSuccess(false);
				params.setErrorMsg(e.getMessage());
				logger.error(params.toString() + " " + e.getMessage(), e);
			}

		}

	}

}
