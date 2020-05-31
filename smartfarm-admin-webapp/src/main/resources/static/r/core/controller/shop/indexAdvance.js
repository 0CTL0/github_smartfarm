var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	$scope.common = {
		showAdd:false,
		showUpdate:false,
	};
	
	$scope.addData = {
		type:'',
		productId:'',
		rentId:'',
		activityId:''
	};
	
	$scope.editData = {
		id:'',
		type:'',
		url:'',
		detailUrl:'',
		orderNum:''
	};
	
	$scope.picurl = picurl;
	
	//初始化页面
	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',
	        url     : '/indexAdvance/queryList.htm',
	        data    : $.param({}),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.list = response.data.list;
	    });
	}
	
	$http({
        method  : 'POST',
        url     : '/indexAdvance/queryAdvanceForAdd.htm',
        data    : $.param({}),
        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
    }).then(function(response) {
    	$scope.productList = response.data.productList;
    	$scope.farmList = response.data.farmList;
    	$scope.activityList = response.data.activityList;
    });

	$scope.pageChanged();
	
	//打开添加框
	$scope.showAddMsg = function() {
		$scope.common.showAdd = true;
		$scope.addData = {
			type:'',
			productId:'',
			rentId:'',
			activityId:''
		};
	}
	
	//关闭添加框
	$scope.closeAddMsg = function() {
		$scope.common.showAdd = false;
	}
	
	//添加
	$scope.submitAdd = function() {
		console.log($scope.addData);
		if(submitNow) {
			return;
		}
		if($scope.addData.type == '') {
			Notify("请选择类型！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.addData.type == 1 && $scope.addData.productId == '') {
			Notify("请选择商品！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.addData.type == 2 && $scope.addData.rentId == '') {
			Notify("请选择地块！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.addData.type == 3 && $scope.addData.activityId == '') {
			Notify("请选择活动！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		submitNow = true;
		var refId = "";
		if($scope.addData.type == 1) {
			refId = $scope.addData.productId;
		}
		if($scope.addData.type == 2) {
			refId = $scope.addData.rentId;
		}
		if($scope.addData.type == 3) {
			refId = $scope.addData.activityId;
		}
		$http({
	        method  : 'POST',
	        url     : '/indexAdvance/addAdvance.htm',
	        data    : $.param({refId:refId,type:$scope.addData.type}),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}
		
			Notify('新增成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showAdd = false;
			$scope.pageChanged();
			submitNow = false;
	    });
	}
	
	$scope.updateStatus = function(id,status) {
		if(submitNow) {
			return;
		}
		submitNow = true;
		$http({
	        method  : 'POST',
	        url     : '/indexAdvance/updateById.htm',
	        data    : $.param({id:id,status:status}),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}
		
			Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.pageChanged();
			submitNow = false;
	    });
	}
	
	//修改
	$scope.submitEdit = function(){

		if(submitNow) {
			return;
		}
		var fd = new FormData();
		if($scope.editData.type == '') {
			Notify("请选择类型！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.editData.orderNum == '') {
			Notify("请输入排序数字！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		var addImg = document.querySelector('input[name="editImg"]').files;
		if(addImg.length > 0){
			fd.append("file",  addImg[0]);
		}
		var addImg2 = document.querySelector('input[name="editImg2"]').files;
		if(addImg2.length > 0){
			fd.append("file2",  addImg2[0]);
		}
		
		
		fd.append("type", $scope.editData.type);
		fd.append("orderNum", $scope.editData.orderNum);
		fd.append("id", $scope.editData.id);
		submitNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/advanceNotice/updateAdvanceNotice.htm',
	        data : fd,
	        transformRequest: angular.identity,
	    	headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}
		
			Notify('修改成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showEdit = false;
			$scope.pageChanged();
			submitNow = false;
		});
	}
});
