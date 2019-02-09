/**
 * 
 */
$(function(){
	var loading = false;
	var maxItems = 999;
	var pageSize = 3;
	var listUrl = '/o2o/frontend/listshops';
	var searchDivUrl = '/o2o/frontend/listshopspageinfo';
	var pageNum = 1;
	var parentId = getQueryString('parentId');
	var areaId = '';
	var shopCategoryId = '';
	var shopName = '';
	getSearchDivData();
	addItems(pageSize,pageNum);
	
	function getSearchDivData(){
		var url = searchDivUrl + '?' + 'parentId=' + parentId;
		$.getJSON(url,function(data){
			if(data.success){
				var shopCategoryList = data.shopCategoryList;
				var html = '';
				html += '<div class="row"><div class="col-33"><a href = "#" class="button" data-category-id="">全部类别</a></div>';
				shopCategoryList.map(function(item,index){
					html += '<div class="col-33"><a href="#" class="button" data-category-id='
						+ item.shopCategoryId
						+ '>'
						+ item.shopCategoryName
						+ '</a>'
						+ '</div>';
				});
				html += '</div>';
				$('#shoplist-search-div').html(html);
				var selectOptions = '<option value="">全部街道</option>';
				var areaList = data.areaList;
				areaList.map(function(item,index){
					selectOptions += '<option value="'
						+ item.areaId + '">'
						+ item.areaName + '</option>';
				});
				$('#area-search').html(selectOptions);
			}
		});
	}
	function addItems(pageSize,pageIndex){
		var url = listUrl + '?' + 'pageIndex=' + pageIndex + '&pageSize='
			+ pageSize + '&parentId=' + parentId + '&areaId=' + areaId
			+ '&shopCategoryId=' + shopCategoryId + '&shopName=' + shopName;
		loading = true;
		$.getJSON(url,function(data){
			if(data.success){
				maxItems = data.count;
				var html = '';
				data.shopList.map(function(item,index){
					html += '' + '<div class="card" data-shop-id="'
						+ item.shopId + '">' + '<div class="card-header">'
						+ item.shopName + '</div>'
						+ '<div class="card-content">'
						+ '<div class="list-block media-list">' + '<ul>'
						+ '<li class="item-content">'
						+ '<div class="item-media">' + '<img src="'
						+ item.shopImg + '"width="44">' + '</div>'
						+ '<div class="item-inner">'
						+ '<div class="item-subtitle">' + item.shopDesc
						+ '</div>' + '</div>' + '</li>' + '</ul>'
						+ '</div>' + '</div>' + '<div class="card-footer">'
						+ '<p class="color-gray">'
						+ new Date(item.lastEditTime).Format("yyyy-MM-dd")
						+ '更新</p>' + '<span>点击查看</span>' + '</div>'
						+ '</div>';
				});
				$('.list-div').append(html);
				var total=$('.list-div .card').length;
				if (total >= maxItems){
					$('.infinite-scroll-preloader').hide();
				}else{
					$('.infinite-scroll-preloader').show();
				}
				pageNum += 1;
				loading = false;
				$.refreshScroller();
			}
		});
	}
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
	$(document).on('infinite','.infinite-scroll-bottom',function(){
		if(loading)
			return;
		addItems(pageSize,pageNum);
	});
	
	$('.shop-list').on('click','.card',function(e){
		var shopId = e.currentTarget.dataset.shopId;
		window.location.href = '/o2o/frontend/shopdetail?shopId=' + shopId;
	});
	
	$('#shoplist-search-div').on('click','.button',function(e){
		if (parentId){
			shopCategoryId = e.target.dataset.categoryId;
			if($(e.target).hasClass('button-fill')){
				$(e.target).removeClass('button-fill');
				shopCategoryId = '';
			}else {
				$(e.target).addClass('button-fill').siblings().removeClass('button-fill');
			}
			$('.list-div').empty();
			pageNum = 1;
			addItems(pageSize,pageNum);
			
		}else{
			parentId = e.target.dataset.categoryId;
			if($(e.target).hasClass('button-fill')){
				$(e.target).removeClass('button-fill');
				parentId = '';
			}else{
				$(e.target).addClass('button-fill').siblings().removeClass('button-fill');
			}
			$('.list-div').empty();
			pageNum = 1;
			addItems(pageSize,pageNum);
			parentId = '';
		}
	});
	
	$('#search').on('change',function(e){
		shopName = e.target.value;
		$('.list-div').empty();
		pageNum = 1;
		addItems(pageSize,pageNum);
	});
	
	$('#area-search').on('change',function(){
		areaId = $('#area-search').val();
		$('.list-div').empty()
		pageNum = 1;
		addItems(pageSize,pageNum);
	});
	
	$('#me').click(function(){
		$.openPanel('#panel-right-demo');
	});
	
	$.init();
});