var editProfile = {	

	addTattooStyle : function (id, name) {        
		var style = document.getElementById("tattoo-style-" + id);
        if(style == null){
            var styleList = document.getElementById("edit-profile-own-style-list");
            styleList.innerHTML += "<li id='tattoo-style-" + id + "' class='edit-profile-style-list-item'>" + name + 
                "<a class='edit-profile-style-item-remove' href='#' onclick='editProfile.removeTattooStyle(" + id + ")'>" + 
                "<i class='fa fa-times fa-lg' aria-hidden='true'></i></a>" + 
				"<input type='hidden' name='edit_profile_tattoo_style_" + id + "' value='" + id + "'/></li>";
            
        }
	},

	removeTattooStyle : function (id) {		
        var parent = document.getElementById("edit-profile-own-style-list");
        var child = document.getElementById("tattoo-style-" + id);
        parent.removeChild(child);
	},
	
	updateInputValue(id){		
		document.getElementById(id).setAttribute("value", $("#" + id).val());
	},
}