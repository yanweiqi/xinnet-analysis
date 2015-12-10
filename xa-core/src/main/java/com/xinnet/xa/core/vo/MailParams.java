package com.xinnet.xa.core.vo;

import java.io.File;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.MimeMessageHelper;

public class MailParams {

	private String to;
	private String cc;
	private String bcc;
	private String subject;
	private String body;
	private String encoding = "utf-8";
	private File[] attachments;
	private Boolean sendSuccess;
	private boolean html;
	private String errorMsg;

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public boolean isHtml() {
		return html;
	}

	public void setHtml(boolean html) {
		this.html = html;
	}

	public Boolean isSendSuccess() {
		return sendSuccess;
	}

	public void setSendSuccess(boolean sendSuccess) {
		this.sendSuccess = sendSuccess;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getCc() {
		return cc;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public String getBcc() {
		return bcc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public File[] getAttachments() {
		return attachments;
	}

	public void setAttachments(File[] attachments) {
		this.attachments = attachments;
	}

	private void check() throws Exception {
		if (StringUtils.isBlank(to) || StringUtils.isBlank(subject)
				|| StringUtils.isBlank(body))
			throw new Exception("收信人、标题、内容不能为空");
	}

	private String[] StringToArray(String str) {
		return str.split(";");
	}

	public void addHelper(MimeMessageHelper helper) throws Exception {
		this.check();
		helper.setTo(StringToArray(to));

		helper.setSubject(subject);
		helper.setText(body, html);
		if (StringUtils.isNotBlank(cc)) {
			helper.setCc(StringToArray(cc));
		}
		if (StringUtils.isNotBlank(bcc)) {
			helper.setBcc(StringToArray(bcc));
		}
		if (ArrayUtils.isNotEmpty(attachments)) {
			for (File file : attachments) {
				if (file.exists()) {
					helper.addAttachment(file.getName(), file);
				} else {
					throw new Exception("附件没有找到");
				}
			}
		}
	}

	@Override
	public String toString() {
		return "MailParams [to=" + to + ", cc=" + cc + ", bcc=" + bcc
				+ ", subject=" + subject + ", body=" + body + ", sendSuccess="
				+ sendSuccess + ", html=" + html + "]";
	}



}
