<%@page import="com.plng.common.schema.model.CafeStockEntry"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.plng.common.schema.model.CafeQuantityMaster"%>
<%@page import="com.plng.common.schema.model.CafeItemMaster"%>
<%@page import="com.plng.common.schema.model.CafeVendorMaster"%>
<%@page import="java.util.List"%>
<%@page import="com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys"%>
<%@page import="com.liferay.portal.kernel.theme.ThemeDisplay"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>
<%@ include file="/init.jsp" %>

<%
if (SessionErrors.contains(request, "stock-details-empty")) {
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

<portlet:actionURL name="<%= CafeteriaVendorManagementPortletKeys.CAFETERIA_VENDOR_STOCK_INVENTORY%>" var="saveVendorStockInventory"></portlet:actionURL>

<%-- <%List<CafeVendorMaster> cafeVendorMastersList =(List<CafeVendorMaster>)request.getAttribute("cafeVendorMastersList"); %> --%>
 <div id="multi-step-form-container " class="my-0 ">
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
       <section id="step-1" class="form-step ">
<div class="plng-accordion-cards step-card-width-30">
<div class="card ">
		
						<div class="align-items-center card-header d-flex justify-content-between align-items-center">
							<h6 class="text-white mb-0 w-75">Canteen Vendor Stock Inventory Portal (Admin)</h6>
							<a class="text-decoration-none back-btn" href="/dashboard"><i class="bi bi-arrow-left-circle-fill text-white pr-2"></i>Back</a>
							</a>
						</div>
						
					<div class="card-body ">
					<form action="${saveVendorStockInventory}" autocomplete="off" method="post" id="stockDetails">
					<input type="hidden" name="redirectURL" value="<%= PortalUtil.getCurrentURL(request)%>">
						
								<div class="row mb-3"  >
									<div class="col-lg-12">
										<label for="">Vendor</label> 
										<select id="vendor" name="vendor" class="form-control">
										<option value="">Select Vendor</option>
										<% List<CafeVendorMaster> cafeVendorMastersList= (List<CafeVendorMaster>) request.getAttribute("cafeVendorMastersList");
												if (cafeVendorMastersList != null) { %>
											    <% for (CafeVendorMaster cafeVendorMaster : cafeVendorMastersList) { %>
											        <option value="<%= cafeVendorMaster.getVendorId() %>"><%= cafeVendorMaster.getVendorName() %></option>
											    <% } %>
											<% } else { %>
											    <option value="">No vendors available</option>
											<% } %> 
										</select>
									</div>
								</div>
								<div class="row mb-3"  >
									<div class="col-lg-12">
										<label for="">Item </label>
										 <select id="item" name="item" class="form-control">
										 <option value="">Select Item</option>
											<% List<CafeItemMaster> cafeItemMasterList = (List<CafeItemMaster>) request.getAttribute("cafeItemMasterList");
												if (cafeItemMasterList != null) { %>
											    <% for (CafeItemMaster itemMasterList : cafeItemMasterList) { %>
											        <option value="<%= itemMasterList.getItemId() %>"><%= itemMasterList.getItemDesc() %></option>
											    <% } %>
											<% } else { %>
											    <option value="">No items available</option>
											<% } %> 
										</select>
									</div>
								</div>
								<div class="row mb-3"  >
									<div class="col-lg-12">
										<label for="">Current Stock Quantity</label>
										 	<input type="number" class="form-control disableReadOnly mr-2" id="currentQty" name="currentQty" readonly >
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
										<label for="">Unit Price Of Item</label>
										 <input type="number" class="form-control" id="unitPrice" name="unitPrice" min="1" >
									</div>
								</div>
								<div class="row mb-3"  >
									<div class="col-lg-12">
										<label for="">Invoice No.</label>
										 <input type="text" class="form-control " id="invcNo" name="invcNo"  >
									</div>
								</div>
								<div class="row mb-3"  >
									<div class="col-lg-12">
										<label for="">Invoice Date</label>
										 <input type="Date" class="form-control" id="invcDate" name="invcDate" onclick="this.showPicker()" >
									</div>
								</div>
								<div class="row mb-3"  >
									<div class="col-lg-12">
										<label for="">Total Amount For This Item(In Rs.)</label>
										 <input type="number" class="form-control" id="totalAmt" name="totalAmt"   min="1">
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
		 <%@ include file="/caf_vendor_stock_inventory/vendorStockInventory.jsp"%>
	 </section>
</div>
<script>
/* 
var currentDate = new Date();
var formattedDate = currentDate.toISOString().split('T')[0];
document.getElementById("invcDate").setAttribute("max", formattedDate); 
document.getElementById("invcDate").value = formattedDate; */

var stockQuantityList = <%= (List<CafeStockEntry>)request.getAttribute("cafeStockEntriesByDateList")%>;
</script>