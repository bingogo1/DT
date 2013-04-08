	
<script>
	function deleteConfirm(formName, buttonName) {
		var selectedFlg = false;
//		alert("formName  " + formName);
//		alert("buttonName  " + buttonName);
	    if (buttonName != null &&
	    	buttonName.toUpperCase() == "DELETE") {
	  		for (j = 0;j < document.forms[formName].all.length;j ++){
	  		    if (document.forms[formName].elements[j]!= null && 
	  		    		document.forms[formName].elements[j].name.length > 8 && 
	  		 	    	document.forms[formName].elements[j].name.toUpperCase().substring("DELETE[") != -1 &&
	  		 	    	document.forms[formName].elements[j].checked == true){
	  		 		//find at least record
	  		 		selectedFlg = true;
	  		 		break;
	  			}
	  		}
		  	if (selectedFlg){
				var flag = window.confirm("<fmt:message key='delete.message'/>");
				if (!flag){
					return false;
				}		
				return true;
		  	}else {
		  		alert("<fmt:message key='select.message'/>");
		  		return false;
		  	}
  		}
  		return true;
	}
	
	function saveConfirm(formName, buttonName) {
		var selectedFlg = false;
//		alert("formName  " + formName);
//		alert("buttonName  " + buttonName);
	    if (buttonName != null &&
	    	buttonName.toUpperCase() == "SAVE") {
	  		for (j = 0;j < document.forms[formName].all.length;j ++){
	  		    if (document.forms[formName].elements[j]!= null && 
	  		    		document.forms[formName].elements[j].name.length > 6 && 
	  		 	    	document.forms[formName].elements[j].name.toUpperCase().substring("SAVE[") != -1 &&
	  		 	    	document.forms[formName].elements[j].checked == true){
	  		 		//find at least record
	  		 		selectedFlg = true;
	  		 		break;
	  			}
	  		}
		  	if (selectedFlg){
				var flag = window.confirm("<fmt:message key='save.message'/>");
				if (!flag){
					return false;
				}		
				return true;
		  	}else {
		  		alert("<fmt:message key='select.message'/>");
		  		return false;
		  	}
  		}
  		return true;
	}
	
	/** rounded to the number of digits specified by the precision setting
 	 * (if necessary), using the selected rounding scale 
 	 *
 	 *  x is to be rounded, y is precision
 	 *
	 * 	eg: x=12345.659, y=2, return 12345.66
	 */
	function round(x, y){
		var p = Math.pow(10,y);
		x *= p;
		x = Math.round(x);
		return x/p;	
	}
	
	/** rounded to the number of digits specified by the precision setting
 	 * (if necessary), using the selected rounding scale 
 	 *
 	 *  x is to be rounded, y is precision
 	 *
	 * 	eg: x=12345.659, y=2, return 12,345.66
	 */
	function roundAndFormatNumber(x, y){
		var p = Math.pow(10,y);
		x *= p;
		x = Math.round(x);

		var formated = formatNumber((x/p).toString());

		var decimalLength = formated.substr(formated.indexOf(".")+1, formated.length-1).length; 

		var decimalPart = "";
		if(formated.indexOf(".") < 0){
			decimalPart = ".";
			decimalLength = 0;
		}

		for(var i=decimalLength; i<y; i++){
			decimalPart += "0";				
		}

		if(decimalLength != y){
			formated += decimalPart;
		}
		
		return formated;
	}
	
	/** format a Number to ###,###.##
 	 *  @ param val must be a String
 	 *
	 */
	function formatNumber(val) {
		var k = val.indexOf('.');
		if(k==-1) k=val.length;
	
		var part = val.slice(0,k);
	
		var len = part.length;
		var sign = "";
		if(part.charAt(0) == '-') {
			sign = "-";
			len = part.length-1;
		}
	
		var count = Math.floor((len-1)/3);
		var str = reverse(part);
		var newstr = "";
		for(var i=0;i<count;i++) {
			newstr += str.slice(i*3, (i+1)*3);
			newstr += ",";
		}
		newstr += str.slice((count)*3, str.length);
		return reverse(newstr) + val.slice(k,val.length);
	}

	function reverse(str) {
		var ret = "";
		var len = str.length;
		for(var i=len-1;i>=0;i--) {
			ret += str.charAt(i);
		}
		return ret;
	}
	
	function saveSuccessfully(){
	  alert("<fmt:message key='saveSuccessfully'/>");
	}

</script>
    
