var accountAllowedForbiddenToggle = {
		
	toggle: true,

	toggleEnable: function(){
		this.toggle = true;
		
		this.showElement('allowed-accounts-table');
		this.showElement('allowed-accounts-control-buttons');
		this.hideElement('forbidden-accounts-table');
		this.hideElement('forbidden-accounts-control-buttons');

		workWithAccountsTable.unselectAllCheckboxes();
	},

	toggleDisable: function(){
		this.toggle = false;
		
		this.showElement('forbidden-accounts-table');
		this.showElement('forbidden-accounts-control-buttons');
		this.hideElement('allowed-accounts-table');
		this.hideElement('allowed-accounts-control-buttons');
		
		workWithAccountsTable.unselectAllCheckboxes();
	},
	
	hideElement : function(id) {
		if (document.getElementById(id)) {
			var element = document.getElementById(id);
			if (element.style.display != "none") {
				element.style.display = "none";
			}
		}
	},

	showElement : function(id) {
		if (document.getElementById(id)) {
			var element = document.getElementById(id);
			if (element.style.display != "block") {
				element.style.display = "block";
			}
		}
	}
}