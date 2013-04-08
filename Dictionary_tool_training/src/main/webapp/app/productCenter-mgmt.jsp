<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<title><fmt:message key="productCenterMgmtMenu.title" /></title>
    <meta name="menu" content="ProductMgmtMenu"/>
    <content tag="heading"><fmt:message key="productCenterMgmtMenu.title"/></content>
    <script type="text/javascript" src="<c:url value='/dwr/interface/productCenterManager.js'/>"></script>

<script language="javascript">
var isEdit = false;
var editLine;
var colsSequence;
var defaultValues;
var defaultCells;
var fieldNameStr = "Product Center,Description";
var dbFieldNames = new Array("centerName", "centerDescription");
var requiredFields = new Array("centerName");
var requiredFldName = new Array("Product Center");

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
					productCenterManager.update(data, callback);
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
		alert("Save failed. Please check existed product center name, or contact with administrator.");
		reset();
		return;
	}

	for (var i = 0; i < defaultCells.length; i++)
		defaultCells[i].innerHTML = $(dbFieldNames[i]).value;
}

</script>

</head>

<body class="onecolumn">
  <!-- Sub menu area -->
  <div class="last">
    <table class="menuTabTable" cellspacing="0" cellpadding="3">
        <tr>
<authz:function ifAnyGranted="productMgmt.action">
          <td class="menuTabUnselected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<a
                href="<s:url action="productMgmt"/>"><fmt:message key="productMgmtMenu.title"/></a>&nbsp;&nbsp;</div>
            </div>
            </td>
</authz:function>
          <td class="menuTabSeparator"></td>
          <td class="menuTabSelected">
            <div class="menu-tr">
              <div class="menu-tl">&nbsp;&nbsp;<fmt:message key="productCenterMgmtMenu.title"/>&nbsp;&nbsp;</div>
            </div>
          </td>
          <td class="menuTabSeparator"></td>
        </tr>
    </table>
  </div>

    <h1><fmt:message key="productCenterMgmtMenu.title"/></h1>
<s:form action="productCenterMgmt" method="post" theme="simple" validate="true">
<%@ include file="/common/messages.jsp"%>
  <table class="inputTable" border="0" cellpadding="0" cellspacing="0" width="50%"> 
    <tr>
      <td class="label" width="40%"><s:label key="productCenter" /></td>
      <td class="ctrl" width="100%" ><s:textfield name="productCenter.centerName" label="centerName"/></td>
    </tr>
  </table>
        <br/>
        <s:submit cssClass="button" name="buttonName" key="button.search"
               onclick="form.action='productCenterMgmt!search.action';return true;" />
        <s:submit cssClass="button" name="buttonName" key="button.add"
               onclick="form.action='productCenterMgmt!edit.action';return true;" />
        <br/> 
        
<s:if test="paginatedList != null">
    <display:table name="paginatedList" cellspacing="1" cellpadding="1"
        id="paginatedList" uid="rowBean" class="table" 
        pagesize="${pageSize}" partialList="true" size="${totalSize}" sort="external" 
        requestURI="productCenterMgmt!search.action">
        <display:column title=""  style="text-align: center; width: 2%">
                <s:property value="${rowBean.rownum + 1 + (currentPage-1)*pageSize}"/>
            </display:column>
        <display:column style="text-align: center; width: 1%" media="html" titleKey="button.edit">
            <a href="#" onclick="javascript:edit('${rowBean.rownum}', '${rowBean.id}')">
            	<img id="edit-${rowBean.id}" alt="Edit & Save" src="<s:url value='./images/edit_icon.gif'/>"></a>
        </display:column>
        
        <display:column 
            titleKey="productLine" style="width: 20%">
            [TBD]
        </display:column>
        <display:column property="centerName"
            titleKey="productCenter" style="width: 20%" />
        <display:column property="centerDescription"
            titleKey="description" style="width: 20%" />
    </display:table>
</s:if>
          
</s:form>

</body>