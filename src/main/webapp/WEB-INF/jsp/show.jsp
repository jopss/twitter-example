<%@include file="/WEB-INF/tags/taglibs.jsp"%>

<script type="text/javascript">
    $(document).ready(function() {
    });
</script>

<h1> <fmt:message key='twitter' /> </h1>
<hr />

<jopss:messages msgSuccess="${msgSuccess}" msgValidate="${msgValidate}" msgError="${msgError}" />

<form:form id="twitter_form" method="POST" action="${contextPage}/twitter/timeline/new/" modelAttribute="twitterForm">
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

    <c:if test="${not empty twitterForm.credentials}" >
        <hr />
        <br/>

        <fmt:message key='twitter.step3' />
        <br/><br/>

        <c:forEach items="${twitterForm.credentials}" var="credential">
            <a href="${contextPage}/twitter/timeline/user/${credential.id}/">${credential.name}</a> <br/>
        </c:forEach>
    </c:if>
        
    <hr />
    <br/><br/>
    <c:forEach items="${twitterForm.statusList}" var="timelineStatus">
        <p>
            <b>${timelineStatus.user.name}:</b> ${timelineStatus.text}
        </p>
    </c:forEach>

</form:form>