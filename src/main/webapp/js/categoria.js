$(document).ready(function(){
	
	$('#btnAbrirNCategoria').click(function (){
		$('#txtNomeCategoriaER').val("");
		$('.error-validation').fadeOut();
		$('#actionCategoria').val("addCategoria");
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
	
	$('#frmCategoriaModal').submit(function (){
		if(validarFormCategoria()){
			$('#nameFormCategoria').val("frmCategoriaModal");
			$('#modalLoadCategoria').modal("show");
		}
		return false;
		
	});
	
	
	$('#modalLoadCategoria').on('shown.bs.modal', function(){
		processarAjaxCategoria();
	
	}); 
	
	addEventsCombosPaginar();
	addValidationFormCategoria();
	
	$('#modalLoadCategoria').modal("show");
	
	
});

function processarAjaxCategoria(){
	var dadosSerializadosCompletos = $('#' + $('#nameFormCategoria').val()).serialize();
	if($('#nameFormCategoria').val().toLowerCase() !== "frmCategoria"){
		dadosSerializadosCompletos += "&txtNomeCategoria=" + $('#txtNomeCategoria').val();
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
			if($('#actionCategoria').val().toLowerCase() === "paginarcategoria"){
				listarCategoria(jsonResponse.BEAN_PAGINATION);
			}else{
				if(jsonResponse.MESSAGE_SERVER.toLowerCase() === "ok"){
					$('#frmModalPrincipalCategoria').modal("hide");
					listarCategoria(jsonResponse.BEAN_PAGINATION);
					viewAlert('Operação efetuada com sucesso!', 'success');
				} else{
					viewAlert(jsonResponse.MESSAGE_SERVER, 'warning');
				}
			}
			 
			  //console.log(jsonResponse); 
			  
		},
		
		error: function (JqXRH, textStatus, errorThrown){
			$('#modalLoadCategoria').modal("hide");
			console.log('Erro de chamada de ajax' + textStatus + errorThrown);
			viewAlert('Erro interno do servidor', 'error');
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
			
			});
			//Adição dos botões de paginação
			
			var defaultOptions = getDefaultOptionsPagination();
			
			var options = getOptionsPagination(BEAN_PAGINATION.COUNT_FILTER, $('#sizePageCategoria'),
						$('#numberPageCategoria'), $('#actionCategoria'), "paginarCategoria", 
						$('#nameFormCategoria'), "frmCategoria", $('#modalLoadCategoria'));

			$pagination.twbsPagination($.extend({}, defaultOptions, options ));
			addEventsButtonsCategoria();
			$('#txtNomeCategoria').focus();
		
	} else {
		viewAlert('Nenhum registro encontrado', 'warning');
	}
	
}

function addEventsButtonsCategoria(){
	$('.editar-categoria').each(function (index, value){
		$(this).click(function (){
			//console.log("clique aqui");
			$('#txtIdCategoriaER').val($(this.parentElement.parentElement).attr('idCategoria'));
			$('#txtNomeCategoriaER').val($(this.parentElement.parentElement).attr('nome'));
			$('#tituloModalPrincipalCategoria').html("ATUALIZAR CATEGORIA");
			$('#actionCategoria').val("updateCategoria");
			$('#frmModalPrincipalCategoria').modal("show");
			 
		});
	
	});
	
	$('.excluir-categoria').each(function (index, value){
		$(this).click(function (){
			//console.log("clique aqui");
			$('#txtIdCategoriaER').val($(this.parentElement.parentElement).attr('idCategoria'));
			viewAlertDelete('Categoria');
		});
	
	});

}

function addValidationFormCategoria(){
	$('#txtNomeCategoriaER').on('change', function(){
		$(this).val() === "" ? $('#validarNomeCategoriaER').fadeIn('slow') : $('#validarNomeCategoriaER').fadeOut();
	});
}

function validarFormCategoria(){
	if($('#txtNomeCategoriaER').val( ) === ""){
		$('#validarNomeCategoriaER').fadeIn('slow');
		return false;
	} else{
		$('#validarNomeCategoriaER').fadeOut();
		
	}
	return true;
}

