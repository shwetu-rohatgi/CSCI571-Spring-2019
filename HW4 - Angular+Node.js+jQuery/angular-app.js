//https://www.termsfeed.com/privacy-policy/dd84d1c9d81d8cc8444a6069335c535a
var app = angular.module('Productsearch', ['ngAria','ngAnimate','ngMaterial','ui.bootstrap','angular-svg-round-progressbar']);
app.controller('formController',['$timeout','$scope', '$http', function($timeout,$scope, $http){
	$timeout(function() {
		    $('[data-toggle="tooltip"]').each(function(index) {
		      // sometimes the title is blank for no apparent reason. don't override in these cases.
		      if ($(this).attr("title").length > 0) {
		        $( this ).attr("data-original-title", $(this).attr("title"));
		      }
		    });
		    $timeout(function() {
		      // finally, activate the tooltips
		      $(document).tooltip({ selector: '[data-toggle="tooltip"]'});
		    }, 500);
		}, 1500);

	$scope.keyword='';
	$scope.whereCameFrom = '';
	$scope.location_btn = "here";
	$scope.result = {};
	$scope.justReset = 1;
	$scope.noresult = false;
	$scope.nowishlist = false;
	$scope.detailBtnFlag=0;
	$scope.selectedItemObj = {};

	// $scope.checkZip = function(){
	// 	if (preg_match('#[0-9]{5}#', $scope.hereText) && $scope.hereText.length==5)
	// 		return true;
	// 	else
	// 		return false;
	// }

	$scope.selectedCategory = 'all';
	$scope.selectedCondition = {};
	$scope.selectedShipping = {};


	$scope.service = {};
	$scope.service.flag = 0;
	$scope.html_text="";

	$scope.itemList = {};

  	//$scope.location_btn 
  	function fetchLocation(){
			if($scope.location_btn=='here'){
				function loadJSON(url) {
					if (window.XMLHttpRequest)
					{// code for IE7+, Firefox, Chrome, Opera, Safari
					xmlhttp=new XMLHttpRequest();
					} 
					else {// code for IE6, IE5
					xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
					}

					xmlhttp.open("GET",url,false); // "synchronous
					xmlhttp.onreadystatechange = function(){
						if(xmlhttp.readyState == 4){
							if(xmlhttp.status=="200"){
								jsonObj= JSON.parse(xmlhttp.responseText);
							}
							else{
								window.alert("Location not found");
								return
							}
						}
					};	
					
					xmlhttp.send();
					return jsonObj;
				}
				jsonObj = loadJSON('http://ip-api.com/json');
				$scope.currentZip = jsonObj.zip;
			}
	}

	$scope.progress = false;
	$scope.wishListFlag = 0;
	$scope.submit_btn = 0;

	$scope.showResults = function(){
		$scope.detailBtnFlag=1;
		$scope.nowishlist = false;
		if($scope.submit_btn==0){
			$scope.wishListFlag = 0;
			$scope.service.flag = 0;
			$scope.noresult = false;
			return;
		}
		$scope.wishListFlag = 0;
		if($scope.submit_btn==1 && contants.findItemsAdvancedResponse[0].searchResult[0]['@count']>0){
			$scope.service.flag = 1;
		}
		else if($scope.noresult==false){
			$scope.service.flag = 1; 
		}
		else{
			$scope.service.flag = 0;
		}
	}

	$scope.showWishlist = function(){
		$scope.service.flag = 0;
		$scope.noresult = false;
		if(localStorage.getItem("savedData")){
			var cookie = JSON.parse(localStorage.getItem("savedData"));
			if(cookie.length>0){
				$scope.wishListFlag = 1;
			}
			else{
				$scope.nowishlist = true;
			}
		}
		else{
			$scope.nowishlist = true;
		}
		$scope.itemDataFlag = 0;
	}

	$scope.localData = [];
	localStorage.setItem("savedData", JSON.stringify($scope.localData));
	$scope.add_to_wishlist = function(obj){
		$scope.localData = JSON.parse(localStorage.getItem("savedData"));
		console.log($scope.localData);
		var i = 0;
		var found = false;
		for(i = 0; i < $scope.localData.length; i++) {
		    if ($scope.localData[i].itemId[0] == obj.itemId[0]) {
		        found = true;
		        break;
		    }
		}
		if(found){
			$scope.localData.splice(i, 1);
			localStorage.removeItem("savedData");
			localStorage.setItem("savedData", JSON.stringify($scope.localData));
		}
		else{
			$scope.localData.push(obj);
			localStorage.removeItem("savedData");
			localStorage.setItem("savedData", JSON.stringify($scope.localData));
		}
	}

	$scope.check_localStorage = function(obj_id){
		if(localStorage.getItem("savedData")){
			var cookie = JSON.parse(localStorage.getItem("savedData"));
				if(cookie.length==0 && $scope.wishListFlag==1){
					$scope.nowishlist = true;
				}
				else{
					$scope.nowishlist = false;
				}
			return cookie.some(item=> item.itemId[0]===obj_id)
		}
		else{
			return false;
		}
	}

	$scope.totalShopping = function(){
						var cookie = JSON.parse(localStorage.getItem("savedData"));
						var sum = 0;
						for(var i = 0; i < cookie.length; i++) {
							sum+=Number(cookie[i].sellingStatus[0].currentPrice[0].__value__);
						}
						return sum.toFixed(2);
					}

	//$scope.dropDownList = [];
	// $scope.autoComplete=function(enterData){
	// 	$scope.drop_list = false;
	// 	$http.get ("http://localhost:8080/autoComplete?hereText="+enterData).then(function(data){
	// 		if(data!=undefined && data.data!=undefined && data.data.postalCodes!=undefined && data.data.postalCodes.length!=0){
	// 			console.log(data.data);
	// 			zipCodeData = data.data.postalCodes;
	// 			for(var i=0; i<zipCodeData.length; i++){
	// 				$scope.dropDownList.push(zipCodeData[i].postalCode);
	// 			}
	// 		}
	// 		console.log($scope.dropDownList);
	// 	});
	// }

	//$scope.isDisabled = false;
	$scope.querySearch = function(enterData){
		$scope.drop_list = false;
		$http.get ("http://ebay-app-236704.appspot.com/autoComplete?hereText="+enterData).then(function(data){
			if(data!=undefined && data.data!=undefined && data.data.postalCodes!=undefined && data.data.postalCodes.length!=0){
				var dropDownList = [];
				//console.log(data.data);
				zipCodeData = data.data.postalCodes;
				for(var i=0; i<zipCodeData.length; i++){
					dropDownList.push(zipCodeData[i].postalCode);
				}
			}
			console.log(dropDownList);
			return dropDownList;
		});

	}

	$scope.fillAuto = function(selectedZip){
		$scope.hereText = selectedZip;
		$scope.drop_list = true;
	}

  	$scope.submit = function(){
  		$scope.detailBtnFlag=1;
  		$scope.submit_btn = 1;
  		$scope.wishListFlag = 0;
  		$scope.progress = true;
  		var strQuery = "";
  		strQuery = "?Keyword="+$scope.keyword;
  		strQuery+="&Category="+$scope.selectedCategory;
  		if($scope.selectedShipping){
  			if($scope.selectedShipping.local){
  				strQuery+="&Local="+$scope.selectedShipping.local;
  			}
  			if($scope.selectedShipping.free){
  				strQuery+="&Free="+$scope.selectedShipping.free;
  			}
  		}
  		if($scope.selectedCondition){
  			if($scope.selectedCondition.new){
  				strQuery+="&New="+$scope.selectedCondition.new;
  			}
  			if($scope.selectedCondition.used){
  				strQuery+="&Used="+$scope.selectedCondition.used;
  			}
  			if($scope.selectedCondition.unspecified){
  				strQuery+="&Unspecified="+$scope.selectedCondition.unspecified;
  			}
  		}
  		if($scope.distance){
  			strQuery+="&Distance="+$scope.distance;
  		}
  		else{
  			strQuery+="&Distance=10";
  		}
  		if($scope.location_btn){
  			if($scope.location_btn=="here"){
  				fetchLocation();
  				strQuery+="&currZip="+$scope.currentZip;
  			}
  			else if($scope.location_btn=="radiozip"){
  				strQuery+="&hereZip="+$scope.hereText;
  			}
  		}

  		$http.get("http://ebay-app-236704.appspot.com/form"+strQuery).then(
  		function(data, response){
  			console.log('Data posted successfully');
					console.log("h2");
					$scope.service.posts = data;
						try{
							contants = $scope.service.posts.data;
							if(contants.findItemsAdvancedResponse[0].searchResult[0]['@count']==0){
								$scope.noresult = true;
								$scope.service.flag = 0;
								$scope.progress = false;
								return 
							}
						// contants = JSON.parse(contants);
						//console.log(contants.findItemsAdvancedResponse);
						$scope.itemList = contants.findItemsAdvancedResponse[0].searchResult[0].item;
						console.log($scope.itemList);
						$scope.progress = false;
						}
						catch(err){}

					$scope.service.flag = 1; 
					$scope.viewby = 10;
					console.log($scope.itemList);
				  	$scope.totalItems = $scope.itemList.length;
				  	$scope.itemsPerPage = 10;

				  	$scope.currentPage = 1;
				  	$scope.maxSize = 5; //Number of pager buttons to show

				  	$scope.setPage = function (pageNo) {
				    	$scope.currentPage = pageNo;
				  	};

					$scope.setItemsPerPage = function(num) {
				  		$scope.itemsPerPage = num;
				  		$scope.currentPage = 1; //reset to first page
					}

  			},
	  		function(error){
	 			console.log("Unable to connect to server");
		 	}
	 	);
  	};
  	$scope.progress = false;
  	$scope.itemDataFlag = 2;

  	$scope.editTitle = function(obj_title){
  		if(obj_title.length>35){
			var newString = '';
			if(obj_title.charAt(34)==' '){
				newString = obj_title.substring(0,35);
			}
			else{
				var validText = obj_title.substring(0,35);
				newString = validText.slice(0, validText.lastIndexOf(' '));
			}
		newString = newString +'...';
		return newString
  		}
  		else{
  			return obj_title
  		}
  }	

  	$scope.itemDetails = function(obj, clickedPage){
  		$scope.selectedProductID = obj.itemId[0];
  		$scope.selectedItemObj = obj;
  		console.log($scope.selectedProductID);
  		$scope.detailBtnFlag=0;
  		$scope.progress = true;
  		$scope.whereCameFrom = clickedPage;
  		var itemUrl = "?itemID=";
  		itemUrl+=obj.itemId[0];
  		itemUrl+="&Keyword="+$scope.keyword;
  		$scope.service.flag = 0;
  		$scope.wishListFlag = 0;
  		$scope.itemDataFlag = 1;
  		$scope.itemData = {}; 
  		$http.get("http://ebay-app-236704.appspot.com/itemDetail"+itemUrl).then(
  		function(data, response){
  			console.log("h3");
  			$scope.itemData = data.data;
  			//console.log($scope.itemData);
  			$scope.ItemSpecificsList = $scope.itemData.Item.ItemSpecifics.NameValueList;
  			$scope.progress = false;
  		},
  		function(error){
 			console.log("Unable to connect to server");
	 	}
	 	); 
	 	$scope.isSelected = function(objID){
	 		if(objID==obj.itemId[0]){
	 			return true;
	 		}
	 		else{
	 			return false;
	 		}
	 	};

	 	$scope.showDetails = function(){
	 		console.log('showDetails');
	 		var a = $scope.service.flag;
	 		var b = $scope.wishListFlag;
	 		$scope.service.flag = 0;
	 		$scope.wishListFlag = 0;
	 		$scope.progress = true;
	 		$timeout(function(){
	 			$scope.detailBtnFlag=0;
		 		if (a==1){
		 			$scope.progress = false;
			 		$scope.itemDataFlag = 1;
			 		$scope.whereCameFrom = 'results';
		 		}
		 		if (b==1){
		 			$scope.progress = false;
			 		$scope.itemDataFlag = 1;
			 		$scope.whereCameFrom = 'wishlist';
		 		}
	 		},500)
	 	};

	 	$scope.showTable = function(){
	 		$scope.itemDataFlag = 0;
	 		$scope.progress = true;
		 		$timeout(function(){
		 			$scope.detailBtnFlag=1;
	 				$scope.progress = false;
					if($scope.whereCameFrom=='results'){
		 			$scope.service.flag = 1;
			 		}
			 		else if($scope.whereCameFrom=='wishlist'){
			 			$scope.wishListFlag = 1;
			 		}
	 				console.log("showTableinTO");
	 			},500)
	 		console.log('showTable');
	 	};

	 	$scope.share = function(){
  			var shareMsg = "Buy "+$scope.itemData.Item.Title+" at "+$scope.itemData.Item.CurrentPrice.Value+" from link below.";
  			FB.ui(
			    {	
			        method: 'share',
			        display: 'popup',
			        quote: shareMsg,
			        href: $scope.itemData.Item.ViewItemURLForNaturalSearch,
			        
			    }, function(response){});
  		};

	 	$http.get("http://ebay-app-236704.appspot.com/googlePhotos"+itemUrl).then(
  		function(data, response){
  			console.log("h4");
  			$scope.googleGallery = data.data.items;
  			$scope.google_array = [];
  			for(var i=0; i<$scope.googleGallery.length; i++){
  				$scope.google_array.push($scope.googleGallery[i].link);
  			}
  			$scope.googlearr1 = $scope.google_array.slice(0, 3);
  			$scope.googlearr2 = $scope.google_array.slice(3, 6);
  			$scope.googlearr3 = $scope.google_array.slice(6, 8);
  		},
  		function(error){
 			console.log("Unable to connect to server");
	 	}
  		);

	 	try{
  		$scope.ShipCost = obj.shippingInfo[0].shippingServiceCost[0].__value__;
  		}
  		catch(err){}
  		try{
  		$scope.ShipLocation = obj.shippingInfo[0].shipToLocations[0];
  		}
  		catch(err){}
  		try{
  		$scope.Handling = obj.shippingInfo[0].handlingTime[0];
  		}
  		catch(err){}
  		try{
  		$scope.Expedite = obj.shippingInfo[0].expeditedShipping[0];
  		}
  		catch(err){}
  		try{
  		$scope.OneShip = obj.shippingInfo[0].oneDayShippingAvailable[0];
  		}
  		catch(err){}
  		try{
  		$scope.returns = obj.returnsAccepted[0];
  		}
  		catch(err){}
  		try{
  		$scope.feedbackScore = obj.sellerInfo[0].feedbackScore[0];
  		}
  		catch(err){}
  		try{
  		$scope.feedPercent = obj.sellerInfo[0].positiveFeedbackPercent[0];
  		}
  		catch(err){}
  		try{
  		$scope.starRating = obj.sellerInfo[0].feedbackRatingStar[0];
  		}
  		catch(err){}
  		try{
  		$scope.topRated = obj.sellerInfo[0].topRatedSeller;
  		}
  		catch(err){}
  		try{
  		$scope.storeName = obj.storeInfo[0].storeName[0];
  		}
  		catch(err){}
  		try{
  		$scope.storeURL = obj.storeInfo[0].storeURL[0];
  		}
  		catch(err){}

  		$http.get("http://ebay-app-236704.appspot.com/getSimilarItem"+itemUrl).then(
  		function(data, response){
  			console.log("h5");
  			//console.log(data.data.getSimilarItemsResponse.itemRecommendations.item);
  			$scope.similarItems = data.data.getSimilarItemsResponse.itemRecommendations.item;
  			for(var i=0;i<20;i++){
  				$scope.similarItems[i].timesLeft = Number($scope.similarItems[i].timeLeft.substring($scope.similarItems[i].timeLeft.lastIndexOf("P") + 1, $scope.similarItems[i].timeLeft.lastIndexOf("D")));
  			}
  			//console.log($scope.similarItems);
  			$scope.limit=5;
			$scope.showMore=function(){
				$scope.limit = $scope.similarItems.length;
			};
			$scope.showLess = function(){
		     $scope.limit = 5;
			};

			// $scope.reverseVal = $scope.filter1;
			// $scope.filterFn = function(){
			// 	$scope.selectedFilter = $scope.filter1.value;
			// 	$scope.reverseVal = false;
			// 	console.log($scope.selectedFilter);
			// };

			// $scope.reverseVal = false;
			// $scope.ascend = function(){
			// 	$scope.reverseVal = $scope.filter2.value;
			// 	console.log($scope.reverseVal);
			// };

  		},
  		function(error){
 			console.log("Unable to connect to server");
	 	}
  		);
  	};

  	$scope.reset_all = function(){
  		$scope.keyword = '';
  		$scope.selectedCondition.new = '';
  		$scope.selectedCondition.used = '';
  		$scope.selectedCondition.unspecified = '';
  		$scope.selectedShipping.local = '';
  		$scope.selectedShipping.free = '';
  		$scope.distance = '';
  		$scope.location_btn = 'here';
  		$scope.hereText = '';
  		$scope.selectedCategory = 'all';
  		$scope.wishListFlag = 0;
  		$scope.itemDataFlag = 2;
  		$scope.service.flag = 0;
  		$scope.progress = false;
  		$scope.submit_btn = 0;
		$scope.itemList = {};
		$scope.service = {};
		$scope.dropDownList = [];
		$scope.whereCameFrom = '';
		$scope.itemData = {};
		$scope.similarItems = []; 
		$scope.googleGallery = [];
		$scope.selectedProductID = 0;
		$scope.justReset = 0;
		$scope.noresult = false;
		$scope.detailBtnFlag=0;
		$scope.selectedItemObj = {};
  	}

}]);
