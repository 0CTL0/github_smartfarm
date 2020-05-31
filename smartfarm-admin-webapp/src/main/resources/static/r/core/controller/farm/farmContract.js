var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http) {
	$scope.picurl =  picurl;
	$scope.pageChanged = function() {
		$http({
			method  : 'POST',
			url     : '/farmInfo/getFarm.htm',
			data    : '',
			headers : {'Content-Type': 'application/x-www-form-urlencoded'},
		}).then(function(response) {
			$scope.farm = response.data.farm;
		});
	}
	$scope.pageChanged();

	$scope.submit = function() {
		if(!picCheck("租赁协议图片","contractImg")){return;}
		let fd = new FormData();
		fd.append("file",  document.querySelector('input[name="contractImg"]').files[0]);
		$http({
			method  : 'POST',
			url     : '/farmInfo/editFarmContract.htm',
			data : fd,
			transformRequest: angular.identity,
			headers : {'Content-Type' : undefined}  //angularjs设置文件上传的content-type修改方式
		}).then(function(response){
			if(!response.data.success) {
				Notify("操作失败！", 'top-right', '4000', 'danger', 'fa-bolt', true);
				return;
			}
			Notify('上传成功', 'top-right', '5000', 'success', 'fa-check', true);
			$scope.pageChanged();
			$(".fileinput-remove-button").click();
		});
	}

});
