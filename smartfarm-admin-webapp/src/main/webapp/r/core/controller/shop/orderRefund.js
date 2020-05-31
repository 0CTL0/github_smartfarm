var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var submitNow = false;
	//控制模态框工具类
	$scope.common = {
		showDetail : false,
		showShip : false 
	};
	//分页参数
	$scope.formData = {
		total:0,
		pageSize:50,
		pageNumber:1,
		status:'',
		order_no:'',
		startDate:'',
		endDate:''
	}
	$('.menu-dropdown').dropdown();

	//格式化时间
	$scope.formatDateTime = function(endDate) {
		if(endDate == undefined || endDate == ''){
			return '';
		}
		return endDate.substring(0,4) + '-' + endDate.substring(4,6) + '-' + endDate.substring(6,8) + " " + endDate.substring(8,10) + ":" + endDate.substring(10,12);
	}
	//格式化时间
	$scope.formatDateTime2 = function(endDate) {
		return endDate.substring(0,4) + endDate.substring(5,7) + endDate.substring(8,10) ;
	}

	//初始化所有时间控件
	for(var i = 0;i<document.getElementsByName("myDates").length;i++){
		$(document.getElementsByName("myDates")).datetimepicker({
			minView: "month",//设置只显示到月份
			format : "yyyy-mm-dd",//日期格式
			autoclose:true,//选中关闭
			todayBtn: true//今日按钮
		});
	}

	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',
	        url     : '/order/queryAllOrderRefund.htm',
	        //data    : $.param($scope.formData),
			params:{
				'pageSize':$scope.formData.pageSize,
				'pageNumber':$scope.formData.pageNumber,
				'order_no':$scope.formData.order_no,
				'status':$scope.formData.status,
				'startDate':$scope.formatDateTime2($scope.formData.startDate),
				'endDate':$scope.formatDateTime2($scope.formData.endDate),
			},
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.orderList = response.data.orderList;
	    	$scope.formData.total = response.data.total;
	    });
	}

	$scope.pageChanged();

	//根据订单id查询订单
	$scope.queryOrderByOid = function(orderId){
		$http({
	        method  : 'GET',
	        url     : '/order/queryOrderByOid.htm',
	        params:{
	            'id':orderId
	        },
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.orderInfo = response.data.orderInfo;
	    });
	}

	//发起退款请求
	$scope.applyRefund = function(orderInfo){
		if(submitNow) {
			return;
		}
		submitNow = true;
		$http({
			method  : 'Post',
			url     : '/order/applyRefund.htm',
			// data : orderInfo,
			// headers : {'Content-Type': undefined},
			params:{
			'orderInfoId':orderInfo.id
			},
			headers : {'Content-Type': 'application/x-www-form-urlencoded'},
		}).then(function(response) {
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.pageChanged();
			submitNow = false;
		});
	}

	//拒绝退款
	$scope.refuseRefund = function(orderInfo){
		$http({
			method  : 'Post',
			url     : '/order/refuseRefund.htm',
			params:{
				'id':orderInfo.id
			},
			headers : {'Content-Type': 'application/x-www-form-urlencoded'},
		}).then(function(response) {
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.pageChanged();
		});
	}

});
