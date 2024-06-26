<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys"%>
<%@ include file="/init.jsp" %>
<portlet:resourceURL id="<%=CafeteriaVendorManagementPortletKeys.CAFE_LIVE_INVENTORY_DETAILS%>" var="fetchLiveEntryDetailsURL"></portlet:resourceURL>

<div class="row">
	
	<div id="cafeteriaDiv" class="col-md-6 m-auto tableFixed ">
		 <div class="plng-accordion-cards ">
			<div class="card">
				<div class="align-items-center card-header d-flex justify-content-between">
					<h6 class="text-white mb-0 w-75">Cafeteria Details Entered Today (Live)</h6>
					<a class="text-decoration-none back-btn" href="/dashboard"><i class="bi bi-arrow-left-circle-fill text-white pr-2"></i>Back</a>
				</div>
				<div class="card-body ">
					<div class="row md-4">
						<div class="col-md-12 table-responsive ">
							<table id="cafe-entry-table" class="cafe-entry-table table" style="width:100%" aria-describedby="cafe-entry-table-info"></table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>	
	
	<div id="mdOrPantryDiv" class="col-md-6 tableFixed">
		<div class="plng-accordion-cards ">
			<div class="card">
				<div class="align-items-center card-header d-flex justify-content-between">
					<h6 class="text-white mb-0 w-75">MD Office/Pantry Details Entered Today (Live)</h6>
				</div>
				<div class="card-body ">
					<div class="row md-4">
						<div class="col-lg-4 ">
							<label class="mb-2">Select Inventory</label>
							<select id="selInv" name="selInv" class="form-control">
								<option value="">Select</option>
								<option  value="MD Office">MD Office</option>
								<option value="Pantry">Pantry</option>
							</select>
							</div>
					</div>
					
					<div class="row md-4">
						<div class="col-md-12 table-responsive ">
							<table id="pantry-entry-table" class="pantry-entry-table table" style="width:100%" aria-describedby="pantry-entry-table-info"></table>
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

