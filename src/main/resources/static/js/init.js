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
    firstDay: 1,
    selectMonths: true, // Creates a dropdown to control month
    selectYears: 15, // Creates a dropdown of 15 years to control year,
    today: 'Bugün',
    clear: 'Temizle',
    close: 'Kapat',
    format: 'dd mmmm yyyy dddd',
    formatSubmit: 'yyyymmdd',
    closeOnSelect: false,
    hiddenName: true,
    min: true
  });
  
  

    $("#f").devbridgeAutocomplete({
	    //lookup: countries,
	    serviceUrl:"Airports", //tell the script where to send requests
	      type:'POST',
	     
	      
	      //callback just to show it's working
	      onSelect: function (suggestion) {
	         // $('#selection').html('You selected: ' + suggestion.value + ', ' + suggestion.data);
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
	    serviceUrl:"Airports", //tell the script where to send requests
	      type:'POST',
	     
	      
	      //callback just to show it's working
	      onSelect: function (suggestion) {
	         // $('#selection').html('You selected: ' + suggestion.value + ', ' + suggestion.data);
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
  
})(jQuery); // end of jQuery name space