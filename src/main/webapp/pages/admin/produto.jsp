<%-- 
    Document   : index
    Created on : 17 de jul. de 2021, 07:47:12
    Author     : laors
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html class="no-js" lang="pt-br">
<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>My App</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="shortcut icon" type="image/png" href="assets/images/icon/favicon.ico">
    <%-- Vamos inserir código java para que não tenhamos problemas quanto as
    importações de css e js para que não tenhamos problemas quando redirecionamos as
    páginas jsp e servlets.
     --%>
     <link rel="shortcut icon" href="<%out.print(getServletContext().getContextPath());%>/resources_app/favicon.png">
     <link rel="stylesheet" href="<%out.print(getServletContext().getContextPath());%>/assets/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%out.print(getServletContext().getContextPath());%>/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="<%out.print(getServletContext().getContextPath());%>/assets/css/themify-icons.css">
    <link rel="stylesheet" href="<%out.print(getServletContext().getContextPath());%>/assets/css/metisMenu.css">
	
    
    <!-- amchart css -->
    <link rel="stylesheet" href="https://www.amcharts.com/lib/3/plugins/export/export.css" type="text/css" media="all" />
	
	<!-- Other css -->
	<link rel="stylesheet" href="<%out.print(getServletContext().getContextPath());%>/assets/css/typography.css">
    <link rel="stylesheet" href="<%out.print(getServletContext().getContextPath());%>/assets/css/default-css.css">
    <link rel="stylesheet" href="<%out.print(getServletContext().getContextPath());%>/assets/css/styles.css">
    <link rel="stylesheet" href="<%out.print(getServletContext().getContextPath());%>/assets/css/responsive.css">
    <link rel="stylesheet" href="<%out.print(getServletContext().getContextPath());%>/css/view/sweetalert.css">
    <link rel="stylesheet" href="<%out.print(getServletContext().getContextPath());%>/css/view/estilos.css">
	
    <!-- modernizr css -->
    <script src="<%out.print(getServletContext().getContextPath());%>/assets/js/vendor/modernizr-2.8.3.min.js"></script>
</head>

