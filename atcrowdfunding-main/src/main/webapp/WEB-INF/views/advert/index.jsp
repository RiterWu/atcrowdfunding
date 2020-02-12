<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020/2/11 0011
  Time: 20:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <div><a class="navbar-brand" style="font-size:32px;" href="#">众筹平台 - 广告管理</a></div>
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
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" onclick="window.location.href='${APP_PATH}/advert/toAdd.htm'"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th>广告描述</th>
                                <th>状态</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                            <tfoot>
                                <tr>
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

<script src="${APP_PATH}/jquery/jquery-2.1.1.min.js"></script>
<script src="${APP_PATH }/jquery/layer/layer.js"></script>
<script src="${APP_PATH}/bootstrap/js/bootstrap.min.js"></script>
<script src="${APP_PATH}/script/docs.min.js"></script>
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
        "pageSize": 2
    };

    function queryPage(pageNo) {
        jsonObj.pageno = pageNo;

        var loadingIndex = -1;

        $.ajax({
            type: "POST",
            data: jsonObj,
            url: "${APP_PATH}/advert/index.do",
            beforeSend: function () {
                loadingIndex = layer.msg("处理中...",{icon: 16});
            },
            success: function (result) {
                layer.close(loadingIndex);
                if(result.status){
                    var page = result.pageInfo;

                    var content = '';

                    $.each(page.list,function (i,n) {
                        content += '<tr>																											';
                        content += '    <td>'+(i+1)+'</td>																									';
                        content += '    <td>'+n.name+'</td>																				';

                        if (n.status == "0"){
                            content += '    <td>草稿</td>	';
                        }
                        else if (n.status == "1"){
                            content += '    <td>未审核</td>	';
                        }
                        else if (n.status == "2"){
                            content += '    <td>审核完成</td>';
                        }
                        else if (n.status == "3"){
                            content += '    <td>发布</td>	';
                        }else {
                            content += '    <td></td>	';
                        }

                        content += '    <td>																										';
                        content += '        <button type="button" class="btn btn-success btn-xs"><i class="glyphicon glyphicon-check"></i></button>	';
                        content += '        <button onclick="window.location.href=\'${APP_PATH}/advert/toUpdate.htm?id='+n.id+'\'" type="button" class="btn btn-primary btn-xs"><i class="glyphicon glyphicon-pencil"></i></button>';
                        content += '        <button onclick="deleteAdvert('+n.id+',\''+n.name+'\')" type="button" class="btn btn-danger btn-xs"><i class="glyphicon glyphicon-remove"></i></button>	';
                        content += '    </td> 																										';
                        content += '</tr>																											';
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

    function pageChange(pageNum) {
        queryPage(pageNum);
    }

    function deleteAdvert(id,name) {

        layer.confirm("您确定要删除["+name+"]广告吗?",{icon: 3, title: '提示'}, function (cindex) {
            layer.close(cindex);

            $.ajax({
                type: "POST",
                data: {
                  "id": id
                },
                url: "${APP_PATH}/advert/delete.do",
                success: function (result) {
                    if (result.status) {
                        layer.msg(result.message, {time: 1000, icon: 1});
                        window.location.href = "${APP_PATH}/advert/toIndex.htm";
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
