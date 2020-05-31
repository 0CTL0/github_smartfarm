var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	$scope.picurl =  picurl;
	//$scope.picurl =  'http://127.0.0.1:8080/r';
	var submitNow = false;
	$scope.names=[
	              {name:"上架",id:1},
	              {name:"下架",id:2}
	              ];
	$scope.names2=[
	              {name:"是",id:1},
	              {name:"否",id:0}
	              ];
	$scope.common = {
		showAdd:false,
		showEdit:false
	};
	
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1,
		name:'',
		categoryId:''
	};
	
	$scope.productAdd = {
			name:'',
			goodsSn:'',
			categoryId:'',
			brief:'',
			status:'',
			unit:'',
			showPrice:'',
			isNew:'',
			isHot:'',
			sortOrder:'',
			point:''
		};

	//根据URL的参数名获取参数值
	$scope.getParams=function(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]);
		return null;
	}
	
	$scope.pageChanged = function() {
		var number=$scope.getParams("pageNumber");
		if(number!=null){
			$scope.formData.pageNumber=number;
		}
		$http({
	        method  : 'POST',	       
	        url     : '/product/queyProductInfoList.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.list = response.data.productList;
	    	$scope.formData.total = response.data.total;
			$scope.categoryList=response.data.categoryList;
			$scope.farmId=response.data.farmId;
	    });
	}
	
	//获得产品的父级分类
	$scope.getCategories = function() {
		$http({
	        method  : 'GET',
	        url     : '/productCategory/queryAllProductCategoryList.htm',
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.categories = response.data.AllCategories;
	    });
	}
	
	
	$scope.pageChanged();
	$scope.getCategories();
	
	
	//跳转到SKU管理页面	
	$scope.toSKU=function(productId){
		window.location.href = "/admin/shop/productSku.htm?productId="+productId+"&pageNumber="+$scope.formData.pageNumber;
	}	
	
	//改变产品状态
	$scope.changeStatus = function(productInfoId){
		$http({
		 	method  : 'GET',
	        url     : '/product/changeStatus.htm',
	        params:{
	            'productInfoId':productInfoId
	        },
	    	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			$scope.pageChanged();
		});
	}
	
	//改变新品状态
	$scope.changeNew = function(productId){
		$http({
		 	method  : 'GET',
	        url     : '/product/changeNew.htm',
	        params:{
	            'productId':productId
	        },
	    	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  
		}).then(function(response){
			$scope.pageChanged();
		});
	}
	//改变人气状态
	$scope.changeHot = function(productId){
		$http({
		 	method  : 'GET',
	        url     : '/product/changeHot.htm',
	        params:{
	            'productId':productId
	        },
	    	headers : {'Content-Type' : 'application/x-www-form-urlencoded'}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			$scope.pageChanged();
		});
	}
	
	
	$scope.showAddMsg = function() {
		$scope.common.showAdd = true;
		$scope.getCategories();
	}
	
	$scope.closeAddMsg = function() {
		$(".fileinput-remove-button").click();
		$scope.productAdd = {
			name:'',
			goodsSn:'',
			categoryId:'',
			brief:'',
			status:'',
			unit:'',
			showPrice:'',
			isNew:'',
			isHot:'',
			sortOrder:''
		};
		$scope.common.showAdd = false;
	}
	
	$scope.showEditMsg = function(productInfoId) {
		$scope.common.showEdit = true;
		var fd = new FormData();
		fd.append("id", productInfoId);
		$http({
		 	method  : 'POST',
	        url     : '/product/queryProductInfoById.htm',
	        data : fd,
	        transformRequest: angular.identity,
	    	headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			//Notify('进入成功', 'top-right', '5000', 'success', 'fa-check', true);
			
			$scope.productEdit = response.data.productInfo;
			$scope.getCategories();
		});
	}
	
	$scope.closeEditMsg = function() {
		$scope.common.showEdit = false;
		$scope.pageChanged();
	}
	
	$scope.submitDelete = function(productInfoId) {
		var fd = new FormData();
		fd.append("id", productInfoId);
		$http({
		 	method  : 'POST',
	        url     : '/product/removeProductInfo.htm',
	        data : fd,
	        transformRequest: angular.identity,
	    	headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			Notify('删除成功', 'top-right', '5000', 'success', 'fa-check', true);		
			$scope.pageChanged();	
		});
	}


		
	$scope.submitEdit = function(productInfoId) {
		var fd = new FormData();
		fd.append("id",$scope.productEdit.id)
		fd.append("name", $scope.productEdit.name);
		fd.append("goodsSn", $scope.productEdit.goodsSn);
		fd.append("categoryId", $scope.productEdit.categoryId);
		fd.append("brief", $scope.productEdit.brief);
		/*fd.append("status", $scope.productEdit.status);*/
		fd.append("unit", $scope.productEdit.unit);
		fd.append("isNew", $scope.productEdit.isNew);
		fd.append("isHot", $scope.productEdit.isHot);
		fd.append("point", $scope.productEdit.point);
		// fd.append("showPrice", $scope.productEdit.showPrice);
		fd.append("sortOrder", $scope.productEdit.sortOrder);
		fd.append("file",  document.querySelector('input[name="editImg"]').files[0]);
		fd.append("file2",  document.querySelector('input[name="editImg2"]').files[0]);
		for(var i=0; i < document.querySelector('input[name="editMainImg"]').files.length; i++) {
		       fd.append("mainImgs", document.querySelector('input[name="editMainImg"]').files[i]);
		     }
		for(var i=0; i < document.querySelector('input[name="editDetailImg"]').files.length; i++) {
		       fd.append("detailImgs", document.querySelector('input[name="editDetailImg"]').files[i]);
		     }
		submitNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/product/editProductInfo.htm',
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
			$scope.common.showEdit = false;
			$scope.pageChanged();
			submitNow = false;
			$(".fileinput-remove-button").click();
			 
		});
	}

	$scope.submitAdd = function() {
		if(submitNow) {
			return;
		}
		if(!stringCheck("产品名称",$scope.productAdd.name)){return;}
		if(!stringCheck("产品分类",$scope.productAdd.categoryId)){return;}
		if(!stringCheck("产品代号",$scope.productAdd.goodsSn)){return;}
		if(!stringCheck("产品简介",$scope.productAdd.brief)){return;}
		if(!stringCheck("产品单位",$scope.productAdd.unit)){return;}
		if(!intCheck("产品积分",$scope.productAdd.point)){return;}
		if(!intCheck("产品序号",$scope.productAdd.sortOrder)){return;}
		if(!picCheck("封面图片","addImg")){return;}
		if(!picCheck("分享图片","addImg2")){return;}
		if(!picCheck("五维图片","addMainImg")){return;}
		if(!picCheck("详情图片","addDetailImg")){return;}

		var fd = new FormData();
		fd.append("name", $scope.productAdd.name);
		fd.append("goodsSn", $scope.productAdd.goodsSn);
		fd.append("categoryId", $scope.productAdd.categoryId);
		fd.append("brief", $scope.productAdd.brief);
		/*fd.append("status", $scope.productAdd.status);*/
		fd.append("unit", $scope.productAdd.unit);				
		fd.append("isNew", $scope.productAdd.isNew);
		fd.append("isHot", $scope.productAdd.isHot);	
		fd.append("point", $scope.productAdd.point);
		// fd.append("showPrice", $scope.productAdd.showPrice);
		fd.append("sortOrder", $scope.productAdd.sortOrder);
			
		fd.append("file",  document.querySelector('input[name="addImg"]').files[0]);
		fd.append("file2",  document.querySelector('input[name="addImg2"]').files[0]);
		for(var i=0; i < document.querySelector('input[name="addMainImg"]').files.length; i++) {
		       fd.append("mainImgs", document.querySelector('input[name="addMainImg"]').files[i]);
		     }
		for(var i=0; i < document.querySelector('input[name="addDetailImg"]').files.length; i++) {
		       fd.append("detailImgs", document.querySelector('input[name="addDetailImg"]').files[i]);
		     }
		
		submitNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/product/addProductInfo.htm',
	        data : fd,
            transformRequest: angular.identity,
        	headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
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
			
			$(".fileinput-remove-button").click();
			$scope.productAdd = {
				name:'',
				goodsSn:'',
				categoryId:'',
				brief:'',
				status:'',
				unit:'',
				// showPrice:'',
				isNew:'',
				isHot:'',
				sortOrder:''
			};
		});			
	}
});
