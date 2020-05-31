var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	//图片路径前缀
	$scope.picUrl= picurl;
	//控制模态框
	$scope.common = {
		showAdd:false,
		showEdit:false
	}
	//添加监控传感器参数
	$scope.sensorInfoAdd = {
		code:'',
		name:'',
		upperLimit:'',
		lowerLimit:'',
		resolutionRatio:'',
		unit:'',
		precision:''
	}
	
	//查询列表参数
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1
	}
	//展示新增框
	$scope.showAddMsg = function(){
		$scope.common.showAdd = true
	}
	//展示编辑框
	$scope.showEdit = function(infoId){
		$scope.querySensorInfoById(infoId);
		$scope.common.showEdit = true;
	}
	//关闭新增框
	$scope.closeAddMsg = function(){
		$scope.common.showAdd = false
	}
	//关闭编辑框
	$scope.closeshowEdit = function(){
		$scope.common.showEdit = false
	}
	//初始化列表
	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',
	        url     : '/monitor/queryMonitorsensorInfoList.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.list = response.data.sensorInfoList;
	    	$scope.formData.total = response.data.total;
	    });
	}
	
	$scope.pageChanged();
	
	//根据id得到监控传感器
	$scope.querySensorInfoById = function(id){
		$http({
	        method  : 'GET',
	        url     : '/monitor/querySensorInfoById.htm',
	        params:{
	            'id':id
	        },
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.sensorInfo = response.data.sensorInfo;
	    });
	}
	//新增
	$scope.submitAdd = function(){
		if($scope.sensorInfoAdd.code == '') {
			Notify("传感器代码不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorInfoAdd.name == '') {
			Notify("传感器名称不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorInfoAdd.upperLimit == '') {
			Notify("测量上限不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorInfoAdd.lowerLimit == '') {
			Notify("测量下限不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if(parseInt($scope.sensorInfoAdd.lowerLimit) >= parseInt($scope.sensorInfoAdd.upperLimit)){
			Notify("测量下限不能大于或等于测量上限！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorInfoAdd.resolutionRatio == '') {
			Notify("分辨率不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorInfoAdd.unit == '') {
			Notify("单位不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		/* if($scope.sensorInfoAdd.precision == '') {
			Notify("精度不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		} */
		var fd = new FormData();
		fd.append("code", $scope.sensorInfoAdd.code);
		fd.append("name", $scope.sensorInfoAdd.name);
		fd.append("upperLimit", $scope.sensorInfoAdd.upperLimit);
		fd.append("lowerLimit", $scope.sensorInfoAdd.lowerLimit);
		fd.append("resolutionRatio", $scope.sensorInfoAdd.resolutionRatio);
		fd.append("unit", $scope.sensorInfoAdd.unit);
		fd.append("precision", $scope.sensorInfoAdd.precision);
		$http({
		 	method  : 'POST',
		 	url     : '/monitor/addSensorInfo.htm',	        
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
			
			$scope.sensorInfoAdd = {
				code:'',
				name:'',
				upperLimit:'',
				lowerLimit:'',
				resolutionRatio:'',
				unit:'',
				precision:''
			}
		});			
	}
	
	//编辑监控传感器
	$scope.submitEdit = function(){
		if($scope.sensorInfo.code == '') {
			Notify("传感器代码不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorInfo.name == '') {
			Notify("传感器名称不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorInfo.upperLimit == '') {
			Notify("测量上限不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorInfo.lowerLimit.length == 0) {
			Notify("测量下限不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorInfo.resolutionRatio == '') {
			Notify("分辨率不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.sensorInfo.unit == '') {
			Notify("单位不能为空！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		var fd = new FormData();
		fd.append("id", $scope.sensorInfo.id);
		fd.append("code", $scope.sensorInfo.code);
		fd.append("name", $scope.sensorInfo.name);
		fd.append("upperLimit", $scope.sensorInfo.upperLimit);
		fd.append("lowerLimit", $scope.sensorInfo.lowerLimit);
		fd.append("resolutionRatio", $scope.sensorInfo.resolutionRatio);
		fd.append("unit", $scope.sensorInfo.unit);
		fd.append("precision", $scope.sensorInfo.precision);
		$http({
		 	method  : 'POST',
		 	url     : '/monitor/editSensorInfoById.htm',	        
	        data : fd,     
            transformRequest: angular.identity,
	        headers : {'Content-Type' : undefined}
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		
			Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showEdit = false;
			$scope.pageChanged();
		});
	}
	
	$scope.deleteById = function(id){
		if(confirm("你确定删除此记录吗?")){
			$http({
			 	method  : 'POST',
			 	url     : '/monitor/deleteSensorInfoById.htm',	        
			 	params:{
		            'id':id
		        },     
	            transformRequest: angular.identity,
		        headers : {'Content-Type' : undefined}
			}).then(function(response){
				if(!response.data.success) {
					Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
					return;
				}
			
				Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
				$scope.pageChanged();
			});
		}else{
			//
		}
	}
});
