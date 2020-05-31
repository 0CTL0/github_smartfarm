var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {
	var editNow = false;
	$scope.common = {
		showDetail : false,
		deliverGoods : false,
		showShip : false 
	};
	$scope.formData = {
			total:0,
			pageSize:50,
			pageNumber:1,
			status:'',
			ship_no:''
	}
	$scope.ship = {
		code:'',
		no:'',

	}

	$scope.showDetail = function(orderDetail) {
		$scope.common.showDetail = true;
		$scope.queryOrderByOid(orderDetail.id);
	}
	
	$scope.showShipMsg = function(orderDetail) {
		$scope.common.showShip = true;
		$scope.queryShip(orderDetail)
	}
	
	$scope.showDeliverGoods = function(orderDetail) {
		$scope.common.deliverGoods = true;
		$scope.queryOrderByOid(orderDetail.id)
	}

	$scope.closeDetail = function() {
		$scope.common.showDetail = false;
	}
	
	$scope.closeShip = function() {
		$scope.common.showShip = false;
	}
	
	$scope.closeDeliverGoods = function() {
		$scope.common.deliverGoods = false;
	}
	
	$scope.pageChanged = function() {
		/* console.log($scope.formData) */
		$http({
	        method  : 'POST',
	        url     : '/order/queryAllOrderDetail.htm',
	        data    : $.param($scope.formData),
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.detailList = response.data.detailList;
	    	$scope.formData.total = response.data.total;
	    });
	}

	$scope.pageChanged();
	
	$scope.queryOrderByOid = function(orderId){
		$http({
	        method  : 'GET',
	        url     : '/order/queryOrderDetailById.htm',
	        params:{
	            'id':orderId
	        },
	        headers : {'Content-Type': 'application/x-www-form-urlencoded'},
	    }).then(function(response) {
	    	$scope.orderDetailInfo = response.data.orderDetailInfo;
	    });
	}
	
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
	    	console.log($scope.ShipMsg.Traces)
	    });
	}

	$scope.changeStatus = function(orderDetail,status){
		var fd = new FormData();
		fd.append("id", orderDetail.id);
		fd.append("status", status);
		$http({
			method  : 'Post',
			url     : '/order/updateOrderDetail.htm',
			data : fd,
			headers : {'Content-Type': undefined},
		}).then(function(response) {
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				/* submitNow = false; */
				return;
			}
			Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.pageChanged();
		});
	}
	
	$scope.submitEdit = function(detailId){
		if(editNow) {
			return;
		}
		
		var shipMsg = $("#ship_msg").val();
		if(shipMsg == '') {
			Notify("请选择快递公司！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}
		if($scope.ship.no == '' && shipMsg != "NONE") {
			Notify("请输入快递单号！", 'top-right', '4000', 'danger', 'fa-bolt', true);
			return;
		}

		var fd = new FormData();
		fd.append("id",detailId)
		fd.append("shipNo", $scope.ship.no);
		fd.append("shipCode", shipMsg);
		editNow = true;
		$http({
		 	method  : 'POST',
	        url     : '/order/updateShipMsgById.htm',
	        data : fd,
            transformRequest: angular.identity,
        	headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				/* submitNow = false; */
				return;
			}

			Notify('发货成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.common.deliverGoods = false;
			$scope.pageChanged();
			editNow = false;
			
			$scope.ship = {
					code:'',
					no:''
				}
		});
	}
});
