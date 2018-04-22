回答接口
url:POST http://test.huabane.com/lottery/sample/checkAnswer
request param
{"questionId":1,"answerId":123}
response param
{"award":null,
 "options":[{"id":1,"questionId":2,"choices":A,"name":"sssss","reason":"回答正确"}],
 "correct":false
}
抽红包接口
url:GET http://test.huabane.com/lottery/lottery/sendRedPack
request 
response
{"fundReceived":true,redpackAmount":0}
