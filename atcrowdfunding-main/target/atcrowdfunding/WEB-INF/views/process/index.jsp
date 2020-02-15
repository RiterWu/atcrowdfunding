<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/15 0015
  Time: 14:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <link rel="stylesheet" href="${APP_PATH}/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/font-awesome.min.css">
    <link rel="stylesheet" href="${APP_PATH}/css/main.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
    </style>
</head>

<body>

<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 流程管理</a></div>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
            <ul class="nav navbar-nav navbar-right">
                <jsp:include page="/WEB-INF/views/common/top.jsp"/>
            </ul>
            <form class="navbar-form navbar-right">
                <input type="text" class="form-control" placeholder="Search...">
            </form>
        </div>
    </div>
</nav>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-3 col-md-2 sidebar">
            <div class="tree">
                <jsp:include page="/WEB-INF/views/common/menu.jsp"/>
            </div>
        </div>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div>
                    <form id="deployPreForm" action="" method="post" enctype="multipart/form-data">
                        <input type="file" id="deployProcFile" name="deployProcFile" style="display: none">
                    </form>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>

                    <button type="button" id="uploadBtn" class="btn btn-primary" style="float:right;" onclick=""><i class="glyphicon glyphicon-upload"></i> 上传流程定义文件</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th>流程定义名称</th>
                                <th>流程定义版本</th>
                                <th>流程定义key</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>


                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination">

                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script src="${APP_PATH }/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH }/jquery/jquery-form/jquery-form.js"></script>
<script src="${APP_PATH }/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH }/script/docs.min.js"></script>
<script src="${APP_PATH }/jquery/layer/layer.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
        queryPage(1);
    });

    var jsonObj = {
        "pageNo": 1,
        "pageSize": 4
    };

    function queryPage(pageNo) {
        jsonObj.pageno = pageNo;

        var loadingIndex = -1;

        $.ajax({
            type: "POST",
            data: jsonObj,
            url: "${APP_PATH}/process/index.do",
            beforeSend: function () {
                loadingIndex = layer.load(2,{time: 10*1000});
                return true;
            },
            success: function (result) {
                layer.close(loadingIndex);
                if(result.status){
                    var page = result.pageInfo;

                    var content = '';

                    $.each(page.list,function (i,n) {

                        content += '<tr>';
                        content += '	<td>'+(i+1)+'</td>';
                        content += '	<td>'+n.name+'</td>';
                        content += '	<td>'+n.version+'</td>';
                        content += '	<td>'+n.key+'</td>';
                        content += '	<td>';
                        content += '		<button type="button" onclick="window.location.href=\'${APP_PATH}/process/toShowImg.htm?id='+n.id+'\'" class="btn btn-success btn-xs"><i class="glyphicon glyphicon-eye-open"></i></button>';
                        content += '		<button type="button" onclick="deleteProcess(\''+n.id+'\',\''+n.name+'\')" class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-remove"></i></button>';
                        content += '	</td>';
                        content += '</tr>';

                    });

                    $("tbody").html(content);

                    var contextBar = '';
                    if(page.hasPreviousPage){
                        contextBar += '		    <li><a href="#" onclick="pageChange('+(page.pageNum-1)+')">上一页</a></li>	';
                    }else {
                        contextBar += '		    <li class="disabled"><a href="#">上一页</a></li>	';
                    }

                    $.each(page.navigatepageNums,function (i,n) {
                        contextBar += '			<li ';
                        if(page.pageNum == n){
                            contextBar += ' class="active" ';
                        }
                        contextBar += '><a href="#" onclick="pageChange('+n+')">'+n+'</a></li>';
                    });

                    if(page.hasNextPage){
                        contextBar += '		    <li><a href="#" onclick="pageChange('+(page.pageNum+1)+')">下一页</a></li>	';
                    }else {
                        contextBar += '		    <li class="disabled"><a href="#">下一页</a></li>	';
                    }

                    $(".pagination").html(contextBar);


                }else {
                    layer.msg(result.message,{time:1000,icon:5,shift:6});
                }
            },
            error: function () {
                layer.msg("加载数据失败！",{time:1000,icon:5,shift:6});
            }
        });
    }

    $("#uploadBtn").click(function () {
        $("#deployProcFile").click();
    });

    $("#deployProcFile").change(function () {
        var loadingIndex = -1;

        var options = {
            url:"${APP_PATH}/process/deployProc.do",
            beforeSubmit : function(){
                loadingIndex = layer.msg('数据正在部署中', {icon: 6});
                return true ; //必须返回true,否则,请求终止.
            },
            success : function(result){
                layer.close(loadingIndex);
                if(result.status){
                    layer.msg("部署流程定义成功！", {time:1000, icon:6});
                    queryPage(1);
                }else{
                    layer.msg(result.message, {time:1000, icon:5, shift:6});
                }
            }
        };

        $("#deployPreForm").ajaxSubmit(options); //异步提交
    });
    
    function deleteProcess(id,name) {

        layer.confirm("您确定要删除["+name+"]流程定义吗?",{icon: 3, title: '提示'}, function (cindex) {
            layer.close(cindex);

            $.ajax({
                type: "POST",
                data: {
                    "id": id
                },
                url: "${APP_PATH}/process/delete.do",
                success: function (result) {
                    if (result.status) {
                        layer.msg("删除流程定义成功！", {time: 1000, icon: 1});
                        queryPage(1);
                    }else {
                        layer.msg(result.message, {time: 1000, icon: 5, shift: 6});
                    }
                }
            });

        });
    }

</script>
</body>
</html>