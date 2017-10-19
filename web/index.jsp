<%-- 
    Document   : home
    Created on : 17/10/2017, 18:09:45
    Author     : italo
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SMD Anonymity</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" type="text/css" href="css/bootstrap.min.css" media="all" />
        <link rel="stylesheet" type="text/css" href="css/font-awesome.min.css" media="all" />
        <link rel="stylesheet" type="text/css" href="css/style.css" media="all" />
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.7/semantic.min.css" />
    </head>

    <body>
        <nav class="navbar navbar-inverse">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#"><i class="fa fa-lock" aria-hidden="true"></i>SMD Anonymity</a>
                </div>
                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#"><i class="fa fa-user-circle fa-fw" aria-hidden="true"></i>Administrador</a></li>
                    <li><a href="dataAnonymityLogin.html" ><i class="fa fa-sign-out fa-fw" aria-hidden="true"></i>Sair</a></li>
                </ul>
            </div>
        </nav>

        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <h2 id="title" class="text-center">Data Anonymization</h2>
                </div>
                <div class="col-lg-6 col-lg-offset-3 col-md-12 col-sm-12 col-xs-12 md-margin ">
                    <%
                        String action = "UploadDataRawServlet";
                        if (request.getAttribute("dataRaw") != null) {
                            action = "UploadHierarchyFilesServlet";
                        }
                    %>

                    <form id="form" action="<%= action %>" enctype="multipart/form-data" method="post">

                        <br>

                        <h4>Baixe o arquivo em anexo para inserir os metadados de forma correta:</h4>

                        <div class="form-group">
                            <br>
                            <a href="C:\Users\italo\Documents\NetBeansProjects\SMDAnonymizer\data\adult.csv" download>
                                <i class="fa fa-download fa-5x down" aria-hidden="true"></i>
                            </a>
                        </div>

                        <%
                            if (request.getAttribute("dataRaw") != null) {
                        %>
                        Arquivo: <%= request.getAttribute("dataRaw")%>
                        <input type="hidden" name="dataRaw" value="<%= request.getAttribute("dataRaw")%>" />
                        <%
                        } else {
                        %>
                        Arquivo: <input type="file" name="datafile" />
                        <%
                        }
                        %>

                        <br>

                        <div class="form-group">
                            <label for="algoritmo" >Algoritmos:</label>
                            <select name="algoritmo" multiple="" class="ui fluid dropdown form-control" id="algoritmo" required="required" >
                                <option value="" selected hidden>Selecione...</option>
                                <option>K-Anonymity</option>
                            </select>
                        </div>



                        <div class="form-group">
                            <label for="exportar" >Campos:</label>
                            <%
                                if (request.getAttribute("csvColumns") != null) {
                                    List<String> csvColumns = (List<String>) request.getAttribute("csvColumns");
                                    for (String column : csvColumns) {
                            %>
                            <div>
                                <input type="checkbox" name="chk_<%= column%>" value="<%= column%>" />&nbsp;<%= column%><input type="file" name="file_<%= column%>" />
                            </div>
                            <%
                                    }
                                }
                            %>
                        </div>
                        
                        <div class="text-right">
                            <input type='reset' class="btn btn-warning" value="Limpar"/>
                            <input id="btnCadastrarFilme" type='submit' class="btn btn-success" value='Confirmar'/>
                        </div>
                    </form>
                </div>
            </div>
        </div>

        <script src="js/validation.js"></script>

        <div id="rodape">
            <span>© 2017 Todos os Direitos Reservados. Italo - SMD UFC</span>
        </div>

        <!-- Scripts -->

        <script type="text/javascript">
            $(":file").filestyle({btnClass: "btn-primary"});
        </script>

        <script src="https://www.gstatic.com/firebasejs/4.1.2/firebase.js"></script>
        <script src="js/jquery-3.1.1.min.js"></script>

        <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.7/semantic.min.js"></script>

        <script>
            $('.ui.dropdown').dropdown();
        </script>

    </body>

</html>
