var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	$scope.common = {
		showAdd:false,
		showEitor:false
	};
	
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1,
		name:'',
		productPageNumber:''
	}	
	
	$scope.names=[
	              {name:"上架",id:1},
	              {name:"下架",id:2}
	              ];
	$scope.names2=[
	              {name:"是",id:1},
	              {name:"否",id:0}
	              ];
	
	$scope.productSkuAdd = {
			price:'',
			stock:'',
			details:'',
			norm:''
		};
	
	$scope.typeList = [
		             		{id:1,name:'商城'},
		             		{id:2,name:'积分'}
		             	];

	$scope.postageTypeList = [
		{id:0,name:'不包邮'},
		{id:1,name:'包邮'}
	];
	
	//根据URL的参数名获取参数值
	$scope.getParams=function(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");  
	    var r = window.location.search.substr(1).match(reg);  
	    if (r != null) return unescape(r[2]);  
	    return null;  
	}
		
	//改变产品状态
	$scope.changeStatus = function(productSkuId){
		$http({
		 	method  : 'GET',
	        url     : '/productSku/changeStatus.htm',
	        params:{
	            'productSkuId':productSkuId
	        },
        	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			$scope.pageChanged();
		});
	}
	
	
	//刷新页面	
	$scope.pageChanged = function() {
		var productId=$scope.getParams("productId");
		$scope.formData.productPageNumber=$scope.getParams("pageNumber");
		$http({
	        method  : 'POST',	       
	        url     : '/productSku/queyProductSkuList.htm?productId='+productId,
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.list = response.data.productSkuList;
	    	$scope.formData.total = response.data.total;
	    	$scope.templates= response.data.templates;
	    });
	}
		
	$scope.pageChanged();
	
	
	$scope.showAddMsg = function() {
		$scope.common.showAdd = true;
	}
	
	$scope.closeAddMsg = function() {
		$scope.common.showAdd = false;
	}
	
	//显示编辑框
	$scope.showEitorMsg = function(productSkuId) {
		$http({
		 	method  : 'POST',
	        url     : '/productSku/queryProductSkuById.htm',
			params:{'id':productSkuId},
            transformRequest: angular.identity,
        	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}			
			$scope.productSkuEitor = response.data.productSku;
			$scope.common.showEitor=true;
		});
	}
	
	//关闭编辑框
	$scope.closeEitorMsg = function() {
		$scope.common.showEitor = false;
		$scope.pageChanged();
	}
				
	//提交编辑	
	$scope.submitEitor = function(productInfoId) {
		if($scope.productSkuEitor.freePostage==0){
			if(!stringCheck("物流模板",$scope.productSkuEitor.logisticsTemplateId)){return;}
		}
		var fd = new FormData();
		fd.append("id",$scope.productSkuEitor.id);
		fd.append("type", $scope.productSkuEitor.type);
		if($scope.productSkuEitor.type==1){
			fd.append("price",$scope.productSkuEitor.price);
		}
		else{
			fd.append("point",$scope.productSkuEitor.point);
		}	
		fd.append("stock", $scope.productSkuEitor.stock);
		fd.append("details", $scope.productSkuEitor.details);
		fd.append("norm", $scope.productSkuEitor.norm);
		fd.append("weight", $scope.productSkuEitor.weight);
		fd.append("freePostage", $scope.productSkuEitor.freePostage);
		if($scope.productSkuEitor.freePostage==0){
			fd.append("logisticsTemplateId", $scope.productSkuEitor.logisticsTemplateId);
		}
		submitNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/productSku/editProductSku.htm',
	        data : fd,
	        transformRequest: angular.identity,
	    	headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}
		
			Notify('编辑成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showEitor = false;
			$scope.pageChanged();
			submitNow = false;
			
			$('#distpicker').distpicker('reset');
			$(".fileinput-remove-button").click();	
		});
	}
	
	//添加框提交
	$scope.submitAdd = function() {		
//			console.log(submitNow);		
		if(submitNow) {
			return;
		}

		if(!stringCheck("产品类型",$scope.productSkuAdd.type)){return;}
		if($scope.productSkuAdd.price == ''&& $scope.productSkuAdd.point == '') {
			Notify("请正确输入产品价格或积分！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if(!intCheck("产品库存",$scope.productSkuAdd.stock)){return;}
		if(!stringCheck("产品详情",$scope.productSkuAdd.details)){return;}
		if(!stringCheck("产品规格",$scope.productSkuAdd.norm)){return;}
		if(!doubleCheck("重量",$scope.productSkuAdd.weight)){return;}
		if(!stringCheck("是否包邮",$scope.productSkuAdd.freePostage)){return;}
		if($scope.productSkuAdd.freePostage==0){
			if(!stringCheck("物流模板",$scope.productSkuAdd.logisticsTemplateId)){return;}
		}

		var fd = new FormData();
		fd.append("productId", $scope.getParams("productId"));
		fd.append("type", $scope.productSkuAdd.type);
		if($scope.productSkuAdd.type==1){
			if(!doubleCheck("sku价格",$scope.productSkuAdd.price)){return;}
			fd.append("price",$scope.productSkuAdd.price);
		}
		else{
			if(!intCheck("产品积分",$scope.productSkuAdd.point)){return;}
			fd.append("point",$scope.productSkuAdd.point);
		}
		fd.append("stock", $scope.productSkuAdd.stock);
		fd.append("details", $scope.productSkuAdd.details);
		fd.append("norm", $scope.productSkuAdd.norm);
		fd.append("weight", $scope.productSkuAdd.weight);
		fd.append("freePostage", $scope.productSkuAdd.freePostage);
		if($scope.productSkuAdd.freePostage==0){
			fd.append("logisticsTemplateId", $scope.productSkuAdd.logisticsTemplateId);
		}
		submitNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/productSku/addProductSku.htm',
	        data : fd,
            transformRequest: angular.identity,
        	headers : {'Content-Type' : undefined}
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}
		
			Notify('添加成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showAdd = false;
			$scope.pageChanged();
			submitNow = false;
			$scope.productSkuAdd = {
				price:'',
				stock:'',
				point:'',
				details:'',
				norm:'',
				weight:'',
				freePostage:5, //随便设的值，因为设为空字符，与0比较时为true
				logisticsTemplateId:''
			};
		});			
	}
});
