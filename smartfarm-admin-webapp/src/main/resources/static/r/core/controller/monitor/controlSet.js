var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	//查询列表参数
	$scope.formData = {
		total:0,
		pageSize:10,
		pageNumber:1
	}
	
	$scope.common = {
		showAdd:false
	}
	
	$scope.addData = {
		type:1,
		name:'',
		startDateStr:'',
		endDateStr:'',
		startTimeStr:'',
		endTimeStr:''
	};
	
	$scope.conditions = [{dataValue:'',type:'',controlNodeId:'',kinds:1},{dataValue:'',type:'',controlNodeId:'',kinds:2}];
	
	//初始化列表
	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',
	        url     : '/controlSet/controlSetPageList.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.list = response.data.list;
	    	$scope.formData.total = response.data.total;
	    });
	}
	
	$scope.pageChanged();
	
	$http({
        method  : 'POST',
        url     : '/controlNode/queryControlNodeListByGate.htm',
        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
    }).then(function(response) {
//    	$scope.gateList = response.data.list;
    	var gateList = response.data.list;
    	if(gateList.length > 0) {
    		var gateNew = [];
    		for(var i = 0;i < gateList.length;i++) {
    			if(gateList[i].controlNodeList.length > 0) {
    				gateNew.push(gateList[i]);
    			}
    		}
    		$scope.gateList = gateNew;
    		$scope.gateList[0].choosed = true;
    		$scope.gateIndex = 0;
    	}
    });
	
	$http({
        method  : 'POST',
        url     : '/controlNode/querySensorNodeListByGate.htm',
        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
    }).then(function(response) {
//    	$scope.sensorGateList = response.data.list;
    	var sensorGateList = response.data.list;
    	var gateNew = [];
		for(var i = 0;i < sensorGateList.length;i++) {
			if(sensorGateList[i].sensorNodeList.length > 0) {
				gateNew.push(sensorGateList[i]);
			}
		}
		$scope.sensorGateList = gateNew;
    });
	
	//展示新增框
	$scope.showAddMsg = function(){
		$scope.common.showAdd = true;
		$('#startDate').datetimepicker({minView:'month'});
		$('#endDate').datetimepicker({minView:'month'});
		$('#startTime').datetimepicker({startView:"day"});
		$('#endTime').datetimepicker({startView:"day"});
	}
	//关闭新增框
	$scope.closeAddMsg = function(){
		$scope.common.showAdd = false
	}
	
	$scope.changeGate = function(id) {
		for(var i = 0; i < $scope.gateList.length;i++) {
			$scope.gateList[i].choosed = false;
			if($scope.gateList[i].id == id) {
				$scope.gateList[i].choosed = true;
				$scope.gateIndex = i;
			}
		}
	}
	
	$scope.changeControl = function(id) {
		for(var i = 0; i < $scope.gateList[$scope.gateIndex].controlNodeList.length;i++) {
			if($scope.gateList[$scope.gateIndex].controlNodeList.id == id) {
				if($scope.gateList[$scope.gateIndex].controlNodeList[i].choosed == undefined || $scope.gateList[$scope.gateIndex].controlNodeList[i].choosed == null
					|| $scope.gateList[$scope.gateIndex].controlNodeList[i].choosed == false) {
					$scope.gateList[$scope.gateIndex].controlNodeList[i].choosed = true;
				}else {
					$scope.gateList[$scope.gateIndex].controlNodeList[i].choosed = false;
				}
			}
		}
	}
	
	$scope.changeCondition = function(index) {
		for(var i = 0; i < $scope.sensorGateList.length;i++) {
			if($scope.conditions[index].gateId == $scope.sensorGateList[i].id) {
				$scope.conditions[index].gateIndex = i;
			}
			
		}
	}
	
	$scope.addCondition = function(kinds) {
		$scope.conditions.push({dataValue:'',type:'',controlNodeId:'',kinds:kinds});
	}
	
	$scope.deleteCondition = function(index) {
		$scope.conditions.splice(index, 1);
	}
	

	$scope.formatDate = function(date) {
		return date.substring(0,4) + date.substring(5,7) + date.substring(8,10);
	}
	
	$scope.formatTime = function(date) {
		return date.substring(0,2) + date.substring(3,5);
	}
	
	$scope.submitAdd = function() {
		if(submitNow) {
			return;
		}
		
		submitNow = true;
		var controlSetNodeJson = [];
		for(var i = 0; i < $scope.gateList.length;i++) {
			for(var j = 0; j < $scope.gateList[i].controlNodeList.length;j++) {
				if( $scope.gateList[i].controlNodeList[j].choosed) {
					controlSetNodeJson.push({controlNodeId: $scope.gateList[i].controlNodeList[j].id});
				}
			}
		}
		
		var controlConditionJson = [];
		for(var i = 0; i < $scope.conditions.length;i++) {
			controlConditionJson.push({
				dataValue:$scope.conditions[i].dataValue,
				type:$scope.conditions[i].type,
				controlNodeId:$scope.conditions[i].controlNodeId,
				kinds:$scope.conditions[i].kinds
			});
		}
		$scope.addData.controlSetNodeJson = JSON.stringify(controlSetNodeJson);
		$scope.addData.controlConditionJson = JSON.stringify(controlConditionJson);
		$scope.addData.startDate = $scope.formatDate($scope.addData.startDateStr);
		$scope.addData.endDate = $scope.formatDate($scope.addData.endDateStr);
		$scope.addData.startTime = $scope.formatTime($scope.addData.startTimeStr);
		$scope.addData.endTime = $scope.formatTime($scope.addData.endTimeStr);
		$http({
	        method  : 'POST',
	        url     : '/controlSet/addControlSet.htm',
	        data    : $.param($scope.addData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	submitNow = false;
	    	if(response.data.success) {
	    		$scope.addData = {
    				type:1,
    				name:'',
    				startDateStr:'',
    				endDateStr:'',
    				startTimeStr:'',
    				endTimeStr:''
    			};
	    		$scope.pageChanged();
	    		$scope.common.showAdd = false;
	    		Notify('添加成功', 'top-right', '5000', 'success', 'fa-check', true);
	    	}else {
	    		Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
	    	}
	    });
		
	}
	
	$scope.changeStatus = function(id,status) {
		if(submitNow) {
			return;
		}
		
		submitNow = true;
		$http({
	        method  : 'POST',
	        url     : '/controlSet/updateStatus.htm',
	        data    : $.param({id:id,status:status}),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	submitNow = false;
	    	if(response.data.success) {
	    		$scope.pageChanged();
	    		Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
	    	}else {
	    		Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
	    	}
	    });
	}
});
