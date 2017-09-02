var workWithConsultationsTable = {
    selectedConsultation : null,
    
    selectedConsultationArr : [],
    
    selectConsultation : function(id) {
        if(this.selectedConsultation == null){
            var consultationId = id.replace("consultation-", "");
            this.selectedConsultation = consultationId;            
            document.getElementById("approve-consultation-id-input").setAttribute("value", consultationId);
            
        }
        
		if (id.search("checkbox-") == -1) {
			id = "checkbox-" + id;
		}		
		this.switchCheckbox(id);
        this.switchApproveButton();
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
			this.selectedConsultationArr.push(id);
		}
	},

	unselectCheckbox : function(id) {
		document.getElementById(id).checked = false;
		
		var i = this.containCheckboxId(id);
		if (i >= 0) {
			this.selectedConsultationArr.splice(i, 1);
		}
        if(this.selectedConsultationArr.length == 0){
            this.selectedConsultation = null;
            this.switchApproveButton();
            document.getElementById("approve-consultation-id-input").removeAttribute("value");
        }
	},
    
    containCheckboxId : function(id) {
		for (var i = 0; i < this.selectedConsultationArr.length; i++) {
			if (this.selectedConsultationArr[i].toLowerCase() === id.toLowerCase()) {
				return i;
			}
		}
		return -1;
	},
    
    switchApproveButton : function() {
		if (this.selectedConsultationArr.length > 0) {
			document.getElementById("approve-consultation-button").disabled = false;			
		} else {
			document.getElementById("approve-consultation-button").disabled = true;
        }
	}
}