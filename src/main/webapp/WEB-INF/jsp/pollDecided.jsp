<%--
  Created by IntelliJ IDEA.
  User: meded
  Date: 30/05/2024
  Time: 01:16
  To change this template use File | Settings | File Templates.
--%>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Confirmation de Réservation</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/style.css" />
    <style>

        .confirmation-container .btn-home {
            display: inline-block;
            padding: 10px 20px;
            color: white;
            background-color: #007bff;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            font-size: 16px;
        }
        .confirmation-container .btn-home:hover {
            background-color: #0056b3;
        }
        .confirmation-container button {
            display: inline-block;
            padding: 10px 20px;
            color: white;
            background-color: #007bff;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            font-size: 16px;
        }
    </style>
</head>
<body>
    <div class="confirmation-container">
        <h2>Réservation Confirmée</h2>
        <c:forEach var="slot" items="${poll.slots}">
           <c:choose>
               <c:when test="${slot.chosen}">
               <p>Vous avez réservé le créneau du sondage intitulé ${poll.title} de ${slot.start.hour}:${slot.start.minute < 10 ? '0' : ''}${slot.start.minute} à ${slot.end.hour}:${slot.end.minute < 10 ? '0' : ''}${slot.end.minute} le ${slot.dayOfWeek}  ${slot.start.dayOfMonth} ${slot.month} ${slot.start.year}
                   <form id="emailForm" action="${pageContext.request.contextPath}/send-mail" method="get">
                   <input type="hidden" name="senderEmail" value="${poll.creator.email}" />
                   <c:forEach var="email" items="${poll.participantMail()}">
                        <input type="hidden" name="recipientEmail" value="${email}" />
                    </c:forEach>
                    <input type="hidden" name="text" value="Le créneau de ${slot.start.hour}:${slot.start.minute < 10 ? '0' : ''}${slot.start.minute} à ${slot.end.hour}:${slot.end.minute < 10 ? '0' : ''}${slot.end.minute} le
               ${slot.dayOfWeek}  ${slot.start.dayOfMonth} ${slot.month} ${slot.start.year} a été choisi concernant votre participation au sondage ${poll.title}" />
                    <button type="submit">Informer les participants</button>
                    </form>
               </c:when>
            </c:choose>
        </c:forEach>


    </div>
</body>
</html>
