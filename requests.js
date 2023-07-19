function get_rate(currency) {
    var codes = {"доллар": "USD", "евро": "EUR"};
    var code = codes[currency];  // приводим русское название к коду
    
    var url = "https://www.cbr-xml-daily.ru/latest.js";
    var response = $http.get(url, {dataType: "json"});
    return Math.round(1 / response.data.rates[code]);  // переворачиваем дробь, округляем
}
