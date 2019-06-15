(function($){
  $(function(){

  $('.button-collapse').sideNav();
    
  $('select').material_select();

  }); // end of document ready
  

  $('.datepicker').pickadate({
	monthsFull: [ 'Ocak', 'Şubat', 'Mart', 'Nisan', 'Mayıs', 'Haziran', 'Temmuz', 'Ağustos', 'Eylül', 'Ekim', 'Kasım', 'Aralık' ],
    monthsShort: [ 'Oca', 'Şub', 'Mar', 'Nis', 'May', 'Haz', 'Tem', 'Ağu', 'Eyl', 'Eki', 'Kas', 'Ara' ],
    weekdaysFull: [ 'Pazar', 'Pazartesi', 'Salı', 'Çarşamba', 'Perşembe', 'Cuma', 'Cumartesi' ],
    weekdaysShort: [ 'Pzr', 'Pzt', 'Sal', 'Çrş', 'Prş', 'Cum', 'Cmt' ],

    selectMonths: true, 
    showMonthsShort: true,
    today: 'Bugün',
    clear: 'Temizle',
    close: 'Kapat',
    format: 'dd mmmm yyyy dddd',
    closeOnSelect: true,
    min: true,
    onSet: function(event){ // this updates the original input
        var selected_date = this.get('select', 'yyyymmdd');
        $('#d_code').val(selected_date);

    }
 });
  
  
  

    $("#f").devbridgeAutocomplete({
	    //lookup: countries,
	    serviceUrl:"rest/airports/listairports", //tell the script where to send requests
	      type:'GET',
	     
	      
      //callback just to show it's working
      onSelect: function (suggestion) {
         // $('#selection').html('You selected: ' + suggestion.value + ', ' + suggestion.data);
    	  $('#f_code').val(suggestion.data);
        },
	  	showNoSuggestionNotice: true,
	    noSuggestionNotice: 'Havalimanı bulunamadı',
	    transformResult: function (response) {
            response = JSON.parse(response);
            var resultSuggestions = [];
            $.map(response, function (jsonItem) {
                if (typeof jsonItem != "string") {
                    $.map(jsonItem, function (suggestionItem) {
                        resultSuggestions.push({ value: suggestionItem.name, data: suggestionItem.code });
                    });
                }
            });
            response.suggestions = resultSuggestions;
            return response;
        }
	  
  
  });
    
    $("#t").devbridgeAutocomplete({
	    //lookup: countries,
	    serviceUrl:"rest/airports/listairports", //tell the script where to send requests
	      type:'GET',
	     
	      
	      //callback just to show it's working
	      onSelect: function (suggestion) {
	         // $('#selection').html('You selected: ' + suggestion.value + ', ' + suggestion.data);
	    	  $('#t_code').val(suggestion.data);
	        },
	  	showNoSuggestionNotice: true,
	    noSuggestionNotice: 'Havalimanı bulunamadı',
	    transformResult: function (response) {
            response = JSON.parse(response);
            var resultSuggestions = [];
            $.map(response, function (jsonItem) {
                if (typeof jsonItem != "string") {
                    $.map(jsonItem, function (suggestionItem) {
                        resultSuggestions.push({ value: suggestionItem.name, data: suggestionItem.code });
                    });
                }
            });
            response.suggestions = resultSuggestions;
            return response;
        }
	  
  
  });
    
    $('#indwait').hide();


	$('#get-data').click(function () {

		$('#indwait').show();

	    var showData = $('#displaySectionID');
	    $('#list').empty();
	  
	    var targetComplete = $('#get-data').attr('targetUrl') + $("#f_code").val() + '/' + $("#t_code").val() + '/' + $("#d_code").val() + '/';
	    
	    $.getJSON(targetComplete + 'KKK', function (data) {	      
	      showData.append(createListVew(data));
	      $("#list li").sort(sort_li).appendTo('#list');
	    });	 
	    $.getJSON(targetComplete + 'PGT', function (data) {	      
			  showData.append(createListVew(data));
			  $("#list li").sort(sort_li).appendTo('#list');
		});	 
	    $.getJSON(targetComplete + 'THY', function (data) {	      
			  showData.append(createListVew(data));
			  $("#list li").sort(sort_li).appendTo('#list');
		});	
	    $.getJSON(targetComplete + 'OHY', function (data) {	      
			  showData.append(createListVew(data));
			  $("#list li").sort(sort_li).appendTo('#list');
		});		    
	    $.getJSON(targetComplete + 'AJA', function (data) {	      
			  showData.append(createListVew(data));
			  $("#list li").sort(sort_li).appendTo('#list');
		});	
	    $('#indwait').hide();
	});
	
	function sort_li(a, b) {
	    return ($(b).data('price')) < ($(a).data('price')) ? 1 : -1;
	}
    
  
})(jQuery); // end of jQuery name space

function createListVew(results){

    var listView = $('#list');

    for(var i=0; i < results.length; i++){
    	
        $('#list').append('<li class="collection-item avatar" data-price="'+results[i].feeAdult+'">'          
        + '<img src="images/' + results[i].airwaysCompanyCode + '.png" alt="" class="circle">'
        + '<span class="title">' + results[i].feeAdult + ' TL</span>'
        + '<p>' + results[i].flightNo + ' Saat  ' +  results[i].departureTime.trim().substring(0,2) + ':' + results[i].departureTime.trim().substring(2,4) + ' <br>'
        + results[i].airwaysCompanyName
        + '</p>'
        + '<a target="_buylink" href="' + results[i].buyLink +  '" class="secondary-content"><i class="material-icons">grade</i></a>'
        + '</li>');
        
    }
    
    return listView;
}