var SEGMENTS = prompt("Enter the number of segments:");
var HEIGHT = prompt("Enter the height of each segment:");

var maxStars = 2*HEIGHT+2*SEGMENTS-3;
var maxStr = "";

for (var len=0; len < maxStars; len++)
{
	maxStr = maxStr + ' ';
}

for (var i=1; i <= SEGMENTS; i++)
{
	for (var line=1; line <= HEIGHT; line++)
	{
		var starStr = "";
		for (var j=1; j <= 2*line+2*i-3; j++)
		{
			if(i===1 && line===1 && j===1) starStr = starStr + '*';
			else starStr = starStr + '0';
		}

		for (var space=0; space <= maxStars-(HEIGHT+line+i); space++)
		{
			starStr = ' ' + starStr;
		}
		console.log(starStr);
	}
}

for (var i=0; i <= maxStars/2;i++)
{
	console.log(" ");
}

console.log("*\n");

for (var i=0; i <= maxStars/2;i++)
{
	console.log(" ");
}

console.log("*\n");
for(var i=0; i <= maxStars/2-3;i++)
{
	console.log(" ");
}
console.log("*******\n");