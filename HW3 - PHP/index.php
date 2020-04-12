<html>
<head>
<style>
	form{
		margin-left: auto;
		margin-right: auto;
		background-color: rgb(245,245,245);
	}
	.form-div{
		width: 650px;
		margin-left: auto;
		margin-right: auto;
	}
	.dis-field{
		margin-top: 4px;
		margin-right: 50px;
		float: right;
	}
	input{
		margin: 5px;
		margin-bottom: 10px;
	}
	input.l-space{
		width: 30px;
	}
	input.l-space2{
		width: 30px;
	}
	.box-size{
		margin-left: 50px;
		width: 60px;
	}
	#nearbySearch{
		margin-left: 170px;
		margin-top: -28px;
	}
	iframe.old{
		visibility: hidden;
	}
	iframe.update{
		width: 80%;
		visibility: visible;
	}
	.similar_item_section_old{
		visibility: hidden;
		/*height: 0px;*/
	}
	.similar_item_section{
		visibility: visible;
		width: 650px;
		overflow-x: scroll; 
		/*overflow-y: hidden;
		height: 370px;*/
	}
	td.similar_item_td{
		padding-left: 20px;
		padding-right: 20px;
		width: 70px;
		font-size: 14px;
	}
	a{
		text-decoration: none;
		color: black;
	}
	a:hover{
		color: rgb(160,160,160);
	}
	.no-record{
		margin-top: 30px;
		width: 1000px;
		height: 30px;
		padding-top: 15px;
		text-align: center;
		background-color: rgb(225,225,225);
	}

</style>

