<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title><fmt:message key="pLineMgmtMenu.title" /></title>
    <meta name="menu" content="ProductMgmtMenu"/>
    <content tag="heading"><fmt:message key="pLineMgmtMenu.title"/></content>

</head>

<body class="onecolumn">
    <h1><fmt:message key="productCenterMgmtMenu.title"/></h1>

<s:form action="productCenterMgmt" method="post" theme="simple" validate="true">
<%@ include file="/common/messages.jsp"%>
  <table class="inputTable" border="0" cellpadding="0" cellspacing="0" width="50%"> 
    <tr>
      <td class="label" width="50%"><s:label key="productCenter" required="true"/></td>
      <td class="ctrl" width="50%" ><s:textfield name="productCenter.centerName" label="name" maxlength="80"/></td>
    </tr>
    <tr>
      <td class="label" width="50%"><s:label key="description" /></td>
      <td class="ctrl" width="50%" ><s:textfield name="productCenter.centerDescription" label="desc" maxlength="200"/></td>
    </tr>
  </table>
        <br/>
      	<s:submit cssClass="button" name="buttonName" key="button.save"
               onclick="form.action='productCenterMgmt!save.action';return true;" />
      	<s:submit cssClass="button" name="buttonName" key="button.cancel"
               onclick="form.action='productCenterMgmt!cancel.action';return true;" />
        <br/>
          
</s:form>

</body>