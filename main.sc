require: patterns.sc
require: requests.js


theme: /

    state: Start
        q!: *start
        q!: * $hello *
        q: * $stop * || fromState = /SuggestCurrency
        random:
            a: Здравствуйте! Меня зовут Эльвира Сахипзадовна. Я чат-бот, 
                знающий курсы валют.
            a: Приветствую Вас! Моё имя Эльвира Сахипзадовна. Я чат-бот, 
                знающий курсы валют.
        go!: /SuggestCurrency

    state: CatchAll
        event!: noMatch
        random:
            a: Извините, не поняла Вас. Я умею только сообщать курсы валют.
            a: Простите, я не понимаю Вас. В мои полномочия входят только курсы 
                валют.
        go!: /SuggestCurrency
                
    state: SuggestCurrency || modal = true
        random:
            a: Курс какой валюты Вы хотели бы узнать?
            a: Курс какой валюты Вас интересует?
        buttons:
            "доллар"
            "евро"
        
        state: ChooseUSD
            q: * (~доллар/~бакс/~американский/USD) *
            script: 
                $temp.currency = "доллар";
            go!: /TellExchangeRate
        
        state: ChooseEUR
            q: * (евро/евра/~европейский/EUR) *
            script: 
                $temp.currency = "евро";
            go!: /TellExchangeRate

        state: LocalCatchAll:
            event: noMatch
            random:
                a: Извините, курс такой валюты я не знаю. Выберите вариант из списка.
                a: Простите, с такой валютой я не работаю. Выберите один из
                    предложенных вариантов.
            go!: /SuggestCurrency
    
    state: TellExchangeRate
        script: 
            var exchangeRate = get_rate($temp.currency);
            var answer = "Курс " + $nlp.inflect($temp.currency, "gent") + 
                " составляет " + exchangeRate + " " + 
                $nlp.conform("рубль", exchangeRate) + ".";
            $reactions.answer(answer)
        go!: /SuggestCurrency
