/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


;
(function (self) {
    var dragging = false;



    //Resize the canvas to 
    $(document).ready(function () {
        $("#glcanvas") //TODO: test if working correct
                .css('width', '100%')
                .css('height', '100%')
                .attr('width', $(this).attr('offsetWidth'))
                .attr('height', $(this).attr('offsetHeight'))
                .mousedown(function (e) {
                    dragging = true;
                    return pauseEvent(e);
                });
    });
    $(window).mouseup(function (e) {
        if (dragging) {
            dragging = false;
            return pauseEvent(e);
        }
    });
    $(window).mousemove(function () {
        console.log(dragging);
    });



})(window.animationframe = window.animationframe || {});