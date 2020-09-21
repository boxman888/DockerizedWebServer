let request = new XMLHttpRequest();

request.open('GET', 'https://api.covid19api.com/total/country/united-states/status/confirmed?from=2020-03-01T00:00:00Z&to=2020-08-01T00:00:00Z', true);

request.onload = function(){
    let data = JSON.parse(this.response);

    if (request.status >= 200 && request.status < 400){
        console.log("request code: " + request.status);
        let i = 0;
        data.forEach((item) => {
            let str = item.Date.split(/[-T]/);
            let date = str[1] + '/' + str[2];
            dataStore.add({id: i, day: date, cases: parseInt(item.Cases)});
            i++;
        })
    } else {
        console.log('error');
    }
}

request.send();


