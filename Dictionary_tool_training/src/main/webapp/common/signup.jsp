<%@ include file="/common/taglibs.jsp"%>
<head>
    <title><fmt:message key="signup.title"/></title>
    <meta name="heading" content="<fmt:message key='signup.heading'/>"/>
</head>

<body id="signup" class="onecolumn"/>

<div class="separator"></div>

<s:form name="signupForm" action="signup" method="post" validate="true" theme="simple">
    <div class="info">
        <fmt:message key="signup.message"/>
    </div>
    <s:label label="%{getText('user.username')}" required="true"/>
    <s:textfield name="user.username"
        value="%{user.username}" cssClass="text large" required="true"/>
    <div>
        <div>
            <div class="left">
                <s:label label="%{getText('user.password')}" required="true"/>
                <s:password name="user.password" showPassword="true"  value="%{user.password}"
                    required="true" cssClass="text medium"/>
            </div>
            <div>
                <s:label label="%{getText('user.confirmPassword')}" required="true"/>
                <s:password name="user.confirmPassword"  value="%{user.confirmPassword}"
                    required="true" showPassword="true" cssClass="text medium"/>
            </div>
        </div>
    </div>

    <s:label label="%{getText('user.passwordHint')}" required="true"/>
    <s:textfield name="user.passwordHint"
        value="%{user.passwordHint}" required="true" cssClass="text large"/>

    <div>
        <div>
            <div class="left">
                <s:label label="%{getText('user.firstName')}" required="true"/>
                <s:textfield name="user.firstName" 
                    value="%{user.firstName}" required="true" cssClass="text medium"/>
            </div>
            <div>
                <s:label label="%{getText('user.lastName')}" required="true"/>
                <s:textfield name="user.lastName" 
                    value="%{user.lastName}" required="true" cssClass="text medium"/>
            </div>
        </div>
    </div>

    <div>
        <div>
            <div class="left">
                <s:label label="%{getText('user.email')}" required="true"/>
                <s:textfield name="user.email" 
                    value="%{user.email}" required="true" cssClass="text medium"/>
            </div>
            <div>
                <s:label label="%{getText('user.phoneNumber')}" required="true"/>
                <s:textfield name="user.phoneNumber" 
                    value="%{user.phoneNumber}" cssClass="text medium"/>
            </div>
        </div>
    </div>

    <s:label label="%{getText('user.website')}" required="true"/>
    <s:textfield name="user.website"
        value="%{user.website}" required="true" cssClass="text large"/>

    <div>
        <fieldset>
            <legend><fmt:message key="user.address.address"/></legend>
            <div>
                <s:label label="%{getText('user.address.address')}" required="true"/>
                <s:textfield name="user.address.address" 
                    value="%{user.address.address}" cssClass="text large" labelposition="bottom"  />
            </div>
            <div class="left">
                <s:label label="%{getText('user.address.city')}" required="true"/>
                <s:textfield name="user.address.city" 
                    value="%{user.address.city}" required="true" cssClass="text medium" labelposition="bottom"/>
            </div>
            <div>
                <s:label label="%{getText('user.address.province')}" required="true"/>
                <s:textfield name="user.address.province" 
                    value="%{user.address.province}" required="true" cssClass="text state" size="2" labelposition="bottom"/>
            </div>
            <div class="left">
                <s:label label="%{getText('user.address.postalCode')}" required="true"/>
                <s:textfield name="user.address.postalCode"  
                    value="%{user.address.postalCode}" required="true" cssClass="text zip" labelposition="bottom"/>
            </div>
            <div>
                <label for="user.address.country"><fmt:message key="user.address.country"/> <span class="req">*</span>
                <s:set name="country" value="user.address.country" scope="page"/>
            </div>
        </fieldset>
    </div>
    <div class="buttonBar bottom">
        <input type="submit" class="button" name="save" value="<fmt:message key="button.register"/>"/>
        <input type="submit" class="button" name="cancel" value="<fmt:message key="button.cancel"/>"/>
    </div>
</s:form>

<script type="text/javascript">
    Form.focusFirstElement(document.forms["signupForm"]);
</script>
