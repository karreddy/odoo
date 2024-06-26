<%@page import="com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="<%=CafeteriaVendorManagementPortletKeys.ITEM_MASTER_DETAILS%>" var="fetchItemMasterDetailsURL"></portlet:resourceURL>

<div class="plng-accordion-cards ">
	<div class="card">
		<div class="align-items-center card-header d-flex justify-content-between">
			<h6 class="text-white mb-0">Master Item List </h6>
			<a href="" class="text-decoration-none text-white btn-sm btn back-btn rounded-circle border-0 "><i class="bi bi-arrow-left-circle-fill mr-2" step_number="1"></i> Back</a>
		</div>
		<div class="card-body ">
			<div class="row md-4 ">
				<div class="col-md-12 table-responsive tableFixed">
					<table id="cafe-item-master-table" class=" cafe-item-master-table table"  style="width:100%" aria-describedby="cafe-item-master-table-info" ></table>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
var fetchItemMasterDetailsURL='${fetchItemMasterDetailsURL}';
</script>

