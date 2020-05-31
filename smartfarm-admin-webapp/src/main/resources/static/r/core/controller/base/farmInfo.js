var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http) {
	var submitNow = false;

	$scope.common = {
		showAdd:false,
		showEdit:false

	};
	$scope.statusList = [
		{id:1,name:'启用'},
		{id:2,name:'禁用'}
	];

	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1,
		name:'',
		status:''
	}

	$scope.pageChanged = function() {
		$http({
			method  : 'POST',
			url     : '/farmInfo/getFarmList.htm',
			data    : $.param($scope.formData),
			headers : {'Content-Type': 'application/x-www-form-urlencoded'},
		}).then(function(response) {
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			$scope.list = response.data.farmInfoList;
			$scope.formData.total = response.data.total;
		});
	}
	$scope.pageChanged();


	$scope.showAddMsg=function(){
		$scope.common.showAdd=true;
	}
	$scope.closeAddMsg=function(){
		$scope.common.showAdd=false;
	}

	$scope.submitAdd=function () {
		if(submitNow) {
			return;
		}

		if($scope.farmInfoAdd.name == '') {
			Notify("请输入农场名称！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}

		if($scope.farmInfoAdd.address == '') {
			Notify("请输入农场地址！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}

		if($scope.farmInfoAdd.longitude == '') {
			Notify("请输入农场经度！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}

		if($scope.farmInfoAdd.latitude == '') {
			Notify("请输入农场纬度！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}

		if($scope.farmInfoAdd.brief == '') {
			Notify("请输入农场简介！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}

		var addCoverImg = document.querySelector('input[name="addCoverImg"]').files;
		if(addCoverImg.length == 0){
			Notify("请上传农场封面！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		var fd = new FormData();
		fd.append("name", $scope.farmInfoAdd.name);
		fd.append("address", $scope.farmInfoAdd.address);
		fd.append("longitude", $scope.farmInfoAdd.longitude);
		fd.append("latitude", $scope.farmInfoAdd.latitude);
		fd.append("brief", $scope.farmInfoAdd.brief);
		fd.append("file",  document.querySelector('input[name="addCoverImg"]').files[0]);
		fd.append("status", 2);
		submitNow = true;
		$http({
			method  : 'POST',
			url     : '/farmInfo/addFarm.htm',
			data : fd,
			transformRequest: angular.identity,
			headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}

			Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showAdd = false;
			$scope.pageChanged();
			submitNow = false;

			$scope.landInfoAdd = {
				name:'',
				address:'',
				longitude:'',
				latitude:'',
				brief:''
			};
			$(".fileinput-remove-button").click();
		});
	}

	$scope.showEditMsg=function(id){
		$http({
			method  : 'POST',
			url     : '/farmInfo/getFarm.htm',
			params:{
				'id':id
			},
			headers : {'Content-Type' : 'application/x-www-form-urlencoded'}
		}).then(function(response) {
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			$scope.farmInfoEdit = response.data.farm;
			$scope.common.showEdit=true;
		});
	}
	$scope.closeEditMsg=function(){
		$scope.common.showEdit=false;
	}


	//提交编辑
	$scope.submitEdit=function () {
		if(submitNow) {
			return;
		}
		var fd = new FormData();
		fd.append("id", $scope.farmInfoEdit.id);
		fd.append("name", $scope.farmInfoEdit.name);
		fd.append("address", $scope.farmInfoEdit.address);
		fd.append("longitude", $scope.farmInfoEdit.longitude);
		fd.append("latitude", $scope.farmInfoEdit.latitude);
		fd.append("brief", $scope.farmInfoEdit.brief);
		fd.append("file",  document.querySelector('input[name="editCoverImg"]').files[0]);
		submitNow = true;
		$http({
			method  : 'POST',
			url     : '/farmInfo/editFarm.htm',
			data : fd,
			transformRequest: angular.identity,
			headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				submitNow = false;
				return;
			}

			Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showEdit = false;
			$scope.pageChanged();
			submitNow = false;
		});
	}


	$scope.changeStatus=function (id) {
		$http({
			method  : 'POST',
			url     : '/farmInfo/changeFarmStatus.htm',
			params:{
				'id':id
			},
			headers : {'Content-Type' : 'application/x-www-form-urlencoded'}
		}).then(function(response) {
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.pageChanged();
		});
	}

	$scope.toFarmBase=function(farmId){
		window.location.href = "/admin/farmBase.htm?farmId="+farmId;
	}
});
