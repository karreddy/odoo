<%@page import="com.cafeteria.constants.CafeteriaPortletKeys"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="<%=CafeteriaPortletKeys.CAFETERIA_COUPONS_DETAILS%>" var="fetchCouponsDetailsURL"></portlet:resourceURL>

<div id ="couponsDiv" class="plng-accordion-cards ">
    <div class="card">
        <div class="align-items-center card-header d-flex justify-content-between">
            <h6 class="text-white mb-0 w-75">Today's Coupons</h6>
        </div>
        <form action="${saveCafeteriaDetails}" autocomplete="off" method="post" id="cafeteriaDetails">
            <div class="card-body container-fluid coupon-data-div">
			         <div  id="coupon-card-container"  class="row mb-1 "></div>
				         <div class="mt-3">
				        	 <label style="color: red;font-size:10px">NOTE : Coupons Will Auto-Expire After 30 Mins Of Placing The Order. </label>
			       		 </div>
		         </div>
        </form>
    </div>
</div>
<script>
var fetchCouponsDetailsURL= '${fetchCouponsDetailsURL}';
</script>


 