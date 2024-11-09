商户ID：1638280194

商户API证书
商户API证书用于证实商户身份，需妥善保管防止泄露：
APIv2中，调用微信支付安全级别较高的接口；APIv3中，调用微信支付所有接口需使用
65DD5B60058E37C2D4BF64DCA71E8149E1231BB3

平台证书
商户调用业务API后，微信支付回调会使用平台证书的私钥生成签名，商户需要使用平台证书的公钥验签。平台证书5年过期一次，为保证业务正常使用，请及时更换
国际RSA证书
当前证书
序列号 1FDF8457B759F5BD1B7CBE0E8331BC5E625A190E
有效期 2023/3/20 ~ 2028/3/18
curl -X GET \
https://api.mch.weixin.qq.com/v3/certificates \
-H "Authorization: WECHATPAY2-SHA256-RSA2048 mchid=\"1638280194\" \
-H "Accept: application/json"

证书串：

-----BEGIN CERTIFICATE-----
MIIEKDCCAxCgAwIBAgIUHuAIAHMiICjEVs9jIPEnnLxYAj0wDQYJKoZIhvcNAQEL
BQAwXjELMAkGA1UEBhMCQ04xEzARBgNVBAoTClRlbnBheS5jb20xHTAbBgNVBAsT
FFRlbnBheS5jb20gQ0EgQ2VudGVyMRswGQYDVQQDExJUZW5wYXkuY29tIFJvb3Qg
Q0EwHhcNMjQxMDI4MDIzODQzWhcNMjkxMDI3MDIzODQzWjCBgTETMBEGA1UEAwwK
MTYzODI4MDE5NDEbMBkGA1UECgwS5b6u5L+h5ZWG5oi357O757ufMS0wKwYDVQQL
DCTmna3lt57lvJXmgZLmlbDmmbrnp5HmioDmnInpmZDlhazlj7gxCzAJBgNVBAYT
AkNOMREwDwYDVQQHDAhTaGVuWmhlbjCCASIwDQYJKoZIhvcNAQEBBQADggEPADCC
AQoCggEBALBbpV3yoU1VP0eWCnJmAa8Ot0xfGB3BXhALac0arbN8qxj7JJL/8Naw
8wX/8eSesxFw0BFQv7ZrbNU1yaVJNw/UyGCCA2EcOnz80QnU9CQhTG4SZcxyt4SL
+HqNF73uImQKeBg/ASTXNBCSycKmNA106Jof1VZxbDHtRsmBhERBZOY0JeV2vMCS
bI30uYK6AnicZkNPFQgOZTTJD0abrybCCQPJSipVXE4Htbotelqr9HjpBckBfIke
5sxxH4O+RPs4TP2kFxX6pzzi8dId2SQAR/0jsAzEWE0/Dzq49v5McaKXV5ZwCPrH
h3ZakXl5S4/ThQCtIkdYWaBjIxx7obcCAwEAAaOBuTCBtjAJBgNVHRMEAjAAMAsG
A1UdDwQEAwID+DCBmwYDVR0fBIGTMIGQMIGNoIGKoIGHhoGEaHR0cDovL2V2Y2Eu
aXRydXMuY29tLmNuL3B1YmxpYy9pdHJ1c2NybD9DQT0xQkQ0MjIwRTUwREJDMDRC
MDZBRDM5NzU0OTg0NkMwMUMzRThFQkQyJnNnPUhBQ0M0NzFCNjU0MjJFMTJCMjdB
OUQzM0E4N0FEMUNERjU5MjZFMTQwMzcxMA0GCSqGSIb3DQEBCwUAA4IBAQAjSZGc
7Ky57ncCjNX+eBPW3FXxYb25jpuuwaXXWU2HEv60EFrEc94aNfBNQ4whw6EG1kWB
ACBHe3By0N8T/FW7FhA65RdACIFpBlY9T2ZW+pCBSvJQkxtI8TkVE/W3fFYf5Y1G
x8LmzrC/Jz4OegJBSN+fdHh2XwH02b8YSd/uFS8IOQtIqydukF/Dat+TEa53JqOX
DXFWxvwm1R2nvsMLEx0z1t+TA0fy/ArXR1nz4+9Hy1Gxm644zHa5y8a8QNtAJxx5
1sW92+IURUm4XKnLxTLUrtnqqnbIcEL8V4JRnnOCI43vIhUD61GLc/fZsW+7fNS0
KUkjk1H+mLtr6hA9
-----END CERTIFICATE-----

apiV3key:Abcdefghijkkkkkkkk123kkkkkkkkkkk
mchId:1638280194
mchPrivateKeyFilePath:/Users/dufeihu/Documents/html/kaoyan/cert/1638280194_20241028_cert/apiclient_key.pem
mchSerialNo:1EE0080073222028C456CF6320F1279CBC58023D
outputFilePath: /Users/dufeihu/Documents/html/kaoyan


证书路径：/Users/dufeihu/cert/1638280194_20241028_cert.zip

java -jar CertificateDownloader.jar -k ${apiV3key} -m ${mchId} -f ${mchPrivateKeyFilePath} -s ${mchSerialNo} -o ${outputFilePath}
java -jar CertificateDownloader.jar -k Abcdefghijkkkkkkkk123kkkkkkkkkkk  -m 1638280194 -f /Users/dufeihu/Documents/html/kaoyan/cert/1638280194_20241028_cert/apiclient_key.pem -s 1EE0080073222028C456CF6320F1279CBC58023D -o /Users/dufeihu/Documents/html/kaoyan


java -jar CertificateDownloader-1.2.0-jar-with-dependencies.jar -k ${1bh5f1pud5wx7daw165kh4za4grjru0g} -m ${1638280194} -f ${/Users/dufeihu/cert/1638280194_20241028_cert.zip} -s ${1EE0080073222028C456CF6320F1279CBC58023D} -o ${/Users/dufeihu/Documents/html/kaoyan}