var app = angular.module("myApp", ["ui.bootstrap"]);
app.controller("myCtrl", function($scope,$http) {
    $scope.common = {
        showAdd: false,
        showUpdate: false,
        updateSelected: {},
        adminAdd: {},
        allChannel: [],
        allSupplier: [],
        allChannelStore: [],
        showRoleUpdate: false,
        roleUpdateId: undefined,
        allRoles: [],

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
            url: '/adminInfo/queryAdminPage.htm',
            data: $.param($scope.pageData),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            $scope.formData.data = response.data.admins;
            $scope.formData.total = response.data.total;
        });
    };
    $scope.formDataChange();
    $http({
        method: 'POST',
        url: '/farmInfo/queryAll.htm',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
    }).then(function (response) {
        $scope.farmList = response.data.list;
    });
    
    
    $scope.toggleAdminStatus = function (item) {
        item.status = item.status === 1 ? 2 : 1;
        $http({
            method: 'POST',
            url: '/adminInfo/update/status.htm',
            data: $.param({id: item.id, status: item.status}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        }).then(function (response) {
            if (!response.data.success) {
            	Notify('操作失败！', 'top-right', '4000', 'warning', 'fa-warning', true);
                return
            }
            Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
        });
    };

    //打开角色修改窗口
    $scope.openRoleUpdateDialog = function (item) {
        $scope.common.roleUpdateId = item.id;
        $http({
            method: 'POST',
            url: '/adminInfo/adminRole.htm',
            data: $.param({adminId: item.id}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            $scope.common.allRoles = response.data.allRoles;
            var roleContained = response.data.roles;
            $scope.common.allRoles.forEach(function (value) {
                value.contained = roleContained.indexOf(value.id) !== -1;
            });
            $scope.common.showRoleUpdate = true;
        });
    };

    //提交角色修改
    $scope.submitRoleUpdate = function () {
        var roleContained = [];
        $scope.common.allRoles.forEach(function (value) {
            if (value.contained) {
                roleContained.push(value.id)
            }
        });
        $http({
            method: 'POST',
            url: '/adminInfo/roleSet.htm',
            data: $.param({id: $scope.common.roleUpdateId, roles: roleContained}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            if (!response.data.success) {
            	Notify('操作失败！', 'top-right', '4000', 'warning', 'fa-warning', true);
                return
            }
            Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);

            $scope.closeRoleUpdateDialog();
            $scope.common.allRoles = [];
        });
    };

    //关闭角色修改窗口
    $scope.closeRoleUpdateDialog = function () {
        $scope.common.showRoleUpdate = false;
    };

    //打开修改窗口
    $scope.openUpdateDialog = function (item) {
        $scope.common.updateSelected = JSON.parse(JSON.stringify(item));
        $scope.common.showUpdate = true;
    };

    //关闭修改窗口
    $scope.closeUpdateDialog = function () {
        $scope.common.showUpdate = false;
    };

    $scope.submitUpdate = function () {
        var item = $scope.common.updateSelected;
        $http({
            method: 'POST',
            url: '/adminInfo/update/remark.htm',
            data: $.param({id: item.id, remark: item.remark}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
        }).then(function (response) {
            if (!response.data.success) {
            	Notify('操作失败！', 'top-right', '4000', 'warning', 'fa-warning', true);
                return
            }
            Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
            $scope.formDataChange();
            $scope.closeUpdateDialog();
        });
    };

    //打开新增窗口
    $scope.openAddDialog = function () {
        $scope.common.adminAdd = {};
        $scope.common.showAdd = true;
    };

    //关闭新增窗口
    $scope.closeAddDialog = function () {
        $scope.common.showAdd = false;
    };

    $scope.adminAddTypeChange = function () {
        $scope.common.adminAdd.channelId = undefined;
        $scope.common.adminAdd.supplierId = undefined;
        $scope.common.adminAdd.channelStoreId = undefined;
    };

    $scope.adminAddChannelIdChange = function () {
        if (Number($scope.common.adminAdd.type) !== 4 || !$scope.common.adminAdd.channelId) {
            return;
        }
        $http({
            method: 'POST',
            url: '/channelStore/all/channelId.htm',
            data: $.param({channelId: $scope.common.adminAdd.channelId}),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            $scope.common.allChannelStore = response.data.stores;
        });
    };

    $scope.submitAdd = function () {
        if (!$scope.common.adminAdd.account) {
        	Notify('请输入帐号！', 'top-right', '4000', 'warning', 'fa-warning', true);
            return;
        }
        if (!$scope.common.adminAdd.password) {
        	Notify('请输入密码！', 'top-right', '4000', 'warning', 'fa-warning', true);
            return;
        }
        if (!$scope.common.adminAdd.farmId) {
        	Notify('请选择所属！', 'top-right', '4000', 'warning', 'fa-warning', true);
            return;
        }

        $http({
            method: 'POST',
            url: '/adminInfo/insert.htm',
            data: $.param($scope.common.adminAdd),
            headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(function (response) {
            if (!response.data.success) {
            	Notify('操作失败！', 'top-right', '4000', 'warning', 'fa-warning', true);
                return
            }
            Notify('操作成功', 'top-right', '5000', 'success', 'fa-check', true);
            $scope.formDataChange();
            $scope.closeAddDialog();
        });
    };
});
