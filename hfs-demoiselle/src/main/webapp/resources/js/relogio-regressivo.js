var segundos = 360;
var partida = new Date();
var intervalo = setInterval(temporizador, 1000);

document.getElementById("relogio-regressivo").innerHTML = toHHMMSS(segundos);

function restoSegundos(secondsElapsed) {
	return segundos - secondsElapsed;
}

function extenderTempo(value) {
	var seconds = parseInt(value), secondsElapsed = Math.round((new Date()
			.getTime() - this.partida.getTime()) / 1000);
	if ((this.restoSegundos(secondsElapsed) + seconds) <= this.segundos) {
		this.partida
				.setSeconds(this.partida.getSeconds() + parseInt(value));
	}
}

function resetarTempo(value) {
	segundos = parseInt(value);
	partida = new Date();
	document.getElementById("relogio-regressivo").innerHTML = toHHMMSS(segundos);
	clearInterval(intervalo);
	intervalo = setInterval(temporizador, 1000);
}

function temporizador() {
	var millisElapsed, secondsElapsed;

	millisElapsed = new Date().getTime() - this.partida.getTime();
	secondsElapsed = Math.floor((millisElapsed) / 1000);

	if (secondsElapsed < segundos) {
		document.getElementById("relogio-regressivo").innerHTML = toHHMMSS(parseInt(restoSegundos(secondsElapsed)));
	} else {
		clearInterval(intervalo);
		document.location = "../index.html";
		//PF('dlgSessaoExpirada').show();
	}
}

function toHHMMSS(seconds) {
	var date = new Date(seconds * 1000);
	var hh = date.getUTCHours();
	var mm = date.getUTCMinutes();
	var ss = date.getSeconds();

	if (hh < 10) {
		hh = "0" + hh;
	}
	if (mm < 10) {
		mm = "0" + mm;
	}
	if (ss < 10) {
		ss = "0" + ss;
	}

	var t = hh + ":" + mm + ":" + ss;
	return t;
}

/*
function contadorRegressivo() {
	var countdown = $("#countdown").countdown360({
		radius : 60,
		seconds : 10,
		fontColor : '#FFFFFF',
		autostart : false,
		onComplete : function() {
			console.log('done')
		}
	});
	countdown.start();
	console.log('countdown360 ', countdown);
}
*/
