var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http) {
    $scope.common = {
        funcTree: [],
        funcSelected: {},
        showAdd: false,
        funcAdd: {}
    };
    $scope.funcTreeUpdate = function() {
        $http({
            method  : 'POST',
            url     : '/func/tree.htm',
            headers : {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function(response) {
            $scope.common.funcTree =  response.data.funcTree;
            $scope.funcChange($scope.common.funcTree[0].id);
        });
    };

    $scope.funcTreeUpdate();
    $scope.funcChange = function (id) {
        $http({
            method  : 'POST',
            url     : '/func/list/' + id + ".htm",
            headers : {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function(response) {
            $scope.common.funcSelected = response.data.function;
        });
    };

    $scope.submitUpdate = function () {
        if (!$scope.common.funcSelected.name) {
        	Notify('菜单名称不允许为空！', 'top-right', '4000', 'warning', 'fa-warning', true);
            return
        }
        $http({
            method  : 'POST',
            url     : '/func/update.htm',
            data: $.param($scope.common.funcSelected),
            headers : {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function(response) {
            if(!response.data.success) {
            	Notify('操作失败！', 'top-right', '4000', 'warning', 'fa-warning', true);
                return
            }
            Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
            $scope.funcChange($scope.common.funcSelected.id);
        });
    };

    //打开新增窗口
    $scope.openAddDialog = function() {
        $scope.common.funcAdd = {};
        $scope.common.showAdd = true;
    };

    //关闭新增窗口
    $scope.closeAddDialog = function() {
        $scope.common.showAdd = false;
    };

    $scope.submitAdd = function () {
        if (!$scope.common.funcAdd.name) {
        	Notify('菜单名称不允许为空！', 'top-right', '4000', 'warning', 'fa-warning', true);
            return
        }
        $http({
            method: "POST",
            url: "/func/insert.htm",
            data: $.param($scope.common.funcAdd),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            if(!response.data.success) {
            	Notify('操作失败！', 'top-right', '4000', 'warning', 'fa-warning', true);
                return
            }
            Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
            $scope.funcTreeUpdate();
            $scope.closeAddDialog();
        })
    };


});