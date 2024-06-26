<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.plng.common.schema.model.CafeInventory"%>
<%@page import="com.plng.common.schema.model.CafeItemMaster"%>
<%@page import="java.util.List"%>
<%@page import="com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys"%>
<%@page import="com.liferay.portal.kernel.theme.ThemeDisplay"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ include file="/init.jsp" %>

<%
if (SessionErrors.contains(request, "cons-details-empty")) {
    %>
    <aui:script use="liferay-util-window">
        Liferay.Util.openToast({
            title: 'Error',
            type: 'danger'
        });
    </aui:script>
    <%
}
%>

<portlet:actionURL name="<%= CafeteriaVendorManagementPortletKeys.CAFETERIA_INVENTORY_CONSUMPTION%>" var="saveConsumptionInventory"></portlet:actionURL>

<%-- <%List<CafeVendorMaster> cafeVendorMastersList =(List<CafeVendorMaster>)request.getAttribute("cafeVendorMastersList"); %> --%>
<div id="multi-step-form-container" class="my-0 step-card-width-25">
 	<ul class="form-stepper form-stepper-horizontal text-center mx-auto pl-0 w-50">
 	<!-- Step 1 -->
                    <li class="form-stepper-active text-center form-stepper-list" step="1">
                        <a class="mx-2">
                            <span class="form-stepper-circle">
                                <span>1</span>
                            </span>
                            <div class="text-decoration-none text-dard text-4 font-weight-bold">Step 1</div>
                        </a>
                    </li>
    <!-- Step 2 -->
                    <li class="form-stepper-unfinished text-center form-stepper-list" step="2">
                        <a class="mx-2">
                            <span class="form-stepper-circle text-muted">
                                <span>2</span>
                            </span>
                            <div class="text-muted text-decoration-none text-dard text-4 font-weight-bold">Step 2</div>
                        </a>
                    </li>
     </ul>
 <!-- Step 1 Content -->
       <section id="step-1" class="form-step"> 
<div class="plng-accordion-cards ">
<div class="card ">
		
						
						<div class="align-items-center card-header d-flex justify-content-between align-items-center">
							<h6 class="text-white mb-0 w-75">Canteen Inventory Portal (Admin)-Inventory Consumption Entry</h6>
								<a class="text-decoration-none back-btn" href="/dashboard"><i class="bi bi-arrow-left-circle-fill text-white pr-2"></i>Back</a>
							</a>
						</div>
						
					<div class="card-body ">
								<form action="${saveConsumptionInventory}" autocomplete="off" method="post" id="consumptionDetails">
								<input type="hidden" name="redirectURL" value="<%= PortalUtil.getCurrentURL(request)%>">
								<div class="row mb-3"  >
									<div class="col-lg-12">
										<label for="">Date</label>
										 <input type="Date" class="form-control" onclick="this.showPicker()" id="date" name="date"  >
									</div>
								</div>
								
								<div class="row mb-3"  >
									<div class="col-lg-12">
										<label for="">Item </label>
										 <select id="item" name="item" class="form-control">
										 <option value="">Select Item</option>
									   <% 
								            JSONArray itemIdAndNameArray = (JSONArray) request.getAttribute("consArray");
								            if (itemIdAndNameArray != null) { 
								                for (int i = 0; i < itemIdAndNameArray.length(); i++) {
								                    JSONObject itemObject = itemIdAndNameArray.getJSONObject(i);
								                    String itemId = itemObject.getString("itemId");
								                    String itemName = itemObject.getString("itemName");
								            %>
								                    <option value="<%= itemId %>"><%= itemName %></option>
								            <% 
								                }
								            } else { 
								            %>
								                <option value="">No items available</option>
								            <% } %>  
										</select>
									</div>
								</div>
								<div class="row mb-3"  >
									<div class="col-lg-12">
										<label for="">Inventory On Display</label>
										 	<input type="number" class="form-control mr-2" id="invDisplay" name="invDisplay" readonly min="1" >
									</div>
								</div>
								<div class="row mb-3"  >
									<div class="col-lg-12">
										<label for="">Quantity</label>
										 <input type="number" class="form-control" id="Qty" name="Qty" min="1">
									</div>
								</div>
								
								<div class="row mb-3"  >
									<div class="col-lg-12">
										<label for="">Reason</label>
										 <input type="text" class="form-control" id="reason" name="reason" >
									</div>
								</div>
								
								
								<div class="row my-4">
									<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
										<button type="submit" id="sub" class="plng-btn" >Save</button>
										</form>
										<button class="button plng-btn btn-navigate-form-step text-decoration-none" type="button" step_number="2" >Get Details</button>
									</div>
								</div>
					</div>
		
	</div>
</div>
</section>
		 <section id="step-2" class="form-step d-none">
			<%@ include file="/cafeteria_consumption/consumptionDetails.jsp"%>
		 </section>
</div>
<script>
var currentDate = new Date();
var formattedDate = currentDate.toISOString().split('T')[0];
document.getElementById("date").value = formattedDate;

var inObject = <%= request.getAttribute("consArray")%>;
</script>