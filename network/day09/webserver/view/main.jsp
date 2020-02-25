<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>

<script>
function display(data){
   var result='';
   $(data).each(function(idx,item){
      result += '<h3>';
      result += item.id+ '<br/><br/>';
      result += item.txt;
      result += '<h3>';
      
   });
   $('#uu').html(result);
};
function getData(){
   $.ajax({
      url:'data.mc',
       dataType:"json",
      success:function(data){
         display(data);
         
         
      }
   })
};
   $(document).ready(function(){
      setInterval(getData,1000);
   })
</script>
<html>
<h1>aaaaaaaa</h1>
<div id = "uu">

</div>
</html>