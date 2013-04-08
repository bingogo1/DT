// calendar.js
// Author: Sherwin Disu
// Date: 04/23/2003

	var bName = navigator.appName;
	var bVer = parseInt(navigator.appVersion);
	
	var NS4 = (bName == "Netscape" && bVer >= 4);
	var IE4 = (bName == "Microsoft Internet Explorer" && bVer >= 4);
	var NS3 = (bName == "Netscape" && bVer < 4);
	var IE3 = (bName == "Microsoft Internet Explorer" && bVer < 4);

	function showCalendar(formObj, fieldObj) {
	  var width;
	  if (NS4 || NS3)
		{
			width = 205;
		}
		else if (IE4 || IE3)
		{
		  width=218;
		}
		var height = 228;
	  var left = (screen.width/2) - width/2;
	  var top = (screen.height/2) - height/2;
	  var styleStr = 'toolbar=no,location=no,directories=no,dependent=yes,alwaysRaised=yes,status=no,menubar=no,scrollbar=no,resizable=no,titlebar=no,copyhistory=yes,width='+width+',height='+height+',left='+left+',top='+top+',screenX='+left+',screenY='+top;
  	  document.forms[0].hField.value = fieldObj;
	  document.forms[0].hName.value = formObj;
	  var msgWindow = window.open("./scripts/calendar.htm","msgWindow", styleStr);
	  msgWindow.focus();
	}
	
	//A.Siva Sep 22, 2004.  New Calendar for SMP work flow
	
	function showNewCalendar(formObj, fieldObj) {
	  var width;
	  if (NS4 || NS3)
		{
			width = 215;
		}
		else if (IE4 || IE3)
		{
		  width=222;
		}
		//Modified by Ace Villafuerte 02/01/05 
		//var height = 238;
		var height = 270;
	  var left = (screen.width/2) - width/2;
	  var top = (screen.height/2) - height/2;
	  var styleStr = 'toolbar=no,location=no,directories=no,dependent=yes,alwaysRaised=yes,status=no,menubar=no,scrollbar=no,resizable=no,titlebar=no,copyhistory=yes,width='+width+',height='+height+',left='+left+',top='+top+',screenX='+left+',screenY='+top;
	  var msgWindow = window.open("calendarNew.html","msgWindow", styleStr);
  	  document.forms[0].hField.value = fieldObj;
	  document.forms[0].hName.value = formObj;
	  msgWindow.focus();
	}
	
	
	var dtFormat = "DD MMM YYYY";
var sepChar = ' ';
var fullDateMask = /^[0-3][0-9]\ [a-z,A-Z][a-z,A-Z][a-z,A-Z]\ [1-2][0-9][0-9][0-9]/;
var lastKeyStrokeVal;
var currMask;
var monthVal;
var dayVal;
var yearVal;
var autoFillVal = '=';
var day1Val;
var day2Val;



var months = new Array(13);
months[1] = "JAN";
months[2] = "FEB";
months[3] = "MAR";
months[4] = "APR";
months[5] = "MAY";
months[6] = "JUN";
months[7] = "JUL";
months[8] = "AUG";
months[9] = "SEP";
months[10] = "OCT";
months[11] = "NOV";
months[12] = "DEC";

function getMonthNumber(strMonth) {
	for(i=1; i < 13; i++) {
	 	var strMon = strMonth.toUpperCase();
	 	test = months[i];
		if(strMon == test) {
			return i;
		}
	}
	return 0;
}

//////////////////////////////////////////////////////////
// returnCurrentDate() : return the current system date //
// in MM/DD/YYYY format                                 //
//////////////////////////////////////////////////////////
function returnCurrentDate()
{
   d = new Date();
   m = parseInt(d.getMonth())+1;
   if (m < 10)
   {
      m = '0' + m;
   }
   return(m + sepChar + d.getDate() + sepChar + d.getYear());
}

