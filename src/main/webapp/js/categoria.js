$(document).ready(function(){
	
	$('#btnAbrirNCategoria').click(function (){
		$('#tituloModalPrincipalCategoria').html("CADASTRAR CATEGORIA");
		$('#frmModalPrincipalCategoria').modal('show');
	});
	
	processarAjaxCategoria();
	
});

function processarAjaxCategoria(){
	var dadosSerializadosCompletos = $('#' + $('#nameFormCategoria').val()).serialize();
	if($('#nameFormCategoria').val().toLowerCase() !== "frmCategoria"){
		dadosSerializadosCompletos += "&nome=" + $('#nome').val();
	}
	
	dadosSerializadosCompletos += "&numberPageCategoria=" + $('#numberPageCategoria').val();
	dadosSerializadosCompletos += "&sizePageCategoria=" + $('#sizePageCategoria').val();
	dadosSerializadosCompletos += "&action=" + $('#actionCategoria').val();
	console.log(dadosSerializadosCompletos);
	$.ajax({
		url: 'categorias',
		type: 'POST',
		data: dadosSerializadosCompletos,
		dataType: 'json',
		success: function(jsonResponse){
			  console.log(jsonResponse); 
			  listarCategoria(jsonResponse.BEAN_PAGINATION);
		},
		
		error: function (JqXRH, textStatus, errorThrown){
			console.log('Erro de chamada de ajax' + textStatus + errorThrown);
			
		}
	
	
	 });
}

function listarCategoria(BEAN_PAGINATION){
	var $pagination = $('#paginationCategoria');
	$('#tbodyCategoria').empty();
	if(BEAN_PAGINATION.COUNT_FILTER > 0){
		var fila;
		var atributos;
		$(BEAN_PAGINATION.LIST).each(function (index, value) {
			//console.log(value);
			fila = "<tr ";
			atributos = "idCategoria='"+ value.idCategoria + "' ";
			atributos += "nome='" + value.nome + "' ";
			fila += atributos ;
			fila += ">";
			fila += "<td>" + value.nome + "</td>";
			fila += "<td class='text-center'><button class='btn btn-secondary btn-xs editar-categoria'><i class='fa fa-edit'></i></button></td>";
			fila += "<td class='text-center'><button class='btn btn-secondary btn-xs excluir-categoria'><i class='fa fa-trash'></i></button></td>";
			fila += "</tr>";
			
			$('#tbodyCategoria').append(fila);
			//Adição dos botões de paginação
			var defaultOptions = {
				totalPages: 10,
				visiblePages: 5,
				initiateStartPageClick: false,
				first: "<i class='fa fa-angle-double-left' aria-hidden='true'></i>",
				prev: "<i class='fa fa-angle-left' aria-hidden='true'></i>",
				next: "<i class='fa fa-angle-right' aria-hidden='true'></i>",
				last: "<i class='fa fa-angle-double-right' aria-hidden='true'></i>"
			};
			
			var totalPages = BEAN_PAGINATION.COUNT_FILTER / parseInt($('#sizePageCategoria').val()) + 1;
			
			var options = {
				startPage: parseInt($('#numberPageCategoria').val()),
				totalPages: totalPages,
				visiblePages: 5,
				initiateStartPageClick: false,
				first: "<i class='fa fa-angle-double-left' aria-hidden='true'></i>",
				prev: "<i class='fa fa-angle-left' aria-hidden='true'></i>",
				next: "<i class='fa fa-angle-right' aria-hidden='true'></i>",
				last: "<i class='fa fa-angle-double-right' aria-hidden='true'></i>",
				
				onPageClick: function(evt, page){
					$('#actionCategoria').val("paginarCategoria");
					$('#numberPageCategoria').val(page);
					$('#nameFormCategoria').val("frmCategoria");
					processarAjaxCategoria();
				}
			};
			$pagination.twbsPagination('destroy');
			$pagination.twbsPagination($.extend({}, defaultOptions, options ));
		});
	}else{
		console.log("Não há nenhum registro filtrado");
	}
	
}