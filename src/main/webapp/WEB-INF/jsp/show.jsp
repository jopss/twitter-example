<%@include file="/WEB-INF/tags/taglibs.jsp"%>

<script type="text/javascript">
    $(document).ready(function() {
    });
</script>

<h1> <fmt:message key='twitter' /> </h1>
<hr />

<jopss:messages msgSuccess="${msgSuccess}" msgError="${msgError}" />

<form:form id="twitter_form" method="POST" action="${contextPage}/twitter/timeline/" modelAttribute="twitterForm">
    <form:hidden path="urlRequestToken"/>
    
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

</form:form>

<c:forEach items="${timeline}" var="timelineStatus">
    <p>
        <b>${timelineStatus.user.name}:</b> ${timelineStatus.text}
    </p>
</c:forEach>
