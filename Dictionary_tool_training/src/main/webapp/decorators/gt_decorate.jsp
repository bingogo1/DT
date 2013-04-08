<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1 Transitional//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@page import="bin.g11n.gt.util.DateUtil" %>
<%@ include file="/common/taglibs.jsp"%>
<%@ include file="/common/commonMethod.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
     <%@ include file="/common/meta.jsp" %>
    <title><decorator:title default="Glossary Tools Page"/></title>
    <link href="<s:url value='/styles/main.css' encode='false' includeParams='none'/>" rel="stylesheet" type="text/css" media="all"/>
    <link href="<s:url value='/struts/niftycorners/niftyCorners.css' encode='false' includeParams='none'/>" rel="stylesheet" type="text/css"/>
    <link href="<s:url value='/struts/niftycorners/niftyPrint.css' encode='false' includeParams='none'/>" rel="stylesheet" type="text/css" media="print"/>
    <link href="<s:url value='/cwc/css/cwc_defaults.css' encode='false' includeParams='none'/>" rel="stylesheet" type="text/css" media="all"/>
    <script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>

    <script language="JavaScript" type="text/javascript" src="<s:url value='/struts/niftycorners/nifty.js' encode='false' includeParams='none'/>"></script>

    <script type="text/javascript" src="<c:url value='/scripts/dragdrop/prototype.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/scripts/dragdrop/scriptaculous.js'/>"></script>


    <script type="text/javascript" src="<c:url value='/scripts/render.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/dwr/engine.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/dwr/util.js'/>"></script>
    <script type="text/javascript" src="<c:url value='/dwr/date.js'/>"></script>
    <!-- For ExcelTable specially: End-->


	<c:set var="theme" scope="request"><decorator:getProperty property="meta.theme"/></c:set>
	<c:if test="${not empty theme}">
		<s:head theme="${theme}" />
	</c:if>

    <decorator:head/>
</head>
 <!--  Don't add any onload event to this element, use the doOnLoads method above. -->
<body<decorator:getProperty property="body.id" writeEntireProperty="true"/><decorator:getProperty property="body.class" writeEntireProperty="true"/>>
    <div id="outer-header" style="height:37px; z-index:1;">  
      <table border="0" cellpadding="0" cellspacing="0" width="100%" class="applicationMastheadSmall" id="cwc_Masthead">
        <tr>
          <td class="mastheadIcon" width="10%">
            <img src="${ctx}/cwc/img/header_left.gif" width="30" height="19" border="0" >
          </td>
          <td class="mastheadTitle" width="50%">
            <fmt:message key='mainMenu.title'/>
          </td>
          <td width="30%"><div class="mastheadPhoto"><img alt="" src="${ctx}/cwc/img/header_right.jpg"></div></td>
          <td class="mastheadLinks" id="logout">
            <c:if test="${!empty pageContext.request.remoteUser}">
              <span>Hi, <authz:authentication operation="fullName"/> &nbsp;&nbsp;|</span>&nbsp;&nbsp;
              <%=DateUtil.getDateTime("MM/dd/yyyy",DateUtil.getNewDate())%>
              <br>
              <a href="<c:url value="flushCache.action"/>" class="needConfirm" title="<fmt:message key="flushCache.message.title"/>">
                <font color="FFFFFF"><fmt:message key="flushCache.title"/><font> 
              </a>
              &nbsp;&nbsp;&nbsp;
              <a href="<c:url value="/logout.jsp"/>" class="needConfirm" >
                <font color="FFFFFF"><fmt:message key="user.logout"/><font> 
              </a>
              <span>&nbsp;&nbsp;</span>
	        </c:if>
          </td>
        </tr>
      </table>
  </div>
<!-- end header -->


<div id="page">

	<%if (request.getRequestURL().toString().endsWith("login.jsp")) { %>
    	<div id="content_login" class="clearfix">
    <% } else { %>
    	<div id="content" class="clearfix">
    <% } %>
		<c:set var="currentMenu" scope="request"><decorator:getProperty property="meta.menu"/></c:set>
		<c:if test="${!empty currentMenu}">
        <div id="nav">
            <div class="wrapper">
                <jsp:include page="/app/menu.jsp" flush="true"/>
            </div>
            <hr/>

        </div><!-- end nav -->
		</c:if>

		<%if (request.getRequestURL().toString().endsWith("mainMenu.action")) { %>
	    	<div id="content_mainMenu" class="clearfix">
	    <% } %>

		<div id="main">
			<div class="wrapper">
        		<decorator:body/>
			</div>
		</div>
		<%if (request.getRequestURL().toString().endsWith("mainMenu.action")) { %>
	    	</div>
	    <% } %>
		<!-- if footer goes here will cause some problems with YUI. -->

    </div><!-- end content -->
	<hr />
    <p/>

</div><!-- end page -->
    <div id="footer" class="clearfix">
		<p align="center">(C) Copyright bingogo1@hotmail.com, LP -  All Rights Reserved.</p>
    </div><!-- end footer -->

<script type="text/javascript">
	<c:set var="confirmPage" scope="request"><decorator:getProperty property="meta.needConfirm"/></c:set>
	<c:if test="${confirmPage == 'true'}">
		processNotConfirmElement();
	</c:if>
</script>

</body>
</html>