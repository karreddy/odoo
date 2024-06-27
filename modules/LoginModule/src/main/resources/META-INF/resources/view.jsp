<%@ include file="/init.jsp"%>
	
	<portlet:resourceURL id="/feature/AjaxOptSend"
	var="AjaxOptSendUrl" />
	<portlet:resourceURL id="/feature/forgotpwd"
	var="forgotpwdURL" />
	<portlet:resourceURL id="/feature/sentLink"
	var="sentLinkURL" />
	<portlet:actionURL var="forgotpwd_sendlink" name="action/forgotpwd_sendlink" />
	<portlet:actionURL var="LoginWithPasswordUrl" name="action/LoginWithPassword" />
<!--     <script src="
https://code.jquery.com/jquery-3.6.4.min.js"></script>
	 -->
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.3.0/font/bootstrap-icons.css">
	

	 
<style>
.login-form .form-floating {
	position: relative;
}

.user-icon {
	position: absolute;
	top: 5px;
	right: 10px;
}
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

/* Firefox */
input[type=number] {
  -moz-appearance: textfield;
   
}
.otp-fields .form-control:focus{

     box-shadow: none;
}

.otp-fields .form-control {
    /* appearance: none; */
     background: rgba(112, 99, 179, 0.17);
    border: 1px solid #e0e0e0 !important;
    height: 40px;
    width: 40px;
    display: inline-block;
    margin: 5px;
    padding: 15px;
}

.ascl-theme-btn{
	background-color: #28419e !important;
    border: none;
}
.time-div{
    text-align: center;
    border: 1px solid #28419e;
    border-radius: 5px;
    font-size: 15px;
    font-weight: 600;
    color: #28419e;
    width: 35px;
    height: 29px;
    line-height: 27px;

   }
   
   #sidebar, .login-link, .header {
   	display : none !important;
   }
   .ascl-login-btn {
       background: #0b0b81;
    	color: white;
    	font-weight: 600;
   }
   .ascl-login-btn:hover{
     background: #0b0b81;
    	color: white;
    	font-weight: 600;
   }
   
   .login-w-otp-text,.login-w-pwd-text{
	   font-weight: 700;
	   color: #0b0b81 !important;
   }
   
   .forgot_passwordbtn{
   color: #0b0b81 !important;
    margin-top: 10px;
    cursor: pointer;
    margin-left: 200px;
    }
       .failed_symbol,.tick_symbol{
    margin: auto;
    font-size: 1.6em;
    /* font-weight: bold; */
    color: red;
   }
   .tick_symbol{
   color: green;
    margin-top: -2px
   }
   .failed_msg{
  	 margin:auto;
  	 margin-top: -15px;
   }
    .forgotmodal,.newpwdmodal{
    margin-right: 500px;
    width: 450px;
    margin-top: 246px;
    }
    .modal1{
     margin-left: 350px;
    margin-top: 150px;
    }
    .failed{
   color: #0b0b81;
    font-weight: 700;
    font-size: 17px;
     margin-top: 5px;
   }
   .successMsg{
       width: 347px;
    margin: auto;
    top: -687px;
    height: 122px;
   }
   #spinner{
	/* position: absolute;
    top: -30px;
    left: 550px;
    display: none; 
    border-right-color: transparent !important; */
    position: absolute;
    top: 405px;
    left: 279px;
    z-index: 10;
    display: none;
    border-right-color: transparent !important;
	}
</style>

<div class="d-flex justify-content-center">
	<div id="spinner" class="spinner-border spinner-border-sm text-primary" role="status">
	<span class="sr-only">Loading...</span>
	</div>
</div>

<div class="container m-0 manage_opacity">
	<div class="row">
		<div class="col-md-6">
			<div class="login-card">
				<div class="card border-0 shadow-lg">
					<div class="card-header bg-transparent text-center">
