function addToWarenkorb(spinner) {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/WebShop/WarenkorbEdit?do=add&id=' + spinner.id.replace(/spinner/, '') + '&amount=' + spinner.value);
    xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
    xhr.onload = function () {
        if (xhr.status === 200) {
            body.innerHTML += "<div id='message'>    <div style='padding: 5px;'>        <div id='inner-message' class='alert alert-danger'>            <button type='button' class='close' data-dismiss='alert'>&times;</button>            test error message        </div>    </div></div>";
        } else {
            alert('Request failed.  Returned status of ' + xhr.status);
        }
    };
    xhr.send();
}