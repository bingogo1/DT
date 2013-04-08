<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="exception.title" /></title>
	<meta name="heading" content="<fmt:message key='exception.title'/>"/>
	<fmt:message key="exception.title" />
	</content>
<script language="javascript">
function back(){
	//alert(window.history.length);
	if (window.history.length == 1){
		window.history.go(-1);
	}else if (window.history.length > 1){
		window.history.go(-2);
	}
}
</script>
	
</head>
<body class="onecolumn">
<h1>Glossary Tools General Exception Page</h1>
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><fmt:message key="exception.message" /></td>
	</tr>
	<tr>
		<td><input type="button" value="Back" name="Back"
			onclick="javascript:window.history.back();" />
		&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="Close Window"
			name="Close Window" onclick="javascript:back();" /></td>
	</tr>
</table>
</body>
