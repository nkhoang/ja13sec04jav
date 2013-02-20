<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><fmt:message key="login.title"/></title>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
    <!-- Bootstrap -->
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/css/bootstrap.min.css' />"/>
    <script type="text/javascript" src="<c:url value="/js/bootstrap.js"/>"></script>
    <!-- Custom CSS -->
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/login-form.css' />"/>
</head>
<body>
<div class="container">
    <div id="login-header" class="row"></div>
    <div class="row">
        <div id="login-form" class="span6 offset3">
            <form class="form-horizontal">
                <div class="control-group">
                    <label class="control-label" for="inputUsername"><fmt:message key="login.username"/></label>

                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-user"></i></span>
                            <input type="text" id="inputUsername"
                                   placeholder="<fmt:message key="login.username.placeholder" />">
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <label class="control-label" for="inputPassword"><fmt:message key="login.password"/></label>

                    <div class="controls">
                        <div class="input-prepend">
                            <span class="add-on"><i class="icon-lock"></i></span>
                            <input type="password" id="inputPassword"
                                   placeholder="<fmt:message key="login.password.placeholder" />">
                        </div>
                    </div>
                </div>
                <div class="control-group">
                    <div class="controls">
                        <label class="checkbox">
                            <input type="checkbox"><fmt:message key="login.rememberMe"/>
                        </label>
                        <a class="btn btn-primary" href="#"><i class="icon-user icon-white"></i> <fmt:message
                                key="login.button.title"/></a>
                        <a href="<c:url value="/user/register.html"/>"><fmt:message key="login.register"/></a>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
<jsp:include page="footer.jsp"/>
</body>
</html>