<!-- 						<h4 class="custom-card-title mb-0">LOGIN</h4>
 -->						<div class="my-4">
							<img src="<%= request.getContextPath()%>/images/c-logo.png" width="120px">
						</div>
						<h3>Hello there <img src="https://cdn-icons-png.freepik.com/512/9267/9267403.png?ga=GA1.1.956128599.1705905902&amp;" width="25px"></h3>
					</div>
					
					<div class="card-body p-4" id="lwp-lwotp-section">
						<div class="row mb-4">
							<div class="col-md-12">
								<label for="email">Login with Password</label>
								<input type="text" class="form-control" autocomplete="off" id="lwp" placeholder="Email ID / Mobile No" readonly="">  
							</div>
						</div>
						
						<div class="row text-center">
							<div class="col-md-12">
								OR
							</div>
						</div>
						 
						<div class="row mt-4">
							<div class="col-md-12">
								<label for="email">Login with email OTP</label>
								<input class="form-control" autocomplete="off" id="lwotp" placeholder="Email ID." readonly="">  
							</div>
						</div>
					</div>
					
					
					<div class="card-body px-4 hide" id="lwp-section">
						<form action="${LoginWithPasswordUrl}" method="post" name="lwp-form" id="lwp-form" novalidate="novalidate">
							<div class="login-form">
								<label for="userId">Login with Password</label> 
								<input type="text" class="form-control mb-4 mailpattern" autocomplete="off" id="userId" placeholder="Email ID" name="userId" maxlength="80"> 				
								<div class="invalid-feedback"></div>
								<input type="password" autocomplete="off" class="form-control" id="password" placeholder="Password" name="password">  
								<div class="invalid-feedback"></div>
								
							<div class="forgot_passwordbtn">Forgot Password</div>
							</div>
							<div class="text-center my-4">
								<a href="javascript:void(0);" class="btn ascl-login-btn loginwithpwdbtn w-50" onclick="asclLoginWithPasswordSubmit()"><span class="btn-text">Login</span></a>
							</div>
							<div class="forgot-psd-div">
								<ul class="list-inline">
									<li class="list-inline-item">
										<a href="javascript:void(0)" class="text-decoration-none text-primary text login-w-otp-text" onclick="loginWithOTP()">Login with email OTP</a>
									</li>
								</ul>
							</div>
						</form>
					</div>
					
				
					
					<div class="card-body hide" id="lwotp-section">
						<form action="" method="post" name="lwotp-form" id="lwotp-form" novalidate="novalidate">
							<input type="hidden" name="password" id="otppass">
							<div class="login-form">
								<label for="email">Login with email OTP</label> 
								<input type="email" class="form-control mailpattern" autocomplete="off" id="email" placeholder="Email ID" name="mobile">
								<div class="invalid-feedback"></div> 
							</div>
							
							<div class="text-center my-4">
								<a href="javascript:void(0)" class="btn ascl-login-btn w-50 " id="sendotpbtn">
									<span class="btn-text sendotpbtn">Send OTP</span>
								</a>
							</div>
							
							<div class="forgot-psd-div">
								<ul class="list-inline">
									<li class="list-inline-item">
										<a href="javascript:void(0)" class="text text-decoration-none text-primary login-w-pwd-text" onclick="loginWithPassword()">Login with Password</a>
									</li>
								</ul>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		<div class="col-md-6 login-slider">
			<div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
			  <ol class="carousel-indicators">
			    <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
			    <li data-target="#carouselExampleIndicators" data-slide-to="1"></li>
			    <li data-target="#carouselExampleIndicators" data-slide-to="2"></li>
			  </ol>
			  <div class="carousel-inner">
			    <div class="carousel-item active">
			      <img src="<%=request.getContextPath()%>/images/image1.png" alt="...">
			    </div>
			    <div class="carousel-item">
			      <img src="<%=request.getContextPath()%>/images/image2.png" alt="...">
			    </div>
			    <div class="carousel-item">
			      <img src="<%=request.getContextPath()%>/images/image1.png" alt="...">
			    </div>
			  </div> 
			</div>
		</div>
	</div>
