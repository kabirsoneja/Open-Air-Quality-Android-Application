<%@ page import="org.bson.Document" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: kabirsoneja
  Date: 4/5/20
  Time: 11:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dashboard</title>
</head>
<style>
    h1 {
        color: blue;
        text-align: center;
        text-shadow: 2px 2px lightskyblue;
    }
    h2 {
        color: orangered;
        text-align: center;
    }
</style>
<body>
<br>
<h1>Open Air Quality Dashboard</h1>
<br>
<h2>Application Logs</h2>
<style>
table {
border-collapse: collapse;
width: 100%;
}

th, td {
text-align: left;
padding: 8px;
}


tr:nth-child(even){background-color: #f2f2f2}

th {
background-color: #4CAF50;
color: white;
}
</style>
<% List<Document> dList = (List<Document>) request.getAttribute("logs");%>
<% List<Document> areaList = (List<Document>) request.getAttribute("areas");%>
<% List<Document> mList = (List<Document>) request.getAttribute("measurements");%>
<table width="500" align="center">
    <tr>
        <th>UserAgent</th>
        <th>Latency</th>
        <th>Search Country</th>
        <th>IP Address</th>
        <th>Areas</th>
        <th>Measurements</th>

    </tr>
    <%for (int i=0;i<dList.size();i++) {%>
    <tr>
        <td><%=dList.get(i).get("useragent").toString()%></td>
        <td><%=dList.get(i).get("latency").toString()%></td>
        <td><%=dList.get(i).get("searchword").toString()%></td>
        <td><%=dList.get(i).get("Ip address").toString()%></td>
        <td><%=areaList.get(i)%></td>
        <td><%=mList.get(i)%></td>
            <%}%>

<%--    <%for(Document d: dList) {%>--%>
<%--    <tr>--%>
<%--        <td><%=d.get("useragent")%></td>--%>
<%--        <td><%=d.get("latency")%></td>--%>
<%--        <td><%=d.get("searchword")%></td>--%>
<%--        <td><%=d.get("Ip address")%></td>--%>
<%--&lt;%&ndash;        <td><%=d.get("")%></td>&ndash;%&gt;--%>
<%--&lt;%&ndash;        <td><%=d.get("")%></td>&ndash;%&gt;--%>
<%--    </tr>--%>
<%--    <%}%>--%>
</table>
<br>
<br>
<h2>Analytic Logs</h2>
<table width="500" align="center">
    <tr>
        <th>Parameter</th>
        <th>Value</th>
    </tr>
    <tr>
        <td>Total Number of Searches</td>
        <td><%=request.getAttribute("numberofsearches")%></td>
    </tr>
    <tr>
        <td>Average Latency</td>
        <td><%=request.getAttribute("avglatency")%></td>
    </tr>
    <tr>
        <td>Maximum Latency</td>
        <td><%=request.getAttribute("maxlatency")%></td>
    </tr>
    <tr>
        <td>Minimum Latency</td>
        <td><%=request.getAttribute("minlatency")%></td>
    </tr>

</table>
</body>
</html>
