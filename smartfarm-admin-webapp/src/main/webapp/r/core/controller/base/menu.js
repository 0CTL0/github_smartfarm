app.controller("menuCtrl", function($scope,$http,$timeout) {
	$http({
         method: 'POST',
         url: "/func/list.htm",
         headers: {'Content-Type': 'application/x-www-form-urlencoded'}
     }).then(function (response) {
         $scope.funcs = response.data.func;
         $scope.nowPath = getUrlRelativePath();
         $timeout(function() {
        	 initSystem();
         }, 100);
         
     });
	
	$scope.checkMenuClass = function(func) {
		if(func.sub.length == 0 && func.url != $scope.nowPath) {
			return "";
		}
		if(func.sub.length == 0 && func.url == $scope.nowPath) {
			return "active";
		}
		for(var i = 0;i < func.sub.length;i++) {
			if(func.sub[i].url == $scope.nowPath) {
				return "active open";
			}
		}
		return '';
	}
});
var headerUrl = "/r/core/controller/base/header.js";
document.write("<script language=javascript src="+headerUrl+"></script>");