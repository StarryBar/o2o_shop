/**
 * 
 */
$(function(){
	var productId = getQueryString('productId');
	var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId='
			+ productId;
	$.getJSON(productUrl,function(data){
		if(data.success){
			var product = data.product;
			
			$('#product-img').attr('src',product.imgAddr);
			$('#product-time').text(new Date(product.lastEditTime).Format("yyyy-MM-dd"));
			$('#product-name').text(product.productName);
			$('#product-desc').text(product.productDesc);
			if(product.normalPrice != undefined && product.promotionPrice!=undefined){
				$('#price').show();
				$('#normalPrice').html('<del>' + '￥' + product.normalPrice + '</del>');
				$('#promotionPrice').text('￥' + product.promotionPrice);
			}else if(product.normalPrice != undefined && product.promotionPrice == undefined){
				$('#price').show();
				$('#promotionPrice').text('￥' + product.normalPrice);
			}else if(product.normalPrice == undefined && product.promotionPrice != undefined){
				$('#promotionPrice').text('￥' + product.promotionPrice);
			}
			var imgListHtml = '';
			product.productImgList.map(function(item,index){
				imgListHtml += '<div> <img src="' + item.imgAddr
					+ '"width="100%"/></div>'
			});
			$('#imgList').html(imgListHtml);
		}
	});
	
	
	
	
	Date.prototype.Format = function(fmt){
		var o = {
			"M+": this.getMonth() + 1,
			"d+": this.getDate(),
			"h+": this.getHours(),
			"m+": this.getMinutes(),
			"s+": this.getSeconds(),
			"q+": Math.floor((this.getMonth()+3)/3),
			"S": this.getMilliseconds()
		};
		if(/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1,(this.getFullYear() + "")
					.substr(4-RegExp.$1.length));
		for(var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1,(RegExp.$1.length == 1)?(o[k])
						:(("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	}

	$('#me').click(function(){
		$.openPanel('#panel-right-demo');
	});
	
	$.init();
});