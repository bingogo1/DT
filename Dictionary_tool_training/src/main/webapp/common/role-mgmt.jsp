<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title><fmt:message key="roleSearchMenu.title" /></title>
    <meta name="menu" content="UserMgmtMenu"/>
    <content tag="heading"><fmt:message key="roleSearchMenu.title"/></content>
    <script type="text/javascript" src="<c:url value='/dwr/interface/roleManager.js'/>"></script>

<script language="javascript">
var isEdit = false;
var editLine;
var colsSequence;
var defaultValues;
var defaultCells;
var fieldNameStr = "Role Name,Short Name,Description";
var dbFieldNames = new Array("name", "shortName", "description");
var requiredFields = new Array("name");
var requiredFldName = new Array("Role Name");

function validation()
{
	var flag = false;
	// check if changed
	for (var i = 0; i < defaultCells.length; i++)
	{
		if ((requiredFields[i] != null && $(dbFieldNames[i]).id == requiredFields[i]) && $(dbFieldNames[i]).value.trim() == "")
		{
			flag = false;
			alert(requiredFldName[i] + "<fmt:message key='errors.field.required'/>");
			return flag;
		}
		
		if ($(dbFieldNames[i]).value.trim() != defaultValues[i])
			flag = true;
	}

	return flag;
}

function edit(lineIndex, recordID)
{
	// clicks same line and isEdit is true, submits these values if they are changed.
	if (isEdit)
	{
		if (lineIndex == editLine)
		{
			var flag = validation();
	
			// record is changed, submit it.
			if (flag)
			{
				var data = new Object();
				data["id"] = recordID;
				for (var i = 0; i < defaultCells.length; i++)
					data[dbFieldNames[i]] = $(dbFieldNames[i]).value;
	
				if (window.confirm("<fmt:message key='save.message'/>"))
					// submit action
					roleManager.update(data, callback);
				else
					reset();
			}
			else
				reset();
	
			isEdit = false;
			editLine = null;
			return;
		}
		else
		{
			if (window.confirm("<fmt:message key='unsaved.message'/>"))
				reset();
			else
				return;
		}
	}
	
	var tblCtl = $("rowBean");

	getContentCols(tblCtl);

	changeInput4Cell(tblCtl, lineIndex);

	isEdit = true;
	editLine = lineIndex;
}

// return an array of content column sequence (title sequence)
function getContentCols(tblCtl)
{
	if (colsSequence != null && colsSequence.length > 0)
		return colsSequence;

	colsSequence = new Array();
	
	var cols = tblCtl.getElementsByTagName("th");
	for (var i = 0; i < cols.length; i++)
	{
		if (cols[i].innerHTML.trim() != "" && fieldNameStr.indexOf(cols[i].innerHTML.trim()) >= 0)
			colsSequence.push(i);
	}
}

// alternate each cell content with inupt area
function changeInput4Cell(tblCtl, lineIndex)
{
	var tblBody = tblCtl.getElementsByTagName("tbody");
	var lines = tblBody[0].rows;
	var line = lines[lineIndex];

	cols = line.getElementsByTagName("td");

	defaultCells = new Array();
	defaultValues = new Array();

	for (var i = 0; i < colsSequence.length; i++)
	{
		defaultCells.push(cols[colsSequence[i]]);
		defaultValues.push(cols[colsSequence[i]].innerHTML);
		cols[colsSequence[i]].innerHTML = "<textarea id='" + dbFieldNames[i] + "'>" + defaultCells[i].innerHTML.trim();
	}
}

function reset()
{
	for (var i = 0; i < defaultCells.length; i++)
		defaultCells[i].innerHTML = defaultValues[i];
}

function callback(result)
{
	if (!result)
	{
		alert("Save failed. Please check existed role name, or contact with administrator.");
		reset();
		return;
	}

	for (var i = 0; i < defaultCells.length; i++)
		defaultCells[i].innerHTML = $(dbFieldNames[i]).value;
}

</script>
</head>

<body class="onecolumn">

  <div class="last">
    <table class="menuTabTable" cellspacing="0" cellpadding="3">
        <tr>
          <td class="menuTabUnselected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<a
                href="<s:url action="editProfile"/>"><fmt:message key="userProfile.heading"/></a>&nbsp;&nbsp;</div>
            </div>
          </td>
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
          <td class="menuTabSelected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<fmt:message key="roleSearchMenu.title"/>&nbsp;&nbsp;</div>
            </div>
          </td>
</authz:function>
        </tr>
    </table>
  </div>

<h1><fmt:message key="roleSearchMenu.title"/></h1>

<s:form action="roleMgmt" method="post" theme="simple" validate="true">
<%@ include file="/common/messages.jsp"%>
  <table class="inputTable" border="0" cellpadding="0" cellspacing="0" width="50%"> 
    <tr>
      <td class="label" width="50%"><s:label key="role.name.title" /></td>
      <td class="ctrl" width="50%" ><s:textfield name="role.name" label="roleName"/></td>
    </tr>
  </table>
        <br/>
        <s:submit cssClass="button" name="buttonName" key="button.search"
               onclick="form.action='roleMgmt!search.action';return true;" />
        <s:submit cssClass="button" name="buttonName" key="button.add"
               onclick="form.action='roleMgmt!edit.action';return true;" />
        <br/> 
        
<s:if test="paginatedList != null">
    <display:table name="paginatedList" cellspacing="1" cellpadding="1"
        id="paginatedList" uid="rowBean" class="table" 
        pagesize="${pageSize}" partialList="true" size="${totalSize}" sort="external" 
        requestURI="roleMgmt!search.action">
        <display:column title=""  style="text-align: center; width: 2%">
                <s:property value="${rowBean.rownum + 1 + (currentPage-1)*pageSize}"/>
            </display:column>
        <display:column style="text-align: center; width: 1%" media="html" titleKey="button.edit">
            <a href="#" onclick="javascript:edit('${rowBean.rownum}', '${rowBean.id}')">
            	<img id="edit-${rowBean.id}" alt="Edit & Save" src="<s:url value='./images/edit_icon.gif'/>"></a>
        </display:column>
        
        <display:column property="name"
            titleKey="role.name.title" style="width: 30%" />
        <display:column property="shortName"
            titleKey="role.short.name" style="width: 20%"/>
        <display:column property="description"
            titleKey="description" style="width: 40%"/>
    </display:table>
</s:if>
          
</s:form>

</body>