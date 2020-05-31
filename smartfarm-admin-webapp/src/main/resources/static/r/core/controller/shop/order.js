var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
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
	//查看详细
	$scope.showDetail = function(order) {
		$scope.common.showDetail = true;
		$scope.queryOrderByOid(order.id)
	}
	//查看物流详细
	$scope.showShipMsg = function(orderDetail) {
		$scope.common.showShip = true;
		$scope.queryShip(orderDetail)
	}
	//关闭详细
	$scope.closeDetail = function() {
		$scope.common.showDetail = false;
	}
	//关闭物流详细
	$scope.closeShip = function() {
		$scope.common.showShip = false;
	}
	//格式化时间
	$scope.formatDateTime = function(endDate) {
		if(endDate == undefined || endDate == ''){
			return '';
		}
		return endDate.substring(0,4) + '-' + endDate.substring(4,6) + '-' + endDate.substring(6,8) + " " + endDate.substring(8,10) + ":" + endDate.substring(10,12);
	}
	//格式化时间
	$scope.formatDateTime2 = function(endDate) {
		if(endDate == undefined || endDate == ''){
			return '';
		}
		return endDate.substring(0,4) + endDate.substring(5,7) + endDate.substring(8,10) + endDate.substring(11,13) + endDate.substring(14,16) + "00";
	}

	//初始化所有时间控件
	for(var i = 0; i<document.getElementsByName("myDates").length; i++){
		$(document.getElementsByName("myDates")).datetimepicker({
			minView : "hour", //
			format : "yyyy-mm-dd hh:ii",//日期格式
			autoclose:true,//选中关闭
			todayBtn: true//今日按钮
		});
	}


	$scope.pageChanged = function() {
		$http({
	        method  : 'POST',
	        url     : '/order/queryAllOrder.htm',
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
			// if(!response.data.success) {
			// 	return false;
			// }
			// return true;
	    });
	}


	// $scope.search = function() {
	// 	console.log($scope.pageChanged())
	// 	if($scope.pageChanged()){
	// 		Notify('查询成功', 'top-right', '5000', 'success', 'fa-check', true);
	// 	}
	// 	else {
	// 		Notify("查询失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
	// 	}
	//
	// }


	$scope.pageChanged();
	//根据订单号查询物流信息
	$scope.queryShip = function(orderDetail){
		var fd = new FormData();
		fd.append("expCode", orderDetail.shipCode);
		fd.append("expNo", orderDetail.shipNo);
		$http({
	        method  : 'Post',
	        url     : '/order/queryShipMsg.htm',
	        data : fd,
	        headers : {'Content-Type': undefined},
	    }).then(function(response) {
	    	$scope.ShipMsg = response.data.ShipMsg;
	    	/* console.log($scope.ShipMsg.Traces) */
	    });
	}
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

	$scope.export = function() {
		if($scope.formData.nowDate == '') {
			alert("请选择导出日期");
			return;
		}
		window.location.href = "/order/exportOrder.htm?nowDate=" + $scope.formData.nowDate.substring(0,4) + $scope.formData.nowDate.substring(5,7) + $scope.formData.nowDate.substring(8,10);

	}
});