////////////////////////////////////////////////////////////
// scrutinizeKeyVal() : apply mask to the keystroke value //
// when each keystroke is typed                           //
////////////////////////////////////////////////////////////
function scrutinizeKeyVal(obj)
{
   ////////////////////////////////////////////////////////////////////
   // If using IE, the "String.fromCharCode(window.event.keyCode)"   //
   // will return the key value pressed. For Netscape, the "which"   //
   // keyword will return the keyvalue. NOT TESTED WITH NETSCAPE YET //
   ////////////////////////////////////////////////////////////////////

   var length = parseInt(obj.value.length);

   lastKeyStrokeVal = String.fromCharCode(window.event.keyCode); // IE =Only

   if (lastKeyStrokeVal == autoFillVal)
   {
      obj.value = returnCurrentDate();
      return -1;
   }
   /////////////////////////////////////////////////////////
   // The date format is mm/dd/yyyy and leading zeros are //
   // required in the Months and Days fields              //
   /////////////////////////////////////////////////////////


 ///////////////////////////////////////////////////////
   // THE FOURTH CHARACTER TYPED - day field            //
   // The fourth char typed should be a number...0,1,2,3//
   // We can't check for leap year yet because we don't //
   // have the year value yet. This will need to be     //
   // done after the date field is populated...         //
   ///////////////////////////////////////////////////////

   //Modified by Ace Villafuerte 01/27/05
   //if (length == 0)   
   if (length == 0 || length == 11)   
   {
     currMask = /^[0-3]/;
     if (!compareValue(lastKeyStrokeVal, currMask))
     { 
        return -1;
     }
     day1Val = lastKeyStrokeVal;
   }
   //////////////////////////////////////////////////////////
   // THE FIFTH CHARACTER TYPED - day field                //
   // The fifth char typed should be a number              //
   // if first char is 0, second char may only be 1-9      //
   // if first char is 1 or 2, second char may only be 0-9 //
   // if first char is 3, second char may only be 0-1      //
   //////////////////////////////////////////////////////////
   if (length == 1)   
   {
     if (day1Val == 0)
     {
        currMask = /^[1-9]/;
     }
     else if ((day1Val == 1) || (day1Val == 2))
     {
        currMask = /^[0-9]/;
     }
     else if (day1Val == 3)
     {
        currMask = /^[0-1]/;
     }
     else
     {
        return -1;
     }
     if (!compareValue(lastKeyStrokeVal, currMask))
     {
        return -1;
     }
     day2Val = lastKeyStrokeVal;
     return 1;
   }

	if (length == 3 || length ==4 || length == 5)   
   {
     currMask = /^[a-z,A-Z]/;
     if (!compareValue(lastKeyStrokeVal, currMask))
     {
        return -1;
     }

      if(length == 5) {
		  monthVal = obj.value + lastKeyStrokeVal;  
	      return 1;
	  }
   }
/*
   /////////////////////////////////////////////
   // FIRST CHARACTER TYPED - month field     //
   // The first char typed should be a 0 or 1 //
   /////////////////////////////////////////////
   if (length == 3)   
   {
     currMask = /^[0-1]/;
     if (!compareValue(lastKeyStrokeVal, currMask))
     {
		return -1;
     }
   }
   ///////////////////////////////////////////////////////
   // SECOND CHARACTER TYPED - month field              //
   // if first char is 1, second char may only be 0,1,2 //
   ///////////////////////////////////////////////////////
   if (length == 4) 
   { 
     if (obj.value.charAt(length -1) == 1)
     {
        currMask = /^[0-2]/; //months 10,11,12
     }
     else
     {
        currMask = /^[1-9]/; //months 01-09
     }
     if (!compareValue(lastKeyStrokeVal, currMask)) 
     {
        return -1;
     }
     //////////////////////////////////
     // capture the month value and  //
     // Autofill the first delimiter //
     //////////////////////////////////
     
     monthVal = obj.value + lastKeyStrokeVal;            
     return 1;                                 
   }

   */
   ////////////////////////////////////////////
   // THE THIRD OR SIXTH CHARACTER TYPED     //
   // This character should be the delimiter //
   // char for the date format "DD MM YYYY"  //
   ////////////////////////////////////////////
   if ((length == 2) || (length == 6)) 
   {
      currMask = /^\//;
      if (!compareValue(lastKeyStrokeVal, currMask))
      {
         return -1;
      }
   }
  
   //////////////////////////////////////////////////////////////
   // THE SEVENTH CHARACTER TYPED - year field                 //
   // Safe to assume this character is going to be a 1 or 2... //
   //////////////////////////////////////////////////////////////
   if (length == 7)   
   {
      currMask = /^[1-2]/;
      if (!compareValue(lastKeyStrokeVal, currMask))
      {
         return -1;
      }
   }
   ///////////////////////////////////////////////////////////
   // THE EIGHTH, NINTH, TENTH CHARACTER TYPED - year field //
   ///////////////////////////////////////////////////////////
   if ((length == 8) || (length == 9) || (length == 10))  
   {
      currMask = /^[0-9]/;
      if (!compareValue(lastKeyStrokeVal, currMask))
      {
         return -1;
      }
   }
   
   temp = obj.value;
   obj.value = temp.toUpperCase();
   /////////////////////////////////////////////////////////
   // Finally, do a mask check for the date val so far... //
   /////////////////////////////////////////////////////////
   if (compareValue(lastKeyStrokeVal, currMask))
   {
      return 0;
   }
   else
   {
      return -1;
   }
} //end scrutinizeKeyVal()

