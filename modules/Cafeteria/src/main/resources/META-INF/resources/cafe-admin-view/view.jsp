<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="com.cafeteria.constants.CafeteriaPortletKeys"%>
<%@ include file="/init.jsp"%>

<portlet:resourceURL id="<%=CafeteriaPortletKeys.CAFETERIA_ADMIN_COUPONS_DETAILS%>" var="fetchCouponsDetailsURL"></portlet:resourceURL>

<portlet:actionURL name="<%=CafeteriaPortletKeys.CAFETERIA_ISSUE_COUPON%>" var="saveCouponDetails"></portlet:actionURL>
<portlet:resourceURL id="<%=CafeteriaPortletKeys.CAFETERIA_ALL_ISSUED_COUPONS%>" var="fetchALLIssuedCouponsURL"></portlet:resourceURL>

<div class="plng-accordion-cards col-md-12 text-right">
	<div class=" col-lg-12 col-md-12 col-sm-12 col-xs-12 mt-4 mb-4 mx-4">
		<button id="viewIssued" class="text-decoration-none back-btn" data-toggle="modal" data-target="#staticBackdrop">Issued Coupons</button>
	</div>
</div>


<div class="container-fluid p-0">
	<div class="plng-accordion-cards lfr-tooltip-scope">
		<div class="row">
			<div class="col-md-4">
				<div class="card ">
					<div
						class="align-items-center card-header d-flex justify-content-between">
						<h6 class="text-white mb-0 w-75">Canteen Issuer's Page</h6>
					</div>
					<div class="card-body">
						<div class="row mb-3">
							<div class="col-lg-12">
								<label id="cafeCouponNo" class="alert alert-info"
									style="background: lightblue;"></label>
							</div>
						</div>
					</div>
				</div>
				<section>
					<%@ include file="/cafe-admin-view/liveInventory.jsp"%>
				</section>
				<div class="card " id="couponDetails">
					<div
						class="align-items-center card-header d-flex justify-content-between">
						<h6 class="text-white mt-1 w-75">Coupon Information - Issue
							Coupon</h6>
					</div>
					<div class="card-body">
						<form action="${saveCouponDetails}" autocomplete="off"
							method="post">
							<input type="hidden" name="redirectURL" value="<%=PortalUtil.getCurrentURL(request)%>">
							<input type="hidden" name="reqFor" id="reqFor" >
							<input type="hidden" name="itemId" id="itemId" >
							<input type="hidden" name="value_quant" id="value_quant" >
							<input type="hidden" name="createDate" id="createDate" >
							<div class="row mb-2">
								<div class="col-lg-6 ">
									<label for="">Employee Id:</label> <input type="text"
										class="form-control " id="eId" name="eId" readonly>
								</div>
								<div class="col-lg-6">
									<label for="">Employee Name </label> <input type="text"
										class="form-control " id="eName" name="eName" readonly>
								</div>
							</div>
							<div class="row mb-2">
								<div class="col-lg-6">
									<label for="">Item Name </label> <input type="text"
										class="form-control " id="item" name="item" readonly>
								</div>
								<div class="col-lg-6">
									<label for="">Item Quantity </label> <input type="number"
										class="form-control " id="ItemQty" name="ItemQty" readonly>
								</div>
							</div>
							<div class="row mb-3">
								<div class="col-lg-6">
									<label for="">Coupon No. </label> <input type="text"
										class="form-control " id="couponNo" name="couponNo" readonly>
								</div>
								<div class="col-lg-6 mt-4">
									<button type="submit" id="sub" class=" plng-btn px-4 py-2">
										<i title="Issue" class="bi bi-send-check text-6"></i>
									</button>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
			<div class="col-md-8">
				<div class="card">
					<div
						class="align-items-center card-header d-flex justify-content-between">
						<h6 class="text-white mt-1">Today's Coupons</h6>
						<a class="text-decoration-none back-btn" href="/dashboard"><i
							class="bi bi-arrow-left-circle-fill text-white pr-2"></i>Back</a>
					</div>
					<div class="card-body container-fluid">
						<div id="coupon-card-container" class="row mb-1"></div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>



<!-- Modal -->
<div class="modal fade" id="staticBackdrop" data-backdrop="static" data-keyboard="false" tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
      <div class="modal-header d-none">
        <h5 class="modal-title" ></h5>
        
      </div>
      <div class="modal-body">
        <div class="row ">
			<div class="col-md-12">
				 <div id="IssuedCouponsDiv" class="plng-accordion-cards ">
					<div class="card" id="staticBackdropLabel">
						<div class="align-items-center card-header d-flex justify-content-between">
							<h6 class="text-white mb-0 w-75">Coupon's Issued Today</h6>
							<button type="button" class="close text-white" data-dismiss="modal" aria-label="Close">
							  <span aria-hidden="true">&times;</span>
							</button>
						</div>
						<div class="card-body coupons-table">
							<div class="row md-4">
								<div class="col-md-12 table-responsive">
									<table id="coupons-issued-today" class="coupons-issued-today table" style="width:100%" aria-describedby="coupons-issued-today-info"></table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>	
		</div>
      </div>
      <div class="modal-footer d-none">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
        <button type="button" class="btn btn-primary">Understood</button>
      </div>
    </div>
  </div>
</div>

<script>
	var fetchCouponsDetailsURL = '${fetchCouponsDetailsURL}';
	var fetchALLIssuedCouponsURL= '${fetchALLIssuedCouponsURL}';
	$('#couponDetails').hide();
	$('#cafeCouponNo').text("Coupon No.-");
</script>

<style>
.hideLabel {
	display: none;
}

    .modal-dialog {
        margin: 1.75rem auto;
        max-width: 1100px;
    }
	
	.modal-content {
    background-clip: padding-box;
    background-color: transparent;
    border: none;
   border-radius: none;
    display: flex;
    flex-direction: column;
    /* outline: 0; 
     overflow: hidden; */
    pointer-events: auto;
    position: relative;
    width: 100%;
}

.coupons-table {
    max-height: 500px;
    overflow-y: auto;
    overflow-x: hidden;
}

.modal-open .modal {
    overflow-x: hidden;
    overflow-y: hidden;
}
</style>

