<%@page import="com.liferay.portal.kernel.json.JSONArray"%>
<%@page import="com.liferay.portal.kernel.service.RoleLocalServiceUtil"%>
<%@page import="com.liferay.portal.kernel.model.Role"%>
<%@page import="com.liferay.portal.kernel.json.JSONObject"%>
<%@page import="com.liferay.portal.kernel.servlet.SessionErrors"%>
<%@page import="com.plng.common.schema.model.CafeInventory"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@ include file="/init.jsp" %>
<%@page import="com.liferay.portal.kernel.theme.ThemeDisplay"%>
<%@page import="com.cafeteria.constants.CafeteriaPortletKeys"%>
<%@page import="javax.portlet.PortletRequest"%>
<%@page import="com.liferay.portal.kernel.util.PortalUtil"%>
<%@page import="com.liferay.portal.kernel.portlet.LiferayWindowState"%>
<%@ page import="com.liferay.portal.kernel.util.WebKeys" %>


<portlet:actionURL name="<%= CafeteriaPortletKeys.CAFETERIA_REPORT_DETAILS%>" var="saveCafeteriaDetails"></portlet:actionURL>

<liferay-portlet:renderURL var="cafeteriaReportURL" plid="${plId}"  portletName="com_cafeteria_CafeteriaReportPortlet"  >
</liferay-portlet:renderURL >


