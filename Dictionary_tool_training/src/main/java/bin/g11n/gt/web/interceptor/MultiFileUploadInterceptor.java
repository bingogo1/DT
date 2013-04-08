package bin.g11n.gt.web.interceptor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper;

import bin.g11n.cil.CILHelper;
import bin.g11n.cil.CILProducer;
import bin.g11n.cil.bundle.IG11nResourceBundle;
import bin.g11n.cil.common.logger.G11nLogFactory;
import bin.g11n.cil.common.logger.ILogger;
import bin.g11n.cil.common.logger.ILogger.ELevel;
import bin.g11n.gt.common.Constants;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * MultiFileUploadInterceptor.java(Updated base on struts2
 * file:FileUploadInterceptor.java)
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history
 * (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
 */
public class MultiFileUploadInterceptor extends AbstractInterceptor {
	private IG11nResourceBundle resources;
	private static transient final ILogger logger = G11nLogFactory
			.getLog(MultiFileUploadInterceptor.class);

	private static final long serialVersionUID = -4764627478894962478L;
	private static final String DEFAULT_DELIMITER = ",";
	private static final String DEFAULT_MESSAGE = "no.message.found";
	protected Long maximumSize;
	protected String allowedTypes;
	protected Set allowedTypesSet;

	public MultiFileUploadInterceptor() {
		allowedTypesSet = Collections.EMPTY_SET;
	}

	public void setAllowedTypes(String allowedTypes) {
		this.allowedTypes = allowedTypes;
		allowedTypesSet = getDelimitedValues(allowedTypes);
	}

	public void setMaximumSize(Long maximumSize) {
		this.maximumSize = maximumSize;
	}

	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ac = invocation.getInvocationContext();
		HttpServletRequest request = (HttpServletRequest) ac
				.get("com.opensymphony.xwork2.dispatcher.HttpServletRequest");

		Map fileMap = (HashMap) request.getSession().getAttribute(
				"uploadFileMap");
		if (fileMap == null) {
			fileMap = new HashMap();
			request.getSession().setAttribute("uploadFileMap", fileMap);
		}

		if (!(request instanceof MultiPartRequestWrapper)) {
			if (logger.isEnabled(ELevel.DEBUG)) {
				ActionProxy proxy = invocation.getProxy();
				logger.log(ELevel.DEBUG, getTextMessage(
						"struts.messages.bypass.request", new Object[] {
								proxy.getNamespace(), proxy.getActionName() },
						ActionContext.getContext().getLocale()));
			}
			return invocation.invoke();
		}
		Object action = invocation.getAction();
		ValidationAware validation = null;
		if (action instanceof ValidationAware)
			validation = (ValidationAware) action;
		MultiPartRequestWrapper multiWrapper = (MultiPartRequestWrapper) request;
		if (multiWrapper.hasErrors()) {
			String error;
			for (Iterator errorIter = multiWrapper.getErrors().iterator(); errorIter
					.hasNext(); logger.log(ELevel.ERROR, error)) {
				error = (String) errorIter.next();
				if (validation != null)
					validation.addActionError(error);
			}

		}
		Map parameters = ac.getParameters();
		Enumeration fileParameterNames = multiWrapper.getFileParameterNames();
		do {
			if (fileParameterNames == null
					|| !fileParameterNames.hasMoreElements())
				break;
			String inputName = (String) fileParameterNames.nextElement();
			String contentType[] = multiWrapper.getContentTypes(inputName);
			if (isNonEmpty(contentType)) {
				String fileName[] = multiWrapper.getFileNames(inputName);
				if (isNonEmpty(fileName)) {
					File files[] = multiWrapper.getFiles(inputName);
					if (files != null) {
						int index = 0;
						while (index < files.length) {
							if (acceptFile(files[index], contentType[index],
									inputName, validation, ac.getLocale())) {
								fileMap.put(fileName, fileToList(files[index]));
								parameters.put(inputName, files);
								parameters.put((new StringBuilder()).append(
										inputName).append("ContentType")
										.toString(), contentType);
								parameters.put((new StringBuilder()).append(
										inputName).append("FileName")
										.toString(), fileName);
							}
							index++;
						}
					}
				} else {
					logger.log(ELevel.ERROR, getTextMessage(
							"struts.messages.invalid.file",
							new Object[] { inputName }, ActionContext
									.getContext().getLocale()));
				}
			} else {
				logger.log(ELevel.ERROR, getTextMessage(
						"struts.messages.invalid.content.type",
						new Object[] { inputName }, ActionContext.getContext()
								.getLocale()));
			}
		} while (true);
		String result = invocation.invoke();
		for (fileParameterNames = multiWrapper.getFileParameterNames(); fileParameterNames != null
				&& fileParameterNames.hasMoreElements();) {
			String inputValue = (String) fileParameterNames.nextElement();
			File file[] = multiWrapper.getFiles(inputValue);
			int index = 0;
			while (index < file.length) {
				File currentFile = file[index];
				logger.log(ELevel.INFO, getTextMessage(
						"struts.messages.removing.file", new Object[] {
								inputValue, currentFile }, ActionContext
								.getContext().getLocale()));
				if (currentFile != null && currentFile.isFile())
					currentFile.delete();
				index++;
			}
		}

		return result;
	}

	protected boolean acceptFile(File file, String contentType,
			String inputName, ValidationAware validation, Locale locale) {
		boolean fileIsAcceptable = false;
		if (file == null) {
			String errMsg = getTextMessage("struts.messages.error.uploading",
					new Object[] { inputName }, locale);
			if (validation != null)
				validation.addFieldError(inputName, errMsg);
			logger.log(ELevel.ERROR, errMsg);
		} else if (maximumSize != null
				&& maximumSize.longValue() < file.length()) {
			String errMsg = getTextMessage(
					"struts.messages.error.file.too.large", new Object[] {
							inputName,
							file.getName(),
							(new StringBuilder()).append("").append(
									file.length()).toString() }, locale);
			if (validation != null)
				validation.addFieldError(inputName, errMsg);
			logger.log(ELevel.ERROR, errMsg);
		} else if (!allowedTypesSet.isEmpty()
				&& !containsItem(allowedTypesSet, contentType)) {
			String errMsg = getTextMessage(
					"struts.messages.error.content.type.not.allowed",
					new Object[] { inputName, file.getName(), contentType },
					locale);
			if (validation != null)
				validation.addFieldError(inputName, errMsg);
			logger.log(ELevel.ERROR, errMsg);
		} else {
			fileIsAcceptable = true;
		}
		return fileIsAcceptable;
	}

	private static boolean containsItem(Collection itemCollection, String key) {
		return itemCollection.contains(key.toLowerCase());
	}

	private static Set getDelimitedValues(String delimitedString) {
		Set delimitedValues = new HashSet();
		if (delimitedString != null) {
			StringTokenizer stringTokenizer = new StringTokenizer(
					delimitedString, ",");
			do {
				if (!stringTokenizer.hasMoreTokens())
					break;
				String nextToken = stringTokenizer.nextToken().toLowerCase()
						.trim();
				if (nextToken.length() > 0)
					delimitedValues.add(nextToken);
			} while (true);
		}
		return delimitedValues;
	}

	private static boolean isNonEmpty(Object objArray[]) {
		boolean result = false;
		for (int index = 0; index < objArray.length && !result; index++)
			if (objArray[index] != null)
				result = true;

		return result;
	}

	private String getTextMessage(String messageKey, Object args[],
			Locale locale) {
		if (args == null || args.length == 0)
			return LocalizedTextUtil.findText(getClass(), messageKey, locale);
		else
			return LocalizedTextUtil.findText(getClass(), messageKey, locale,
					"no.message.found", args);
	}

	/**
	 * FileToList
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 *             List
	 */
	public List fileToList(File file) throws Exception {
		List list = new ArrayList();
		String read;
		FileReader fileread = new FileReader(file);
		BufferedReader bufread = new BufferedReader(fileread);
		int counter = 0;
		while ((read = bufread.readLine()) != null) {
			if (read.equals(""))
				continue;
			list.add(read);
		}
		return list;
	}

	private IG11nResourceBundle getBundleInstance() {
		if (resources == null) {
			// Create and set up the window.
			CILHelper cILHelper = new CILHelper((new CILProducer())
					.createInfra());
			// Get interface
			resources = cILHelper.getResourceBundle();
			// Instantiates the bundle class with 1 parameter of baseName.
			resources = resources.getBundle(Constants.BUNDLE_KEY);
		}
		return resources;
	}

}
