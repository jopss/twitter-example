<%@tag description="Componente para exibir mensagens em formularios." pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<fmt:setBundle basename="messages"/> 

<%@ attribute name="msgSuccess" required="true" rtexprvalue="true" description="Mensagem a ser exibido como sucesso. Deve ser uma key bundle."%>
<%@ attribute name="msgError" required="true" rtexprvalue="true" description="Mensagem a ser exibido como erro. Deve ser um texto normal."%>

<c:if test="${msgSuccess != null and fn:length(msgSuccess) gt 0}" >
    <div>
        <p style="color:blue;">
            <fmt:message key='${msgSuccess}' />
        </p>
    </div>
</c:if>

<c:if test="${msgError != null and fn:length(msgError) gt 0}" >
    <div>
        <p style="color:red;">
            ${msgError}
        </p>
    </div>
</c:if>