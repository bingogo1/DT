<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
    <title><fmt:message key="userList.title" /></title>
    <meta name="menu" content="UserMgmtMenu"/>
    <meta name="heading" content="<fmt:message key='userList.heading'/>" />

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
<authz:function ifAnyGranted="addUser.action">
          <td class="menuTabSeparator"></td>
          <td class="menuTabUnselected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<a
                href="<s:url action="addUser"/>"><fmt:message key="userAddMenu.title"/></a>&nbsp;&nbsp;</div>
            </div>
          </td>
</authz:function>
          <td class="menuTabSeparator"></td>
          <td class="menuTabSelected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<fmt:message key="userListMenu.title"/>&nbsp;&nbsp;</div>
            </div>
          </td>
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

<h1><fmt:message key="userListMenu.title" /></h1>
<s:form action="userMgmt" method="post" theme="simple" validate="true" >
<%@ include file="/common/messages.jsp"%>
  <table class="inputTable" border="0" cellpadding="0" cellspacing="0" width="98%"> 
    <tr>
      <td class="label" width="10%"><s:label key="activeUsers.fullName" /></td>
      <td class="ctrl" width="10%" ><s:textfield name="searchCondition.fullName" /></td>
      
      <td class="label" width="10%"><s:label key="user.phoneNumber" /></td>
      <td class="ctrl" width="10%" ><s:textfield name="searchCondition.companyTel" /></td>
      
    </tr>
    <tr>
      <td class="label" width="10%"><s:label key="user.email" /></td>
      <td class="ctrl" width="10%" ><s:textfield name="searchCondition.email" /></td>
      
      <td class="label" width="10%"><s:label key="user.accountLocked" /></td>
      <td class="ctrl" width="10%" >
        <s:checkbox name="searchCondition.accountLocked"
                value="%{searchCondition.accountLocked}" fieldValue="true" theme="simple" />
      </td>
    </tr>
  </table>
        <br/>
        <s:submit cssClass="button" name="buttonName" key="button.search"
               onclick="form.action='userMgmt!search.action';return true;" />
        <br/> 
        
<s:if test="paginatedList != null">
    <display:table name="paginatedList" cellspacing="1" cellpadding="1"
        id="paginatedList" uid="rowBean" class="table" 
        pagesize="${pageSize}" partialList="true" size="${totalSize }" sort="external" 
        requestURI="userMgmt!search.action">
        <display:column title="#"  style="text-align: center; width: 2%">
                <s:property value="${rowBean.rownum + 1 + (currentPage-1)*pageSize}"/>
            </display:column>
        <display:column property="username"
            titleKey="label.username" style="width: 15%" />
        <display:column  
            titleKey="activeUsers.fullName" style="width: 15%" >
            <a href="<s:url action='updateUser-${rowBean.id}'/>">${rowBean.fullName}</a>
        </display:column>
        <display:column property="email"
            titleKey="user.email" style="width: 15%" />
        <display:column property="companyTel"
            titleKey="user.phoneNumber" style="width: 5%" />
        <display:column property="accountLocked" 
            titleKey="user.accountLocked" style="width: 1%" />
    </display:table>
</s:if>
          
</s:form>

</body>

