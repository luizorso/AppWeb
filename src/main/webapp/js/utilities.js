function getDefaultOptionsPagination(){
	var defaultOptions = {
				totalPages: 10,
				visiblePages: 5,
				initiateStartPageClick: false,
				first: "<i class='fa fa-angle-double-left' aria-hidden='true'></i>",
				prev: "<i class='fa fa-angle-left' aria-hidden='true'></i>",
				next: "<i class='fa fa-angle-right' aria-hidden='true'></i>",
				last: "<i class='fa fa-angle-double-right' aria-hidden='true'></i>"
			};
			return defaultOptions;
}

function getOptionsPagination(count_filter, $sizePage, $numberPage, $action, valueAction, $form, valueForm, $modalLoading){
	var totalPages = getTotalPages(count_filter, parseInt($sizePage.val()));
	var options = {
				startPage: parseInt($numberPage.val()),
				totalPages: totalPages,
				visiblePages: 5,
				initiateStartPageClick: false,
				first: "<i class='fa fa-angle-double-left' aria-hidden='true'></i>",
				prev: "<i class='fa fa-angle-left' aria-hidden='true'></i>",
				next: "<i class='fa fa-angle-right' aria-hidden='true'></i>",
				last: "<i class='fa fa-angle-double-right' aria-hidden='true'></i>",
				
				onPageClick: function(evt, page){
					$action.val(valueAction);
					$numberPage.val(page);
					$form.val(valueForm);
					$modalLoading.modal("show");
					}
				};
	return options
}

function getTotalPages(count_filter, size_page){
	var count_pages = (count_filter / size_page) + 1;
	/*if(count_filter % size_page > 0){
		count_filter += 1;
	}
	*/
	return count_pages;
	
	
}

function addEventsCombosPaginar(){
	$('.combo-paginar').on('change', function(){
		$('#' + $(this).attr('idBtnBuscar')).trigger('click');
	});
	
}

function viewAlert(message, type){
	swal({
        title: "WebApp",
        text: message,
        type: type,
        showCancelButton: false,
        confirmButtonText: "Fechar",
        confirmButtonClass: 'btn btn-primary',
        buttonsStyling: false
    });
}

function viewAlertDelete(entidade){
	swal({
		title: 'AppWeb',
		text: "Deseja excluir esta categoria?",
		type: 'warning',
		showCancelButton: true,
		confirmButtonText: 'Sim',
		confirmButtonClass: 'btn btn-primary',
		cancelButtonClass: 'btn btn-danger',
		buttonsStyling: false
		}).then((result) => {
			if(result.value){
				$('#action' + entidade).val("delete" + entidade);
				$("#nameForm" + entidade).val("frm"+ entidade +"Modal");
				$('#modalLoad' + entidade).modal("show");
						
			}else{
				swal({
					title:"AppWeb",
					text: "Operação Cancelada",
					type: "success",
					showCancelButton: false,
					confirmButtonColor: '#3085d6',
					confirmButtonText: 'Aceitar',
					confirmButtonClass: 'btn btn-primary',
					buttonsStyling: false
				}
			);
		}

	});
 	$('.swal2-confirm').css("margin-right", "15px");
 }
