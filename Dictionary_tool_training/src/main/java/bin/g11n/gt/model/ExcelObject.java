package bin.g11n.gt.model;

import java.io.Serializable;

import bin.g11n.gt.common.Constants;

/**
 * 
 * @author bguo
 * @version $Revision: 1.1 $ $Date 2009/03/07
 * @history (C) Copyright bingogo1@hotmail.com, LP - All Rights Reserved.
 */
public class ExcelObject implements Serializable {

	private static final long serialVersionUID = 2248647928493037667L;

	private String productLine = null;
	private String productCenter = null;
	private String productName = null;
	private String version = null;
	private String sourceItem = null;
	private String type = null;
	private String definition = null;
	private String translatedValue = null;
	private String translationComments = null;
	private String status = null;
	private String translationSuggestion = null;
	private String reviewComments = null;
	private String language = null;
	private String locale = null;
	private String fieldName = null;	// record field name which length overflow
	private int operationResult = Constants.DATA_PARSE_NORMAL;
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	public int getOperationResult() {
		return operationResult;
	}
	public void setOperationResult(int operationResult) {
		this.operationResult = operationResult;
	}
	public String getDefinition() {
		return definition;
	}
	public String getLanguage() {
		return language;
	}

	public String getLocale() {
		return locale;
	}

	public String getProductCenter() {
		return productCenter;
	}

	public String getProductLine() {
		return productLine;
	}

	public String getProductName() {
		return productName;
	}

	public String getReviewComments() {
		return reviewComments;
	}

	public String getSourceItem() {
		return sourceItem;
	}

	public String getStatus() {
		return status;
	}

	public String getTranslatedValue() {
		return translatedValue;
	}

	public String getTranslationComments() {
		return translationComments;
	}

	public String getTranslationSuggestion() {
		return translationSuggestion;
	}

	public String getType() {
		return type;
	}

	public String getVersion() {
		return version;
	}

	public void setDefinition(String definition) {
		this.definition = definition;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public void setProductCenter(String productCenter) {
		this.productCenter = productCenter;
	}

	public void setProductLine(String productLine) {
		this.productLine = productLine;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setReviewComments(String reviewComments) {
		this.reviewComments = reviewComments;
	}

	public void setSourceItem(String sourceItem) {
		this.sourceItem = sourceItem;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setTranslatedValue(String translatedValue) {
		this.translatedValue = translatedValue;
	}

	public void setTranslationComments(String translationComments) {
		this.translationComments = translationComments;
	}

	public void setTranslationSuggestion(String translationSuggestion) {
		this.translationSuggestion = translationSuggestion;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}