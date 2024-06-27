<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys"%>
<%@ include file="/init.jsp" %>
<portlet:resourceURL id="<%=CafeteriaVendorManagementPortletKeys.PANTRY_OFFICE_INVENTORY_DETAILS%>" var="fetchPantryDetailsURL"></portlet:resourceURL>
<portlet:resourceURL id="<%=CafeteriaVendorManagementPortletKeys.MDPANTRY_INVENTORY_DETAILS%>" var="fetchMdPantryURL"></portlet:resourceURL>

<div class="row">
	
	<div id="cafeteriaDiv"  class="col-md-6 m-auto">
		 <div class="plng-accordion-cards ">
			<div class="card">
				<div class="align-items-center card-header d-flex justify-content-between">
					<h6 class="text-white mb-0 w-75">Cafeteria Details Entered Today</h6>
										<a href="" class="text-decoration-none text-white btn-sm btn back-btn rounded-circle border-0 "><i class="bi bi-arrow-left-circle-fill mr-2" step_number="1"></i> Back</a>
				</div>
				<div class="card-body ">
					<div class="row md-4">
						<div class="col-md-12 table-responsive">
							<table id="cafe-entry-table" class="cafe-entry-table table" style="width:100%" aria-describedby="cafe-entry-table-info"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>	
	
	<div id="mdOrPantryDiv" class="col-md-6 m-auto">
		<div class="plng-accordion-cards ">
		 <form action="${fetchMdPantryURL}" autocomplete="off" method="post" id="invDetails">
			<div class="card">
				<div class="align-items-center card-header d-flex justify-content-between py-3">
					<h6 class="text-white mb-0 w-75">MD Office/Pantry Details Entered Today</h6>
				</div>
				<div class="card-body ">
					<div class="row md-4 flt">
						<div class="col-lg-4 ">
							<label class="mb-0">Select Inventory</label>
							<select id="selInv" name="selInv" class="form-control">
								<option selected value="1">Select</option>
								<option  value="2">MD Office</option>
								<option value="3">Pantry</option>
							</select>
							</div>
					</div>
				
					<div class="row md-4">
						<div class="col-md-12 table-responsive">
							<table id="pantry-entry-table" class="pantry-entry-table table" style="width:100%" aria-describedby="pantry-entry-table-info"></table>
						</div>
					</div>
				</div>
			  </div>
			</form>
		</div>
	</div>
	
	
</div>

<script>
var fetchCanteenURL= '${fetchPantryDetailsURL}';
var fetchPantryURL= '${fetchMdPantryURL}';
</script>
