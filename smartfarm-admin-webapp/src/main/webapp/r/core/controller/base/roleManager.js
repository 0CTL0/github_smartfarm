var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http) {
	var submitNow = false;
    $scope.common = {
        showUpdate:false,
        showAdd:false,
        showFuncUpdate: false,
        funcTree: [],
        funcContained: [],
        roleSelectedId: undefined,
        roleUpdate:{},
        roleAdd:{}
    };
    $scope.pageData = {
        pageNumber:1,
        pageSize:50
    };
    //分页数据
    $scope.formData = {
        total:0,
        data:{}
    };
    $scope.formDataChange = function() {
        $http({
            method  : 'POST',
            url     : '/role/queryPage.htm',
            data: $.param($scope.pageData),
            headers : {'Content-Type': 'application/x-www-form-urlencoded'},
        }).then(function(response) {
            $scope.formData.data = response.data.roles;
            $scope.formData.total = response.data.total;
        });
    };

    $scope.formDataChange();

    //打开新增窗口
    $scope.openAddDialog = function() {
        $scope.common.showAdd = true;
    };

    //关闭新增窗口
    $scope.closeAddDialog = function() {
        $scope.common.showAdd = false;
    };

    // 打开修改窗口
    $scope.openUpdateDialog = function (item) {
        $scope.common.showUpdate = true;
        $scope.common.roleUpdate = JSON.parse(JSON.stringify(item));
    };

    // 关闭修改窗口
    $scope.closeUpdateDialog = function () {
        $scope.common.showUpdate = false;
    };

    $scope.submitUpdate = function () {
        if (!$scope.common.roleUpdate.name) {
        	Notify('角色名称不允许为空！', 'top-right', '4000', 'warning', 'fa-warning', true);
            return;
        }
        $http({
            method: "POST",
            url: "/role/update.htm",
            data: $.param($scope.common.roleUpdate),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            if(!response.data.success) {
            	Notify('操作失败！', 'top-right', '4000', 'warning', 'fa-warning', true);
                return
            }
            Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
            $scope.formDataChange();
            $scope.closeUpdateDialog();
        })
    };

    $scope.submitAdd = function () {
        if (!$scope.common.roleAdd.name) {
        	Notify('角色名称不允许为空！', 'top-right', '4000', 'warning', 'fa-warning', true);
            return;
        }
        $http({
            method: "POST",
            url: "/role/add.htm",
            data: $.param($scope.common.roleAdd),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            if(!response.data.success) {
            	Notify('操作失败！', 'top-right', '4000', 'warning', 'fa-warning', true);
                return
            }
            Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
            $scope.formDataChange();
            $scope.closeAddDialog();
        })
    };

    //打开菜单修改窗口
    $scope.openFuncUpdateDialog = function(id) {
        $scope.common.roleSelectedId = id;
        $http({
            method  : 'POST',
            url     : '/func/tree.htm',
            headers : {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function(response) {
            $scope.common.funcTree =  response.data.funcTree;
        });
        $http({
            method  : 'POST',
            url     : '/role/func.htm',
            data : $.param({'id':id}),
            headers : {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function(response) {
            $scope.common.funcContained =  response.data.funcContained;
            $scope.common.showFuncUpdate = true;
        });

    };

    //关闭菜单修改窗口
    $scope.closeFuncUpdateDialog = function() {
        $scope.common.showFuncUpdate = false;
    };

    $scope.funcSelected = function (id) {
        return ($scope.common.funcContained.indexOf(id) !== -1);
    };
    $scope.toggleSelect = function (id) {
        if ($scope.common.funcContained.indexOf(id) === -1) {
            $scope.common.funcContained.push(id);
            return
        }
        Array.prototype.remove = function(from, to) {
            var rest = this.slice((to || from) + 1 || this.length);
            this.length = from < 0 ? this.length + from : from;
            return this.push.apply(this, rest);
        };
        $scope.common.funcContained.remove($scope.common.funcContained.indexOf(id));
    };
    
    $scope.unique = function(arr) {
    	  return Array.from(new Set(arr))
    }
    $scope.submitFuncUpdate = function () {
    	if(submitNow) {
    		return;
    	}
    	submitNow = true;
        $http({
            method  : 'POST',
            url     : '/role/func/update.htm',
            data: $.param({'id': $scope.common.roleSelectedId, 'func': $scope.unique($scope.common.funcContained)}),
            headers : {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function(response) {
        	submitNow = false;
            if (!response.data.success) {
            	Notify('更新失败', 'top-right', '4000', 'warning', 'fa-warning', true);
                return;
            }
            Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
            $scope.closeFuncUpdateDialog();
        });
    }
});