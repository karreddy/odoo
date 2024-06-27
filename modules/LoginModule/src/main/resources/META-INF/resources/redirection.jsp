<%@ include file="/init.jsp"%>


<c:choose>
    <c:when test="${isLoggedIn}">
    <script>
		location.href="${redirect}";
	</script>
</c:when>
 <c:otherwise>
        
	<%@ include file="/view.jsp"%>
</c:otherwise>
</c:choose>