</div>



 
<div class="modal fade" id="OTPModal" tabindex="-1"
	aria-labelledby="OTPModalLabel" aria-hidden="true"
	style="display: none;">
	<div class="modal-dialog modal1">
		<div class="modal-content">
		<div id="otp_content" class="d-none">
			<div class="modal-header text-center">
				<h5 class="modal-title" id="OTPModalLabel">OTP</h5>
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close" onclick="closeOTPBox()">
					<i class="fas fa-times"></i>
				</button>
			</div>
			<div class="modal-body text-center otp-fields">
				<h6>Please enter OTP sent to your email for
					verification.</h6>
				<form id="otpform" autocomplete="off">
					<!-- <input type="text" class="form-control"  placeholder="Enter OTP" id="otp" /> -->
					<input type="number" class="form-control otp__digit" min="1"
						max="1" id="otp1" name="otp1">
						<input type="number"
						class="form-control otp__digit" min="1" max="1" id="otp2"
						name="otp2" readonly> <input type="number"
						class="form-control otp__digit" min="1" max="1" id="otp3"
						name="otp3" readonly> <input type="number"
						class="form-control otp__digit" min="1" max="1" id="otp4"
						name="otp4" readonly> <input type="number"
						class="form-control otp__digit" min="1" max="1" id="otp5"
						name="otp5" readonly> <input type="number"
						class="form-control otp__digit" min="1" max="1" id="otp6"
						name="otp6" readonly> 
						 <span id="errrotp"
						class="d-block text-2 text-danger"></span>
				</form>
			</div>
			<div class="modal-footer mx-auto">
				<button type="button" class="btn btn-primary btn-sm ascl-theme-btn"
					id="btnonOtp">Verify</button>
				<button type="button" class="btn btn-primary btn-sm ascl-theme-btn"
					id="btnResendOtp" 
					disabled="">Resend</button>
				<div class="time-div">
					<span id="timer"></span>
				</div>
			</div>
			</div>
			
			<!-- forgot password content  -->
				<div id="forgot_pwd_content">
					<div class="modal-header text-center">
						<h5 class="modal-title" id="forgot" style="color: #0b0b81 !important;">Forgot Password</h5>
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<i class="fas fa-times"></i>
						</button>
					</div>
					
					<div class="modal-body text-center">
					<%-- <form action="${forgotpwd_sendlink}" method="post" > --%>
					 <div id="forgot_pwd_email">

								<input type="email" class="form-control mailpattern" autocomplete="off" id="email2" placeholder="Email ID" name="mobile">
								<div class="invalid-feedback"></div> 
							
							
							<div class="text-center my-4">
								<a href="javascript:void(0)" class="btn ascl-login-btn w-50 " id="sendlinkbtn" >
									<span class="btn-text sendlinkbtn">Send Link</span>
								</a>
							</div>
			
					</div>
					<!-- </form> -->
		
				</div>		
		</div>
	</div>
</div>
</div>

			
			<div class="card text-center successMsg d-none">
                <div class="failed"></div>
                <div class="failed_symbol"><i class="bi bi-arrow-clockwise"></i></div>
                <div class="tick_symbol"> <i class="bi bi-check-circle"></i></div>
                <div class="failed_msg"></div>
            </div>

<!--   <div class="modal fade" id="newpasswordmodal" tabindex="-1"
	aria-labelledby="OTPModalLabel" aria-hidden="true"
	style="display: none;">
	<div class="modal-dialog newpwdmodal" >
		<div class="modal-content">
			
			<div class="modal-body text-center">
                <div class="mb-2" style=" color: #0b0b81 !important;">New Password</div>
               <input type="password" class="form-control" autocomplete="off" id="newpassword" placeholder="Enter New Password" name="newpassword">
              <div class="text-center my-4">
								<a href="javascript:void(0)" class="btn ascl-login-btn w-50 " id="newpasswordsubmit" >
									<span class="btn-text newpasswordsubmit">Submit</span>
								</a>
				</div>
            </div>
			
		</div>
	</div>
</div> -->

<script>
var lastOtp="";
var timer;
var seconds = 30;
/* $('#otp1').on('input', function(){
 	this.value = this.value.replace(/[^0-9.]/g, ''); });
	$(this).val().length;
}) */
$('#password').on('keypress', function (e) {
    
    if (e.which === 13) {
        e.preventDefault();
        asclLoginWithPasswordSubmit();
    }
});
$('#userId').on('keypress', function (e) {
    
    if (e.which === 13) {
        e.preventDefault();
        asclLoginWithPasswordSubmit();
    }
});

