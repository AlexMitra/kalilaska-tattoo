var addTattooEvent = {	

	addTattooConsultation : function (id, name) {        
//		var masterArea = document.getElementById("add-tattoo-consultation-master");
//        
//        masterArea.innerHTML = "<input type='hidden' name='add_tattoo_consultation_master' value='" + id + "'/>" + name + 
//            "<a class='add-tattoo-event-item-remove' href='#' onclick='addTattooEvent.removeTattooConsultation(" + id + ")'>" + 
//            "<i class='fa fa-times' aria-hidden='true'></i></a>";
        
        var masterIdInput = document.getElementById("add-tattoo-consultation-master-id");
		var masterNameInput = document.getElementById("add-tattoo-consultation-master-name");
        masterIdInput.value = id;
        masterNameInput.value = name;
        $('#add-tattoo-consultation-form').formValidation('revalidateField', 'add_tattoo_consultation_master_name');
	},

//	removeTattooConsultation : function (id) {
//        var masterArea = document.getElementById("add-tattoo-consultation-master");
//        masterArea.innerHTML = "";
//	},
    
    addTattooSeance : function (id, name) {
        var masterIdInput = document.getElementById("add-tattoo-seance-master-id");
		var masterNameInput = document.getElementById("add-tattoo-seance-master-name");
        masterIdInput.value = id;
        masterNameInput.value = name;
        $('#add-tattoo-seance-form').formValidation('revalidateField', 'add_tattoo_seance_master_name');
	}
}