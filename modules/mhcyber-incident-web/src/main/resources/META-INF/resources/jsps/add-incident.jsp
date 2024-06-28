<%@page import="mhcyber.incident.web.constants.MhcyberIncidentWebPortletKeys"%>
<%@ include file="/init.jsp" %>

<portlet:resourceURL id="<%=MhcyberIncidentWebPortletKeys.SAVE_INCIDENT_RESOURCE_COMMAND%>"
var="saveIncidentURL" />
<div class="card border-0 shadow ">
		<div class="card-head d-flex justify-content-between align-items-baseline">
			<h6 class="mb-0 text-white text-center">
				title
			</h6>		
		</div>
		<div class="card-body bg-white">
			<form id="add_Incident"> 
				<div class="row">
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
						<div class="form-group"> 
							<label for="incidentType" >Incident Type<span class="text-danger">*</span></label>
							<select class="form-control" id="grievanceType" name="incidentType">
							   <option value="FinancialFraud">Financial Fraud</option>
							   <option value="FinancialFraud">Financial Fraud</option>
							   <option value="DataThief">Data Thief</option>
							   <option value="Hacking">Hacking</option>
							</select>
						</div>
					</div>
					
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
						<div class="form-group"> 
							<label for="profileDescription">Incident Date<span class="text-danger">*</span></label> 
						 	 <input  type="date" name="incidentDate" id="incidentDate" class="form-control" placeholder="Date of Incident" onclick="this.showPicker()"> 
		               
						</div>
					</div>
					
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
						<div class="form-group"> 
							<label for="remarks" >Description<span class="text-danger">*</span></label>
           				    <textarea class="form-control" id="description" name="description" style="height: 59px;"></textarea>
						</div>
					</div>
				</div>	
				
				<div class="row">
				
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
							<div class="form-group"> 
								<label for="remarks" >Affected System<span class="text-danger">*</span></label>
	           				   <input type="text" class="form-control" id="affectedSystem" name="affectedSystem"></input>
							</div>
					</div>
						
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
							<div class="form-group"> 
								<label for="severityLevel" >Severity Level<span class="text-danger">*</span></label>
							<select class="form-control" id="severityLevel" name="severityLevel">
							   <option value="">select</option>
							   <option value="1">Low</option>
							   <option value="2">Medium</option>
							   <option value="3">High</option>
							</select>
						</div>
					</div>
					
					
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
						<div class="form-group"> 
							<label class="form-label" for="evidence">Evidence</label>
                            <input type="file" class="form-control"  id="evidence"  name="evidence" />
						</div>
					</div>
					
				</div>	
				
				<div class="row">
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
						<div class="form-group"> 
							<label for="contactPerson" >Contact Person<span class="text-danger">*</span></label>
           				    <input type="text" class="form-control" id="contactPerson" name="contactPerson" ></input>
						</div>
					</div>
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
						<div class="form-group"> 
							<label for="contactEmail" >Contact Email<span class="text-danger">*</span></label>
           				    <input type="text" class="form-control" id="contactEmail" name="contactEmail" ></input>
						</div>
					</div>
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
						<div class="form-group"> 
							<label for="contactNumber" >Contact Number<span class="text-danger">*</span></label>
           				    <input type="text" class="form-control" id="contactNumber" name="contactNumber" ></input>
						</div>
					</div>
				</div>
				
				<div class="row">
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
						<div class="form-group"> 
							<label for="actionTaken" >Action Taken<span class="text-danger">*</span></label>
           				     <textarea class="form-control" id="actionTaken" name="actionTaken" style="height: 59px;"></textarea>
						</div>
					</div>
					<div class="col-md-4 col-lg-4 col-sm-3 col-xs-3">
						<div class="form-group"> 
							<label for="reqForAssist" >Request for Assistance<span class="text-danger">*</span></label>
           				    <input type="checkbox" class="form-control" id="reqForAssist" name="reqForAssist" ></textarea>
						</div>
					</div>
				</div>	
				
				<div class="row">
                   	<div class="col-md-12 d-flex justify-content-end align-items-center">
                      	<button class="button plng-btn submit-btn text-decoration-none icon-btn" onclick="addIncident()" title="Send" type="button" >Submit</button>
                   	</div>
               	</div>
			</form>
		</div>
	</div>
	
<!-- modal popup for add grievance -->
<div class="modal fade omsb-modal" id="saveIncident" tabindex="-1" role="dialog"
			aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
			<div class="modal-dialog modal-dialog-centered w-50" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h5 class="modal-title" id="exampleModalLongTitle">Add Incident</h5>
						<button type="button" class="close d-none" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-md-12 text-center">
								<div class="omsb-card omsb-card-graybg py-3">
									<div>
										 <p id="success-incident"></p>
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
    $("#saveIncident").modal('hide');
}
function addGrievance(){debugger
	var form = $("#add_Incident")[0];
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
	        url: '${saveIncidentURL}' ,
	        data:  formData, 
	        enctype: 'multipart/form-data',
	        contentType : false,
			cache : false,
			processData : false,
	        success: function(data){ 
	        	if(data.incidentSuccess == true){
               	 var msg = 'Your Incident ID Number ' + data.incidentNumber + '</b>'+"Registered Successfully.";
	        		$('#success-incident').html()
	        		$("#saveIncident").modal('show');
	        	}
	    	 }
	       
	    });
 };
</script>