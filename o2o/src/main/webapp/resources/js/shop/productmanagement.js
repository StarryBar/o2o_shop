$(function(){
	var listUrl = '/o2o/shopadmin/getproductlistbyshop?pageIndex=1&pageSize=999';
	var statusUrl = '/o2o/shopadmin/modifyproduct';
	getList();
	
	function getList(){
		$.getJSON(listUrl,function(data){
			if(data.success){
				var productList = data.productList;
				var tempHtml = '';
				
				productList.map(function(item,index){
					var textOp = "下架";
					var contraryStatus = 0;
					if (item.enableStatus == 0){
						textOp = "上架";
						contraryStatus = 1;
					}else{
						contraryStatus = 0;
					}
					
					tempHtml += ''+'<div class="row row-product">'
						+ '<div class="col-33">'
						+ item.productName
						+ '</div>'
						+ '<div class="col-20">'
						+ item.priority
						+ '</div>'
						+ '<div class="col-40">'
						+ '<a href="#" class="edit" data-id="'
						+ item.productId
						+ '"data-status="'
						+ item.enableStatus
						+ '">编辑</a>'
						+ '<a href="#" class="status" data-id="'
						+ item.productId
						+ '"data-status="'
						+ contraryStatus
						+ '">'
						+ textOp
						+ '</a>'
						+ '<a href="#" class="preview" data-id="'
						+ item.productId
						+ '"data-status="'
						+ item.enableStatus
						+ '">预览</a>'
						+ '</div>'
						+ '</div>';
				});
				$('.product-wrap').html(tempHtml);
			}
			
		});
	}
	
	//将class位product-wrap里面的a表i按绑定上点击事件
	$('.product-wrap')
		.on('click','a',function(e){
			var target = $(e.currentTarget);
			if (target.hasClass('edit')){
				//如果有classedit则点击进入店铺信息编辑页面
				window.location.href='/o2o/shopadmin/productoperation?productId='
					+ e.currentTarget.dataset.id;
				
			}else if(target.hasClass('status')){
				changeItemStatus(e.currentTarget.dataset.id,
						e.currentTarget.dataset.status);
			}else if(target.hasClass('preview')){
				window.location.href = '/o2o/frontend/productdetail?productId='
					+ e.currentTarget.dataset.id;
			}
	});
	function changeItemStatus(id,enableStatus){
		var product={};
		product.productId = id;
		product.enableStatus = enableStatus;
		$.confirm('确定么？',function(){
			$.ajax({
				url: statusUrl,
				type: 'POST',
				data:{
					productStr:JSON.stringify(product),
					statusChange:true
				},
				dataType: 'json',
				success: function(data){
					if (data.success){
						$.toast('操作成功！');
						getList();
					} else {
						$.toast('操作失败!');
					}
				}
			});
		});
		
	}
	
});
