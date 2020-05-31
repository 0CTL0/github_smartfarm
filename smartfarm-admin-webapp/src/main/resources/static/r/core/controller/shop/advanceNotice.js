var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	$scope.common = {
		showAdd:false,
		showUpdate:false,
	};
	
	$scope.addData = {
		type:'',
		url:'',
		detailUrl:'',
		orderNum:''
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
	        url     : '/advanceNotice/queryList.htm',
	        data    : $.param({}),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.list = response.data.list;
	    });
	}

	$scope.pageChanged();
	
	//打开添加框
	$scope.showAddMsg = function() {
		$scope.common.showAdd = true;
		$scope.addData = {
			type:'',
			url:'',
			detailUrl:'',
			orderNum:''
		};
	}
	
	//关闭添加框
	$scope.closeAddMsg = function() {
		$scope.common.showAdd = false;
	}
	
	//添加
	$scope.submitAdd = function() {
		if(submitNow) {
			return;
		}
		if(!stringCheck("类型",$scope.addData.type)){return;}
		if(!intCheck("排序",$scope.addData.orderNum)){return;}
		if(!picCheck("封面图片","addImg")){return;}

		var addImg = document.querySelector('input[name="addImg"]').files;
		var addImg2 = document.querySelector('input[name="addImg2"]').files;
		
		var fd = new FormData();
		fd.append("type", $scope.addData.type);
		fd.append("orderNum", $scope.addData.orderNum);
		fd.append("file",  addImg[0]);
		fd.append("file2",  addImg2[0]);
		submitNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/advanceNotice/addAdvanceNotice.htm',
	        data : fd,
	        transformRequest: angular.identity,
	    	headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
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
	
	
	//打开编辑框
	$scope.showEdit = function(data){
		console.log(data);
		$scope.editData = {
			id:data.id,
			type:data.type,
			url:'',
			detailUrl:'',
			orderNum:data.orderNum
		};
		$scope.common.showEdit = true;
	}
	
	$scope.closeEditMsg = function(){
		$scope.common.showEdit = false;
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
