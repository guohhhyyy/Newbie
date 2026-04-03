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
                body: JSON.stringify({ phone: phone, password: password }),//把手机号转化成JSON字符串格式，放在请求里面
                credentials: 'include'
            });

            if (!response.ok) {
                throw new Error('后端返回错误状态码：' + response.status);
            }

            const result = await response.json();//response.json() 把服务器返回的结果解析成JavaScript对象
            return result;
        } catch (error) {//catch 要是网络请求，就抓捕错误
            console.error('请求失败详情：', error);
            return { 
                success: false, 
                message: error.message || '后端未启动/网络异常/响应格式错误' 
            };
        }
    }
```
