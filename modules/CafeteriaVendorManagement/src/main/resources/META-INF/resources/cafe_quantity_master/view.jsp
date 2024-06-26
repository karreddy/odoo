<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.plng.common.schema.model.CafeItemMaster"%>
<%@page import="java.util.List"%>
<%@page import="com.cafeteriaVendorManagement.constants.CafeteriaVendorManagementPortletKeys"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@ include file="/init.jsp" %>

<%
if (SessionErrors.contains(request, "qty-details-empty")) {
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

<portlet:actionURL name="<%=CafeteriaVendorManagementPortletKeys.QUANTITY_MASTER%>" var="saveQuantityMaster"></portlet:actionURL>
<div id="multi-step-form-container" class="my-0 step-card-width-30">
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
	<div class="card">
			
			<div class="align-items-center card-header d-flex justify-content-between align-items-center">
					<h6 class="text-white mb-0 w-75">PLL Canteen Management System : Quantity Master - Master Entry</h6>
					<a class="text-decoration-none back-btn" href="/dashboard"><i class="bi bi-arrow-left-circle-fill text-white pr-2"></i>Back</a>
				</div>
			
			<div class="card-body ">
			<form action="${saveQuantityMaster}" autocomplete="off" method="post" id="quantityDetails">
			<input type="hidden" name="redirectURL" value="<%= PortalUtil.getCurrentURL(request)%>">
				<div class="row mb-3">
					<div class="col-lg-12">
						<label for="">Item Description</label>
						<select id="itemDesc" name="itemDesc" class="form-control">
							 <option value="">Select Item</option>
								<% List<CafeItemMaster> cafeItemMasterList = (List<CafeItemMaster>) request.getAttribute("cafeItemMasterList");
									if (cafeItemMasterList != null) { %>
								    <% for (CafeItemMaster itemMasterList : cafeItemMasterList) { %>
								        <option value="<%= itemMasterList.getItemDesc() %>"><%= itemMasterList.getItemDesc() %></option>
								    <% } %>
								<% } else { %>
								    <option value="">No items available</option>
								<% } %> 
						</select>
					</div>
				</div>
				<div class="row mb-3">
					<div class="col-lg-12">
						<label for="">Item Quantity</label>
						<input type="number" class="form-control" id="itemQty" name="itemQty" min="1" oninput="this.value = Math.abs(this.value)" >
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
			 <%@ include file="/cafe_quantity_master/quantityMasterDetails.jsp"%>
		 </section>
</div>

<script>
//stepper config
$(document).ready(function() {
const navigateToFormStep = (stepNumber) => {
    document.querySelectorAll(".form-step").forEach((formStepElement) => {
        formStepElement.classList.add("d-none");
    });
    document.querySelectorAll(".form-stepper-list").forEach((formStepHeader) => {
        formStepHeader.classList.add("form-stepper-unfinished");
        formStepHeader.classList.remove("form-stepper-active", "form-stepper-completed");
    });
    document.querySelector("#step-" + stepNumber).classList.remove("d-none");
    const formStepCircle = document.querySelector('li[step="' + stepNumber + '"]');
    formStepCircle.classList.remove("form-stepper-unfinished", "form-stepper-completed");
    formStepCircle.classList.add("form-stepper-active");
    for (let index = 0; index < stepNumber; index++) {
        const formStepCircle = document.querySelector('li[step="' + index + '"]');
        if (formStepCircle) {
            formStepCircle.classList.remove("form-stepper-unfinished", "form-stepper-active");
            formStepCircle.classList.add("form-stepper-completed");
        }
    }
};
document.querySelectorAll(".btn-navigate-form-step").forEach((formNavigationBtn) => {
    formNavigationBtn.addEventListener("click", () => {
        const stepNumber = parseInt(formNavigationBtn.getAttribute("step_number"));
        navigateToFormStep(stepNumber);
    });
});

});
</script>

<script>
$(document).ready(function() {
    
    $('#quantityDetails').validate({
	    rules: {
	    	itemDesc:{
			    required: true,
			},
			itemQty:{
			    required: true,
			    maxlength:5
			},
	    },
	    messages: {
	    	itemDesc:{
	    		required:'Please Select Any Option',
	    		maxlength:'Please Enter Characters Less Than 5'
	    	} ,
	    	itemQty:{
	    		required:'Please Enter The Quantity',
	    		maxlength:'Please Enter Characters Less Than 5'
	    	}
	    },
	    errorPlacement:function( error, element ){
	    	error.appendTo(element.parent().after());
		  },
	    submitHandler: function(form) {
	        form.submit();
	    }
	});
    
});
</script>

