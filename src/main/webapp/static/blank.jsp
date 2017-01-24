<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<script type="text/javascript">

    function myfunction() {
        if (self != top) {
            console.log("在iframe中,让父页面直接跳转");
            top.window.location = "https://${fns:getConfig("cas.server.host.config")}/cas?service=http://${fns:getConfig("cas.service.host.config")}${fns:getAdminPath()}/cas";
        } else {
            console.log("不在ifame中,直接跳转到CAS登陆页面");
            window.location = "https://${fns:getConfig("cas.server.host.config")}/cas?service=http://${fns:getConfig("cas.service.host.config")}${fns:getAdminPath()}/cas";
        }
    }
</script>

<body onload="myfunction()">
</body>