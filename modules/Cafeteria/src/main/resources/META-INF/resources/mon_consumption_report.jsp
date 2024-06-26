<%@page import="com.cafeteria.constants.CafeteriaWebConstantKeys"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="com.cafeteria.constants.CafeteriaPortletKeys"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="<%=CafeteriaPortletKeys.CAFETERIA_REPORT_MONTHLY_DETAILS%>" var="fetchCafeteriaReportURL"></portlet:resourceURL>
<div class="plng-accordion-cards step-card-width-10">
<div class="card">
		<form action="${saveCafeteriaDetails}" autocomplete="off" method="post" id="cafeteriaDetails">
			<input type="hidden" name="redirectURL" value="<%=PortalUtil.getCurrentURL(request)%>">
		
      	<div class="align-items-center card-header d-flex justify-content-between">
	         <h6 class="text-white mb-0 w-75">Cafeteria Management System : Monthly Consumption Report</h6>
     		<a class="text-decoration-none back-btn" href="/cafeteria"><i class="bi bi-arrow-left-circle-fill text-white pr-2"></i>Back To Coupon Generation</a>
		</div>
		
				<div class="card-body ">
								<div class="row mb-4 mt-1">
									<div class="col-lg-2 ">
									<label for="">Select Month</label> 
										<select id="month" name="month" class="form-control">
											<option value="1">Janaury</option>
											<option value="2">February</option>
											<option value="3">March</option>
											<option value="4">April</option>
											<option value="5">May</option>
											<option value="6">June</option>
											<option value="7">July</option>
											<option value="8">August</option>
											<option value="9">September</option>
											<option value="10">October</option>
											<option value="11">November</option>
											<option value="12">December</option>
										</select>
									</div>


									<div class="col-lg-2">
										<label for="">Select Year</label> 
										<select id="year" name="year" class="form-control">
										</select>
									</div>

									<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2 mt-4">
										<button id="sub" class="plng-btn" type="submit">Change Month</button>
									</div>
									
									<div class="col-lg-2 col-md-2 col-sm-2 col-xs-2">
									<% if (Boolean.parseBoolean(request.getAttribute(CafeteriaWebConstantKeys.IS_ROLE).toString())) { %>
											<label for="">Select Request For</label> 
												<select class="form-control" id="ModelFilter">
												<option value="All">All</option>
												<option value="Guest">Guest</option>
												<option value="Self">Self</option>
												</select>
									 <% } %>	
									</div>
									
								     <div class="col-lg-4 col-md-4 col-sm-4 col-xs-4 mt-4 text-center">
										<label class="alert alert-info py-2" id="total-consumed-amount"></label> 
									</div> 
				           		
									
								</div>

					<div class="row md-4">
						<div class="col-md-12 table-responsive">
							<table id="cafeteria-report-table" class="cafeteria-report-table  table" style="width:100%" aria-describedby="cafeteria-report-table-info"></table>
						</div>
						
					</div>
			</div>
	</form>
</div>
</div>
<style>
@media print {
	#cafeteria-report-table td, tr {
	    background: transparent !important;
	}
} 
</style>

<script>
var fetchCafeteriaReportURL='${fetchCafeteriaReportURL}';
</script> 