<script>
		function httpres(response){
		console.log("Hello");
		try{
		//console.log(response.findItemsAdvancedResponse);
		var contants = response; 
		//document.write(contants);
		if(contants.findItemsAdvancedResponse[0].searchResult[0]['@count']==0){
			var html_text = "<center><div class='no-record'>No Record has been found</div></center>";
			document.write(html_text);
			return
		}
		var html_text = "";
		html_text+="<center><table id='table_1' width='1000px' border='1'>";
		var thead = [
          "Index",
          "Photo",
          "Name",
          "Price",
          "Zip Code",
          "Condition",
          "Shipping Options"
        ];
		for(var i in thead){
		    header_print = thead[i];
		    html_text+="<th>"+header_print+"</th>";
		}
		// contants = JSON.parse(contants);
		//console.log(contants.findItemsAdvancedResponse);
		var itemList = contants.findItemsAdvancedResponse[0].searchResult[0].item;

		html_text+="<tbody>";
		for(var i=0; i<itemList.length; i++){
			var y=i+1;
			html_text+="<tr><td>"+y+"</td>";
			var itemData = itemList[i];  //this is a dictionary of each and every listing. item data is a dictionary against a listing
			var itemData_keys = Object.keys(itemData);
				//var key = itemData_keys[j]
					try{
						html_text+="<td><img src="+ itemData["galleryURL"][0] +" width='"+80+"' height='"+80+"' onerror='this.style.display=\"none\"'></td>";
					}
					catch(error){
						html_text+="<td>N/A</td>";
					}	
					try{
						/*var getString = "&keyword=" + document.getElementById("keyWord") +
											"&category=" + document.getElementById("categoryID").value +
											"&shipping=" + document.getElementById("LocalCheck").checked +
											"&shipping=" + document.getElementById("FreeCheck").checked +
											"&condition=" + document.getElementById("NewCheck").checked +
											"&condition=" + document.getElementById("UsedCheck").checked +
											"&condition=" + document.getElementById("UnspecifiedCheck").checked +
											"&nearby=" + document.getElementById("nearby").checked;

						if(document.getElementById("nearby").checked){
							getString += "&distance=" + document.getElementById("nearby").value;
							if()
						}
						*/
						html_text += "<td><a href='index.php?itemid="+itemData['itemId'][0]+"'>"+itemData["title"][0]+"</a></td>";
					}
					catch(error){
						html_text+="<td>N/A</td>";
					}
					try{ 
							html_text+="<td> $"+itemData["sellingStatus"][0].currentPrice[0].__value__+"</td>";
						}
					catch(error){
							html_text+="<td>N/A</td>";
						}
					try{
							html_text+="<td>"+itemData["postalCode"][0]+"</td>";
						}
					catch(error){
							html_text+="<td>N/A</td>";
						}
					try{
						html_text+="<td>"+itemData["condition"][0].conditionDisplayName[0]+"</td>"
						}
					catch(error){
							html_text+="<td>N/A</td>";
						}	
					
					try{
							if (itemData["shippingInfo"][0].shippingServiceCost[0].__value__=="0.0"){
								html_text+="<td>Free Shipping</td>";
							}
							else{
								html_text+="<td>"+itemData["shippingInfo"][0].shippingServiceCost[0].__value__+"</td>";
							}
						}
					catch(error){
							html_text+="<td>N/A</td>";
						}
					
		}
		html_text+="</tr></tbody></table></center>";
		document.write(html_text);
		}
		catch(err){
			document.write("<center><div class='no-record'>No Results Found</div></center>");
		}
	}

	function httpgetinfo(response2, response3){

	//console.log("Here it is");
	try{
		contants = response2;
		contantsTable = response3;

		var html_text = "";
		html_text+="<center><table id='table_page' width='620px' border='1'><caption><h1>Item Details</h1></caption><tbody>";
		try{
			if(contants.Item.PictureURL!=undefined){
				html_text+="<tr><td><b>Photo</b></td><td><img src='"+contants.Item.PictureURL+"' height=250px width=300px></td></tr>";
			}
		}
		catch(err){}
		try{
			if(contants.Item.Title!=undefined){
				html_text+="<tr><td><b>Title</b></td><td>"+contants.Item.Title+"</td></tr>";
			}
		}
		catch(err){}
		try{
			if(contants.Item.Subtitle!=undefined){
				html_text+="<tr><td><b>Subtitle</b></td><td>"+contants.Item.Subtitle+"</td></tr>";
			}
		}
		catch(err){}
		try{
			if(contants.Item.CurrentPrice!=undefined){
				html_text+="<tr><td><b>Price</b></td><td>"+contants.Item.CurrentPrice.Value+" "+contants.Item.CurrentPrice.CurrencyID+"</td></tr>";
			}
		}
		catch(err){}
		try{
			if(contants.Item.Location!=undefined || contants.Item.PostalCode!=undefined){
				if(contants.Item.Location!=undefined && contants.Item.PostalCode==undefined){
					html_text+="<tr><td><b>Location</b></td><td>"+contants.Item.Location+"</td></tr>";
				}
				else if(contants.Item.Location==undefined && contants.Item.PostalCode!=undefined){
					html_text+="<tr><td><b>Location</b></td><td>"+contants.Item.PostalCode+"</td></tr>";
				}
				else{
					html_text+="<tr><td><b>Location</b></td><td>"+contants.Item.Location+", "+contants.Item.PostalCode+"</td></tr>";
				}
			}
		}
		catch(err){}
		try{
			if(contants.Item.Seller.UserID!=undefined){
				html_text+="<tr><td><b>Seller</b></td><td>"+contants.Item.Seller.UserID+"</td></tr>";
			}
		}
		catch(err){}
		try{
			if(contants.Item.ReturnPolicy.ReturnsAccepted!=undefined){
				html_text+="<tr><td><b>ReturnPolicy(US)</b></td><td>"+contants.Item.ReturnPolicy.ReturnsAccepted+"</td></tr>";
			}
		}
		catch(err){}
		
		try{
			var nameValuePairs = contants.Item.ItemSpecifics.NameValueList; //this is basically an array
			for(var x=0; x<nameValuePairs.length;x++){
				try{
					if(nameValuePairs[x].Value!=undefined){
						html_text+="<tr><td><b>"+nameValuePairs[x].Name+"</b></td><td>"+nameValuePairs[x].Value+"</td></tr>";
					}
				}
				catch(err){}
			}
		}
		catch(err){
		}


		html_text+="</tbody></table> <br> <div id='show_seller' style = 'display: block;' onclick=show_seller_fn()><p id='show_seller_msg'>click to show seller message <br><img id='show_seller_img' src='http://csci571.com/hw/hw6/images/arrow_down.png' width=40px height=20px/></div><div id='hide_seller' style = 'display: none;' onclick=show_seller_fn()><p id='hide_seller_msg'>click to hide seller message <br><img id='hide_seller_img' src='http://csci571.com/hw/hw6/images/arrow_up.png' width=40px height=20px/></p></div><iframe style = 'display: block;' id='item-iframe' class='old' srcdoc='"+contants.Item.Description+"'></iframe> <center><div style = 'display: none;' class='no-record' id='no-iframe'>No Record has been found</div></center> <br> <div id='show_similar' onclick=show_similar_fn() style = 'display: block;'><p>click to show similar items <br><img src='http://csci571.com/hw/hw6/images/arrow_down.png' width=40px height=20px/></p></div><div id='hide_similar' onclick=show_similar_fn() style = 'display: none;'><p>click to hide similar items <br><img src='http://csci571.com/hw/hw6/images/arrow_up.png' width=40px height=20px/></p></div>";
		
		similar_items_list = contantsTable.getSimilarItemsResponse.itemRecommendations.item;
		if(similar_items_list.length==0){
			html_text+="<center><div class='no-record similar_item_section_old' id='similar_items'>No Similar Elements Found</div></center>";
			html_text+="</center>";
			document.write(html_text);
		}
		else{
			html_text+="<div class='similar_item_section_old' id='similar_items'><table frame='box' cellpadding='10'><tbody><tr>";
			for(var l=0; l<similar_items_list.length; l++){
				//document.write(similar_items_list[l]);
				html_text+="<td><center><img height='90px' width='110px' src='"+similar_items_list[l].imageURL+"'/'></center></td>";
			}
			html_text+="</tr><tr>"
			for(var l=0; l<similar_items_list.length; l++){
				//document.write(similar_items_list[l]);
				html_text+="<td class='similar_item_td'><center><a href='index.php?itemid="+similar_items_list[l].itemId+"'>"+similar_items_list[l].title+"</a></center></td>";
			}
			html_text+="</tr><tr>"
			for(var l=0; l<similar_items_list.length; l++){
				//document.write(similar_items_list[l]);
				html_text+="<td><center><b>"+similar_items_list[l].buyItNowPrice.__value__+"</b></center></td>";
			}

			html_text+="</tr></tbody></table></div>";

			html_text+="</center>";
			document.write(html_text);
		}
	}
	catch(error){
		document.write("<center><div class='no-record'>Record Details Not Found</div></center>");
	}
}
	</script>
