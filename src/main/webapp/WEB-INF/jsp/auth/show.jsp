<%@include file="/WEB-INF/tags/taglibs.jsp"%>

<h1> <fmt:message key='twitter' /> </h1>
<h2> <fmt:message key='twitter.auth' /> </h2>

<hr />

<h3> <fmt:message key='twitter.messages' /> </h3>
<jopss:messages msgSuccess="${msgSuccess}" msgValidate="${msgValidate}" msgError="${msgError}" />

<hr />

<form:form id="twitter_form" method="POST" action="${contextPage}/twitter/auth/timeline/new/" modelAttribute="twitterForm">
    <form:hidden path="urlRequestToken"/>
    
    <h3> <fmt:message key='twitter.mode1.title' /> </h3>
    <fmt:message key='twitter.step1'>
        <fmt:param value="${twitterForm.urlRequestToken}"/>
    </fmt:message>
    <br/><br/>
    
    <fmt:message key='twitter.step2' />
    <br/>
    
    <fmt:message key='twitter.pin' />
    <form:input path="pin" />
    <br/><br/>
    
    <input type="submit" value="<fmt:message key='twitter.button.timeline' />" />

    <c:if test="${not empty twitterForm.credentials}" >
        <hr />
        
        <h3> <fmt:message key='twitter.mode2.title' /> </h3>
        <c:forEach items="${twitterForm.credentials}" var="credential">
            <a href="${contextPage}/twitter/auth/timeline/user/${credential.id}/">${credential.name}</a> <br/>
        </c:forEach>
    </c:if>
        
    <hr />
    
    <h3> <fmt:message key='twitter.timelineFriends' /> </h3>
    <c:forEach items="${twitterForm.statusList}" var="timelineStatus">
        <p>
            <b>${timelineStatus.user.name}:</b> ${timelineStatus.text}
        </p>
    </c:forEach>

</form:form>