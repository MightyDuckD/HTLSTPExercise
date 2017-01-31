/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


;
(function (self) {
    var dragging = false;
    var oldX, oldY;
    var camX = 0, camY = 0;
    var max = Math.max;
    var min = Math.min;
    var zoom = 50;

    //Resize the canvas to 
    $(document).ready(function () {
        $("#glcanvas") //TODO: test if working correct
                .mousedown(function (e) {
                    dragging = true;
                    oldX = e.screenX;
                    oldY = e.screenY;
                    return pauseEvent(e);
                })
                .each(self.resizeCanvas).each(self.redraw);
        $(window).resize(function (e) {
            $("#glcanvas").each(self.resizeCanvas).each(self.redraw);
        });
    });
    $(window).mouseup(function (e) {
        if (dragging) {
            dragging = false;
            $('html,body').removeClass("dragging");
            return pauseEvent(e);
        }
    });
    $(window).mousemove(function (e) {
        if (dragging) {
            $('html,body').addClass("dragging");
            camX += (e.screenX - oldX);
            camY -= (e.screenY - oldY);
            oldX = e.screenX;
            oldY = e.screenY;
            $("#glcanvas").each(self.redraw);
        }
    });

    self.resizeCanvas = function (id, elem) {
        elem.style.width = '100%';
        elem.style.height = '100%';
        elem.width = $("#animationframe").width();
        elem.height = $("#animationframe").height();
    };

    self.renderText = function (ctx, text, x, y) {
        //according to: http://usefulangle.com/post/18/javascript-html5-canvas-solving-problem-of-inverted-text-when-y-axis-flipped
        //if we just draw the text at a specific position the invertex Y-Axis would 
        //cause the text to be drawn upside down.
        //therefore we translate the origin to where the text should be drawn, flip 
        //the Y-Axis again and draw the text at the new origin.
        ctx.save();
        ctx.translate(x, y);
        ctx.scale(1, -1);
        ctx.fillText(text , 0, 0);
        ctx.restore();
    };
    self.renderCoordinateLines = function (ctx, width, height) {

        ///
        ctx.save();
        ctx.beginPath();
        ctx.strokeStyle = "grey";
        ctx.translate(0, max(min(camY, height - 5), 15));
        //draw the X-Axis
        ctx.moveTo(0, 0);
        ctx.lineTo(width, 0);
        //draw the little lines along the X-Axis
        for (var i = -1; i < (width / zoom) + 1; i++) {
            var delta = (camX % zoom) + i * zoom;
            ctx.moveTo(delta, -5);
            ctx.lineTo(delta, 5);
            self.renderText(ctx,(i * zoom) - (((camX/zoom)|0)*zoom),delta,-10);
        }
        ctx.stroke();
        ctx.restore();

        ///
        ctx.save();
        ctx.beginPath();
        ctx.strokeStyle = "grey";
        ctx.translate(max(min(camX, width - 5), 30), 0);
        //draw the Y-Axis
        ctx.moveTo(0, 0);
        ctx.lineTo(0, height);
        //draw the little lines along the Y-Axis
        var oldAlign = ctx.textAlign;
        ctx.textAlign="right";
        for (var i = -1; i < (height / zoom) + 1; i++) {
            var delta = (camY % zoom) + i * zoom;
            ctx.moveTo(-5, delta);
            ctx.lineTo(5, delta);
            self.renderText(ctx,(i * zoom) - (((camY/zoom)|0)*zoom),-10,delta);
        }
        ctx.textAlign = oldAlign;
        ctx.stroke();
        ctx.restore();

    };

    self.render = function (ctx, width, height) {
        ctx.clearRect(0, 0, width, height);
        ctx.save();
        ctx.transform(1, 0, 0, -1, 0, height);
        ctx.translate(0.5, 0.5);//+0.5 because of blurry lines
        self.renderCoordinateLines(ctx, width, height);
        ctx.translate(camX, camY);
        ctx.beginPath();
        ctx.strokeStyle = "red";
        ctx.rect(5, 5, 5, 5);
        ctx.stroke();
        ctx.restore();
    };

    self.redraw = function (id, elem) {
        self.render(elem.getContext("2d"), elem.width, elem.height);
    };

})(window.animationframe = window.animationframe || {});