$('#sendotpbtn').on('click',function(){
	asclSendOTP($('#email').val(),$('#email'));
})
$('#sendlinkbtn').on('click',function(){
	forgotPassword($('#email2').val(),$('#email2'));
})
$('.forgot_passwordbtn').on('click', function(){
		$("#OTPModal").modal("show");
		$("#otp_content").addClass('d-none');
		$("#forgot_pwd_content").removeClass('d-none')
})
$('#btnonOtp').on('click',function(){
	var combinedOTP="";
	$('.otp__digit').each(function() {
        var value = $(this).val();
            combinedOTP += value;
	});
/* 	console.log("combinedOTP is "+combinedOTP+" & osent otp is "+lastOtp);
 */	if(combinedOTP==lastOtp){
		
		
 		$("#OTPModal").modal("hide");
 		 $('#timer').text(seconds);

		Liferay.Util.openToast({
		    title: "Otp Verification", 
		    message: "Otp Matched Successfully!",
		    type:'success'
		});
		let loginemail=$('#email').val();
		window.location.href = themeDisplay.getPortalURL()+"/c/portal/login?action=ehfbefiehfhefeifefewfefef&emailAddress="+loginemail;
		$('#btnonOtp').prop("disabled",true);
		$('.manage_opacity').css("opacity", "0.5");
    	$("#spinner").show(); 
/* 		window.location.href = themeDisplay.getPortalURL()+"/c/portal/login?emailAddress=darshan@yopmail.com"; */
		
	}else{
		Liferay.Util.openToast({
		    title: "Otp Verification", 
		    message: "Wrong Otp! Please Try Again",
		    type:'danger'
		});
		$('#timer').text(0);
		seconds=30;
	}
	$('.otp__digit').each(function(index) {
		$(this).val("");
		if(index == 0){
			 $(this).removeAttr("readonly");
		}
		else if (index > 0) {
	    	 $(this).attr("readonly", true);
	    }
	});
	combinedOTP="";
	lastOtp="";
	enableButtonAndClearTimer();
});

$('.mailpattern').on('blur', function () {
	let emailInput=	$(this).val();
	let elem=	$(this);
	var valid=validateEmail(emailInput,elem);
	    console.log("blur email validate- "+ valid);
	});
$('#password').on('blur', function () {
	let passwordInput=	$(this).val();
	let elem=	$(this);
	var valid=validatePassword(passwordInput,elem )
	console.log("blur password validate- "+ valid);
});
	function validateEmail(emailInput,elem) {
		var res=false;

			console.log("emailInput",emailInput);
			console.log("elem",elem);
		    var email = emailInput.trim();
		    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
		 
		    if (email === '') {
		    	console.log('Email is required');
			 	elem.next().show();
		    	elem.next().html('Email is required');
		    	
		    	/*  $('.ascl-login-btn').addClass('disabled'); */ 
		    	/* elem.closest('#lwotp-section').find('.ascl-login-btn').addClass('disabled'); */
		    
		    	res= false;
		    } else if (!emailRegex.test(email)) {
		    	console.log('Invalid email format');
		    	elem.next().show();
		    	elem.next().html('Invalid email format');
		    	/*  $('.ascl-login-btn').addClass('disabled'); */ 
		    	/* elem.closest('#lwotp-section').find('.ascl-login-btn').addClass('disabled'); */
		    
		    	res= false;
		    } 
		 else {
		    	console.log('correct formate');
		    	elem.next().hide();
		    	elem.next().html('');
		    	/*  $('.ascl-login-btn').removeClass('disabled');  */
		    	 
		    	/* elem.closest('#lwotp-section').find('.ascl-login-btn').removeClass('disabled'); */
		    	res=true;
		    }
		
		
		
	    return res;
	}
	function validatePassword(passwordInput,elem) {
		var res=false;

			console.log("emailInput",passwordInput);
			console.log("elem",elem);
		    var password = passwordInput.trim();
/* 		    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
 */		 
		    if (password === '') {
		    	console.log('Password is required');
			 	elem.next().show();
		    	elem.next().html('Password is required');
		    	
		    	/*  $('.ascl-login-btn').addClass('disabled'); */ 
		    	/* elem.closest('#lwotp-section').find('.ascl-login-btn').addClass('disabled'); */
		    
		    	res= false;
		    } /* else if (!emailRegex.test(email)) {
		    	console.log('Invalid email format');
		    	elem.next().show();
		    	elem.next().html('Invalid email format');
		    	res= false;
		    }  */
		 else {
		    	console.log('correct formate');
		    	elem.next().hide();
		    	elem.next().html('');
		    	/*  $('.ascl-login-btn').removeClass('disabled');  */
		    	 
		    	/* elem.closest('#lwotp-section').find('.ascl-login-btn').removeClass('disabled'); */
		    	res=true;
		    }
		
		
		
	    return res;
	}
	$('.mailpattern').on('input',function(){
		$(this).next().hide();
	});
	$('#email').on('input',function(){
		$(this).next().hide();
	});
	
	
