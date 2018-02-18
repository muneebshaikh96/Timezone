//Php file to request google timzone API with location for timezone and Send that timezone to the Android frontend 
<?php

$latitude = $_POST['latitude'];
$longitude = $_POST['longitude'];

$time = time();
$url = "https://maps.googleapis.com/maps/api/timezone/json?location=$latitude,$longitude&timestamp=$time&key=xxxxxxxxx";
$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, $url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, 1);
$responseJson = curl_exec($ch);
curl_close($ch);
$response = json_decode($responseJson);

$timeZoneId = $response->timeZoneId; 
//TIMEZONE VALUE YOU GET FROM THE RESPONSE OF MAPS API

$date = new DateTime("now", new DateTimeZone($timeZoneId) );
echo $date->format('H:i:s d-m-Y');

?>
