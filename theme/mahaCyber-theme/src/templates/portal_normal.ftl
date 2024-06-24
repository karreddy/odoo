<!DOCTYPE html>

<#include init />

<html class="${root_css_class}" dir="<@liferay.language key="lang.dir" />" lang="${w3c_language_id}">

<head>
	<title>Maharashtra Cyber : ${the_title}</title>

	<meta content="initial-scale=1.0, width=device-width" name="viewport" /> 
	<link rel="stylesheet" type="text/css" href="${css_folder}/bootstrap-icons.min.css">
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/animate.min.css">
	<link rel="stylesheet" type="text/css" href="${css_folder}/plugins/aos.css"> 

	<@liferay_util["include"] page=top_head_include />
</head>

<body class="${css_class}">

<@liferay_ui["quick-access"] contentId="#main-content" />

<@liferay_util["include"] page=body_top_include />

<#if is_signed_in && permissionChecker.isOmniadmin()>
	<@liferay.control_menu /> 
</#if>

<div class="container-fluid position-relative lfr-tooltip-scope p-0" id="wrapper">
	<div id="loader-wrapper">
		<#--  <img src="${images_folder}/mahaCyber.png" class="d-block">  -->
		<div class="loader"></div>
	</div>

	<header>
		<#include "${full_templates_path}/header.ftl" />
	</header>
	 
	<#if has_navigation && is_setup_complete>
		<#include "${full_templates_path}/navigation.ftl" />
	</#if>  
	 
	<section id="content">
		<h2 class="hide-accessible sr-only d-none" role="heading" aria-level="1">${htmlUtil.escape(the_title)}</h2>   
		<#if selectable>
			<@liferay_util["include"] page=content_include />
		<#else>
			${portletDisplay.recycle()}

			${portletDisplay.setTitle(the_title)}

			<@liferay_theme["wrap-portlet"] page="portlet.ftl">
				<@liferay_util["include"] page=content_include />
			</@>
		</#if>
	</section> 

	<footer id="footer" role="contentinfo" class="">
		<#include "${full_templates_path}/footer.ftl" />
	</footer> 
</div>

<@liferay_util["include"] page=body_bottom_include />

<@liferay_util["include"] page=bottom_include />



<!--Offline Plugins-->
<script src="${javascript_folder}/plugins/jquery.validate.min.js"></script>
<script src="${javascript_folder}/plugins/aos.js"></script> 
  
</body> 

</html>