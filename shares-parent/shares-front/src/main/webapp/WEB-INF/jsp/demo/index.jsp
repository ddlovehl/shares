<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../taglibs.jsp"%>
<html>

	<head>
		<meta charset="UTF-8" />
		<meta name="renderer" content="webkit" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0,user-scalable=0,uc-fitscreen=yes" />
		<meta name="apple-mobile-web-app-capable" content="yes" />
		<meta name="apple-mobile-web-app-status-bar-style" content="black" />
		<meta name="format-detection" content="telephone=no" />
		<title>miniMobile</title>
		<meta name="keywords" content="miniMobile的demo" />
		<meta name="description" content="miniMobile是一个简单易用的移动框架！" />
	</head>

	<body class="fadeIn animated">
		<header class="ui-header clearfix w75 h8 f46 pl3 pr3 color8 bg-color-success t-c o-h">
			<div class="ui-header-l fl w5">
				<b class="icon iconfont icon-sortlight"></b>
			</div>
			<div class="ui-header-c fl f30 w59">
				快速预览
			</div>
			<div class="ui-header-r fl w5">
				<i class="icon iconfont icon-phone"></i>
			</div>
		</header>
		<!-- aside -->
		<aside class="ui-aside w40 bg-color-success f30">
			<div class="user p3 color8 clearfix">
				<div class="fl w10">
					<img src="${ctx }/static/img/user.jpg" class="w10 h10 radius-o" />
				</div>
				<div class="fr w22">
					<span>狂奔的蜗牛！</span><br />
					<font class="tag f28">12</font>
				</div>
			</div>
			<ul class="f30 mt2">
				<li>
					<a href="index.html" class="pl3 color8">首页</a>
				</li>
				<li>
					<a href="list-button.html" class="pl3 color8">左滑出按钮</a>
				</li>
				<li>
					<a href="list.html" class="pl3 color8">常用列表</a>
				</li>
				<li>
					<a href="comment.html" class="pl3 color8">评论列表</a>
				</li>
				<li>
					<a href="my.html" class="pl3 color8">我的面板</a>
				</li>
			</ul>
		</aside>
		<style>
			/*只针对侧栏内容部分做简单的样式*/
			
			.ui-aside {
				line-height: 1.5em;
			}
			
			.ui-aside ul {
				border-top: 0.02rem solid #017da7;
			}
			
			.ui-aside li {
				line-height: 0.8rem;
				border-bottom: 0.02rem solid #017da7;
			}
			
			.ui-aside a {
				display: block;
			}
		</style>
		<script type="text/javascript" src="${ctx }/static/js/miniMobile.js"></script>
		<script type="text/javascript">
			var aside = $(".ui-aside").asideUi({
				hasmask: true,
				size: "4rem",
				position: "left",
				sidertime: 300
			});
			$(".ui-header-l").on('touchend', function() {
				aside.toggle();
			})
		</script>
		<!-- swiper -->
		<section class="swiper-container h40">
			<div class="swiper-wrapper">
				<div class="swiper-slide"><img src='${ctx}/static/img/banner1.jpg' class="w75 h40" /></div>
				<div class="swiper-slide"><img src='${ctx}/static/img/banner2.jpg' class="w75 h40" /></div>
				<div class="swiper-slide"><img src='${ctx}/static/img/banner3.jpg' class="w75 h40" /></div>
			</div>
			<!-- Add Arrows -->
			<div class="swiper-button-next"></div>
			<div class="swiper-button-prev"></div>
			<!-- Add Pagination -->
			<div class="swiper-pagination"></div>
		</section>
		<script>
			var swiper = new Swiper('.swiper-container', {
				navigation: {
					nextEl: '.swiper-button-next',
					prevEl: '.swiper-button-prev',
				},
				pagination: {
					el: '.swiper-pagination',
				}
			});
		</script>
		<style>
			.swiper-button-next,
			.swiper-button-prev {
				/*swiper 默认图标适应性较差，使用rem单位规定左右按钮大小，图标大小*/
				width: 0.3rem !important;
				height: 0.5rem !important;
				background-size: cover !important;
				margin-top: -0.23rem !important;
			}
		</style>
		<!--  -->
		<div class="t-c f28 p6 color4 bg-color6">
			<h2 class="color-danger f46">
				miniMobile
			</h2>
			<p>用最少的codding写出最灵活的代码。</p>
		</div>
		<!-- 导航 -->
		<section class="demo-nav t-c f28 clearfix">
			<div class="col-3 h19">
				<a href="button.html" class="pt4 pb4"><i class="f42 color-primary icon iconfont icon-anniu"></i>
					<p>按钮徽章</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="layer.html" class="pt4 pb4">
					<i class="f46 color-success icon iconfont icon-dialog"></i>
					<p>消息提示</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="grid.html" class="pt4 pb4"><i class="f44 color-info icon iconfont icon-biaoge"></i>
					<p>栅格化</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="form.html" class="pt4 pb4"><i class="f44 color-warning icon iconfont icon-iconfontliebiao1copy"></i>
					<p>表单元素</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="icon.html" class="pt4 pb4"><i class="f46 color-danger icon iconfont icon-tupian"></i>
					<p>icon</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="aside.html" class="pt4 pb4"><i class="f46 color-warning icon iconfont icon-menu"></i>
					<p>侧栏导航</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="font.html" class="pt4 pb4"><i class="f50 color-info icon iconfont icon-font"></i>
					<p>字体排版</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="foldingPanel.html" class="pt4 pb4"><i class="f50 color-primary icon iconfont icon-zhediemianban"></i>
					<p>折叠面板</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="swiper.html" class="pt4 pb4"><i class="f50 color-success icon iconfont icon-ic_view_carousel_px"></i>
					<p>swiper</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="flow.html" class="pt4 pb4"><i class="f42 color-warning icon iconfont icon-pubuliu"></i>
					<p>瀑布流</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="iscroll.html" class="pt4 pb4"><i class="f46 color-danger icon iconfont icon-tables-copy"></i>
					<p>iScroll</p>
				</a>
			</div>
			<div class="col-3 h19">
				<a href="listPic.html" class="pt4 pb4"><i class="f46 color-success icon iconfont icon-anniu"></i>
					<p>相册切换</p>
				</a>
			</div>	
			<div class="col-3 h19">
				<a href="keyboard.html" class="pt4 pb4"><i class="f46 color-primary icon iconfont icon-jianpan"></i>
					<p>安全键盘</p>
				</a>
			</div>	
			<div class="col-3 h19">
				<a href="Progress.html" class="pt4 pb4"><i class="f60 color-danger icon iconfont icon-jindutiao"></i>
					<p>Progress</p>
				</a>
			</div>	
			<div class="col-3 h19">
				<a href="fullWinodwPage.html" class="pt4 pb4"><i class="f50 color-warning icon iconfont icon-icwindowzoom48px"></i>
					<p>全屏切换</p>
				</a>
			</div>	
			<div class="col-3 h19">
				<a href="tab.html" class="pt4 pb4"><i class="f40 color-info icon iconfont icon-tab"></i>
					<p>Tab切换</p>
				</a>
			</div>	
		</section>
		<style>
			.demo-nav {
				line-height: 1.8em;
			}
			
			.demo-nav div {
				border-left: 1px solid #f1f1f1;
				border-bottom: 1px solid #f1f1f1;
			}
			
			.demo-nav a {
				display: block;
				width: 100%;
				height: 100%;
			}
			
			.demo-nav div:nth-child(4n+1) {
				border-left: none;
			}
		</style>
		<!-- 底部导航 -->
		<nav class="demo-bottomNav mt6 w75 h12 pt1 t-c f28 bg-color8 o-h clearfix">
			<div class="w15 fl">
				<a href="index.html">
					<i class="f46 icon iconfont icon-home_light"></i>
					<p>首页</p>
				</a>
			</div>
			<div class="w15 fl">
				<a href="list-button.html">
					<i class="f46 icon iconfont icon-comment"></i>
					<p>左滑</p>
				</a>
			</div>
			<div class="w15 fl">
				<a href="list.html">
					<i class="f46 icon iconfont icon-rank"></i>
					<p>列表</p>
				</a>
			</div>
			<div class="w15 fl">
				<a href="comment.html">
					<i class="f46 icon iconfont icon-list"></i>
					<p>评论</p>
				</a>
			</div>
			<div class="w15 fl">
				<a href="my.html">
					<i class="f46 icon iconfont icon-servicefill"></i>
					<p>我的</p>
				</a>
			</div>
		</nav>
		<style type="text/css">
			.demo-bottomNav {
				line-height: 1.8em;
				border-top:1px solid #F1F1F1;
			}
			
			.demo-bottomNav a {
				display: block;
				width: 100%;
				height: 100%;
			}
		</style>
	</body>

</html>