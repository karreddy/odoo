<%@page import="com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="<%=CafeteriaVendorManagementPortletKeys.VENDOR_STOCK_ENTRY_DETAILS%>" var="fetchstockDetailsURL"></portlet:resourceURL>

<div class="plng-accordion-cards step-card-width-20">
	<div class="card">
		<div class="align-items-center card-header d-flex justify-content-between">
			<h6 class="text-white mt-1">Details Entered Today</h6>
			<a href="" class="text-decoration-none text-white btn-sm btn back-btn rounded-circle border-0 "><i class="bi bi-arrow-left-circle-fill mr-2" step_number="1"></i> Back</a>
		</div>
		<div class="card-body ">
			<div class="row md-4">
				<div class="col-md-12 table-responsive">
					<table id="cafe-stock-table" class="cafe-stock-table table" style="width:100%" aria-describedby="cafe-stock-table-info"></table>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
	var fetchstockDetailsURL='${fetchstockDetailsURL}';
</script>
