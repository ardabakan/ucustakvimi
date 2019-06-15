<%@ page contentType="text/html;charset=utf-8" autoFlush="true" buffer="1024kb"%>
	<%        
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setDateHeader("Expires", -1);
%>
<html>
<head>
<!-- Global Site Tag (gtag.js) - Google Analytics -->
<script async src="https://www.googletagmanager.com/gtag/js?id=UA-75604848-2"></script>
<script>
  window.dataLayer = window.dataLayer || [];
  function gtag(){dataLayer.push(arguments);}
  gtag('js', new Date());

  gtag('config', 'UA-75604848-2');
</script>

  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0"/>
  <title>Uçuş Takvimi</title>
 <meta http-equiv="Pragma" content="no-cache">
 <meta http-equiv="Cache-Control" content="no-cache">
 <meta http-equiv="Expires" content="Sat, 01 Dec 2001 00:00:00 GMT">
  <!-- CSS  -->
  <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
  <link href="css/materialize.css" type="text/css" rel="stylesheet" media="screen,projection"/>
  <link href="css/stylemat.css" type="text/css" rel="stylesheet" media="screen,projection"/>
  
    <style type="text/css">
  	.autocomplete-suggestions { border: 1px solid #999; background: #FFF; overflow: auto; }
	.autocomplete-suggestion { padding: 2px 5px; white-space: nowrap; overflow: hidden; }
	.autocomplete-selected { background: #F0F0F0; }
	.autocomplete-suggestions strong { font-weight: normal; color: #3399FF; }
	.autocomplete-group { padding: 2px 5px; }
	.autocomplete-group strong { display: block; border-bottom: 1px solid #000; }

  </style>
		<meta name="description" content="Uçak bileti fiyatlarını karşılaştırıp ucuz uçak bileti bulun, direkt firmadan satın alın. Yurtiçi ucuz uçak bileti Uçuş Takvimi'nde" />
		<meta name="keywords" content="ucuz uçak bileti, uçak bileti, uçuş ara, türk hava yolları, pegasus, atlasjet, ucuz bilet, sunexpress, anadolujet, atlasglobal" />
		<meta name="author" content="ucustakvimi.com" />
		
		<meta property="og:title" content="Uçak bileti fiyatlarını karşılaştırıp ucuz uçak bileti bulun, direkt firmadan satın alın. Yurtiçi ucuz uçak bileti Uçuş Takvimi'nde" />
		<meta property="og:type" content="article" />
		<meta property="article:publisher" content="https://www.facebook.com/ucustakvimi" /> 
		<meta property="og:image" content="http://ucustakvimi.com/images/travelboard.jpg" />
		<meta property="og:url" content="https://www.ucustakvimi.com/" />
		<meta property="og:site_name" content="ucustakvimi.com" />
		<meta property="og:description" content="Uçak bileti fiyatlarını karşılaştırıp ucuz uçak bileti bulun, direkt firmadan satın alın. Yurtiçi ucuz uçak bileti Uçuş Takvimi'nde" />
		<meta property="fb:app_id" content="296701540724842" />
</head>
<body>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-75604848-2', 'auto');
  ga('send', 'pageview');

</script>
  <nav class="orange lighten-1" role="navigation">
    <div class="nav-wrapper container">
    <a id="logo-container" href="#" class="brand-logo"><img src="images/rsz_unnamed.png"></a>
      <ul class="right hide-on-med-and-down">
        <li><a href="#avantajlar">Neden biz?</a></li>
      </ul>

      <ul id="nav-mobile" class="side-nav">
        <li><a href="#avantajlar">Navbar Linkd</a></li>
      </ul>
      <a href="#" data-activates="nav-mobile" class="button-collapse"><i class="material-icons">menu</i></a>
    </div>
  </nav>
  
  
  <div class="section no-pad-bot" id="index-banner">
    <div class="container">
      <br><br>
      <h1 class="header center orange-text">Uçak bileti! 5 saniyede!</h1>
      <div class="row center">
        <h5 class="header col s12 light">Tüm biletleri burada karşılaştır, havayolundan al!</h5>
      </div>
      <div class="row center">
        <a href="#arama" id="download-button" class="btn-large waves-effect waves-light orange">Hemen Başla!</a>
      </div>
      <br><br>

    </div>
  </div>

  
  <div class="container" id="arama">
  <div class="row">
  	<nav>
    <div class="nav-wrapper">
      <div class="col s12">
        <a href="#!" class="breadcrumb">Bilet Arama</a>
        <a href="#!" class="breadcrumb">Uçak Bileti</a>
        <a href="#!" class="breadcrumb">Yurt içi</a>
      </div>
    </div>
  </nav>
  </div>
  
	<div class="row">
		<div class="input-field col s12 m3">
			<i class="material-icons prefix">flight_takeoff</i>
			<input id="f_code" type="hidden" name="f_code">
			<input onfocus="this.value = '';"  id="f" type="text" name="f" class="validate">
			<label for="f">Kalkış Havaalanı</label>	    
		 </div>
		 <div class="input-field col s12 m3">
		 	<i class="material-icons prefix">flight_land</i>
		 	<input id="t_code" type="hidden" name="t_code">
		    <input onfocus="this.value = '';" id="t" type="text" name="t" class="validate">
			<label for="t">İniş Havaalanı</label>	
		 </div>
		 <div class="input-field col s12 m3">
		 	<i class="material-icons prefix">date_range</i>
		    <label for="d">Uçuş Tarihi</label>
		    <input id="d_code" type="hidden" name="d_code">
        	<input id="d" type="text" class="datepicker">
		 </div>
		 <div class="input-field col s12 m3">
			 <button id="get-data" class="btn-large waves-effect waves-light green" targetUrl="/rest/flight/search/bydateandairway/">Uçuş Ara</button>
		 </div>
		 
	</div>
	<div class="row">
		<div id="displaySectionID">
			<ul class="collection" id="list">
	   		 </ul>
   		 </div>
		<div class="progress" id="indwait">
		  <div class="indeterminate"></div>
		</div>
	</div>
	

  </div>
  

  <div class="container" id="avantajlar">
    <div class="section">

      <!--   Icon Section   -->
      <div class="row">
        <div class="col s12 m4">
          <div class="icon-block">
            <h2 class="center light-blue-text"><i class="material-icons">flash_on</i></h2>
            <h5 class="center">Biletinizi hızla bulun</h5>

            <p class="light">Bir çok havayolunu aynı anda arıyor, biletleri listeliyoruz</p>
          </div>
        </div>

        <div class="col s12 m4">
          <div class="icon-block">
            <h2 class="center light-blue-text"><i class="material-icons">group</i></h2>
            <h5 class="center">Mil Programınızı Kullanın</h5>

            <p class="light">Milleriniz tercih ettğiniz havayoluna işlensin, havayolunun kampanyalarından sonuna kadar faydalanın!</p>
          </div>
        </div>

        <div class="col s12 m4">
          <div class="icon-block">
            <h2 class="center light-blue-text"><i class="material-icons">settings</i></h2>
            <h5 class="center">Kullanım kolaylığı</h5>

            <p class="light">Size basit bir arama ekranı sunuyor ve detaylarla uğraştırmıyoruz</p>
          </div>
        </div>
      </div>

    </div>
    <br><br>
  </div>

  <footer class="page-footer orange">
    <div class="container">
      <div class="row">
        <div class="col l6 s12">
          <h5 class="white-text">Manifestomuz</h5>
          <p class="grey-text text-lighten-4">Uçmak herkesin hakkı olduğu gibi en kısa sürede bulmak da! İşimiz yalnızca bileti bulmak..</p>


        </div>
        <div class="col l6 s12">
          <h5 class="white-text">Havayolları</h5>
          <ul>
            <li><a class="white-text" href="#!">Atlasglobal</a></li>
            <li><a class="white-text" href="#!">THY</a></li>
            <li><a class="white-text" href="#!">Pegasus</a></li>
            <li><a class="white-text" href="#!">Onurair</a></li>
          </ul>
        </div>
      </div>
    </div>
    <div class="footer-copyright">
      <div class="container">
      Made by <a class="orange-text text-lighten-3" href="http://materializecss.com">Materialize</a>
      </div>
    </div>
  </footer>


  <!--  Scripts-->
  <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
  <script src="js/materialize.js"></script>
  <script type="text/javascript" src="js/jquery.autocomplete.js" ></script>
  <script src="js/init.js"></script>

  </body>
</html>