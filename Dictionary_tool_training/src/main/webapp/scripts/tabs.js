/*
Dynamic Tabs 1.0
Copyright (c) 2005 Rob Allen (rob at akrabat dot com)

Permission is hereby granted, free of charge, to any person 
obtaining a copy of this software and associated documentation 
files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge,
publish, distribute, sublicense, and/or sell copies of the Software,
and to permit persons to whom the Software is furnished to do so,
subject to the following conditions:

The above copyright notice and this permission notice shall be 
included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, 
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES 
OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER 
DEALINGS IN THE SOFTWARE.
 
*/


function getChildElementsByClassName(parentElement, className)
{
	var i, childElements, pattern, result;
	result = new Array();
	pattern = new RegExp("\\b"+className+"\\b");


	childElements = parentElement.getElementsByTagName('*');
	for(i = 0; i < childElements.length; i++)
	{
		if(childElements[i].className.search(pattern) != -1)
		{
			result[result.length] = childElements[i];
		}
	}
	return result;
}


function ActivateTab(activeTabIndex)
{
	var i, tabContainer, tabContents;
	tabContainer = document.getElementById('SubMenu');
	tabContents = document.getElementsByClassName('TabContent');
//	tabContents = getChildElementsByClassName(tabContainer, 'TabContent');
	if(tabContents.length > 0)
	{
		for(i = 0; i < tabContents.length; i++)
		{
			tabContents[i].style.display = "none";
		}

		tabContents[activeTabIndex].style.display = "block";


		tabList = document.getElementById('TabList');
		tabs = getChildElementsByClassName(tabContainer, 'TabItem');
		if(tabs.length > 0)
		{
			for(i = 0; i < tabs.length; i++)
			{
				tabs[i].className = "TabItem";
			}

			tabs[activeTabIndex].className = "TabItem selected";
	//		tabs[activeTabIndex].blur();
		}
	}
}
