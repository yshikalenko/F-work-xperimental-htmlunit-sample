"use strict";


if(typeof String.prototype.trim !== 'function') { 
    String.prototype.trim = function() {
      return this.replace(/^\s\s*/, '').replace(/\s\s*$/, ''); 
    };
}

function applyMarital(value) {
    var el = document.getElementById("_spouse_name_row");
    if (el) {
        var cname = el.className;    
        if ("single" === value) {
            if (!cname) { 
                cname = "hidden";
            } else if (!cname.includes("hidden")) {
                cname += " hidden";
            } 
        } else if ("married" === value) {
            cname = cname.replace("hidden", "");
            cname = cname.trim();
        }        
        el.className = cname;
    }
}
