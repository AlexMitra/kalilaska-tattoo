var workWithAccountsTable = {
		
	selectedAccountsArr : [],
	
	selectAccount : function(id) {
		if (id.search("checkbox-") == -1) {
			id = "checkbox-" + id;
		}
		
		this.switchCheckbox(id);
		this.switchAccountsTableButtons();
	},

	unselectAll : function() {		
		this.switchAccountsTableButtons();
	},
	
	switchCheckbox : function(id) {

		if (document.getElementById(id).checked == true) {
			this.unselectCheckbox(id);
		} else {
			this.selectCheckbox(id);
		}
	},
	
	selectCheckbox : function(id) {

		document.getElementById(id).checked = true;
		var i = this.containCheckboxId(id);
		if (i == -1) {
			
			this.selectedAccountsArr.push(id);
		}
	},

	unselectCheckbox : function(id) {

		document.getElementById(id).checked = false;
		
		var i = this.containCheckboxId(id);
		if (i >= 0) {
			this.selectedAccountsArr.splice(i, 1);
		}
	},
	
	containCheckboxId : function(id) {
		for (var i = 0; i < this.selectedAccountsArr.length; i++) {
			if (this.selectedAccountsArr[i].toLowerCase() === id.toLowerCase()) {
				return i;
			}
		}
		return -1;
	},
	
	unselectAllCheckboxes : function() {

		for (var i = 0; this.selectedAccountsArr.length > 0;) {
			document.getElementById(this.selectedAccountsArr[i]).checked = false;			
			this.selectedAccountsArr.shift();
		}
		this.switchAccountsTableButtons();
	},
	
	switchAccountsTableButtons : function() {
		if (this.selectedAccountsArr.length > 0 && accountAllowedForbiddenToggle.toggle) {
			document.getElementById("unselect-all-account-button").disabled = false;
			document.getElementById("update-account-button").disabled = false;
			document.getElementById("forbide-account-button").disabled = false;
		} else if (this.selectedAccountsArr.length <= 0){
			document.getElementById("unselect-all-account-button").disabled = true;
			document.getElementById("update-account-button").disabled = true;
			document.getElementById("forbide-account-button").disabled = true;
			document.getElementById("allow-account-button").disabled = true;
			document.getElementById("delete-account-button").disabled = true;
		} else if (this.selectedAccountsArr.length > 0 && accountAllowedForbiddenToggle.toggle== false){
			document.getElementById("allow-account-button").disabled = false;
			document.getElementById("delete-account-button").disabled = false;
		}
	}
}

