<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title><fmt:message key="roleAddMenu.title" /></title>
    <meta name="menu" content="UserMgmtMenu"/>
    <content tag="heading"><fmt:message key="roleAddMenu.title"/></content>

</head>

<body class="onecolumn">
    <h1><fmt:message key="roleAddMenu.title"/></h1>

<s:form action="roleAdd" method="post" theme="simple" validate="true">
<%@ include file="/common/messages.jsp"%>
  <table class="inputTable" border="0" cellpadding="0" cellspacing="0" width="50%"> 
    <tr>
      <td class="label" width="50%"><s:label key="role.name.title" required="true"/></td>
      <td class="ctrl" width="50%" ><s:textfield name="role.name" label="name" maxlength="80"/></td>
    </tr>
    <tr>
      <td class="label" width="50%"><s:label key="role.short.name" /></td>
      <td class="ctrl" width="50%" ><s:textfield name="role.shortName" maxlength="30"/></td>
    </tr>
    <tr>
      <td class="label" width="50%"><s:label key="description" /></td>
      <td class="ctrl" width="50%" ><s:textfield name="role.description" label="desc" maxlength="200"/></td>
    </tr>
  </table>
        <br/>
      	<s:submit cssClass="button" name="buttonName" key="button.save"
               onclick="form.action='roleAdd!save.action';return true;" />
      	<s:submit cssClass="button" name="buttonName" key="button.cancel"
               onclick="form.action='roleAdd!cancel.action';return true;" />
        <br/>
          
</s:form>

</body>