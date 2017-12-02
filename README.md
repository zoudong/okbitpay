# okbitpay
okbitpay is a bitcoin payment system by java

1、如果你想拥有一个专属于你自己的不用与任何平台对接的私有比特币支付系统来把你的支付订单与业务系统关联起来

2、如果国外主流比特币钱包或支付系统受限于政策不能支持或允许受限国家接入比特币支付系统

3、如果你想通过区块链实现一些高度个性化的私人比特币财务系统需求

那么checkout this okbitpay 帮你自己建立一套快捷、安全、易用的比特币支付系统

未来开源网址 http://www.okbitpay.com

项目开发中……

使用说明:
1、安装bitCoin core 
bit coinCore config:
server=1
rest=1
rpcbind=127.0.0.1:8332
rpcuser=root
rpcpassword=123456



2、config.properties配置同步

#bitcoin core client config by okbitpay
rpcaddress=127.0.0.1
rpcuser=root
rpcpassword=123456
rpcport=8332

validation_level=6
maxretry_count=9

3、数据库okbitpay.sql安装

4、对接你的订单系统
post http://localhost:8080/createPayOrder?amount=1&orderId=0000000000&callbackUrl=http://127.0.0.1:8080

返回结果:
{
    "status": "success",
    "msg": "success",
    "data": {
        "code": "aa7eec07-efed-4d92-ae6a-b7861475291e"
    },
    "externData": null
}

一个orderId返回一个code自己保存在订单系统



get http://localhost:8080/selectAllPayOrder

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

**Donation: bitcoin address:   12F1PyutAzus9Q9Bg64fYqCXyyYcMxts4b


