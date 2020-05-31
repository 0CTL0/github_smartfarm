var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http,$timeout) {

	$('.menu-dropdown').dropdown();

	//格式化时间
	$scope.formatDateTime2 = function(endDate) {
		if(endDate == undefined || endDate == ''){
			return '';
		}
		return endDate.substring(0,4) + endDate.substring(5,7) + endDate.substring(8,10) + endDate.substring(11,13) + endDate.substring(14,16) + "00";
	}

	//初始化所有时间控件
	for(var i = 0;i<document.getElementsByName("myDates").length;i++){
		$(document.getElementsByName("myDates")).datetimepicker({
			minView: "hour",//设置只显示到月份
			format : "yyyy-mm-dd hh:ii",//日期格式
			autoclose:true,//选中关闭
			todayBtn: true//今日按钮
		});
	}

	$scope.export = function() {
		if(!stringCheck("导出开始日期",$scope.formData.endDate)){return;}
		if(!stringCheck("导出结束日期",$scope.formData.endDate)){return;}
		window.location.href = "/order/exportOrder.htm?startDate=" + $scope.formatDateTime2($scope.formData.startDate) + "&endDate=" + $scope.formatDateTime2($scope.formData.endDate);

	}
}