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
            url: '/productSkuCommission/getProductSkuCommissionList.htm',
            data: $.param($scope.pageData),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            $scope.formData.data = response.data.skuCommissions;
            $scope.formData.total = response.data.total;
        });
    };
    $scope.formDataChange();
    
    $scope.submitUpdate = function (productSkuCommission) {
        $http({
            method: 'POST',
            url: '/productSkuCommission/editProductSkuCommission.htm',
            data: $.param(productSkuCommission),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        }).then(function (response) {
            if (!response.data.success) {
            	Notify('操作失败！', 'top-right', '4000', 'warning', 'fa-warning', true);
                return
            }
            Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
            $scope.formDataChange();
        });
    };


});
