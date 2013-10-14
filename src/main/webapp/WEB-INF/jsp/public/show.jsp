<%@include file="/WEB-INF/tags/taglibs.jsp"%>

<h1> <fmt:message key='twitter' /> </h1>
<h2> <fmt:message key='twitter.public' /> </h2>

<hr />

<h3> <fmt:message key='twitter.messages' /> </h3>
<jopss:messages msgSuccess="${msgSuccess}" msgValidate="${msgValidate}" msgError="${msgError}" />

<hr />

<form:form id="twitter_form" method="POST" action="${contextPage}/twitter/public/user/timeline/" modelAttribute="twitterForm">
    
    <fmt:message key='twitter.username' />
    <form:input path="username" />
    <br/><br/>
    
    <input type="submit" value="<fmt:message key='twitter.button.timeline' />" />

    <hr />
    
    <h3> <fmt:message key='twitter.timelineUser' /> </h3>
    <c:forEach items="${twitterForm.statusList}" var="timelineStatus">
        <p>
            <b>${timelineStatus.user.name}:</b> ${timelineStatus.text}
        </p>
    </c:forEach>

</form:form>