app.controller("headerCtrl", function($scope,$http,$timeout) {
	
	$scope.logout = function() {
		$http({
	         method: 'POST',
	         url: "/adminInfo/signOut.htm",
	         headers: {'Content-Type': 'application/x-www-form-urlencoded'}
	     }).then(function (response) {
	         window.location.href = "http://" + window.location.host;
	     });
	}
	
	$http({
        method: 'POST',
        url: "/adminInfo/loginChannelAdminInfo.htm",
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    }).then(function (res) {
    	$scope.adminInfo = res.data.admin;
    });
});