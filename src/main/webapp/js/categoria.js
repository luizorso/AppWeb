$(document).ready(function(){
	
	$('#btnAbrirNCategoria').click(function (){
		$('#tituloModalPrincipalCategoria').html("CADASTRAR CATEGORIA");
		$('#frmModalPrincipalCategoria').modal('show');
	});
	
	$('#frmCategoria').submit(function (){
		$('#actionCategoria').val("paginarCategoria");
		$('#nameFormCategoria').val("frmCategoria");
		$('#numberPageCategoria').val("1");
		$('#modalLoadCategoria').modal("show");
		return false;
	
	});
	
	
	
	$('#modalLoadCategoria').on('shown.bs.modal', function(){
		processarAjaxCategoria();
	
	}); 
	
	addEventsCombosPaginar();
	
	$('#modalLoadCategoria').modal("show");
	
	
	
	
	
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
			  $('#modalLoadCategoria').modal("hide");
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
	$pagination.twbsPagination('destroy');
	 $('#nameCrudCategoria').html("[ " + BEAN_PAGINATION.COUNT_FILTER + " ] CATEGORIAS");
	if(BEAN_PAGINATION.COUNT_FILTER > 0){
		var fila;
		var atributos;
		$(BEAN_PAGINATION.LIST).each(function (index, value) {
			//console.log(value);
			fila = "<tr ";
			atributos = "idCategoria='"+ value.idCategoria + "' ";
			atributos += "nome='" + value.nome + "' ";
			fila += atributos;
			fila += ">";
			fila += "<td>" + value.nome + "</td>";
			fila += "<td class='text-center'><button class='btn btn-secondary btn-xs editar-categoria'><i class='fa fa-edit'></i></button></td>";
			fila += "<td class='text-center'><button class='btn btn-secondary btn-xs excluir-categoria'><i class='fa fa-trash'></i></button></td>";
			fila += "</tr>";
			
			$('#tbodyCategoria').append(fila);
			//Adição dos botões de paginação
			
			var defaultOptions = getDefaultOptionsPagination();
			
			var options = getOptionsPagination(BEAN_PAGINATION.COUNT_FILTER, $('#sizePageCategoria'),
						$('#numberPageCategoria'), $('#actionCategoria'), "paginarCategoria", 
						$('#nameFormCategoria'), "frmCategoria", $('#modalLoadCategoria'));

			$pagination.twbsPagination($.extend({}, defaultOptions, options ));
			$('#idCategoria').focus();
		});
	}else{
		console.log("Não há nenhum registro filtrado");
	}
	
}