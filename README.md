# 网络购物系统
>这是一个大二学生做的第一个全栈项目，用于交流和学习，严禁用于任何商业行为
---

## 项目功能
- **用户认证**：包含用户的登录、注册和管理员的登录
- **分类设计**：支持多级商品的分类，便于查找商品
- **定位功能**：能够定位到用户当前所在的位置
- **统计功能**：管理员后台能够反应每个账号所购买的商品，并且将数量和总价加以统计
- **页面装饰**：轮播图
---

## 技术栈
- **前端**：HTML,CSS,JavaScript
- **后端**：Java
- **数据库**：MySQL
---

## 核心功能实现源代码
### 1.用户认证（包括：用户登录、用户注册、管理员后台登录）
- **手机号校验**
```javascript
function checkPhone() {
        const phone = document.querySelector('input[type="tel"]').value;
        const reg = /^1[3-9]\d{9}$/;
        if (!reg.test(phone)) {
            showToast('请输入有效的11位手机号!', 'warning');
            return false;
        } else {
            return true;
        }
    }
```
- **showToast函数**
```javascript
function showToast(message, type = 'success', duration = 3000) {//message 显示要输出的文字内容 type 默认要输出的是success duration 动画时长3s
        const container = document.getElementById('toastContainer');// 从 HTML 里面获取元素 toastContainer
        const toast = document.createElement('div');//创建div 创建toast元素
        toast.className = `toast ${type}`;//toast 基础类名 所有的Toast共用的样式 比如：位置，圆角，内边距等等 type 动态类名 不同的提示类名有不同的颜色样式
        toast.textContent = message;//显示输出的内容
        
        container.appendChild(toast);//把toast 放入容器
        
        // 显示Toast 添加一个动画效果 setTimeout 定时函数
        setTimeout(() => toast.classList.add('show'), 10);
        
        // 自动消失 定时嵌套
        setTimeout(() => {
            toast.classList.remove('show');
            setTimeout(() => container.removeChild(toast), 300);
        }, duration);
        
        // 点击关闭
        toast.addEventListener('click', () => {
            toast.classList.remove('show');
            setTimeout(() => container.removeChild(toast), 300);
        });
    }
```
- **登录请求函数**
```javascript
async function loginRequest(phone, password) {//定义一个异步函数，用于接收手机号和密码
        try {
            const response = await fetch('http://ggghhhyyy.xyz/api/login', {//fetch 是向这个服务器发送HTTP请求
                method: 'POST',//使用POST方法
                headers: { 
                    'Content-Type': 'application/json',//告诉服务器，用的是JSON格式
                    'Access-Control-Allow-Origin': '*'
                },
                body: JSON.stringify({ phone: phone, password: password }),//把手机号和密码转化成JSON字符串格式，放在请求里面
                credentials: 'include'
            });

            if (!response.ok) {
                throw new Error('后端返回错误状态码：' + response.status);
            }

            const result = await response.json();//response.json() 把服务器返回的结果解析成JavaScript对象
            return result;
        } catch (error) {//catch 要是网络请求失败，就抓捕错误
            console.error('请求失败详情：', error);
            return { 
                success: false, 
                message: error.message || '后端未启动/网络异常/响应格式错误' 
            };
        }
    }
```
- **登录按钮点击事件**
```javascript
const loginButton = document.querySelector('.dl');
    
    loginButton.addEventListener('click', async function () {
        // 校验手机号
        const isPhoneValid = checkPhone();
        if (!isPhoneValid) return;

        // 获取输入值
        const phone = document.getElementById('phoneInput').value;
        const password = document.getElementById('passwordInput').value;

        // 校验密码非空
        if (!password) {
            showToast('请输入密码!', 'warning');
            return;
        }

        // 显示加载状态
        loginButton.disabled = true;
        loginButton.textContent = "登录中...";

        try {
            // 发送登录请求
            const result = await loginRequest(phone, password);//await 等待loginRequest处理完成
            
            if (result.success) {
                sessionStorage.setItem('currentUserPhone',phone);//把用户手机号存到会话存储里面（页面被关闭的时候，手机号被清除）
                console.log('登录成功，已保存用户手机号:',phone);
                showToast('登录成功！', 'success');
                // 登录成功后跳转到首页
                setTimeout(() => {
                    window.location.href = "inde.html";// 当登录成功之后，页面跳转至 inde.html
                }, 1500);
            } else {
                showToast('登录失败：' + result.message, 'error');
            }
        } catch (error) {//错误抓捕
            showToast('请求出错：' + error.message, 'error');
            console.error('登录出错:', error);
        } finally {//最终执行
            // 恢复按钮状态
            loginButton.disabled = false;
            loginButton.textContent = "登录";
        }
    });
```
- **注册请求函数**
```javascript
async function registerRequest(phone, password) {//异步函数 接受手机号和密码
        try {
            const response = await fetch('http://ggghhhyyy.xyz/api/login', {//fetch 向这个服务器发送HTTP请求 
                method: 'POST',//方法 POST
                headers: {
                    'Content-Type': 'application/json'//格式是JSON
                },
                body: JSON.stringify({phone, password})//将手机号和密码转换成JSON字符串 放在请求里面
            });
            
            if (!response.ok) {//若是 HTTP状态码在 200-299就成功
                throw new Error('注册失败,状态码' + response.status);
            }
            
            return await response.json();//返回服务器反应（HTTP成功的时候执行）
        } catch (error) {//抓捕错误
            console.error('注册请求失败:', error);
            return {//HTTP失败的时候执行
                success: false,
                message: '注册失败: ' + (error.message || '网络错误')
            };
        }
    }
```
- **注册按钮点击事件**
```javascript
const registerButton = document.querySelector('.dl');  // 获取注册按钮
    
    registerButton.addEventListener('click', async function() {
        console.log('注册按钮被点击了！'); 
        
        // 获取输入值
        const phone = document.getElementById('phoneInput').value.trim();
        const password = document.getElementById('passwordInput').value;
        
        console.log('手机号:', phone, '密码:', password);  // 调试用
        
        // 验证手机号
        if (!checkPhone()) {
            showToast('请输入有效的11位手机号', 'warning');
            return;
        }
        
        // 验证密码
        if (!password) {
            showToast('请输入密码', 'warning');
            return;
        }
        
        //限制密码长度
        if (password.length < 6) {
            showToast('密码长度至少6位', 'warning');
            return;
        }
        
        // 显示加载状态
        registerButton.disabled = true;
        registerButton.textContent = "注册中...";
        
        try {
            // 发送注册请求
            const result = await registerRequest(phone, password);//调用函数registerRequest 等待返回结果
            console.log('注册结果:', result); 
            
            // 处理结果
            if (result.success) {
                showToast('注册成功！正在跳转到登录页面...', 'success');
                // 延迟跳转，让用户看到提示
                setTimeout(() => {
                    window.location.href = "login.html"; // 跳转到登录页
                }, 1500);
            } else {
                showToast('注册失败：' + result.message, 'error');
            }
        } catch (error) {
            showToast('请求出错：' + error.message, 'error');
            console.error('注册出错:', error);
        } finally {
            // 恢复按钮状态
            registerButton.disabled = false;
            registerButton.textContent = "注册";
        }
    });
```
