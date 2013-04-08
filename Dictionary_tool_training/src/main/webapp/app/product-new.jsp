<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title><fmt:message key="product.title" /></title>
    <meta name="menu" content="ProductMgmtMenu"/>
    <content tag="heading"><fmt:message key="productMgmtMenu.title"/></content>
    <script type="text/javascript" src="<c:url value='/dwr/interface/ProductManager.js'/>"></script>

</head>

<body class="onecolumn">
    <h1><fmt:message key="product.title"/></h1>

<s:form action="productMgmt" method="post" theme="simple" validate="true">
<%@ include file="/common/messages.jsp"%>
  <table class="inputTable" border="0" cellpadding="0" cellspacing="0" width="50%"> 
    <tr>
      <td class="label" width="20%"><s:label key="productCenter" /></td>
      <td class="ctrl" width="30%">
      	<s:select list="productCenterDropdownList" name="searchCondition.productCenterId.centerName" 
        	listKey="label" listValue="value"/>
      </td>
    </tr>
    <tr>
      <td class="label" width="20%"><s:label key="productName" required="true" /></td>
      <td class="ctrl" width="30%" ><s:textfield name="searchCondition.productName" label="name" maxlength="80"/></td>
    </tr>
    <tr>
      <td class="label" width="20%"><s:label key="version" /></td>
      <td class="ctrl" width="30%" ><s:textfield name="searchCondition.productVersion" label="ver" maxlength="40"/></td>
    </tr>
    <tr>
      <td class="label" width="20%"><s:label key="description" /></td>
      <td class="ctrl" width="30%" ><s:textfield name="searchCondition.productDescription" label="desc" maxlength="200"/></td>
    </tr>
  </table>
        <br/>
      	<s:submit cssClass="button" name="buttonName" key="button.save"
               onclick="form.action='productMgmt!save.action';return true;" />
      	<s:submit cssClass="button" name="buttonName" key="button.cancel"
               onclick="form.action='productMgmt!cancel.action';return true;" />
        <br/>
          
</s:form>

</body>