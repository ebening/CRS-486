function isNumberKey(evt) {
    var charCode = (evt.which) ? evt.which : event.keyCode;
    if (charCode > 31 && (charCode != 46 && (charCode < 48 || charCode > 57)))
        return false;
    return true;
}

function onlyNumber(arg) {
    var x = arg;
    if (isNaN(x))
        x = "";
}


function prueba() {

    var str = "                   ";


    if (str.trim() == "") {
        alert("esta vasia");
    } else {
        alert("no esta vasia");
    }


}
