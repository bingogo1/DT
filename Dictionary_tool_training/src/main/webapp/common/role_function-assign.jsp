<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
    <title><fmt:message key="RoleFunctionAssign.title" /></title>
    <meta name="menu" content="UserMgmtMenu"/>
    <meta name="heading" content="<fmt:message key='RoleFunctionAssign.title'/>" />
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
    var obj = $("assignedFunction");
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
<authz:function ifAnyGranted="assignUserRole.action">
          <td class="menuTabSeparator"></td>
          <td class="menuTabUnselected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<a
                href="<s:url action="assignUserRole"/>"><fmt:message key="userRoleAssign.title"/></a>&nbsp;&nbsp;</div>
            </div>
          </td>
</authz:function>
          <td class="menuTabSeparator"></td>
          <td class="menuTabSelected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<fmt:message key="RoleFunctionAssign.title"/>&nbsp;&nbsp;</div>
            </div>
          </td>
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
<h1><fmt:message key="RoleFunctionAssign.title" /></h1>
<s:form action="assignRoleFunction" method="post" theme="simple" name="assignForm">
  <!-- business area -->
  <table class="inputTable" border="0" cellpadding="0" cellspacing="0" width="98%"> 
    <tr>
      <td class="label" width="15%"><s:label key="role.name.title" /></td>
      <td class="ctrl" width="15%">
        <s:select list="roleDropdownList" id="role" name="role.id" 
            listKey="value" listValue="label" headerKey="" headerValue="--Please Select--"
        />
      </td>
    </tr>
  </table>
  <s:submit cssClass="button" name="buttonName" key="button.assign"
            onclick="form.action='assignRoleFunction!assignCheck.action';return true;" />
  </br>
<s:if test="showAssignAreaFlg">
  <table class="inputTable" width="98%">
    <tr >
      <td  width="200px" ><h3><s:label key="assign.unassign.function" /></h3></td>
      <td  width="50px" ></td>
      <td  width="200px" ><h3><s:label key="assign.assigned.function" /></h3></td>
      
    </tr>
    <tr>
      <td valign="center"  class="ctrl" ><s:select id="unassignFunction"
             name="unassignFunctionID"
             list="unassignFunctionList"
             listKey="value"
             listValue="label"
             multiple="true"
             cssStyle="width:400px;"
             size="10"
             required="false"
             value="unassignFunctionID"
             ondblclick="selectIn(this.form.unassignFunction,this.form.assignedFunction)"/></td>
      <td align="center" width="5%"> <input name="leftToright" type="button" value="&nbsp;&nbsp;Add&nbsp;&nbsp;>>&nbsp;&nbsp;" fontsize="10"
             onClick="selectIn(this.form.unassignFunction,this.form.assignedFunction)"/>
        <br/>
        <br/>
        <input name="rightToleft" type="button"  value="Remove<<" 
             onclick="selectIn(this.form.assignedFunction, this.form.unassignFunction)"/>   
      </td>
      <td valign="center"  class="ctrl" >    <s:select id="assignedFunction"
             name="assignedFunctionID"
             list="assignedFunctionList"
             listKey="value"
             listValue="label"
             multiple="true"
             cssStyle="width:400px;"
             size="10"
             required="false"
             ondblclick="selectIn(this.form.assignedFunction,this.form.unassignFunction)"/></td>   
    </tr>  
    
  </table>
  <br/>
  <s:submit cssClass="button" name="buttonName" key="button.submit"
            onclick="javascript:setSelectOptions();form.action='assignRoleFunction!assign.action';return true;" />
  <br/> 
</s:if>
</s:form>
</body>
   
    
  