$('#btnResendOtp').on('click',function(){
	asclSendOTP($('#email').val(),$('#email'));
});


	$("#lwp").focus(function() {
		loginWithPassword();
/* 		 $("#userId").focus();
 */	});

	$("#lwotp").focus(function() {
		loginWithOTP();
	});

	function loginWithPassword() {
		$('#lwp-lwotp-section').addClass('hide');
		$('#lwp-section').removeClass('hide');
		$('#lwotp-section').addClass('hide');
		 $("#userId").focus();
	}
	function loginWithOTP() {
		$('#lwp-lwotp-section').addClass('hide');
		$('#lwotp-section').removeClass('hide');
		$('#lwp-section').addClass('hide');
		$("#email").focus();
	}

	function asclLoginWithPasswordSubmit(){
		console.log("password::::::",$('#password').val())
	    var validEmail=false;
	    var validPass=false;
		
	    validEmail=validateEmail($('#userId').val(), $('#userId'));
		validPass=validatePassword($('#password').val(), $('#password'));
		
		console.log("asclLoginWithPasswordSubmit validEmail ---"+ validEmail)
		console.log("asclLoginWithPasswordSubmit validPass ---"+ validPass)
	   	if(validEmail && validPass){
			$('#lwp-form').submit();
			validEmail=false;
		   	validPass=false;
		   	
		   	$('.loginwithpwdbtn').addClass('disabled');
	   		  $('.manage_opacity').css("opacity", "0.5");
    		 $("#spinner").show(); 
	   	}
	   	validEmail=false;
	   	validPass=false;
	}
	
	function asclSendOTP(email, elem) {

	   var valid= validateEmail(email, elem);
		
 if(valid){
		console.log("email in AJAX:", email);
		if (email!==""){
			var dataToSend = {
					"email" : email
				};
				$.ajax({
					url : "${AjaxOptSendUrl}",
					data : dataToSend,
					type : 'POST',
					success : function(data, textStatus, jqXHR) {
		 
						console.log(data);
						 var jsonObj = JSON.parse(data);
		 
						if (jsonObj.emailValid == "false") {
						 	content="Email is not registered";
						 	
						 	$('#email').next().show();
						 	$('#email').next().html(content);
							/* $('#update').prop('disabled', true); */ 
						
						 
						 } else if (jsonObj.emailValid == "true") { 
							lastOtp=jsonObj.otp;
							if (jsonObj.emailSent == "true") {
								
						 		$("#OTPModal").modal("show");
						 		$("#otp_content").removeClass('d-none');
						 		$("#forgot_pwd_content").addClass('d-none');
								disableButtonAndStartTimer();
									
								
		 
							}
						}
		 
					},
					error : function(jqXHR, textStatus, errorThrown) {
						console.log("Error Recieved is ", textStatus);
						reject(textStatus);
					}
				});
		}
		else{
			content="Email is required";
		
				$("#failedmodal").modal("hide");
		 	$('#email').next().show();
		 	$('#email').next().html(content);
			
		}
		
 }
	}
	function forgotPassword(email, elem) {
		   var valid= validateEmail(email, elem);
			
	 if(valid){

			if (email!==""){
				var dataToSend = {
						"email" : email
					};
					$.ajax({
						url : "${forgotpwdURL}",
						data : dataToSend,
						type : 'POST',
						success : function(data, textStatus, jqXHR) {
			 
							console.log(data);
							 var jsonObj = JSON.parse(data);
			 
							if (jsonObj.emailValid == "false") {
							 	content="Email is not registered";
							 	
							 	/* 	$("#failedmodal").modal("show");
							 		$(".failed").html("Failed");
							 		$(".failed_symbol").removeClass("d-none");
							 		$(".tick_symbol").addClass("d-none");
									 $(".failed_msg").html(content);
									 $(".failed_msg").addClass("text-danger");
									 $(".failed_msg").removeClass("text-success"); */
							 		 $('#email2').next().show();
								 	$('#email2').next().html(content);
								 
							 	
							 } else if (jsonObj.emailValid == "true") { 
								
								 setTimeout(() => {
									  $("#OTPModal").modal("hide");
									  setTimeout(() => {
										  $(".successMsg").removeClass("d-none");
											$(".failed").html("Success");
											$(".failed_symbol").addClass("d-none");
											$(".tick_symbol").removeClass("d-none");
											$(".failed_msg").html("Sent link to email id");
											$(".failed_msg").removeClass("text-danger");
											$(".failed_msg").addClass("text-success");
									  }, 800);
									}, 0);
									 		
	
									 	setTimeout(()=>{
									 		$(".successMsg").addClass("d-none");
								    	},3000);
									 	
										 
										/*  $('#email2').next().show();
										 $('#email2').next().html("Email sent successfully"); 
										 
										 $('#email2').next().addClass("text-success"); 
										 $('#email2').next().removeClass("text-danger");  */
										/* $("#newpasswordmodal").modal("show"); */
								
			 							
								}
							
			 
						},
						error : function(jqXHR, textStatus, errorThrown) {
							console.log("Error Recieved is ", textStatus);
							reject(textStatus);
						}
					});
			}
			else{
				content="Email is required";
	/* 		
					$("#failedmodal").modal("show");
					 $(".failed_msg").html(content); */
				 	$('#email2').next().show();
				 	$('#email2').next().html(content);
				
				
			}
			
	 }
		}	
	
	 function disableButtonAndStartTimer() {
		 	seconds=30;
	        $('#btnResendOtp').prop('disabled', true); // Disable the button
	        timer = setTimeout(countdown, 1000); // Start the timer
	        updateTimerDisplay(); // Update initial display
	    }

	    function countdown() {
	        seconds--;
	        updateTimerDisplay();

	        if (seconds > 0) {
	            timer = setTimeout(countdown, 1000); // Keep counting down
	        } else {
	            enableButtonAndClearTimer();
	        }
	    }

	    function updateTimerDisplay() {
	        $('#timer').text(seconds); // Update the timer display
	    }

	    function enableButtonAndClearTimer() {
	        $('#btnResendOtp').prop('disabled', false); // Enable the button
	        clearTimeout(timer); // Clear the timer
	        seconds = 30; // Reset seconds for next countdown
	    }
	    
	    $('.otp__digit').on('input', function (e) {
	        // Get the current input field
	        var $currentInput = $(this);
 
	        // Get the value entered by the user
	        var enteredValue = $currentInput.val();
 
	        // Check if the entered value is a single digit
	        if (/^\d$/.test(enteredValue)) {
	            // Move to the next input field
	          /*  $currentInput.attr("readonly","true"); */
	          if($currentInput.next('.otp__digit').length){
	           $(this).attr("readonly","true"); 
	             $currentInput.next('.otp__digit').removeAttr("readonly");
	            $currentInput.next('.otp__digit').focus();	        	  
	          }
	        }
	    })
	   $('.otp__digit').on('keydown', function (e) {
			  if (e.keyCode === 8) { // Backspace key
				  if (!(/^\d$/.test($(this).val()))) {
					  if ($(this).prev('.otp__digit').length) { // No content, check previous field
					    	$(this).attr("readonly","true");
					    	$(this).prev('.otp__digit').removeAttr("readonly");
					    	$(this).prev('.otp__digit').focus(); // Focus on previous field
					      $(this).prev('.otp__digit').val($(this).prev('.otp__digit').val().slice(0, -1)); // Remove last character from previous
					   }
			 	  }
			  }
			});
</script>