<%@ include file="/common/taglibs.jsp"%>

<head>
	<title><fmt:message key="403.title" /></title>
	<meta name="heading" content="<fmt:message key='403.title'/>"/>
	<fmt:message key="403.title" />
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
<table width="100%" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td><fmt:message key="403.message" /></td>
	</tr>
	<tr>
		<td><input type="button" value="Back" name="Back"
			onclick="javascript:back();" />
		&nbsp;&nbsp;&nbsp;&nbsp; <input type="button" value="Close Window"
			name="Close Window" onclick="javascript:window.close();" /></td>
	</tr>
</table>
</body>
