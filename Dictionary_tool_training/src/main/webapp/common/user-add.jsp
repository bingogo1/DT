<%@ include file="/common/taglibs.jsp"%>

<head>
    <title><fmt:message key="userAddMenu.title" /></title>
    <meta name="menu" content="UserMgmtMenu"/>
    <meta name="heading" content="<fmt:message key='userAddMenu.title'/>" />

<script language="JavaScript">
</script>
</head>

<body>
  <div class="last">
    <table class="menuTabTable" cellspacing="0" cellpadding="3">
        <tr>
<authz:function ifAnyGranted="addUser.action">
          <td class="menuTabSeparator"></td>
          <td class="menuTabUnselected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<a
                href="<s:url action="editProfile"/>"><fmt:message key="userProfile.heading"/></a>&nbsp;&nbsp;</div>
            </div>
          </td>
</authz:function>
          <td class="menuTabSeparator"></td>
          <td class="menuTabSelected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<fmt:message key="userAddMenu.title"/>&nbsp;&nbsp;</div>
            </div>
          </td>
<authz:function ifAnyGranted="userMgmt.action">
          <td class="menuTabSeparator"></td>
          <td class="menuTabUnselected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<a
                href="<s:url action="userMgmt"/>"><fmt:message key="userListMenu.title"/></a>&nbsp;&nbsp;</div>
            </div>
          </td>
</authz:function>
<authz:function ifAnyGranted="assignUserRole.action">
          <td class="menuTabSeparator"></td>
          <td class="menuTabUnselected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<a
                href="<s:url action="assignUserRole"/>"><fmt:message key="userRoleAssign.title"/></a>&nbsp;&nbsp;</div>
            </div>
          </td>
</authz:function>
<authz:function ifAnyGranted="assignRoleFunction.action">
          <td class="menuTabSeparator"></td>
          <td class="menuTabUnselected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<a
                href="<s:url action="assignRoleFunction"/>"><fmt:message key="RoleFunctionAssign.title"/></a>&nbsp;&nbsp;</div>
            </div>
          </td>
</authz:function>
<authz:function ifAnyGranted="roleMgmt.action">
          <td class="menuTabSeparator"></td>
          <td class="menuTabUnselected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<a
                href="<s:url action="roleMgmt"/>"><fmt:message key="roleSearchMenu.title"/></a>&nbsp;&nbsp;</div>
            </div>
          </td>
</authz:function>
        </tr>
    </table>
  </div>

<h1><fmt:message key="userAddMenu.title"/></h1>
<s:form action="addUser" method="post" theme="simple" validate="true">
<%@ include file="/common/messages.jsp"%>
  <table class="inputTable" border="0" cellpadding="0" cellspacing="0" width="50%">
    <tr>
      <td class="label" width="25%"><s:label key="user.username" required="true" /></td>
      <td class="ctrl" width="25%"><s:textfield name="user.username" label="username" maxlength="50"/></td>
      <td class="label" width="25%"></td>
      <td class="ctrl" width="25%"></td>
    </tr>
    <tr>
      <td class="label" width="25%"><s:label key="user.password" required="true" /></td>
      <td class="ctrl" width="25%"><s:password name="user.password" label="password" showPassword="true" maxlength="100"/></td>
      <td class="label" width="25%"><s:label key="user.confirmPassword" required="true" /></td>
      <td class="ctrl" width="25%"><s:password name="user.confirmPassword" label="confirmPassword" showPassword="true" maxlength="100"/></td>
    </tr>
    <tr>
      <td class="label" width="25%"><s:label key="user.firstName" required="true" /></td>
      <td class="ctrl" width="25%"><s:textfield name="user.firstName" label="firstName" maxlength="30"/></td>
      <td class="label" width="25%"><s:label key="user.lastName" required="true" /></td>
      <td class="ctrl" width="25%"><s:textfield name="user.lastName" label="lastName" maxlength="30"/></td>
    </tr>
    <tr>
      <td class="label" width="25%"><s:label key="user.email" /></td>
      <td class="ctrl" width="25%"><s:textfield name="user.email" label="email" maxlength="240"/></td>
      <td class="label" width="25%"><s:label key="user.phoneNumber" /></td>
      <td class="ctrl" width="25%"><s:textfield name="user.companyTel" label="companyTel" maxlength="14"/></td>
    </tr>
  </table>
        <br/>
      	<s:submit cssClass="button" name="buttonName" key="button.save"
               onclick="form.action='addUser!save.action';return true;" />
      	<s:submit cssClass="button" name="buttonName" key="button.cancel"
               onclick="form.action='addUser!cancel.action';return true;" />
        <br/>
          
</s:form>

</body>
</html>