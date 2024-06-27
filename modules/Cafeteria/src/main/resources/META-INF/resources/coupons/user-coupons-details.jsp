<%@page import="com.cafeteria.constants.CafeteriaPortletKeys"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@ include file="/init.jsp" %>
<portlet:resourceURL id="<%=CafeteriaPortletKeys.CAFETERIA_USERS_COUPONS%>" var="fetchUserCouponsDetailsURL"></portlet:resourceURL>

<div class="row">
	<div class="col-md-12">
		 <div id="couponsGeneratedDiv" class="plng-accordion-cards ">
			<div class="card">
				<div class="align-items-center card-header d-flex justify-content-between">
					<h6 class="text-white mb-0 w-75">Coupon's Generated Today</h6>
				</div>
				<div class="card-body coupons-table">
					<div class="row md-4">
						<div class="col-md-12 table-responsive">
							<table id="coupons-generated-today" class="coupons-generated-today table" style="width:100%" aria-describedby="coupons-generated-today-info"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>	
</div>

<script>
var  fetchUserCouponsDetailsURL= '${fetchUserCouponsDetailsURL}';
</script>
