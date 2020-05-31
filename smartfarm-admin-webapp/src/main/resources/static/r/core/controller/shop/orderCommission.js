var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http) {
    $scope.common = {
        defaultNgModelOptions: {
            debounce: 500
        }
    };

    //分页数据
    $scope.pageData = {
        pageNumber: 1,
        pageSize: 50
    };


    $scope.formData = {
        total: 0,
        data: []
    };

    $scope.formDataChange = function () {
        $http({
            method: 'POST',
            url: '/order/getOrderCommissionList.htm',
            data: $.param($scope.pageData),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            $scope.formData.data = response.data.orderCommissionList;
            $scope.formData.total = response.data.total;
        });
    };
    $scope.formDataChange();

    //将传入的时间格式化
    $scope.formatDateTime = function(endDate) {
        return endDate.substring(0,4) + '-' + endDate.substring(4,6) + '-' + endDate.substring(6,8) + " " + endDate.substring(8,10) + ":" + endDate.substring(10,12);
    }
});
