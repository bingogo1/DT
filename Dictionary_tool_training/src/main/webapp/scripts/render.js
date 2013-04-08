Render_base = Class.create();

Render_base.prototype = {
	initialize: function(cell, grid) {
		this.grid = grid;
		this.cell = cell;
	},

	getRenderHTML: function(val) {}
}

Render_text = Class.create();

Object.extend(Object.extend(Render_text.prototype, Render_base.prototype), {
	getRenderHTML: function(val) {
		if (val == null || val == "") {
			return "&nbsp;";
		}
		//return val; --> update by zxu on 2008/01/18 for '&' can not display.
		return val.replace(/&/g,'&amp;');
	}
});

Render_check = Class.create();

Object.extend(Object.extend(Render_check.prototype, Render_base.prototype), {
	getRenderHTML: function(val) {
		if (val == "") {
			val = "0";
		}
		if (val == "1" || val == "0") {
			return "<img src='"+this.grid.imgURL+"item_chk"+val+".gif'>";
		} else if (val == null) {
			return "&nbsp;";
		} else
			return val;
	}
});

Render_radio = Class.create();

Object.extend(Object.extend(Render_radio.prototype, Render_base.prototype), {
	getRenderHTML: function(val) {
		if (val == "") {
			val = "0";
		}
		if (val == "1" || val == "0") {
			return "<img src='"+this.grid.imgURL+"radio_chk"+val+".gif'>";
		} else if (val == null) {
			return "&nbsp;";
		} else
			return val;
	}
});

Render_combo = Class.create();

Object.extend(Object.extend(Render_combo.prototype, Render_base.prototype), {
	initialize: function(cell, grid) {
		this.cell = cell;
		this.grid = grid;
		this.combo = this.grid.getCombo(this.grid.getAdjustedColIndex(this.cell));
	},

	getRenderHTML: function(val) {
		return val
	}
});

Render_select = Class.create();

Object.extend(Object.extend(Render_select.prototype, Render_combo.prototype), {
	initialize: function(cell, grid) {
		this.cell = cell;
		this.grid = grid;
		this.combo = this.grid.getCombo(this.grid.getAdjustedColIndex(this.cell));
	},

	getRenderHTML: function(val) {
		return this.combo.get(val)
	}
});

Render_number = Class.create();

Object.extend(Object.extend(Render_number.prototype, Render_base.prototype), {
	getRenderHTML: function(val) {
		if (val == null || val == "") {
			return "&nbsp;";
		}

		if(!parseInt(val)) return val;
		return formatNumber(""+val);

	}

});

Render_date = Class.create();

Object.extend(Object.extend(Render_date.prototype, Render_base.prototype), {
	getRenderHTML: function(val) {
		if (val == null || val == "") {
			return "&nbsp;";
		}

		return val.format(this.grid.dateFormat);

	}
});

Render_price = Class.create();

Object.extend(Object.extend(Render_price.prototype, Render_number.prototype), {
	getRenderHTML: function(val) {
		if (val == null || val == "") {
			return "&nbsp;";
		}

		if(!parseInt(val)) return val;
		return this.formatNumber(""+val);

	}
});

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

