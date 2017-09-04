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

var consultationAllowedForbiddenToggle = {
		
		toggle: true,

		toggleEnable: function(){
			this.toggle = true;
			
			this.showElement('approved-consultations-table');
			
			this.hideElement('unapproved-consultations-table');
			this.hideElement('unapproved-consultations-control-buttons');

			workWithConsultationsTable.unselectAllCheckboxes();
		},

		toggleDisable: function(){
			this.toggle = false;
			
			this.showElement('unapproved-consultations-table');
			this.showElement('unapproved-consultations-control-buttons');
			
			this.hideElement('approved-consultations-table');
			
			workWithConsultationsTable.unselectAllCheckboxes();
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

var seanceAllowedForbiddenToggle = {
		
		toggle: true,

		toggleEnable: function(){
			this.toggle = true;
			
			this.showElement('approved-seances-table');
			
			this.hideElement('unapproved-seances-table');
		},

		toggleDisable: function(){
			this.toggle = false;
			
			this.showElement('unapproved-seances-table');			
			
			this.hideElement('approved-seances-table');
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