/*
 * This functions renders img two times onto the provided context. 
 * One time shifted by 'offset' with the composite op 'difference'
 * @param {CanvasRenderingContext2D} ctx 
 * @param {Image} img
 * @param {} offset
 */
var render = function (ctx, img, offset) {
	ctx.drawImage(img, 0, 0, img.width, img.height);
	ctx.globalCompositeOperation = 'difference';
	ctx.drawImage(img, offset, 0, img.width, img.height);
};