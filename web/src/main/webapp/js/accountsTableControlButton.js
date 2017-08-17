var accountsTableAddButton = {

	showDialog: function(){
		this.cleanForm();
		document.getElementById("add-account-add-button").disabled = true;
		$('#add-account-dialog').on('show.bs.modal', function (event) {
		    var button = $(event.relatedTarget)
		})
	},

	addAccount: function () {
		document.getElementById("add-account-dialog-close").click();
	},

	cleanForm: function(){
		document.getElementById("add-account-form").reset();		
	}
}


var accountsTableEditButton = {

	checkboxId: null,

	accountId: null,

	accountName: null,

	accountEmail: null,
	
	accountPhone: null,

	accountRole: null,

	baseTitle: null,

	showDialog: function(){
		this.showTitle();
		this.fillForm();

		$('#edit-account-dialog').on('show.bs.modal', function (event) {
			var button = $(event.relatedTarget)
		})
	},

	showTitle: function(){
		if(this.baseTitle == null){
			this.baseTitle = document.getElementById("edit-account-dialog-title").innerHTML;
		}

		if(workWithAccountsTable.selectedAccountsArr.length > 0){

			this.checkboxId = workWithAccountsTable.selectedAccountsArr[0];
			this.accountId = this.checkboxId.replace("checkbox-account-", "");

			this.accountName = document.getElementById("accountName-" + this.accountId).innerHTML;			

			var element = document.getElementById("edit-account-dialog-title");
			element.innerHTML = this.baseTitle + " " + this.accountName;
		}
	},

	fillForm: function(){
		this.cleanForm();		

		document.getElementById("edit-account-id").setAttribute("value", this.accountId);
		document.getElementById("edit-account-name").setAttribute("value", this.accountName);

		this.accountEmail = document.getElementById("accountEmail-" + this.accountId).innerHTML;
		
		this.accountPhone = document.getElementById("accountPhone-" + this.accountId).innerHTML;
		if(this.accountPhone == "-" || this.accountPhone.length <= 1){
			this.accountPhone = "";
		}		
		this.accountRole = document.getElementById("accountRole-" + this.accountId).innerHTML;
		

		document.getElementById("edit-account-email").setAttribute("value", this.accountEmail);
		document.getElementById("edit-account-phone").setAttribute("value", this.accountPhone);
		document.getElementById("edit-account-role").setAttribute("value", this.accountRole);

		this.addRoleMarker();
	},

	addRoleMarker: function(){
		var roleMarker = '<span class="role-marker">' + this.accountRole + '</span> ';		
		document.getElementById("edit-account-role-marker").innerHTML = roleMarker;
	},

	changeRole: function(roleId){
		this.accountRole = roleId.replace("li-", "");
		this.addRoleMarker();
		document.getElementById("edit-account-role").setAttribute("value", this.accountRole);
	},
	
	updateInputValue(id){		
		document.getElementById(id).setAttribute("value", $("#" + id).val());
	},

	editAccount: function () {		
		this.cleanForm();
		document.getElementById("edit-account-dialog-close").click();

		workWithAccountsTable.unselectAllCheckboxes();
		workWithAccountsTable.switchAccountsTableButtons();

	},

	cleanForm: function(){
		document.getElementById("edit-account-form").reset();			
	}
}

var accountsTableForbideButton = {

	checkboxId: null,

	accountId: null,

	baseTitle: null,

	showDialog: function(){
		this.writeDialogContent();

		$('#forbide-account-dialog').on('show.bs.modal', function (event) {
			var button = $(event.relatedTarget)
		})

	},

	writeDialogContent: function(){
		if(this.baseTitle == null){
			this.baseTitle = document.getElementById("forbide-account-dialog-title").innerHTML;
		}

		if(workWithAccountsTable.selectedAccountsArr.length > 0){

			this.checkboxId = workWithAccountsTable.selectedAccountsArr[0];
			this.accountId = this.checkboxId.replace("checkbox-account-", "");

			var accountName = document.getElementById("accountName-" + this.accountId).innerHTML;

			var element = document.getElementById("forbide-account-dialog-title");
			element.innerHTML = this.baseTitle + accountName;
			
			document.getElementById("forbide-account-id-input").setAttribute("value", this.accountId);
		}
	},


	forbideAccount: function(){
		document.getElementById("forbide-account-dialog-close").click();
		workWithAccountsTable.unselectAllCheckboxes();
		workWithAccountsTable.switchAccountsTableButtons();
	}

}


var accountsTableAllowButton = {

	checkboxId: null,

	accountId: null,

	baseTitle: null,

	showDialog: function(){
		this.writeDialogContent();

		$('#enable-account-dialog').on('show.bs.modal', function (event) {
				var button = $(event.relatedTarget)
		})

	},

	writeDialogContent: function(){
		if(this.baseTitle == null){
			this.baseTitle = document.getElementById("allow-account-dialog-title").innerHTML;
		}

		if(workWithAccountsTable.selectedAccountsArr.length > 0){

			this.checkboxId = workWithAccountsTable.selectedAccountsArr[0];
			this.accountId = this.checkboxId.replace("checkbox-account-", "");

			var accountName = document.getElementById("accountName-" + this.accountId).innerHTML;

			var element = document.getElementById("allow-account-dialog-title");
			element.innerHTML = this.baseTitle + accountName;
				
			document.getElementById("allow-account-id-input").setAttribute("value", this.accountId);
		}
	},

	allowAccount: function(){
		document.getElementById("allow-account-dialog-close").click();
		workWithAccountsTable.unselectAllCheckboxes();
		workWithAccountsTable.switchAccountsTableButtons();
	}
}

var accountsTableDeleteButton = {

	checkboxId: null,

	accountId: null,

	baseTitle: null,

	showDialog: function(){
		this.writeDialogContent();

		$('#delete-account-dialog').on('show.bs.modal', function (event) {
			var button = $(event.relatedTarget)
		})

	},

	writeDialogContent: function(){
		if(this.baseTitle == null){
			this.baseTitle = document.getElementById("delete-account-dialog-title").innerHTML;
		}

		if(workWithAccountsTable.selectedAccountsArr.length > 0){

			this.checkboxId = workWithAccountsTable.selectedAccountsArr[0];
			this.accountId = this.checkboxId.replace("checkbox-account-", "");

			var accountName = document.getElementById("accountName-" + this.accountId).innerHTML;

			var element = document.getElementById("delete-account-dialog-title");
			element.innerHTML = this.baseTitle + accountName;
			
			document.getElementById("delete-account-id-input").setAttribute("value", this.accountId);
		}
	},

	deleteAccount: function(){
		document.getElementById("delete-account-dialog-close").click();
		workWithAccountsTable.unselectAllCheckboxes();
		workWithAccountsTable.switchAccountsTableButtons();
	}
}
