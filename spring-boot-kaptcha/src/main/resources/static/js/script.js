document.addEventListener('DOMContentLoaded', function() {
    const form = document.getElementById('verifyForm');
    const kaptchaImage = document.getElementById('kaptchaImage');
    const refreshBtn = document.getElementById('refreshBtn');
    const resultContainer = document.getElementById('result');
    const codeInput = document.getElementById('code');
    
    // 刷新验证码
    refreshBtn.addEventListener('click', function() {
        refreshKaptchaImage();
        
        // 调用后端刷新接口
        fetch('/refreshKaptcha')
            .then(response => response.json())
            .then(data => {
                console.log(data.message);
            })
            .catch(error => {
                console.error('刷新验证码失败:', error);
            });
    });
    
    // 表单提交
    form.addEventListener('submit', function(e) {
        e.preventDefault();
        
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const code = codeInput.value;
        
        // 基础验证
        if (!username) {
            showResult('请输入用户名', 'error');
            return;
        }
        
        if (!password) {
            showResult('请输入密码', 'error');
            return;
        }
        
        if (!code) {
            showResult('请输入验证码', 'error');
            return;
        }
        
        // 显示加载状态
        const submitBtn = form.querySelector('.submit-btn');
        const originalText = submitBtn.innerHTML;
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> 验证中...';
        submitBtn.disabled = true;
        
        // 发送验证请求
        fetch('/verify', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'code=' + encodeURIComponent(code)
        })
        .then(response => response.json())
        .then(data => {
            if (data.success) {
                showResult('验证成功', 'success');
                // 验证成功后刷新验证码
                refreshKaptchaImage();
            } else {
                showResult(data.message, 'error');
                // 验证失败后刷新验证码
                refreshKaptchaImage();
                // 清空验证码输入框
                codeInput.value = '';
                // 聚焦到验证码输入框
                codeInput.focus();
            }
        })
        .catch(error => {
            console.error('验证请求失败:', error);
            showResult('网络错误，请重试', 'error');
        })
        .finally(() => {
            // 恢复按钮状态
            submitBtn.innerHTML = originalText;
            submitBtn.disabled = false;
        });
    });
    
    // 显示结果
    function showResult(message, type) {
        resultContainer.textContent = message;
        resultContainer.className = 'result-container ' + type;
        resultContainer.style.display = 'block';
        
        // 5秒后自动隐藏结果
        setTimeout(() => {
            resultContainer.style.display = 'none';
        }, 5000);
    }
    
    // 刷新验证码图片
    function refreshKaptchaImage() {
        kaptchaImage.src = '/kaptcha?t=' + new Date().getTime();
    }
    
    // 点击验证码图片也可以刷新
    kaptchaImage.addEventListener('click', function() {
        refreshBtn.click();
    });
});