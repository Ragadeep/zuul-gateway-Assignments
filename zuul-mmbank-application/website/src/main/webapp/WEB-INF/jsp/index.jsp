<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<table>
		<ul>
			<li><a href="depositForm">Deposit</a></li>
			<li><a href="withdrawForm">withdraw</a></li>
			<li><a href="fundTransferForm">fund Transfer</a></li>
		</ul>
	</table>
	<a href="statement?offset=1&size=5">Get Statement</a>
	<table>
		<tr>
			<th>transaction Id</th>
			<th>account number</th>
			<th>amount </th>
			<th>current_balance </th>
			<th>transaction_date </th>
			<th>transaction_details </th>
			<th>transaction_type </th>
		</tr>
		<jstl:if test="${currentDataSet.transactions!=null}">
		<jstl:forEach var="transactions" items="${currentDataSet.transactions}"> 
			<tr>
				<td>${transactions.transactionId}</td>
				<td>${transactions.accountNumber}</td>
				<td>${transactions.amount}</td>
				<td>${transactions.transactionType}</td>
				<td>${transactions.transactionDate}</td>
				<td>${transactions.transactionDetails}</td>
				<td>${transactions.currentBalance}</td>
			</tr>
		</jstl:forEach>
		</jstl:if>
	</table>
	<div>
		<a href="${currentDataSet.previousLink.href}">Previous</a> |
		<a href="${currentDataSet.nextLink.href}">Next</a>
	</div>
</body>
</html>