<body>
    <!--[if lt IE 8]>
            <p class="browserupgrade">You are using an <strong>outdated</strong> browser. Please <a href="http://browsehappy.com/">upgrade your browser</a> to improve your experience.</p>
        <![endif]-->
    <!-- preloader area start -->
    <div id="preloader">
        <div class="loader"></div>
    </div>
    <!-- preloader area end -->
    <!-- page container area start -->
    <div class="page-container">
        <!-- sidebar menu area start -->
        <div class="sidebar-menu">
            <div class="sidebar-header">
                <div class="logo">
                    <a href="index.html"><img src="<%out.print(getServletContext().getContextPath());%>/assets/images/icon/logo.png" alt="logo"></a>
                </div>
                <br>
                <h6 class="text-center" style="color: aliceblue"><i class="fa fa-user"></i> <strong>JCode</strong></h6>
            </div>
            <!-- MENU AREA -->
            <jsp:include page="../../menu.jsp" />
        </div>
        <!-- sidebar menu area end -->
        <!-- main content area start -->
        <div class="main-content">
            <!-- header area start -->
            <div class="header-area">
                <div class="row align-items-center">
                    <!-- nav and search button -->
                    <div class="col-4 clearfix">
                        <div class="nav-btn pull-left" style="margin-top: 0px">
                            <span></span>
                            <span></span>
                            <span></span>
                        </div>
                    </div>
                    <!-- profile info & task notification -->
                    <div class="col-8 clearfix">
                        <ul class="notification-area pull-right">
                            <li>
                                <a href="">
                                    Encerrar sessão
                                    <i class="fa fa-sign-out" aria-hidden="true"></i>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>
            <!-- header area end -->
            <div class="main-content-inner" style="padding-top: 30px">
				<div class="row" >
					<div class="col-12">
						<div class="card">
							<div class="card-body">
								<h5 class="card-title">Produtos</h5>
								<input type="hidden" id="nameFormProduto" value="frmProduto">
								<input type="hidden" id="actionProduto" value= "paginarProduto">
								<input type="hidden" id="numberPageProduto" value="1">
								<form id="frmProduto">
									<div class="row mt-3">
										<div class="form-group col-md-9 col-12">
											<input type="text" id="txtNomeProduto" name="txtNomeProduto"
													class="form-control form-control-sm" placeholder="Nome">
										</div>
										<div class="form-group- col-md-3 col-12">
											<button type="submit" id="btnBuscarProduto" class="btn btn-primary btn-xs mr-3" data-toggle="tooltip"><i class="fa fa-search" aria-hidden="true"></i> PESQUISAR</button>
											<button type="button" id="btnAbrirProduto" class="btn btn-primary btn-xs" data-toggle="tooltip" title="Adicionar Produto"><i class="fa fa-plus-square" aria-hidden="true"></i></button>
										</div>
									</div>
								</form>
								<div class="row">
									<div class="col-12">
										<div class="table-responsive">
											<table class="table table-hover table-bordered">
												<thead class="bg-primary">
													<tr class="text-white">
														<th>NOME</th>
														<th>PREÇO</th>
														<th>ESTOQUE</th>
														<th>ESTOQUE MÍNIMO</th>
														<th>ESTOQUE MÁXIMO</th>
														<th style="width: 10%" colspan="2" class="text-medium-table">AÇÃO</th>
													</tr>
												</thead>
												<tbody id="tbodyProduto">
																								
												</tbody>
											</table>
										</div>
										</div>
									</div>
									<div class="row">
										<div class="col-12">
											<div class="card" style="height: 50px; margin-bottom: 0px">
												<div class="card-body" style="padding-top: 10px; padding-bottom: 10px; padding-left: 0px; padding-right: 0px">
													<div class="row">
														<div class="col-md-2 col-sm-3 col-4">
															<select id="sizePageProduto" name="sizePageProduto"
																class="form-control form-control-sem combo-paginar"
																idBtnBuscar="btnBuscarProduto">
																<option value="5">5</option>
																<option value="10">10</option>
																<option value="15">15</option>
																<option value="20">20</option>
															</select>
														</div>
														<div class="col-md-10 col-12">
															<nav>
																<ul id="paginationProduto" class="pagination pagination-sm justify-content-end">
												
																</ul>
															</nav>
														</div>
														
													</div>
												</div>
											</div>
										</div>
									</div>
							</div>
						</div>
					</div>
				</div>
            </div>
        </div>
        <!-- main content area end -->
        <div id="frmModalPrincipalProduto" class="modal" tabindex="-1" 
        		role="dialog" data-backdrop="static" data-keyboard="false">
        	<div class="modal-dialog" role="document">
        		<div class="modal-content">
        			<form id="frmProdutoModal">
        				<div class="modal-header">
        					<h6 class="modal-title" id="tituloModalManProduto"></h6>
        					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
        						<span aria-hidden="true">&times;</span>
        					</button>
        				</div>
        				<div class="modal-body">
        					<div class="row">
        						<div class="col-12 form-group">
        							<label for="txtNomeProdutoER">NOME</label>
        							<input type="text" id="txtNomeProdutoER" name="txtNomeProdutoER"
        								   class="form-control form-control-sm" placeholder="NOME">
        							<div class="error-validation" id="validarNomeProdutoER">Informe o nome</div>
        						</div>
        						<div class="col-sm-6 col-12 form-group">
        							<label for="txtPrecoER">PREÇO</label>
        							<input type="text" id="txtPrecoER" name="txtPrecoER"
        									class="form-control form-control-sm" placeholder="PREÇO">
        							<div class="error-validation" id="validarPrecoProdutoER">Informe o preço</div>
        						</div>
        						<div class="col-sm-6 col-12 form-group">
        							<label for="txtEstoqueER">Estoque</label>
        							<input type="text" id="txtEstoqueER" name="txtEstoqueER"
        								class="form-control form-control-sm" placeholder="Estoque">
        							<div class="error-validation" id="validatEstoqueER">Informe o estoque</div>
        						</div>
        						<div class="col-sm-6 col-12 form-group">
        							<label for="txtEstoqueMinER">Estoque Mínimo</label>
        							<input type="text" id="txtEstoqueMinER" name="txtEstoqueMinER"
        								class="form-control form-control-sm" placeholder="Estoque Mínimo">
        							<div class="error-validation" id="validatEstoqueMinER">Informe o estoque mínimo</div>
        						</div>
        						<div class="col-sm-6 col-12 form-group">
        							<label for="txtEstoqueMaxER">Estoque Mínimo</label>
        							<input type="text" id="txtEstoqueMaxER" name="txtEstoqueMaxER"
        								class="form-control form-control-sm" placeholder="Estoque Máximo">
        							<div class="error-validation" id="validatEstoqueMaxER">Informe o estoque máximo</div>
        						</div>
        						<div class="col-12 form-group">
        							<label for="cboCategoriaProdutoER">CATEGORIA</label>
        							<select class="form-control form-control-sm" id="cboCategoriaProdutoER" name="cboCategoriaProdutoER">
        							
        							</select>
        							<div class="error-validation" id="validarCategoriaProdutoER">Selecione a Categoria</div>
        						</div>
        						<input type="hidden" id="txtIdProdutoER" name="txtIdProdutoER" value="">
        					</div>
        				</div>
        				<div class="modal-footer">
        					<button type="button" class="btn btn-secondary btn-xs" data-dismiss="modal">Fechar</button>
        					<button type="button" class="btn btn-primary btn-xs">Salvar</button>
        				</div>
        				   			
        			</form>
        		</div>
        	</div>
        
        </div>
        <div class="modal" id="modalLoadProduto" data-backdrop="static" data-keyboard="false"
        	 tabindex="-1" role="dialog" aria-hidden="true" style="padding-top: 18%; overflow-y:visible;">
        	<div class="modal-dialog modal-sm">
        		<div class="modal-body">
        			<div class="progress" style="margin-bottom: opx;">
        				<div class="progreess-bar progress-bar-striped progress-bar-animated"
        					role="progressbar" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100%"></div>
        					Carregando ...
        			</div>
        		</div>
        	</div>
        
        </div>
        <!-- footer area start-->
        	<jsp:include page="../../rodape.jsp"/>
        <!-- footer area end-->
    </div>
    <!-- page container area end -->
    <!-- jquery latest version -->
    <script src="<%out.print(getServletContext().getContextPath());%>/assets/js/vendor/jquery-2.2.4.min.js"></script>
    <!-- bootstrap 4 js -->
    <script src="<%out.print(getServletContext().getContextPath());%>/assets/js/popper.min.js"></script>
    <script src="<%out.print(getServletContext().getContextPath());%>/assets/js/bootstrap.min.js"></script>
    <script src="<%out.print(getServletContext().getContextPath());%>/assets/js/owl.carousel.min.js"></script>
    <script src="<%out.print(getServletContext().getContextPath());%>/assets/js/metisMenu.min.js"></script>
    <script src="<%out.print(getServletContext().getContextPath());%>/assets/js/jquery.slimscroll.min.js"></script>
    <script src="<%out.print(getServletContext().getContextPath());%>/assets/js/jquery.slicknav.min.js"></script>

    <!-- start chart js -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.7.2/Chart.min.js"></script>
    <!-- start highcharts js -->
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <!-- start zingchart js -->
    <script src="https://cdn.zingchart.com/zingchart.min.js"></script>
    <script>
        zingchart.MODULESDIR = "https://cdn.zingchart.com/modules/";
        ZC.LICENSE = ["569d52cefae586f634c54f86dc99e6a9", "ee6b7db5b51705a13dc2339db3edaf6d"];
    </script>
    <!-- all line chart activation -->
    <script src="<%out.print(getServletContext().getContextPath());%>/assets/js/line-chart.js"></script>
    <!-- all pie chart -->
    <script src="<%out.print(getServletContext().getContextPath());%>/assets/js/pie-chart.js"></script>
    <!-- others plugins -->
    <script src="<%out.print(getServletContext().getContextPath());%>/assets/js/plugins.js"></script>
    <script src="<%out.print(getServletContext().getContextPath());%>/assets/js/scripts.js"></script>
	<script src="<%out.print(getServletContext().getContextPath());%>/js/view/jquery.Pagination.min.js"></script>
    <script src="<%out.print(getServletContext().getContextPath());%>/js/view/sweetalert.min.js"></script>
    <script src="<%out.print(getServletContext().getContextPath());%>/js/utilities.js"></script>
	<script src="<%out.print(getServletContext().getContextPath());%>/js/produto.js"></script>

</body>

</html>
