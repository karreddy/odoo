<%@page import="com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="<%=CafeteriaVendorManagementPortletKeys.VENDOR_MASTER_DETAILS%>" var="fetchVendorMasterDetailsURL"></portlet:resourceURL>

<div class="plng-accordion-cards ">
	<div class="card">
		<div class="align-items-center card-header d-flex justify-content-between">
			<h6 class="text-white mt-1">Master Vendor List</h6>
			<a href="" class="text-decoration-none text-white btn-sm btn back-btn rounded-circle border-0 "><i class="bi bi-arrow-left-circle-fill mr-2" step_number="1"></i> Back</a>
		
		</div>
		<div class="card-body ">
			<div class="row md-4">
				<div class="col-md-12  ">
					<div class="table-responsive ">
						<table id="cafe-vendor-master-table" class="cafe-vendor-master-table dataTable" aria-describedby="cafe-vendor-master-table-info" style="width:100%"></table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
var fetchVendorMasterDetailsURL= '${fetchVendorMasterDetailsURL}';
</script>

