<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
    <title><fmt:message key="userRoleAssign.title" /></title>
    <meta name="menu" content="UserMgmtMenu"/>
    <meta name="heading" content="<fmt:message key='userRoleAssign.title'/>" />
<script language="JavaScript">
//select multiple
function selectIn(objFrom,objTo){
   for(var i = 0; i < objFrom.options.length; i ++){
       if(objFrom.options[i].selected==true){
           var flag = true;
           for(var j = 0; j < objTo.options.length; j ++){
               if(objTo.options[j].value==objFrom.options[i].value){
                   flag = false;
                   break;
               }
           }
           if(flag){
               var opt = document.createElement("OPTION");
               opt.innerHTML = objFrom.options[i].innerHTML ;
               opt.value = objFrom.options[i].value ;
               opt.selected = true;
               objTo.appendChild(opt);    
               objFrom.remove(i);
               i--;
           }
       }
   }
}

function setSelectOptions(){
    var obj = $("assignedRole");
    for(var j = 0; j < obj.options.length; j ++){
        obj.options[j].selected=true;
    }
}

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
<authz:function ifAnyGranted="userMgmt.action">
          <td class="menuTabSeparator"></td>
          <td class="menuTabUnselected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<a
                href="<s:url action="userMgmt"/>"><fmt:message key="userListMenu.title"/></a>&nbsp;&nbsp;</div>
            </div>
          </td>
</authz:function>
          <td class="menuTabSeparator"></td>
          <td class="menuTabSelected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<fmt:message key="userRoleAssign.title"/>&nbsp;&nbsp;</div>
            </div>
          </td>
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
<%@ include file="/common/messages.jsp"%>
<h1><fmt:message key="userRoleAssign.title" /></h1>
<s:form action="assignUserRole" method="post" theme="simple" name="assignForm">
  <!-- business area -->
  <table class="inputTable" border="0" cellpadding="0" cellspacing="0" width="98%"> 
    <tr>
      <td class="label" width="15%"><s:label key="label.username" /></td>
      <td class="ctrl" width="15%">
        <s:select list="userDropdownList" id="user" name="user.id" 
            listKey="value" listValue="label" headerKey="" headerValue="--Please Select--"
        />
      </td>
    </tr>
  </table>
  <s:submit cssClass="button" name="buttonName" key="button.assign"
            onclick="form.action='assignUserRole!assignCheck.action';return true;" />
  </br>
<s:if test="showAssignAreaFlg">
  <table class="inputTable" width="98%">
    <tr >
      <td  width="200px" ><h3><s:label key="assign.unassign.role" /></h3></td>
      <td  width="50px" ></td>
      <td  width="200px" ><h3><s:label key="assign.assigned.role" /></h3></td>
      
    </tr>
    <tr>
      <td valign="center"  class="ctrl" ><s:select id="unassignRole"
             name="unassignRoleID"
             list="unassignRoleList"
             listKey="value"
             listValue="label"
             multiple="true"
             cssStyle="width:400px;"
             size="10"
             required="false"
             value="unassignRoleID"
             ondblclick="selectIn(this.form.unassignRole,this.form.assignedRole)"/></td>
      <td align="center" width="5%"> <input name="leftToright" type="button" value="&nbsp;&nbsp;Add&nbsp;&nbsp;>>&nbsp;&nbsp;" fontsize="10"
             onClick="selectIn(this.form.unassignRole,this.form.assignedRole)"/>
        <br/>
        <br/>
        <input name="rightToleft" type="button"  value="Remove<<" 
             onclick="selectIn(this.form.assignedRole, this.form.unassignRole)"/>   
      </td>
      <td valign="center"  class="ctrl" >    <s:select id="assignedRole"
             name="assignedRoleID"
             list="assignedRoleList"
             listKey="value"
             listValue="label"
             multiple="true"
             cssStyle="width:400px;"
             size="10"
             required="false"
             ondblclick="selectIn(this.form.assignedRole,this.form.unassignRole)"/></td>   
    </tr>  
    
  </table>
  <br/>
  <s:submit cssClass="button" name="buttonName" key="button.submit"
            onclick="javascript:setSelectOptions();form.action='assignUserRole!assign.action';return true;" />
  <br/> 
</s:if>
</s:form>
</body>
   
    
  