//////////////////////////////////////////////////////////
// processKeyPress(): Check the value of each keystroke //
// as they are typed                                    //
//////////////////////////////////////////////////////////
function processKeyPress(obj)
{
    var retVal = scrutinizeKeyVal(obj);
  
   if (retVal == -1) // scrutinizeKeyVal returned false: Key value =does not match mask //
   {
      return false;
   }
   else if (retVal == 0) // scrutinizeKeyVal returned true: Key =value does match mask //
   {
		return true;
   }
   else if (retVal == 1) // scrutinizeKeyVal encountered delimiter = character //
   {
      //////////////////////////////////////////////////////
      // This will cancel the current keypress event and  //
      // force the separator char to be appended in field //
      //////////////////////////////////////////////////////

      obj.value = obj.value + lastKeyStrokeVal + sepChar;
      return false;
   }
} //end processKeyPress()


////////////////////////////////////////////////////////
// isValidDate(): Determines if a date value is valid //
// Uses the date format "MM/DD/YYYY"                  //
////////////////////////////////////////////////////////
function isValidDate(obj)      
{
   var s = new String;
   s = obj.value;
   dayVal = s.charAt(0) + s.charAt(1);
   monthVal = s.charAt(3) + s.charAt(4) + s.charAt(5)
   yearVal = s.charAt(7) + s.charAt(8) + s.charAt(9) + s.charAt(10);
 
   monthVal = getMonthNumber(monthVal);
   
   if (monthVal == 0 || (parseInt(dayVal) > parseInt(daysInMonth(monthVal))) )
   {  
      obj.focus();
      obj.select();
      return false;
   }
   return true;
}  //end isValidDate()


/////////////////////////////////////////////
// daysInMonth(): Determines the number of //
// allowable days in a month.              //                     
/////////////////////////////////////////////
function daysInMonth(charMonth)
{
   if ((charMonth == months[1]) || (charMonth == months[3]) || (charMonth == months[5])
       || (charMonth == months[7]) || (charMonth == months[8]) || (charMonth == months[10])
       || (charMonth == months[12]))
	   return 31;

   if (charMonth == months[2])
   {
      if (isLeapYear(yearVal))
         return 29;
      return 28;
   }

   if ((charMonth == months[4]) || (charMonth == months[6]) || (charMonth === months[9])
       || (charMonth == months[11]))
      return 30;
}


//////////////////////////////////////////////
// isLeapYear(): Determines if year is leap //
//////////////////////////////////////////////
function isLeapYear(intYear)
{
   if ((intYear % 100 == 0) && (intYear % 400 == 0))
   {
      return true;
   }
   else
   {
      if ((intYear % 4) == 0)
         return true;
      return false;
   }
}


