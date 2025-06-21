/* login-forgot_pasword-sign-in */
// Parallax effect
document.addEventListener('mousemove', function(e) {
  const xAxis = (window.innerWidth / 2 - e.pageX) / 25;
  const yAxis = (window.innerHeight / 2 - e.pageY) / 25;
  const elements = document.querySelectorAll('.3d-effect');
  
  elements.forEach(element => {
    element.style.transform = `rotateY(${xAxis}deg) rotateX(${yAxis}deg)`;
  });
});

// Input focus effects
const inputs = document.querySelectorAll('.input-3d');
inputs.forEach(input => {
  input.addEventListener('focus', function() {
    this.parentNode.classList.add('focused');
  });
  
  input.addEventListener('blur', function() {
    this.parentNode.classList.remove('focused');
  });
});

// Button ripple effect
const buttons = document.querySelectorAll('.3d-button');
buttons.forEach(button => {
  button.addEventListener('click', function(e) {
    const x = e.clientX - e.target.getBoundingClientRect().left;
    const y = e.clientY - e.target.getBoundingClientRect().top;
    
    const ripple = document.createElement('span');
    ripple.classList.add('ripple');
    ripple.style.left = `${x}px`;
    ripple.style.top = `${y}px`;
    
    this.appendChild(ripple);
    
    setTimeout(() => {
      ripple.remove();
    }, 1000);
  });
});
/* end-login-forgot_pasword-sign-in */