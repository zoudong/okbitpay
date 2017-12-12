# okbitpay 几句代码搞定比特币在线支付对接业务系统   
okbitpay is a bitcoin payment system by java   

1、如果你想拥有一个专属于你自己的不用与任何平台对接的私有比特币支付系统来把你的支付订单与业务系统关联起来   

2、如果国外主流比特币钱包或支付系统受限于政策不能支持或允许受限国家接入比特币支付系统   

3、如果你想通过区块链实现一些高度个性化的私人比特币财务系统需求   

那么checkout this okbitpay 帮你自己建立一套快捷、安全、易用的比特币支付系统   

未来开源网址 http://www.okbitpay.com 还未建立   


#使用说明:   

开启https支持:重新生成keystore.p12替换或去权威机构注册个证书   
keytool -genkey -alias okbitpay  -storetype PKCS12 -keyalg RSA -keysize 2048  -keystore keystore.p12 -validity 3650   

close postman SSL certificate verification   
###特别注意：支付系统部署和比特币linux客户端都要放到防火墙之后，除了你自己的业务系统，不要暴露给外部访问。   

##1、安装bitCoin core    
bit coinCore and config enable rpc:   

server=1   
rest=1   
rpcbind=127.0.0.1:8332   
rpcuser=root   
rpcpassword=123456-youpassword   



##2、config.properties配置同步   

rpcaddress=127.0.0.1   
rpcuser=root   
rpcpassword=123456   
rpcport=8332   

validation_level=6   
maxretry_count=9   

##3、数据库okbitpay.sql import安装   

##4、对接你的订单系统 interface:2   
##创建支付订单   
###post https://localhost:8443/bitcoinPayment/createPayOrder?amount=1&orderId=0000000000&callbackUrl=http://127.0.0.1:8080   
返回结果:   
{  
    "status": "success",  
    "msg": "success",  
    "data": {  
        "code": "aa7eec07-efed-4d92-ae6a-b7861475291e"  
    },  
    "externData": null  
}   
#####一个orderId返回一个code自己保存在订单业务系统关联支付状态   
###如果支付成功并被6个区块确认（标准6个）后会回调你创建订单时的回调地址并附加3个业务参数过来，完成支付流程。      
###支付速度取决于你支付时给矿工的手续费-不是给平台的，平台不收。手续费与矿池挖矿确认区块的优先级有很大关系。   

##查询支付订单   
###get https://localhost:8443/bitcoinPayment/selectPayOrderByPage   

返回结果:  
{   
    "status": "success",  
    "msg": "success",  
    "data": [  
        {  
            "id": 1,  
            "start": 0,  
            "length": 10,  
            "ordBy": null,  
            "ordCol": null,  
            "code": "25bdded6-9251-435d-9b05-b8b6158c9d54",  
            "amount": 1,  
            "receiveAddress": "16VKk2FYYn1nVCtZ16QGSJZ6bq7ZEgBziM",  
            "sendAddress": null,  
            "payTime": null,  
            "retryCount": 10,  
            "lastRetryTime": 1512149030000,  
            "payStatus": "pending",  
            "payDescription": null,  
            "productId": null,  
            "productName": null,  
            "productNumber": null,  
            "accountCode": null,  
            "accountName": null,  
            "status": "enable",  
            "createTime": 1512061076000,  
            "updateTime": null,  
            "callbackUrl": "http://127.0.0.1:8080",  
            "orderId": null  
        }  
    ],  
    "externData": null,  
    "draw": 0,  
    "start": 1,  
    "length": 10,  
    "itotalRecords": 1,  
    "itotalDisplayRecords": 1,  
    "iTotalRecords": 1,  
    "iTotalDisplayRecords": 1  
}  
   
####you order system config callback url param 3: code=xxxxx&orderId=xxxx&payStatus=xxx   


Donation bitcoin address:12F1PyutAzus9Q9Bg64fYqCXyyYcMxts4b    
喜爱这项目赠送比特币给我:12F1PyutAzus9Q9Bg64fYqCXyyYcMxts4b   
^_^^_^^_^^_^^_^^_^^_^^_^^_^^_^^_^^_^^_^^_^^_^^_^^_^^_^^_^^_^
联系方式:（技术交流^_^）   
mail:692737146@qq.com    
qq:692737146    

