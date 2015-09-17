<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%String path=request.getContextPath(); %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>首页 - iWork</title>


<link href="<%=path %>/ui/main/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=path %>/ui/main/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="<%=path %>/ui/main/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="<%=path %>/ui/main/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<!--[if IE]>
<link href="<%=path %>/ui/main/themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>
<![endif]-->

<!--[if lte IE 9]>
<script src="<%=path %>/ui/main/js/speedup.js" type="text/javascript"></script>
<![endif]-->

<script src="<%=path %>/ui/main/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="<%=path %>/ui/main/js/jquery.cookie.js" type="text/javascript"></script>
<script src="<%=path %>/ui/main/js/jquery.validate.js" type="text/javascript"></script>
<script src="<%=path %>/ui/main/js/jquery.bgiframe.js" type="text/javascript"></script>
<script src="<%=path %>/ui/main/xheditor/xheditor-1.2.1.min.js" type="text/javascript"></script>
<script src="<%=path %>/ui/main/xheditor/xheditor_lang/zh-cn.js" type="text/javascript"></script>
<script src="<%=path %>/ui/main/uploadify/scripts/jquery.uploadify.js" type="text/javascript"></script>

<!-- svg图表  supports Firefox 3.0+, Safari 3.0+, Chrome 5.0+, Opera 9.5+ and Internet Explorer 6.0+ -->
<script type="text/javascript" src="<%=path %>/ui/main/chart/raphael.js"></script>
<script type="text/javascript" src="<%=path %>/ui/main/chart/g.raphael.js"></script>
<script type="text/javascript" src="<%=path %>/ui/main/chart/g.bar.js"></script>
<script type="text/javascript" src="<%=path %>/ui/main/chart/g.line.js"></script>
<script type="text/javascript" src="<%=path %>/ui/main/chart/g.pie.js"></script>
<script type="text/javascript" src="<%=path %>/ui/main/chart/g.dot.js"></script>


<!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换是下面dwz.regional.zh.js还需要引入)-->
<script src="<%=path %>/ui/main/js/dwz.min.js" type="text/javascript"></script>

<script src="<%=path %>/ui/main/js/dwz.regional.zh.js" type="text/javascript"></script>

<script type="text/javascript">
$(function(){
	DWZ.init("<%=path %>/main/dwz.frag.xml", {
		loginUrl:"<%=path %>/login.html", loginTitle:"登录",	// 弹出登录对话框
//		loginUrl:"login.html",	// 跳到登录页面
		statusCode:{ok:200, error:300, timeout:301}, //【可选】
		pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
		keys: {statusCode:"statusCode", message:"message"}, //【可选】
		ui:{hideMode:'offsets'}, //【可选】hideMode:navTab组件切换的隐藏方式，支持的值有’display’，’offsets’负数偏移位置的值，默认值为’display’
		debug:false,	// 调试模式 【true|false】
		callback:function(){
			initEnv();
			$("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
		}
	});
});

</script>

</head>
<body scroll="no">
	<div id="layout" >
		<div id="header">
			<div class="headerNav">
				<span style="color: white;font-size: 24pt;margin: 10px;line-height: 50px;">iWork</span>
				<ul class="nav">					
					<li><a href="#" >${admin.realname }（${admin.username }）</a></li>
					<li><a href="changepwd.html"  target="dialog" rel="changepwd" >修改密码</a></li>
					<li><a href="logout.jsp" onclick="return confirm('${admin.realname }，您是否确认退出系统？');">退出</a></li>
				</ul>				
			</div>

			<!-- navMenu -->
			
		</div>

		<div id="leftside">
			<div id="sidebar_s">
				<div class="collapse">
					<div class="toggleCollapse"><div></div></div>
				</div>
			</div>
			<div id="sidebar">
				<div class="toggleCollapse"><h2>管理菜单</h2><div>收缩</div></div>

				<div class="accordion" fillSpace="sidebar">
					<div class="accordionHeader">
						<h2><span>Folder</span>日常工作</h2>
					</div>
					<div class="accordionContent">
						<ul class="tree treeFolder">
                            <li><a>我</a>
                                <ul>
                                    <li><a href="<%=path %>/ShowReport.wx?PAGEID=my_task_list" target="navTab" rel="my_task_list" external="true">我接收的任务</a></li>
                                    <li><a href="<%=path %>/ShowReport.wx?PAGEID=t_task_list" target="navTab" rel="t_task_list" external="true">我下达的任务</a></li>
                                    <li><a href="<%=path %>/ShowReport.wx?PAGEID=t_kpi_list" target="navTab" rel="t_kpi_list" external="true">我下达的KPI</a></li>
                                    <li><a href="<%=path %>/ShowReport.wx?PAGEID=t_version_list" target="navTab" rel="t_version_list" external="true">我的版本</a></li>
                                </ul>
                            </li>
                            <li><a>综合查询</a>
								<ul>
                                    <li><a href="<%=path %>/ShowReport.wx?PAGEID=user_list" target="navTab" rel="user_list" external="true">通讯录</a></li>
									<li><a href="<%=path %>/ShowReport.wx?PAGEID=task_list" target="navTab" rel="task_list" external="true">任务查询</a></li>
									<li><a href="<%=path %>/ShowReport.wx?PAGEID=kpi_list" target="navTab" rel="kpi_list" external="true">KPI考核查询</a></li>					
									<li><a href="<%=path %>/ShowReport.wx?PAGEID=version_list" target="navTab" rel="version_list" external="true">版本查询</a></li>									
								</ul>
							</li>
							<li><a>系统管理</a>
								<ul>						
									<li><a href="<%=path %>/ShowReport.wx?ACTIONTYPE=updateconfig" target="navTab" rel="updateconfig" external="true">热布署</a></li>
								</ul>
							</li>					
						</ul>
					</div>
					<div class="accordionHeader">
						<h2><span>Folder</span>版本信息</h2>
					</div>
					<div class="accordionContent">
						<div align="center">V1.0</div>
					</div>					
				</div>
			</div>
		</div>
		<div id="container">
			<div id="navTab" class="tabsPage">
				<div class="tabsPageHeader">
					<div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
						<ul class="navTab-tab">
							<li tabid="main" class="main"><a href="javascript:;"><span><span class="home_icon">我的主页</span></span></a></li>
						</ul>
					</div>
					<div class="tabsLeft">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" -->
					<div class="tabsRight">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" -->
					<div class="tabsMore">more</div>
				</div>
				<ul class="tabsMoreList">
					<li><a href="javascript:;">我的主页</a></li>
				</ul>
				<div class="navTab-panel tabsPageContent layoutBox">
					<div class="page unitBox">
						<div class="accountInfo">
							<div class="alertInfo">
								<p><a href="#" target="_blank" style="line-height:19px"><span>操作手册</span></a></p>
							</div>			
							<div class="divider"></div>
						</div>				
						
					</div>					
				</div>
			</div>
		</div>
	</div>
	<div id="footer" >Copyright &copy; 2015 <a href="#" target="dialog">GGS</a> </div>
</body>
</html>