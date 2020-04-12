var http = require('http');
var https = require('https');
var express  = require('express');
var cors = require('cors')
var express_app = express();
express_app.use(cors())

express_app.use(express.static('public'));

//const options = 'http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Shwetans-ProductS-PRD-a16e557a4-7ccd2f20&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&paginationInput.entriesPerPage=50&keywords=iphone&buyerPostalCode=90007';
var api_url = "";


express_app.get('/form', function(request, res, next){
	console.log("abc");
	console.log(request.query);
	console.log(request);
	api_url = "http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Shwetans-ProductS-PRD-a16e557a4-7ccd2f20&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&paginationInput.entriesPerPage=50&keywords=";
	var index = 0;
	var keyword = request.query.Keyword;
	keyword = keyword.replace(" ", "%20")
	var category_code = request.query.Category;
	api_url+=keyword;
	if(category_code!='all' && category_code!='undefined'){
		api_url+='&categoryId='+category_code;
	}
	try{
		if(request.query.currZip){
		api_url+='&buyerPostalCode='+request.query.currZip+'&itemFilter('+index+').name=MaxDistance&itemFilter('+index+').value='+request.query.Distance;
		index+=1;
		}
	}
	catch(err){}
	try{
		if(request.query.hereZip){
		api_url+='&buyerPostalCode='+request.query.hereZip+'&itemFilter('+index+').name=MaxDistance&itemFilter('+index+').value='+request.query.Distance;
		index+=1;
		}
	}
	catch(err){}
	
	try{
		if((request.query.Local == 'true' || request.query.Local == 'false') && (request.query.Free == 'true' || request.query.Free == 'false')){
			api_url+='&itemFilter('+index+').name=FreeShippingOnly&itemFilter('+index+').value='+request.query.Free;
			index+=1;
			api_url += '&itemFilter('+index+').name=LocalPickupOnly&itemFilter('+index+').value='+request.query.Local;
			index+=1;
		}
		else if(request.query.Local == 'true' || request.query.Local == 'false'){
			api_url += '&itemFilter('+index+').name=LocalPickupOnly&itemFilter('+index+').value='+request.query.Local;
			index+=1;	
		}
		else if(request.query.Free == 'true' || request.query.Free == 'false'){
			api_url+='&itemFilter('+index+').name=FreeShippingOnly&itemFilter('+index+').value='+request.query.Free;
			index+=1;	
		}
	}
	catch(err){
		api_url += '&itemFilter('+index+').name=FreeShippingOnly&itemFilter('+index+').value=true';
		index+=1;
		api_url += '&itemFilter('+index+').name=LocalPickupOnly&itemFilter('+index+').value=true';
		index+=1;
	}
	api_url+='&itemFilter('+index+').name=HideDuplicateItems&itemFilter('+index+').value=true';
	index+=1;
	var count = 0;
	try{
		if(request.query.New=='true'){
			api_url +='&itemFilter('+index+').name=Condition&itemFilter('+index+').value('+count+')=New';
			count+=1;
		}
	}catch(err){}
	try{
		if(request.query.Used=='true'){
			api_url +='&itemFilter('+index+').name=Condition&itemFilter('+index+').value('+count+')=Used';
			count+=1;
		}
	}catch(err){}
	try{
		if(request.query.Unspecified=='true'){
			api_url +='&itemFilter('+index+').name=Condition&itemFilter('+index+').value('+count+')=Unspecified';
			count+=1;
		}
	}catch(err){}

	api_url+='&outputSelector(0)=SellerInfo&outputSelector(1)=StoreInfo';
	var show_url = "http://ebay-app-236704.appspot.com"+request.url;
	console.log(show_url);
	let data='';
		
	const req = http.get(api_url, (resp) => {
	  //console.log(`STATUS: ${res.statusCode}`);
	  //console.log(`HEADERS: ${JSON.stringify(res.headers)}`);
	  resp.setEncoding('utf8');
	  resp.on('data', (chunk) => {
	  	data+=chunk; 
	    //console.log(`BODY: ${chunk}`);
	  });
	  resp.on('end', () => {
	    console.log('No more data in response.');
	    res.send(JSON.parse(data));
	  });
	  //console.log(data);
	});

	req.on('error', (e) => {
	  console.error(`problem with request: ${e.message}`);
	});

})

