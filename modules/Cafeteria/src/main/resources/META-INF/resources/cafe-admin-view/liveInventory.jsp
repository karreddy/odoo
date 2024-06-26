<%@page import="com.cafeteria.constants.CafeteriaPortletKeys"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@ include file="/init.jsp" %>
<portlet:resourceURL id="<%=CafeteriaPortletKeys.CAFETERIA_LIVE_MENU%>" var="fetchLiveEntryDetailsURL"></portlet:resourceURL>

<div class="row">
	<div class="col-md-12">
		 <div class="plng-accordion-cards ">
			<div class="card">
				<div class="align-items-center card-header d-flex justify-content-between">
					<h6 class="text-white mb-0 w-75">Cafeteria Menu (Live)</h6>
				</div>
				<div class="card-body ">
					<div class="row md-4">
						<div class="col-md-12 table-responsive ">
							<table id="cafe-live-menu-table" class="cafe-live-menu-table table" style="width:100%" aria-describedby="cafe-live-menu-table-info"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>	
</div>

<script>
var fetchLiveEntryDetailsURL='${fetchLiveEntryDetailsURL}';
</script>