</head>
<body>
	<div class="form-div">
		<form action="" method="GET">
			<fieldset>
			<center><h2>Product Search</h2></center>
			<hr>
		<?php if(isset($_GET['submit'])==false): ?>
			<label>Keyword <input id="keyWord" style="margin-left: 5px;" type="text" name="keyword" required></label><br>
			<label>
			Category 
			<select id="categoryID" style="margin-left: 6px;" name="category">
			    <option value="all" selected="selected">All Categories</option>
			    <option value="" disabled>-------------------------------------------</option>
			    <option value="550">Art</option>
			    <option value="2984">Baby</option>
			    <option value="267">Books</option>
			    <option value="11450">Clothing, Shoes & Accessories</option>
			    <option value="58058">Computer/Tablets & Networking</option>
			    <option value="26395">Health & Beauty</option>
			    <option value="11233">Music</option>
			    <option value="1249">Video Games & Consoles</option>
			 </select>
			  <br>
			</label>
			
			Condition
			<label> 
			<input class="l-space" id="NewCheck" type="checkbox" name="condition[]" value="New">New
			</label>
			<label>
			<input class="l-space" id="UsedCheck" type="checkbox" name="condition[]" value="Used">Used
			</label>
			<label>
			<input class="l-space" id="UnspecifiedCheck" type="checkbox" name="condition[]" value="Unspecified">
			Unspecified
			</label>
			<br>
			Shipping Options
			<label>
			<input class="l-space2" id="LocalCheck" type="checkbox" name="shipping[]" value="Pickup">Local Pickup
			</label>
			<label>
			<input class="l-space2" id="FreeCheck" type="checkbox" name="shipping[]" value="Free">Free Shipping 
			</label>
			<br>
			<label><input id="nearby" type="checkbox" name="nearby" value="nearby" onclick="chk_btn()">Enable Nearby Search</label>
			<div id="nearbySearch">
				<label><input class="box-size" type="text" name="distance" id="distanceVal" value="10">miles from</label> 
				
				<div class="dis-field">
				<input type="radio" id="herebtn" name="here" value="here" checked>Here <br>
				<input type="radio" id="radiozip" name="here" value="radiozip"><input id="zipcode" type="text" name="hereText" placeholder="zipcode" required>
				</div>
			</div>
			
			<br>
			<br>
			<br>
		<?php else: ?>
			<label>Keyword <input id="keyWord" style="margin-left: 5px;" type="text" name="keyword" value = "<?php echo $_GET['keyword'];?>" required></label><br>
			<label>
			Category 
			<select style="margin-left: 6px;" name="category">
			    <option id="categoryID" value="all" <?php if($_GET['category'] == "all") { echo "selected=\"selected\""; } ?>>All Categories</option>
			    <option id="categoryID" value="" disabled>-------------------------------------------</option>
			    <option id="categoryID" value="550" <?php if($_GET['category'] == "550") { echo "selected=\"selected\""; } ?>>Art</option>
			    <option id="categoryID" value="2984" <?php if($_GET['category'] == "2984") { echo "selected=\"selected\""; } ?>>Baby</option>
			    <option id="categoryID" value="267" <?php if($_GET['category'] == "267") { echo "selected=\"selected\""; } ?>>Books</option>
			    <option id="categoryID" value="11450" <?php if($_GET['category'] == "11450") { echo "selected=\"selected\""; } ?>>Clothing, Shoes & Accessories</option>
			    <option id="categoryID" value="58058" <?php if($_GET['category'] == "58058") { echo "selected=\"selected\""; } ?>>Computer/Tablets & Networking</option>
			    <option id="categoryID" value="26395" <?php if($_GET['category'] == "26395") { echo "selected=\"selected\""; } ?>>Health & Beauty</option>
			    <option id="categoryID" value="11233" <?php if($_GET['category'] == "11233") { echo "selected=\"selected\""; } ?>>Music</option>
			    <option id="categoryID" value="1249" <?php if($_GET['category'] == "1249") { echo "selected=\"selected\""; } ?>>Video Games & Consoles</option>
			 </select>
			  <br>
			</label>
			
			Condition
			<label> 
			<input class="l-space" type="checkbox" name="condition[]" value="New"<?php if(isset($_GET['condition'])) { foreach($_GET['condition'] as $tmp) { if($tmp == "New") { echo "checked=\"checked\""; break; }}} ?>>New
			</label>
			<label>
			<input class="l-space" type="checkbox" name="condition[]" value="Used" <?php if(isset($_GET['condition'])) { foreach($_GET['condition'] as $tmp) { if($tmp == "Used") { echo "checked=\"checked\""; break; }}} ?>> Used
			</label>
			<label>
			<input class="l-space" type="checkbox" name="condition[]" value="Unspecified" <?php if(isset($_GET['condition'])) { foreach($_GET['condition'] as $tmp) { if($tmp == "Unspecified") { echo "checked=\"checked\""; break; }}} ?>>
			Unspecified
			</label>
			<br>
			Shipping Options
			<label>
			<input class="l-space2" type="checkbox" name="shipping[]" value="Pickup" <?php if(isset($_GET['shipping'])) { foreach($_GET['shipping'] as $tmp) { if($tmp == "Pickup") { echo "checked=\"checked\""; break; }}} ?>>Local Pickup
			</label>
			<label>
			<input class="l-space2" type="checkbox" name="shipping[]" value="Free" <?php if(isset($_GET['shipping'])) { foreach($_GET['shipping'] as $tmp) { if($tmp == "Free") { echo "checked=\"checked\""; break; }}} ?>>Free Shipping 
			</label>
			<br>
			<label><input id="nearby" type="checkbox" name="nearby" value="nearby"  onclick="chk_btn()" <?php if(isset($_GET['nearby'])) { if($_GET['nearby'] == "nearby") { echo "checked=\"checked\""; }} ?>>Enable Nearby Search</label>
			<div id="nearbySearch">
				<label><input class="box-size" type="text" name="distance" value="<?php if(isset($_GET['distance'])) {echo $_GET['distance'];} else{echo "10";} ?>" <?php echo (isset($_GET['nearby']))? '':'disabled'?>>miles from</label> 
				
				<div class="dis-field">
				
				<input type="radio" id="herebtn" name="here" value="here" <?php if(isset($_GET['nearby'])) {if($_GET['here'] == 90007) { echo "checked=\"checked\""; }} ?> <?php echo (isset($_GET['nearby']))? '':'disabled' ?>>Here 
				<br>
				<input type="radio" id="radiozip" name="here" value="radiozip"  <?php if(isset($_GET['nearby'])) {if($_GET['here'] == "radiozip") { echo "checked=\"checked\""; }} ?><?php echo (isset($_GET['nearby']))? '':'disabled' ?>>
				<input id="zipcode" type="text" name="hereText" placeholder="zipcode" value = "<?php if(isset($_GET['hereText'])){echo $_GET['hereText'];} ?>" <?php if(isset($_GET['here'])){ echo ($_GET['here']=='radiozip')? '':'disabled';}?> required>
				</div>
			</div>
			
			<br>
			<br>
			<br>

		<?php endif; ?>
			<center>
			<input type="submit" name="submit" value="submit" onclick="formData(this.form)">
			<input type="reset" value="Clear" onclick="clearfunc()">
			</center>
			</fieldset>
		</form>
	</div>
	<div>
		<?php

			if(isset($_GET['submit'])){
				$api_url="";
				$index=0;
				$_keyword = $_GET['keyword'];
				$_keyword = str_replace(' ', '%20', $_keyword);
				$_category = $_GET['category'];

				try{

				$api_url.='http://svcs.ebay.com/services/search/FindingService/v1?OPERATION-NAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Shwetans-ProductS-PRD-a16e557a4-7ccd2f20&RESPONSE-DATA-FORMAT=JSON&RESTPAYLOAD&paginationInput.entriesPerPage=20&keywords='.$_keyword;

				if ($_category!='all'){
					$api_url.='&categoryId='.$_category;
				}

				if (isset($_GET['nearby'])){
					$_maxdist = $_GET['distance'];
					if($_GET['here']=='radiozip'){
						$_zip = $_GET['hereText'];
							if (validateZipCode($_zip)==false){
								throw new Exception("Zip Code is Invalid");
							}
						}
					else{
						$_zip = $_GET['here'];
					}
					$api_url.='&buyerPostalCode='.$_zip.'&itemFilter('.$index.').name=MaxDistance&itemFilter('.$index.').value='.$_maxdist;
					$index+=1;	
				}
				
				if(!empty($_GET['shipping'])){
					#echo(print_r($_GET['shipping']));
						if($_GET['shipping'][0]=='Free' and count($_GET['shipping'])==1){
						$api_url = $api_url . '&itemFilter('.$index.').name=FreeShippingOnly&itemFilter('.$index.').value=true';
						$index+=1;
						$api_url = $api_url . '&itemFilter('.$index.').name=LocalPickupOnly&itemFilter('.$index.').value=false';
						$index+=1;
						
						}else if($_GET['shipping'][0]=='Pickup' and count($_GET['shipping'])==1){
						$api_url = $api_url . '&itemFilter('.$index.').name=FreeShippingOnly&itemFilter('.$index.').value=false';
						$index+=1;
						$api_url = $api_url . '&itemFilter('.$index.').name=LocalPickupOnly&itemFilter('.$index.').value=true';
						$index+=1;
						}
						else if(count($_GET['shipping'])==2){
							$api_url = $api_url . '&itemFilter('.$index.').name=FreeShippingOnly&itemFilter('.$index.').value=true';
							$index+=1;
							$api_url = $api_url . '&itemFilter('.$index.').name=LocalPickupOnly&itemFilter('.$index.').value=true';
							$index+=1;
						}
					

				}else{
					//$api_url.='&itemFilter('.$index.').name=FreeShippingOnly&itemFilter('.$index.').value=true';
					//$index+=1;
					//$api_url.='&itemFilter('.$index.').name=LocalPickupOnly&itemFilter('.$index.').value=true';
					//$index+=1;
				}
				$api_url.='&itemFilter('.$index.').name=HideDuplicateItems&itemFilter('.$index.').value=true';
				$index+=1;
				if(!empty($_GET['condition'])){
					$count = 0;
					foreach ($_GET['condition'] as $value) {
						$api_url = $api_url . '&itemFilter('.$index.').name=Condition&itemFilter('.$index.').value('.$count.')='.$value;
						$count+=1;
					}
				}
	
			#echo($api_url);
			$response = file_get_contents($api_url);
			echo "<script> httpres($response); </script>";
			}
			catch (Exception $e){
				echo("<center><div class='no-record'>".$e->getMessage()."</div></center>");
			}
			#echo($response);

			#$response['findItemsAdvancedResponse']
			#$response = json_decode($response, true);
			#echo(print_r($response));
			}
			if(isset($_GET['itemid'])){
				#echo htmlspecialchars($_GET['itemid']);
				$item_id = $_GET['itemid'];
				$singleItemAPICall="http://open.api.ebay.com/shopping?callname=GetSingleItem&responseencoding=JSON&appid=Shwetans-ProductS-PRD-a16e557a4-7ccd2f20&siteid=0&version=967&ItemID=".$item_id."&IncludeSelector=Description,Details,ItemSpecifics";
				#echo($singleItemAPICall);
				$response2 = file_get_contents($singleItemAPICall);

				$similarProductAPIcall = "http://svcs.ebay.com/MerchandisingService?OPERATION-NAME=getSimilarItems&SERVICE-NAME=MerchandisingService&SERVICE-VERSION=1.1.0&CONSUMER-ID=Shwetans-ProductS-PRD-a16e557a4-7ccd2f20&RESPONSE-DATA-FORMAT=JSON&REST-PAYLOAD&itemId=".$item_id."&maxResults=8";
				$response3 = file_get_contents($similarProductAPIcall);

				#echo($similarProductAPIcall);
				echo "<script> httpgetinfo($response2,$response3); </script>";
				#echo(print_r($singleItemAPICall));
			}

			#$api_url = 'http://svcs.ebay.com/services/search/FindingService/v1?OPERATIONNAME=findItemsAdvanced&SERVICE-VERSION=1.0.0&SECURITY-APPNAME=Shwetans-ProductS-PRD-a16e557a4-7ccd2f20&RESPONSE-DATA-FORMAT=JSON&RESTPAYLOAD&paginationInput.entriesPerPage=20&keywords='.$_keyword.'&buyerPostalCode='.$_zip.'&itemFilter(0).name=MaxDistance&itemFilter(0).value='.$_maxdist.'&itemFilter(1).name=FreeShippingOnly&itemFilter(1).value='.$_freeshipping.'&itemFilter(2).name=LocalPickupOnly&itemFilter(2).value='.$_pickup.'&itemFilter(3).name=HideDuplicateItems&itemFilter(3).value=true&itemFilter(4).name=Condition&itemFilter(4).value(0)='.$_condition1.'&itemFilter(4).value(1)='.$_condition2.'&itemFilter(4).value(2)='.$_condition3.''
		
		function validateZipCode($zipCode) {
		if (preg_match('#[0-9]{5}#', $zipCode))
			return true;
		else
			return false;
		}
		?>
	</div>
	
	<script type="text/javascript">
		var checkbtn = document.getElementById('nearby');
		var disablediv = document.getElementById('nearbySearch').getElementsByTagName('*');
		for(var i = 0; i < disablediv.length; i++){
				     disablediv[i].disabled = true;
				     disablediv[i].style.opacity = 0.7;
			}
		function chk_btn(){
			if(checkbtn.checked){
				//disablediv.disabled = false;
				for(var i = 0; i < disablediv.length; i++){
			     disablediv[i].disabled = false;
			     disablediv[i].style.opacity = 1;
				}
				
				var zipcodetxt = document.getElementById('zipcode');
				var here_btn = document.getElementById('herebtn');
				zipcodetxt.disabled = true;
				zipcodetxt.style.opacity = 0.7;
			}
			else{
				//disablediv.disabled = true;
				for(var i = 0; i < disablediv.length; i++){
				     disablediv[i].disabled = true;
				     disablediv[i].style.opacity = 0.7;
				}
			}
		}
		
		var radiobtn = document.getElementById('radiozip');
		var zipcodetxt = document.getElementById('zipcode');
		var here_btn = document.getElementById('herebtn');
		radiobtn.onchange = function(){
			console.log("Hello");
			if(radiobtn.checked){
				zipcodetxt.disabled = false;
				zipcodetxt.style.opacity = 1;
				radiobtn.style.opacity = 1;
			}
		}
		here_btn.onchange = function(){
			if(here_btn.checked){
				zipcodetxt.disabled = true;
				zipcodetxt.style.opacity = 0.7;
				radiobtn.style.opacity = 0.7;
			}
		}

		function clearfunc(){
			<?php 
				unset($_GET);
			?>
			var disablediv = document.getElementById('nearbySearch').getElementsByTagName('*');
			for(var i = 0; i < disablediv.length; i++){
					     disablediv[i].disabled = true;
					     disablediv[i].style.opacity = 0.7;
				}	
			try{	
			var elem = document.getElementById('table_page');
    		elem.parentNode.removeChild(elem);
    		}
    		catch(err){}
    		try{
    		var elem2 = document.getElementById('show_seller');
    		elem2.parentNode.removeChild(elem2);
    		}
    		catch(err){}
    		try{
    		var elem3 = document.getElementById('hide_seller');
    		elem3.parentNode.removeChild(elem3);
    		}
    		catch(err){}
    		try{
    		var elem4 = document.getElementById('show_similar');
    		elem4.parentNode.removeChild(elem4);
    		}
    		catch(err){}
    		try{
    		var elem5 = document.getElementById('hide_similar');
    		elem5.parentNode.removeChild(elem5);
    		}
    		catch(err){}
    		try{
    		var elem6 = document.getElementById('item-iframe');
    		elem6.parentNode.removeChild(elem6);
    		}
    		catch(err){}
    		try{
    		var elem7 = document.getElementById('similar_items');
    		elem7.parentNode.removeChild(elem7);
    		}
    		catch(err){}
    		try{
    		var elem8 = document.getElementById('table_1');
    		elem8.parentNode.removeChild(elem8);
    		}
    		catch(err){}

    		

    		//return false;
		}
		
		function formData(data){
			if(data.here.value=='here'){
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
				here_btn.value = jsonObj.zip;
			}
		}

		function show_seller_fn(){
			//console.log("H");
			var x = document.getElementById('show_seller');
			var y = document.getElementById('hide_seller');

			var a = document.getElementById('show_similar');
			var b = document.getElementById('hide_similar');

			var frame = document.getElementById('item-iframe');
			var no_iframe = document.getElementById('no-iframe');
			var similar_table = document.getElementById('similar_items');
			if(a.style.display=="none"){
				similar_table.classList.toggle("similar_item_section");
				a.style.display = "block";
				b.style.display = "none";
				ifLoad();
			}
			if (x.style.display == "none" || y.style.display == "block") {
			    y.style.display = "none";
			    x.style.display = "block";
			    no_iframe.style.display = "none";
			    frame.height = "0px";
			    if(a.style.display=="none" && b.style.display == "block"){
			    	console.log("hey4");
			    	a.style.display = "block";
			    	b.style.display = "none";
					similar_table.classList.toggle("similar_item_section");
			    }
			}
			else if (x.style.display == "block" && y.style.display == "none"){
			    x.style.display = "none";
			    y.style.display = "block";
			    ifLoad();
			}
			var iframe_item = document.getElementById('item-iframe');
			//iframe_item.srcdoc = contants.Item.Description;
			iframe_item.classList.toggle("update");
			}
		function show_similar_fn(){
			//console.log("S");
			try{
				var a = document.getElementById('show_similar');
				var b = document.getElementById('hide_similar');

				var x = document.getElementById('show_seller');
				var y = document.getElementById('hide_seller');

				var frame = document.getElementById('item-iframe');
				var no_iframe = document.getElementById('no-iframe');
				var similar_table = document.getElementById('similar_items');


				if (a.style.display == "none" && b.style.display == "block") {
				    b.style.display = "none";
				    a.style.display = "block"; 
				}
				else if (a.style.display == "block" && b.style.display == "none"){
				    a.style.display = "none";
				    b.style.display = "block";

				    if(y.style.display=="block" && b.style.display=="block"){
					frame.style.display = "none";
					no_iframe.style.display = "none";
					//b.style.display = "none";
					//a.style.display = "block";
					y.style.display = "none";
					x.style.display = "block";
					}
				}
				//iframe_item.srcdoc = contants.Item.Description;
				similar_table.classList.toggle("similar_item_section");
			}
			catch(err){}
			}
		function ifLoad(){
			setTimeout(function(){
				var frame = document.getElementById('item-iframe');
				var no_iframe = document.getElementById('no-iframe');
				console.log(frame.contentWindow.document.body.scrollHeight);	
				if (frame.contentWindow.document.body.scrollHeight){
					frame.height = (frame.contentWindow.document.body.scrollHeight+40) +"px";
					frame.setAttribute("scrolling","no");
				}
				else{
					frame.style.display = "none";
					no_iframe.style.display = "block";
				}
			}, 0);
		}
	</script>
</body>
</html>