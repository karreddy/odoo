<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@ include file="/init.jsp" %>

<%
if (SessionErrors.contains(request, "vendor-details-empty")) {
    %>
    <aui:script use="liferay-util-window">
        Liferay.Util.openToast({
            title: 'Error',
            message: 'Empty Form Submission',
            type: 'danger'
        });
    </aui:script>
    <%
}
%>

<portlet:actionURL name="<%=CafeteriaVendorManagementPortletKeys.ITEM_MASTER%>" var="saveVendorMaster"></portlet:actionURL>
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
		
				<div class="card-header d-flex justify-content-between align-items-center">
					<h6 class="text-white mb-0 w-75">PLL Canteen Management System : Item Master - Master Entry</h6>
					<a class="text-decoration-none back-btn" href="/dashboard"><i class="bi bi-arrow-left-circle-fill text-white pr-2"></i>Back</a>
				</div>
			
			<div class="card-body ">
				<form action="${saveVendorMaster}" autocomplete="off" method="post" id="cafeItemDetails">
				<input type="hidden" name="redirectURL" value="<%= PortalUtil.getCurrentURL(request)%>">
				<div class="row mb-3">
					<div class="col-lg-12">
						<label for="">Item Description</label>
						<input type="text" class="form-control" id="itemDesc" name="itemDesc"  >
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-lg-12">
						<label for="">Item Units</label>
						<select id="itemUnits" name="itemUnits" class="form-control">
							 <option value="">Select Item</option>
							 <option selected  value="Pieces">Pieces/Packets</option>
						 </select>
					</div>
				</div>
				<div class="row my-4">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<button type="submit" id="sub" class="plng-btn"" >Save</button>
						</form>	
						<button class="button plng-btn btn-navigate-form-step text-decoration-none" type="button" step_number="2" >Get Details</button>
					</div>
				</div>
				
				</div>
				
			</div>
	</div>
</section>
		 <section id="step-2" class="form-step d-none">
			<%@ include file="/caf_item_master/itemMasterDetails.jsp"%>
		 </section>
</div>
