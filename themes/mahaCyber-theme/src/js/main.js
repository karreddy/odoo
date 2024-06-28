AUI().ready(function () {});

Liferay.Portlet.ready(function (_portletId, _node) {});

Liferay.on('allPortletsReady', function () {});  

//Page Load Animation
$(document).ready(function(){
  $(window).on('load', function() {
    $("#loader-wrapper").fadeOut(700);
  }); 

  /* Page Animation Effects */
  AOS.init();

  /* Dark Mode */
  if (sessionStorage.getItem('darkMode') === 'true') {
    enableDarkMode();
  }

  document.getElementById('darkModeToggle').addEventListener('click', function() {
    if (document.body.classList.contains('dark-mode')) {
      disableDarkMode();
    } else {
      enableDarkMode();
    }
  });

  function enableDarkMode() {
    document.body.classList.add('dark-mode');
    document.getElementById('darkModeIcon').src = '/o/mahaCyber-theme/images/icons/darkmode.png';
    sessionStorage.setItem('darkMode', 'true');
  }

  function disableDarkMode() {
    document.body.classList.remove('dark-mode');
    document.getElementById('darkModeIcon').src = '/o/mahaCyber-theme/images/icons/darkmode.png';
    sessionStorage.setItem('darkMode', 'false');
  }


/* Language Increment & Decrement */
$('#incrementFont').click(function () {
  curSize = parseInt($('html').css('font-size')) + 1;
  if (curSize <= 20)
        $('html').css('font-size', curSize);
});
$('#decrementFont').click(function () {
    curSize = parseInt($('html').css('font-size')) - 1;
    if (curSize >= 10)
        $('html').css('font-size', curSize);
});
$('#resetFont').click(function () {
    $('html').css('font-size', 16);
});
});

