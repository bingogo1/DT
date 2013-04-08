<%@ page language="java" errorPage="/error.jsp" pageEncoding="UTF-8"
	contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<HTML>
<head>
<title><fmt:message key="login.title" /></title>
<meta name="heading" content="<fmt:message key='login.heading'/>" />
<link
	href="<s:url value='/styles/main.css' encode='false' includeParams='none'/>"
	rel="stylesheet" type="text/css" media="all" />
<link
    href="<s:url value='/cwc/css/cwc_login.css' encode='false' includeParams='none'/>"
    rel="stylesheet" type="text/css" media="all" />
<link
    href="<s:url value='/cwc/css/cwc_defaults.css' encode='false' includeParams='none'/>"
    rel="stylesheet" type="text/css" media="all" />
<script type="text/javascript">
    function saveUsername(theForm) {
        var expires = new Date();
        expires.setTime(expires.getTime() + 24 * 30 * 60 * 60 * 1000); // sets it for approx 30 days.
        setCookie("username",theForm.j_username.value,expires,"<c:url value="/"/>");
    }
    
    function validateForm(form) {                                                               
        return validateRequired(form); 
    } 
    
    function required () { 
        this.aa = new Array("j_username", "<s:text name="errors.login.requiredUserName"><s:param><s:text name="label.username"/></s:param></s:text>", new Function ("varName", " return this[varName];"));
        this.ab = new Array("j_password", "<s:text name="errors.login.requiredPassword"><s:param><s:text name="label.password"/></s:param></s:text>", new Function ("varName", " return this[varName];"));
    } 
    
 
/* This function is used to get cookies */
function getCookie(name) {
	var prefix = name + "=" 
	var start = document.cookie.indexOf(prefix) 

	if (start==-1) {
		return null;
	}
	
	var end = document.cookie.indexOf(";", start+prefix.length) 
	if (end==-1) {
		end=document.cookie.length;
	}

	var value=document.cookie.substring(start+prefix.length, end) 
	return unescape(value);
}

function setCookie(name,value,expires,path,domain,secure) {
  document.cookie = name + "=" + escape (value) +
    ((expires) ? "; expires=" + expires.toGMTString() : "") +
    ((path) ? "; path=" + path : "") +
    ((domain) ? "; domain=" + domain : "") + ((secure) ? "; secure" : "");
}

function validateRequired(form) {                                    
    var bValid = true;
    var focusField = null;
    var i = 0;                                                                                          
    var fields = new Array();                                                                           
    oRequired = new required();                                                                         
                                                                                                        
    for (x in oRequired) {                                                                              
        if ((form[oRequired[x][0]].type == 'text' || form[oRequired[x][0]].type == 'textarea' || form[oRequired[x][0]].type == 'select-one' || form[oRequired[x][0]].type == 'radio' || form[oRequired[x][0]].type == 'password') && form[oRequired[x][0]].value == '') {
           if (i == 0)
              focusField = form[oRequired[x][0]]; 
              
           fields[i++] = oRequired[x][1];
            
           bValid = false;                                                                             
        }                                                                                               
    }                                                                                                   
                                                                                                       
    if (fields.length > 0) {
       focusField.focus();
       alert(fields.join('\n'));                                                                      
    }                                                                                                   
                                                                                                       
    return bValid;                                                                                      
}

	
</script>

</head>
<BODY id="login" class="loginContainer" >
  <!-- Begin Main View -->
  <div id="page" class="loginContainer" >
    <!-- Full screen One Voice login - contains the logo application title and application graphic -->
	<div id="outer-header" class="loginBrand">
	  <table id="cwc_Masthead" cellspacing="0" cellpadding="0" border="0">
	    <tr>
	      <td class="loginLogo">
            <img alt="HP" src="${ctx}/cwc/img/logo_hp_largemasthead.gif"  border="0">
          </td>
	      <td class="loginTitle"><fmt:message key='mainMenu.title'/></td>
	      <td class="loginImage"><img src="${ctx}/cwc/img/cwc_splash.gif" alt=""></td>
	    </tr>
	  </table>
    </div>
    <div id="content_login" class="loginForm">
      <form name="Login" style="margin-top:4px;" id="loginForm" method="POST" action="<c:url value='/j_security_check'/>" onsubmit="saveUsername(this);return validateForm(this)" >
        <table id="cwc_custom_loginform" cellspacing="0" cellpadding="0" border="0">
          <tr>
            <td colspan="2"></td>
		    <td class="loginInput" colspan="2">
		      <c:if test="${param.error != null}">
		        <div id="messageWarning" align="left" style="margin: 0px 6px 0px 0px;">
		          <div><img src="${ctx}/cwc/img/msg_warning.gif" alt=""/>
		             <div><fmt:message key="errors.password.mismatch" /></div>
		          </div>
		        </div>
		      </c:if>
		    </td>
		  </tr>
		  <tr>
		    <td colspan="2" class="loginLabel">
		        <label for="j_username" id="LoginUsernameLabel" class="loginLabel"><fmt:message
		        key="label.username" /> <span class="required">*</span></label>
		    </td>
		    <td class="loginInput">
		        <input id="j_username" class="formElement" type="text" name="j_username" tabindex="1" />
		    </td>
		  </tr>
		  <tr>
		    <td colspan="2" class="loginLabel">
		        <label for="j_password" id="LoginPasswordLabel" class="loginLabel">
		           <fmt:message key="label.password" /> <span class="required">*</span> 
		        </label>
		    </td>
		    <td class="loginInput">
		        <input name="j_password" id="j_password" class="formElement" type="password" tabindex="2" />
		    </td>
		  </tr>
		  <tr>
		    <td colspan="2" class="loginLabel">&nbsp;</td>
		    <td class="loginInput">
		      <div class="bWrapperUp" style="margin-top:5px;">
		        <div>
		          <div>
		            <input name="login" type="submit" class="hpButton" value="<fmt:message key='button.login'/>" tabindex="4" />
		          </div>
		       </div>
		     </div>
		     </td>
		  </tr>
		  <tr>
		    <td colspan="2" class="loginLabel">&nbsp;</td>
		      <td class="loginInput"><img src="${ctx}/cwc/img/backgrounds/cwc_loginFormBkg.gif" alt="" height="1"></td>
		  </tr>
		  <tr>
            <td />
              <div id="licensingLogos" class="loginLogos">
                <img src="${ctx}/cwc/img/tsg.gif"/>
                <img src="${ctx}/cwc/img/javaLogo_login.gif"/>
              </div>
            </td>
		    <td class="loginCopyright" colspan="2">
		      <div id="cwc_copyright">
		        (C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.
		      </div>
		    </td>
		  </tr>
		</table>
      </form>
    </div>
  </div>
</body>
</html>