<%
if (SessionErrors.contains(request, "cafe-details-empty")) {
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

<%
Calendar calendar = Calendar.getInstance();
SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
String currentMonth = dateFormat.format(calendar.getTime());
%>

<div class="plng-accordion-cards step-card-width-10 ">
<div class="row">
<div class="col-md-6 ">
	<div class="card">
		<form action="${saveCafeteriaDetails}" autocomplete="off" method="post" id="cafeteriaDetails">
		<input type="hidden" name="empId" id="empId" value="${themeDisplay.getUserId()}">
		<input type="hidden" name="redirectURL" value="<%= PortalUtil.getCurrentURL(request)%>">
		<input hidden type="Date" id="date" name="date"  >	
		
		<div class="align-items-center card-header d-flex justify-content-between">
			<h6 class="text-white mb-0 w-75">PLL Canteen Management System :Coupon Generation</h6>
			  <c:if test="${!isContEmp}">
        		<a class="text-decoration-none back-btn" href="/dashboard"><i class="bi bi-arrow-left-circle-fill text-white pr-2"></i>Back</a>
    			</c:if>
		</div>	
			<div class="card-body coupon-details-form">
				<div class="row mb-3">
					<div class="col-lg-12">
						<label for="">Employee Name</label>
						<input type="text" class="form-control disableReadOnly" id="eName" name="eName" value="${themeDisplay.getUser().getFullName()}">
					</div>
				</div>
				
				<div class="row mb-3">
					<div class="col-lg-12">									
					<label for="">Request For</label> 
						<select class="form-control" id="reqFor" name="reqFor" >
							<option value="S">Self</option>
							<c:if test="${isGrade5}">
							 <option value="G">Guest</option>
							</c:if>
						</select>
					</div>
				</div>
				
				<div class="row mb-3">
				    <div class="col-lg-12">
				        <label for="">Item</label>
				        <select id="itemid" name="itemid" class="form-control">
				            <option value="">Select ...</option>
				            <% 
					            JSONArray itemIdAndNameArray = (JSONArray) request.getAttribute("itemIdAndNameArray");
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
			
				<div class="row mb-3" >
					<div class="col-lg-12">
						<label for="">Available Quantity </label>
						 <input type="number" class="form-control disableReadOnly" id="availQty" name="availQty"  readonly>
					</div>
				</div>
				
			
				<div class="row mb-3">
				    <div class="col-lg-12">
				        <label for="">Requested Quantity</label>
				        <div class="">
				           
				            <div class="d-grid mb-2">
				            	<label class="form-check-label" id="labelRq1">
				                <input class="form-check-radio" type="radio" name="rq" id="rq1" value="1"> One (01) 
				            </label>
				            <label class="form-check-label" id="labelRq2">
				                <input class="form-check-radio" type="radio" name="rq" id="rq2" value="2"> Two (02) 
				            </label>
				             <input class="form-control" type="number" name="rq" id="rq0" style="display: none;"  min=1>
				            </div>
				        </div>
				    </div>
				</div>
				
				<div class="row mb-3">
					<div class="col-lg-12 ">
						<label for="">Value for Quantity</label> <input class="form-control" id="val" name="val" type="hidden" value=""> 
						<input class="form-control disableReadOnly" id="ItemVal" name="ItemVal" type="number"  readonly >
					</div>
				</div>
				
				<div class="row mb-2">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
						<button type="submit" id="sub" class=" plng-btn" >Generate</button>
						<a href="/monthly-report" type="submit" class="plng-btn">Get Details</a>
					</div>
				</div>
			   
				<div class="row mb-3">
					<div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
					<input class="form-control" id="totalAmount" name="totalAmount" type="hidden" value="<%= request.getAttribute("totalAmount")%>">
					 <c:choose>
							<c:when test="${!isGrade5}">
								<label>Total Consumed Value For Self In <%= request.getAttribute("currentMonth") %>: &#x20B9; <%= request.getAttribute("selfTotalAmount") %> (INR)</label>
							</c:when>
							<c:otherwise>
								 <label>Total Consumed Value For Self In <%= request.getAttribute("currentMonth")%> :&#x20B9; <%= request.getAttribute("selfTotalAmount")%> (INR)</label></br>
								 <label>Total Consumed Value For Guest In <%= request.getAttribute("currentMonth") %> : &#x20B9; <%= request.getAttribute("guestTotalAmount") %> (INR)</label>
							</c:otherwise>
					</c:choose>
					<span class="d-block py-1" id="amountExceedMessage" style="color: red; font-size: 20px; font-weight: 600;"></span>
					</div>
				</div>
				
				
			</div>
		</form>
	</div>
</div>
<div class="col-md-6">
	<%@ include file="/coupons/user-coupons-details.jsp"%>
	<%@ include file="/coupons/view.jsp"%>
</div>
	
</div>	
</div>

<script>
    Liferay.on('sessionExpired', function() {
        location.reload();
    });

    var oneB =false;
    var isContEmp = ${isContEmp};
    var isGrade5 = ${isGrade5};
    var inObject = <%= request.getAttribute("itemIdAndNameArray")%>;
    var totalAmount = <%= request.getAttribute("totalAmount") %>;
    
    
    var reqForSelect = document.getElementById("reqFor");
    reqForSelect.addEventListener("change", function() {
        var selectedValue = reqForSelect.value;
        var guestQuantityInput = document.getElementById("rq0");
        var radioButtons = document.querySelectorAll(".form-check-label");
        if (selectedValue === "G") {
            guestQuantityInput.style.display = "block";
            radioButtons.forEach(function(button) {
                button.style.display = "none"; 
            });
        } else {
            guestQuantityInput.style.display = "none";
            radioButtons.forEach(function(button) {
                button.style.display = "block"; 
            });
        }
    });


var currentDate = new Date();
var formattedDate = currentDate.toISOString().split('T')[0];
document.getElementById("date").value = formattedDate;

$(document).ready(function() {
    $("#itemid").change(function() {
        resetForm('itemid');
        getSelectedItemQty();
    });

    $.validator.addMethod("validateQuantity", function(value, element) {
        var curStockQty = parseFloat($('#availQty').val());
        var selQty = parseFloat(value);
        return selQty <= curStockQty;
    }, "Selected Quantity Is Greater Than Available Quantity");

    $.validator.addMethod("checkMaxQuantity", function(value, element) {
        var reqFor = $("#reqFor").val(); 
        var selectedItem = $("#itemid").val();
        
        var selectedStock = inObject.find(function(stock) {
            return stock.itemId == selectedItem;
        });
        
        if (selectedStock) {
            var quantity = parseFloat(selectedStock.totalReqQty);
            
            if (reqFor === "G" && parseFloat(selectedStock.unit_price) > 100) {
                return false;  
            }
        }

        if (reqFor !== "S") {
            return true;
        }

        var selectedQuantity = parseFloat($("input[name='rq']:checked").val()) || parseFloat($("#rq0").val());
        var limit = selectedStock.oneB ? 1 : 2;
        return (selectedQuantity + quantity) <= limit;
    }, function() {
        var reqFor = $("#reqFor").val();
        var selectedItem = $("#itemid").val();
        var selectedStock = inObject.find(function(stock) {
            return stock.itemId == selectedItem;
        });

        if (reqFor === "G" && selectedStock && parseFloat(selectedStock.unit_price) > 100) {
            return "This item is for self.";
        }

        return selectedStock && selectedStock.oneB ? "You can order only 1 quantity for today" : "You can order up to 2 quantities for today";
    });

    $.validator.addMethod("checkTotalAmount", function(value, element) {
        var reqFor = $("#reqFor").val(); 
        var selectedItem = $("#itemid").val();
        var selectedStock = inObject.find(function(stock) {
            return stock.itemId == selectedItem;
        });

        if (selectedStock && reqFor === "S") {
            var selectedQuantity = parseFloat($("input[name='rq']:checked").val()) || parseFloat($("#rq0").val());
            var itemUnitPrice = parseFloat(selectedStock.unit_price);
            var totalValue = selectedQuantity * itemUnitPrice;
            var currentTotalAmount = parseFloat("<%= request.getAttribute("selfTotalAmount") %>");

            return (currentTotalAmount + totalValue) <= 3000;
        }
        return true;
    }, "Monthly Limit Exceeded.");
    
    

    $('#cafeteriaDetails').validate({
        rules: {
            itemid: {
                required: true,
            },
            rq: {
                required: true,
                validateQuantity: true,
                checkMaxQuantity: true
            },
            ItemVal:{
            	checkTotalAmount:true
            }
        },
        messages: {
            itemid: 'Please Select An Item',
            rq: {
                required: 'Quantity Is Required',
                validateQuantity: 'Selected Quantity Is Greater Than Available Quantity',
                checkMaxQuantity: function() {
                	var reqFor = $("#reqFor").val();
                    var selectedItem = $("#itemid").val();
                    var selectedStock = inObject.find(function(stock) {
                        return stock.itemId == selectedItem;
                    });
                    if (reqFor === "G" && selectedStock && parseFloat(selectedStock.unit_price) > 100) {
                        return "This item is for self.";
                    }
                    return selectedStock && selectedStock.oneB ? "You Can Order Only 1 Quantity For Today" : "You Can Order Upto 2 Quantities For Today";
                }
            },
            ItemVal:{
            	checkTotalAmount:"Monthly Limit Exceeded."
            }
        },
        submitHandler: function(form) {
        	 $('#sub').prop('disabled', true);
            form.submit();
        }
    });

    $("#rq0").on("keypress keydown keyup focus", function() {
        updateItemVal();
    });

    $("input[name='rq']").on("change", function() {
        updateItemVal();
    });

    $("#reqFor").on("change", function() {
        resetForm('reqFor');
    });

    function updateItemVal() {
        var curStockQty = parseFloat($('#availQty').val());
        var selectedQuantity = parseFloat($("input[name='rq']:checked").val()) || parseFloat($("#rq0").val()) || 0;
        var selectedItem = $("#itemid").val();
        var reqFor = $("#reqFor").val(); 

        var selectedStock = inObject.find(function(stock) {
            return stock.itemId == selectedItem;
        });

        if (selectedStock) {
            var itemUnitPrice = parseFloat(selectedStock.unit_price);

            var totalValue = selectedQuantity * itemUnitPrice;
            $("#ItemVal").val(totalValue.toFixed(2)); 
        }
    }

    function resetForm(triggeredBy) {
        $("#rq1").prop("checked", false);
        $("#rq2").prop("checked", false);
        $("#rq2").prop("disabled", false);
        $("#availQty").val("");
        $("#ItemVal").val("");
        $("#ItemVal-error").text("");
        $("#rq0").val("");
        $("#amountExceedMessage").text("");
        $("#sub").prop("disabled", false);

        $(".error").removeClass("error");
        $("#rq-error").text("");
        $("#rq0-error").text("");

        if (triggeredBy === 'reqFor') {
            $("#itemid").val(""); 
        }
    }


    function getSelectedItemQty() {
        var selectedItem = $("#itemid").val();
        var selectedStock = inObject.find(function(stock) {
            return stock.itemId == selectedItem;
        });
        if (selectedStock) {
            var itemQty = selectedStock.qty;
            var oneB = selectedStock.oneB;

            $("#availQty").val(itemQty);

            if (oneB == 1) {
                $("#rq2").prop("disabled", true);
            }
        }
    }
});



</script> 