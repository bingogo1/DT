<%@ include file="/common/taglibs.jsp"%>


<head>
    <title><fmt:message key="errorPage.title"/></title>
    <meta name="heading" content="<fmt:message key='errorPage.title'/>"/>
</head>

<body class="onecolumn">
<p>
    <fmt:message key="errorPage.heading">
        <fmt:param><c:url value="/mainMenu.action"/></fmt:param>
    </fmt:message>
</p>
<p style="text-align: center; margin-top: 20px">
    <img  src="<c:url value="/cwc/img/msg_error.gif"/>" alt="error page" />
</p>
</body>