//////////////////////////////////////////////////
// compareValue(): Compares a value to its mask //
// (both args are passed in)                    //
//////////////////////////////////////////////////
function compareValue(cmpVal, mask)
{
  if(!cmpVal.match(mask))
  {
     return false;
  }
  else
  {
     return true;
  }
}
//Ace Villafuerte 01/28/05 
//New Date Formatting
function formatDate(obj)
{
	var formName = obj.name;
	var docuForm = eval('document.forms[0].'+formName);
	var displayDay;
	var displayMonth;
	var displayYear;
	var slash = '/';
	var space = ' ';
	var dateValue = obj.value;
	var length = parseInt(obj.value.length);
	var slashPos1 = dateValue.indexOf(slash);
	if(slashPos1 == -1)
	{
		var spacePos1 = dateValue.indexOf(space);
		if(spacePos1 == -1)
		{
			alert(INVALID_DT_FRMT);
			docuForm.focus();
			return;
		}
		else
		{
			dayVal = dateValue.substring(0, spacePos1);
			displayDay = getDay(dayVal);
			if(displayDay < 0)
			{
				alert(INVALID_DT);
				docuForm.focus();
				return;
			}
			else
			{
				displayDay = displayDay;
			}
			
			var secondPart = dateValue.substring(spacePos1+1, length);
			var spacePos2 = secondPart.indexOf(space);
			if(spacePos2 == -1)
			{
				alert(INVALID_DT_FRMT);
				docuForm.focus();
				return;
			}
			else
			{
				var month = secondPart.substring(0, spacePos2);
				var month = month.toUpperCase();
				monthVal = getMonth(month, space);
				if(monthVal < 0)
				{
					alert(INVALID_DT_FRMT);
					docuForm.focus();
					return;
				}
				else
				{
					displayMonth = months[monthVal];
				}
			}
			var secondPartLen = parseInt(secondPart.length);
			displayYear = secondPart.substring(spacePos2+1, length);
			displayYear = getYear(displayYear);
			if(displayYear < 0)
			{
				alert(INVALID_DT_FRMT);
				docuForm.focus();
				return;
			}
			else
			{
				displayYear = displayYear;
			}
		}
	}
	else
	{
		var month = dateValue.substring(0, slashPos1);
		monthVal = getMonth(month, slash);		
		if(monthVal < 0)
		{
			alert(INVALID_DT_FRMT);
			docuForm.focus();
			return;
		}
		else
		{
			displayMonth = months[monthVal];
		}
		
		var secondPart = dateValue.substring(slashPos1+1, length);
		var slashPos2 = secondPart.indexOf(slash);
		if(slashPos2 == -1)
		{
			alert(INVALID_DT_FRMT);
			docuForm.focus();
			return;
		}
		else
		{
			dayVal = secondPart.substring(0, slashPos2);
			displayDay = getDay(dayVal);
			if(displayDay < 0)
			{
				alert(INVALID_DT);
				docuForm.focus();
				return;
			}
			else
			{
				displayDay = displayDay;
			}
		}
		
		var secondPartLen = parseInt(secondPart.length);
		displayYear = secondPart.substring(slashPos2+1, length);
		displayYear = getYear(displayYear);
		if(displayYear < 0)
		{
			alert(INVALID_DT_FRMT);
			docuForm.focus();
			return;
		}
		else
		{
			displayYear = displayYear;
		}
	}
	if(displayMonth == months[2] && displayDay > 28)
	{
		if(displayDay == 29)
		{
			if(isLeapYear(displayYear))
			{
				docuForm.value = displayDay+' '+displayMonth+' '+displayYear;
			}
			else
			{
				alert(INVALID_LP_YR_DT);
				docuForm.focus();
				return;
			}
		}
		else
		{
			alert(INVALID_DT);
			docuForm.focus();
			return;
		}
	}
	else
	{
		docuForm.value = displayDay+' '+displayMonth+' '+displayYear;
	}
}

function getMonth(month, separator)
{
	if(separator == '/')
	{
		var monthLen = parseInt(month.length);
		if(monthLen == 1)
		{
			currMask = /^[0-9]/;
			if(compareValue(month, currMask))
			{
				return month;
			}
			else
			{
				return -1;
			}
		}	
		if(monthLen == 2)
		{
			var month1 = month.substring(0, 1);
			var month2 = month.substring(1, 2);
			if(month1 == '0')
			{
				currMask = /^[0-9]/;
				if(compareValue(month2, currMask))
				{
					return month2;
				}
				else
				{
					return -1;
				}
			}
			else if(month1 == '1')
			{
				currMask = /^[0-2]/;
				if(compareValue(month2, currMask))
				{
					return month;
				}
				else
				{
					return -1;
				}
			}
			else
			{
				return -1;
			}
		}
	}
	else
	{
		if(month == months[1])
			return 1;
		else if(month == months[2])
			return 2;
		else if(month == months[3])
			return 3;
		else if(month == months[4])
			return 4;
		else if(month == months[5])
			return 5;
		else if(month == months[6])
			return 6;
		else if(month == months[7])
			return 7;
		else if(month == months[8])
			return 8;
		else if(month == months[9])
			return 9;
		else if(month == months[10])
			return 10;
		else if(month == months[11])
			return 11;
		else if(month == months[12])
			return 12;
		else
			return -1;
	}
}

function getDay(day)
{
	var dayLen = parseInt(day.length);
	if(dayLen == 1)
	{
		currMask = /^[0-9]/;
		if(compareValue(day, currMask))
		{
			displayDay = '0'+day;
			return displayDay;
		}
		else
		{
			return -1;
		}
	}
	if(dayLen == 2)
	{
		day1 = day.substring(0, 1);
		day2 = day.substring(1, 2);
		if(day1 == '0')
		{
			displayDay = day;
			return displayDay;
		}
		else
		{
			if(day <= 31)
			{
				displayDay = day;
				return displayDay;
			}
			else
			{
				return -1;
			}
		}
	}
}

function getYear(year)
{
	var yearLen = parseInt(year.length);
	if(yearLen <= 4)
	{
		if(yearLen == 3 || yearLen <= 1)
		{
			return -1;
		}
		if (yearLen == 2)
		{
			if(year < 79) // highest date in dbase is 2078
			{
				year = '20'+year;
				return year;
			}
			else
			{
				year = '19'+year;
				return year;
			}
		}
		if(yearLen == 4)
		{
			if(year > 2078)
			{
				return -1;
			}
			else if(year < 1900)
			{
				return -1;
			}
			else
			{
				return year;
			}
		}
	}
	else
	{
		return -1;
	}
}