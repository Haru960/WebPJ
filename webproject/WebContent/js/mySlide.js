/**
 * 
 */

var slideIndex = 1;
var auto = null;
showSlides(slideIndex);

function plusSlides(n) {
  showSlides(slideIndex += n);
  
}

function currentSlide(n) {
  showSlides(slideIndex = n);
}

function showSlides(n) {
  var i;
//  alert(slideIndex +"들어오는 값 확인");
  var slides = document.getElementsByClassName("mySlides");
  var dots = document.getElementsByClassName("dot");
  if (n > slides.length) {slideIndex = 1}    
  if (n < 1) {slideIndex = slides.length}
  for (i = 0; i < slides.length; i++) {
      slides[i].style.display = "none";  
  }
  for (i = 0; i < dots.length; i++) {
      dots[i].className = dots[i].className.replace(" active", "");
  }
  if(slideIndex == 4){
	  slideIndex = 1;
  }
  slides[slideIndex-1].style.display = 'block';  
//  slides[slideIndex-1].css("display", "block");
  dots[slideIndex-1].className += " active";
  slideIndex++;
  if(auto != null){
	  clearInterval(auto);
  }
  auto = setInterval(showSlides, 10000);
}

