var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	//控制模态框
	$scope.common = {
		showAdd:false,
		showBatchNum:false,
		showAddBatchRule:false,
		showAllRule:false
	};

	//新增批次参数
	$scope.batchAdd = {
		couponBatchId:'',
		startTime:'',
		startTimeStr:'',
		endTime:'',
		endTimeStr:'',
		timeForUser:'',
		totalNum:''
	}

	//分页数据
	$scope.formData = {
		pageNumber:1,
		pageSize:10,
		total:0
	};

	for(var i = 0;i<document.getElementsByName("myDates").length;i++){
		$(document.getElementsByName("myDates")).datetimepicker({
			  minView: "month",//设置只显示到月份
			  format : "yyyy-mm-dd",//日期格式
			  autoclose:true,//选中关闭
			  todayBtn: true//今日按钮
		});
	}

	

	//初始化页面
	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',
	        url     : '/coupon/queryCouponSendList.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.couponSendList = response.data.couponInfoList;
	    	$scope.formData.total = response.data.total;
	    });
	}

	$scope.pageChanged();

	$http({
		method  : 'POST',
		url     : '/coupon/queryAllBatch.htm',
		headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	}).then(function(response) {
		$scope.batchList = response.data.list;
	});

	
	//打开添加框
	$scope.showAddMsg = function() {
		$scope.categoryId ='';
		$scope.common.showAdd = true;//时间控件初始化
		$('.date-picker').datetimepicker();
	}
	//打开编辑框
	$scope.showEdit = function(send){
		$scope.editSend = send;
		$scope.common.showEdit = true;
		$scope.editSend.startTimeStr = $scope.formatDateTime($scope.editSend.startTime);
		$scope.editSend.endTimeStr = $scope.formatDateTime($scope.editSend.endTime);


	}
	//关闭添加框
	$scope.closeAddMsg = function() {
		$scope.categoryId ='';
		$scope.common.showAdd = false;
	}
	$scope.closeEditMsg = function(){
		$scope.common.showEdit = false;
	}
	
	//将时间控件生成的时间格式化为20180101124500
	$scope.formatDateTime2 = function(endDate) {
		return endDate.substring(0,4) + endDate.substring(5,7) + endDate.substring(8,10) +  endDate.substring(11,13)+  endDate.substring(14,16)+"000000";
	}
	$scope.formatDateTime = function(endDate) {
		return endDate.substring(0,4) + '-' + endDate.substring(4,6) + '-' + endDate.substring(6,8);
	}
	
	
	//提交添加项目
	$scope.submitAdd = function() {
		if(submitNow) {
			return;
		}
		submitNow = true;
		if($scope.batchAdd.couponBatchId == '') {
			Notify("请选择优惠券批次！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.batchAdd.startTimeStr == '') {
			Notify("请选择开始日期！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.batchAdd.endTimeStr == '') {
			Notify("请选择结束日期！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.batchAdd.timeForUser == '') {
			Notify("请输入用户领取次数！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.batchAdd.totalNum == '') {
			Notify("请输入优惠券总数！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		$scope.batchAdd.startTime = $scope.formatDateTime2($scope.batchAdd.startTimeStr);
		$scope.batchAdd.endTime = $scope.formatDateTime2($scope.batchAdd.endTimeStr);

		$http({
		 	method  : 'POST',
		 	url     : '/coupon/addCouponSend.htm',
	        data : $.param($scope.batchAdd),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).then(function(response){
			submitNow = false;
			if(!response.data.success) {
				Notify("新增失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			if(response.data.hasUsed) {
				Notify("此批次已有活动！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			Notify('新增成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showAdd = false;
			$scope.pageChanged();
			$scope.batchAdd = {
				couponBatchId:'',
				startTime:'',
				startTimeStr:'',
				endTime:'',
				endTimeStr:'',
				timeForUser:'',
				totalNum:''
			}
		});			
	}
	//修改
	$scope.submitEdit = function(){
		if(submitNow) {
			return;
		}
		submitNow = true;
		if($scope.editSend.startTimeStr == '') {
			Notify("请选择开始日期！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.editSend.endTimeStr == '') {
			Notify("请选择结束日期！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.editSend.timeForUser == '') {
			Notify("请输入用户领取次数！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.editSend.totalNum == '') {
			Notify("请输入优惠券总数！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		$scope.editSend.startTime = $scope.formatDateTime2($scope.editSend.startTimeStr);
		$scope.editSend.endTime = $scope.formatDateTime2($scope.editSend.endTimeStr);

		$http({
		 	method  : 'POST',
		 	url     : '/coupon/updateSend.htm',
	        data : $.param($scope.editSend),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).then(function(response){
			submitNow = false;
			if(!response.data.success) {
				Notify("修改失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
		
			Notify('修改成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showEdit = false;
			$scope.pageChanged();
		});
	};
	//开启or关闭状态
	$scope.reviseStatus = function (couponId,status) {
		console.log(couponId,status);
		$http({
			method  : 'POST',
			url     : '/coupon/updateSendStatus.htm',
			params:{
				'id':couponId,
				'status':status
			},
			headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		}).then(function(response){
			if(!response.data.success) {
				Notify("修改失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}

			Notify('开启成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.showEdit = false;
			$scope.pageChanged();

		});
	}



});
