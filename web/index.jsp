<%--
  Created by IntelliJ IDEA.
  User: Igor
  Date: 06.12.2016
  Time: 0:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <link rel="stylesheet" href="/bootstrap-3.3.7-dist/css/bootstrap.css">
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <style>
      .tabbed > input{
        display:none;
      }

      .tabbed > label{
        display:block;
        float:left;
        padding: 12px 20px;
        margin-right:5px;
        cursor:pointer;
      }

      .tabbed > label:hover, .tabbed>input:checked+label{
        background: #4EC6DE;
        color:white;
      }

      .tabs{
        clear:both;
        position:relative;
      }

      .tabs > div{
        position:absolute;
        display:none;
      }

      #tab1:checked ~ .tabs>div:nth-of-type(1),
      #tab2:checked ~ .tabs>div:nth-of-type(2),
      #tab3:checked ~ .tabs>div:nth-of-type(3){
        display:block;
      }

    </style>
    <script>
        function getData(){
            var input1=document.getElementById("url1");
            var input2=document.getElementById("url2");
            var input3=document.getElementById("url3");

            if(validURL(input1) && validURL(input2) && validURL(input3)) {

                document.getElementById("progress-bar").setAttribute("style","display:block");

                $.ajax({
                    type: 'POST',
                    data: 'url1=' + input1.value + '&url2=' + input2.value + '&url3=' + input3.value,
                    url: 'ajax/process',
                    success: function (data) {
                        console.log(data);
                        document.getElementById("tab1table").getElementsByTagName("caption").item(0).innerHTML = data[0].url;
                        document.getElementById("tab2table").getElementsByTagName("caption").item(0).innerHTML = data[1].url;
                        document.getElementById("tab3table").getElementsByTagName("caption").item(0).innerHTML = data[2].url;
                        deleteTables();
                        createTables(data);
                        document.getElementById("progress-bar").setAttribute("style","display:none");
                    },
                    error: function(){
                        document.getElementById("progress-bar").setAttribute("style","display:none");
                        document.getElementById("danger").setAttribute("style","display:block;");
                    }
                });

            }
        }

        function deleteTables(){
            var tbody = document.getElementById("tab1table").getElementsByTagName("tbody").item(0);
            var nodes= tbody.childNodes;
            var n = nodes.length;
            for(var i = 0; i < n; i++){
                nodes[0].remove();
            }

            tbody = document.getElementById("tab2table").getElementsByTagName("tbody").item(0);
            nodes= tbody.childNodes;
            n = nodes.length;
            for(var i = 0; i < n; i++){
                nodes[0].remove();
            }

            tbody = document.getElementById("tab3table").getElementsByTagName("tbody").item(0);
            nodes= tbody.childNodes;
            n = nodes.length;
            for(var i = 0; i < n; i++){
                nodes[0].remove();
            }
        }

        function createTables(obj){
            var counter=0;

            for(var i=0;i<obj[0].tags.length;i++){

                counter+=1;

                var tr=document.createElement("tr");
                var td1=document.createElement("td");
                var td2=document.createElement("td");
                var td3=document.createElement("td");

                var text=document.createTextNode(counter);
                td1.appendChild(text);
                tr.appendChild(td1);

                text=document.createTextNode(obj[0].tags[i].name);
                td2.appendChild(text);
                tr.appendChild(td2);

                text=document.createTextNode(obj[0].tags[i].value);
                td3.appendChild(text);
                tr.appendChild(td3);

                document.getElementById("tab1table").getElementsByTagName("tbody").item(0).appendChild(tr);
            }

            counter=0;

            for(var i=0;i<obj[1].tags.length;i++){
                counter+=1;
                var tr=document.createElement("tr");
                var td1=document.createElement("td");
                var td2=document.createElement("td");
                var td3=document.createElement("td");

                var text=document.createTextNode(counter);
                td1.appendChild(text);
                tr.appendChild(td1);

                text=document.createTextNode(obj[1].tags[i].name);
                td2.appendChild(text);
                tr.appendChild(td2);

                text=document.createTextNode(obj[1].tags[i].value);
                td3.appendChild(text);
                tr.appendChild(td3);
                document.getElementById("tab2table").getElementsByTagName("tbody").item(0).appendChild(tr);
            }

            counter=0;

            for(var i=0;i<obj[2].tags.length;i++){
                counter+=1;
                var tr=document.createElement("tr");
                var td1=document.createElement("td");
                var td2=document.createElement("td");
                var td3=document.createElement("td");

                var text=document.createTextNode(counter);
                td1.appendChild(text);
                tr.appendChild(td1);

                text=document.createTextNode(obj[2].tags[i].name);
                td2.appendChild(text);
                tr.appendChild(td2);

                text=document.createTextNode(obj[2].tags[i].value);
                td3.appendChild(text);
                tr.appendChild(td3);

                document.getElementById("tab3table").getElementsByTagName("tbody").item(0).appendChild(tr);
            }
        }

        function validURL(elem) {
            var pattern = new RegExp('^(https?:\\/\\/){1}'+ // protocol
                '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|'+ // domain name
                '((\\d{1,3}\\.){3}\\d{1,3}))'+ // OR ip (v4) address
                '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // port and path
                '(\\?[;&a-z\\d%_.~+=-]*)?'+ // query string
                '(\\#[-a-z\\d_]*)?$','i'); // fragment locater
            if(!pattern.test(elem.value)) {
                document.getElementById("danger").setAttribute("style","display:block;");
                elem.parentElement.setAttribute("class","col-sm-11 has-error");
                return false;
            } else {
                document.getElementById("danger").setAttribute("style","display:none;");
                elem.parentElement.setAttribute("class","col-sm-11 has-success");
                return true;
            }
        }

    </script>
  </head>

  <body>
  <div class="container">
    <div class="col-md-12">
      <h1 align="center">Разбор WEB страниц</h1>
      <form class="form-horizontal">
        <div class="alert alert-danger" role="alert" id="danger" style="display:none;">
          <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
          Please, enter a valid URL (i.e. http://...) and try again!
        </div>
        <div class="form-group">
          <label for="url1" class="col-sm-1 control-label">URL1</label>
          <div class="col-sm-11">
            <input type="text" class="form-control" id="url1" placeholder="URL1">
          </div>
        </div>
        <div class="form-group">
          <label for="url2" class="col-sm-1 control-label">URL2</label>
          <div class="col-sm-11">
            <input type="text" class="form-control" id="url2" placeholder="URL2">
          </div>
        </div>
        <div class="form-group ">
          <label for="url3" class="col-sm-1 control-label">URL3</label>
          <div class="col-sm-11">
            <input type="text" class="form-control" id="url3" placeholder="URL3">
          </div>
        </div>
        <div class="form-group">
          <div class="col-sm-12 center-block">
            <button type="button" class="btn btn-primary center-block" onclick="getData()">PROCESS</button>
          </div>
        </div>
      </form>
      <div class="col-sm-4 col-sm-offset-4">
        <div class="progress" id="progress-bar" style="display:none;">
          <div class="progress-bar progress-bar-success progress-bar-striped active" role="progressbar" style="width: 100%"></div>
        </div>
      </div>
      <div class="col-sm-11 col-sm-offset-1">
        <div class="tabbed">
          <input type="radio" name="tabs" id="tab1" checked>
          <label for="tab1">URL1</label>
          <input type="radio" name="tabs" id="tab2">
          <label for="tab2">URL2</label>
          <input type="radio" name="tabs" id="tab3">
          <label for="tab3">URL3</label>
          <div class="tabs">
            <div class="col-sm-12">
              <table class="table table-bordered" id="tab1table">
                <caption></caption>
                <thead>
                <tr>
                  <th>№</th>
                  <th>Tag</th>
                  <th>Value</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
              </table>
            </div>
            <div class="col-sm-12">
              <table class="table table-bordered" id="tab2table">
                <caption></caption>
                <thead>
                <tr>
                  <th>№</th>
                  <th>Tag</th>
                  <th>Value</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
              </table>
            </div>
            <div class="col-sm-12">
              <table class="table table-bordered" id="tab3table" >
                <caption></caption>
                <thead>
                <tr>
                  <th>№</th>
                  <th>Tag</th>
                  <th>Value</th>
                </tr>
                </thead>
                <tbody>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  </body>
</html>
