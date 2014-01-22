 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
 <%@ taglib prefix="s" uri="/s" %>  
 <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
 <html>  
 <head>  
 <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">  
 <title>Insert title here</title>  
 </head>  
 <body>  
     <s:form action="login" method="post" namespace="/">  
        <s:hidden name="method" value="login"/>
        <s:textfield name="userName" label="userName"></s:textfield>  
        <s:password name="password" label="password"></s:password>  
        <s:submit label="submit"></s:submit>  
     </s:form>  
 </body>  
 </html>  