express_app.get('/itemDetail', function(request, response){
	console.log(request.query);
	api2_url = 'http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=JSON&appid=Shwetans-ProductS-PRD-a16e557a4-7ccd2f20&siteid=0&version=967&ItemID=';
	api2_url+=request.query.itemID;
	api2_url+='&IncludeSelector=Description,Details,ItemSpecifics';

	console.log(api2_url);
	let data='';

	const req = http.get(api2_url, (resp) => {
	  //console.log(`STATUS: ${response.statusCode}`);
	  //console.log(`HEADERS: ${JSON.stringify(response.headers)}`);
	  resp.setEncoding('utf8');
	  resp.on('data', (chunk) => {
	  	data+=chunk; 
	    //console.log(`BODY: ${chunk}`);
	  });
	  resp.on('end', () => {
	    console.log('No more data in response.');
	    response.send(JSON.parse(data));
	  });
  	//console.log(data);
	});

	req.on('error', (e) => {
	  console.error(`problem with request: ${e.message}`);
	});

})

express_app.get('/autoComplete', function(request, response){
	console.log(request.query);
	api4_url='http://api.geonames.org/postalCodeSearchJSON?postalcode_startsWith='+request.query.hereText;
	api4_url+='&username=srohatgi&country=US&maxRows=5';

	console.log(api4_url);
	let data='';

	const req = http.get(api4_url, (resp) => {
	  //console.log(`STATUS: ${response.statusCode}`);
	  //console.log(`HEADERS: ${JSON.stringify(response.headers)}`);
	  resp.setEncoding('utf8');
	  resp.on('data', (chunk) => {
	  	data+=chunk; 
	    //console.log(`BODY: ${chunk}`);
	  });
	  resp.on('end', () => {
	    console.log('No more data in response.');
	    response.send(JSON.parse(data));
	  });
  	//console.log(data);
	});

	req.on('error', (e) => {
	  console.error(`problem with request: ${e.message}`);
	});

})

express_app.get('/googlePhotos', function(request, response){
	console.log(request.query);
	var url_text_google='https://www.googleapis.com/customsearch/v1?q='+request.query.Keyword+'&cx=011572014524656636668:fuajcjgw75g&imgSize=medium&imgType=news&num=8&searchType=image&key=AIzaSyDV8_05oaa-iVMJ-_p2gOsr2Ymsw90EVBY';
	//console.log(url_text_google);
	let data='';

	const req = https.get(url_text_google, (resp) => {
	  //console.log(`STATUS: ${response.statusCode}`);
	  //console.log(`HEADERS: ${JSON.stringify(response.headers)}`);
	  resp.setEncoding('utf8');
	  resp.on('data', (chunk) => {
	  	data+=chunk; 
	    //console.log(`BODY: ${chunk}`);
	  });
	  resp.on('end', () => {
	    console.log('No more data in response.');
	    response.send(JSON.parse(data));
	  });
  	//console.log(data);
	});

	req.on('error', (e) => {
	  console.error(`problem with request: ${e.message}`);
	});

})

express_app.get('/getSimilarItem', function(request, response){
	console.log(request.query);
	var api3_url = 'http://svcs.ebay.com/MerchandisingService?OPERATION-NAME=getSimilarItems&SERVICE-NAME=MerchandisingService&SERVICE-VERSION=1.1.0&CONSUMER-ID=Shwetans-ProductS-PRD-a16e557a4-7ccd2f20&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&itemId='
	api3_url+=request.query.itemID;
	api3_url+='&maxResults=20';
	console.log(api3_url);
	let data='';

	const req = http.get(api3_url, (resp) => {
	  //console.log(`STATUS: ${response.statusCode}`);
	  //console.log(`HEADERS: ${JSON.stringify(response.headers)}`);
	  resp.setEncoding('utf8');
	  resp.on('data', (chunk) => {
	  	data+=chunk; 
	    //console.log(`BODY: ${chunk}`);
	  });
	  resp.on('end', () => {
	    console.log('No more data in response.');
	    response.send(JSON.parse(data));
	  });
  	//console.log(data);
	});

	req.on('error', (e) => {
	  console.error(`problem with request: ${e.message}`);
	});

})
//var api_url = 'http://svcs.ebay.com/services/search/FindingService/v1?OPERATIONNAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Shwetans-ProductS-PRD-a16e557a4-7ccd2f20&RESPONSE-DATA-FORMAT=JSON&RESTPAYLOAD&paginationInput.entriesPerPage=20&keywords='.$_keyword.'&buyerPostalCode='.$_zip.'&itemFilter(0).name=MaxDistance&itemFilter(0).value='.$_maxdist.'&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value='.$_freeshipping.'&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value='.$_pickup.'&itemFilter(3).name=HideDuplicateItems&itemFilter(3).value=true&itemFilter(4).name=Condition&itemFilter(4).value(0)='.$_condition1.'&itemFilter(4).value(1)='.$_condition2.'&itemFilter(4).value(2)='.$_condition3.'';

express_app.listen(8080);
console.log("App listening on port 8080");

express_app.get('/', function(req, res){
	res.send("CSCI 571 Homework 8");
})

express_app.get('/api', function(req, res){
	res.json(JSON.parse(data));
	//console.log(output);
})