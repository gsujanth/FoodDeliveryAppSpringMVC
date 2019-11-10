<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<%
	if (session.getAttribute("customerId") != null 
	&& session.getAttribute("userRole") != null
	&& session.getAttribute("userRole").equals("user")){
%>
<html>
<head>
<title>Restaurant Menu View Page</title>
<jsp:include page="headerUser.jsp" />
</head>
<body>
<h1>Menu</h1>
<c:url value="/addMenuItem" var="addItemUrl" />
<c:forEach items="${menuMap}" var="mapElement" varStatus="theCount">
	<p><button class="btn btn-success" type="button" data-toggle="collapse" data-target="#divID${theCount.index}" aria-expanded="false" aria-controls="divID${theCount.index}">
          ${mapElement.key}
    </button></p>
	<div class="collapse in" id="divID${theCount.index}">
	 <table class="table table-hover">
       <c:forEach items="${mapElement.value}" var="menuItem" >
         <tr>
           <td></td>
           <td width="30%">${menuItem.itemName}</td>
           <td>
           <fmt:formatNumber type="currency" currencySymbol="$" value="${menuItem.cost}" />
           </td>
           <td>${menuItem.calories} cals</td>
           <td>
	       <button class="btn btn-info" onclick="location.href='${addItemUrl}/${menuItem.itemId}'">Add to Cart</button>
	       </td>
         </tr>
       </c:forEach>
     </table>
    </div>
   </c:forEach>
<script type="text/javascript">
$('.btn-info').click(function (){
	  var itemCount = localStorage.getItem("itemCount");
	  itemCount ++;
	  localStorage.setItem("itemCount", itemCount);
	  $('#itemCount').html(itemCount).css('display', 'block');
});
</script>
</body>
</html>
<%
	}
	else{
		response.sendRedirect("/myfood/view/login.jsp");
	}
%>