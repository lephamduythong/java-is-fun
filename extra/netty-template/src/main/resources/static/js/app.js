document.addEventListener('DOMContentLoaded', function() {
    const testBtn = document.getElementById('testBtn');
    const message = document.getElementById('message');
    
    if (testBtn) {
        testBtn.addEventListener('click', function() {
            message.textContent = 'JavaScript is working! Static files are being served correctly by Netty.';
            message.classList.add('show');
            
            // Hide message after 5 seconds
            setTimeout(function() {
                message.classList.remove('show');
            }, 5000);
        });
    }
    
    console.log('Netty Static File Server - JavaScript loaded successfully!');
});
