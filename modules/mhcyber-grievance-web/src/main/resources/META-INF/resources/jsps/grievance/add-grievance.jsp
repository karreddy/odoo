<%@page import="com.mhcyber.grievance.constants.MhcyberGrievanceWebPortletKeys"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="<%=MhcyberGrievanceWebPortletKeys.SAVE_GRIEVANCE_RESOURCE_COMMAND%>"
var="saveGrievanceURL" />
<div class="card border-0 shadow ">
		<div class="card-head d-flex justify-content-between align-items-baseline">
			<h6 class="mb-0 text-white text-center">
				title
			</h6>		
		</div>
		<div class="card-body bg-white">
			<form id="add_griev"> 
				<div class="row">
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-3">
						<div class="form-group"> 
							<label for="grievanceType" >Grievance<span class="text-danger">*</span></label>
							<select class="form-control" id="grievanceType" name="grievanceType">
							   <option value="FinancialFraud">Financial Fraud</option>
							   <option value="FinancialFraud">Financial Fraud</option>
							   <option value="DataThief">Data Thief</option>
							   <option value="Hacking">Hacking</option>
							</select>
						</div>
					</div>
					<div class="col-md-6 col-lg-6 col-sm-6 col-xs-3">
						<div class="form-group"> 
							<label for="remarks" >Description<span class="text-danger">*</span></label>
           				    <textarea class="form-control" id="description" name="description" style="height: 59px;"></textarea>
						</div>
					</div>
				</div>	
				<div class="row">
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
						<div class="form-group"> 
							<label class="form-label" for="photo">Upload a Photo</label>
                            <input type="file" class="form-control"  id="photoFile"  name="photoFile"/>
						</div>
					</div>
					
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
						<div class="form-group"> 
							<label class="form-label" for="photo">Upload a Audio File</label>
                            <input type="file" class="form-control"  id="audioFile"  name="audioFile" />
						</div>
					</div>
					
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
						<div class="form-group"> 
							<label class="form-label" for="photo">Upload a Video</label>
                            <input type="file" class="form-control"  id="videoFile"  name="videoFile" />
						</div>
					</div>
				</div>	
				<div class="row">
                   	<div class="col-md-12 d-flex justify-content-end align-items-center">
                      	<button class="button plng-btn submit-btn text-decoration-none icon-btn" onclick="addGrievance()" title="Send" type="button" >Submit</button>
                   	</div>
               	</div>
			</form>
		</div>
	</div>
	
<!-- modal popup for add grievance -->
<div class="modal fade omsb-modal" id="saveGrievance" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered w-50" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLongTitle">Add Grievance</h5>
						<button type="button" class="close d-none" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12 text-center">
								<div class="omsb-card omsb-card-graybg py-3">
									<div>
										 <p id="success-grv"></p>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="modal-footer d-flex justify-content-end">
       					 <a href="" type="button" id="closeModal" class="plng-btn" data-bs-dismiss="modal" onclick="closeModal()">Close</a>
					</div>
				</div>
			</div>
		</div>
<!-- modal popup for add grievance -->
<script>
function closeModal() {
    $("#saveGrievance").modal('hide');
}
function addGrievance(){debugger
	var form = $("#add_griev")[0];
	var formData = new FormData(form);
	var photoFile = document.getElementById("photoFile").files[0];
	var audioFile = document.getElementById("audioFile").files[0];
	var videoFile = document.getElementById("videoFile").files[0];
	console.log("Selected file: " + photoFile.name+"audio file "+audioFile +"videoFile "+videoFile);
	formData.append('photoFile', photoFile);	
	formData.append('audioFile', audioFile);	
	formData.append('videoFile', videoFile);	
	
	 $.ajax({
	        type: "POST",
	        url: '${saveGrievanceURL}' ,
	        data:  formData, 
	        enctype: 'multipart/form-data',
	        contentType : false,
			cache : false,
			processData : false,
	        success: function(data){ 
	        	if(data.grievanceSuccess == true){
               	 var msg = 'Your Grievance ID Number ' + data.grievanceUniqueId + '</b>'+"Registered Successfully.";
	        		$('#success-grv').html()
	        		$("#saveGrievance").modal('show');
	        	}
	    	 }
	       
	    });
 };
</script>