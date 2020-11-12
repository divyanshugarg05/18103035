var input = prompt("Please enter the string containing only braces:");

var ans = true;

var stack = [];

for(var i=0; i<input.length; i++)
{
	if(input[i]==='(' || input[i]==='{' || input[i]==='[')
		stack.unshift(input[i]);

	else
	{
		if(input[i]===')')
		{
			if(stack.length===0 || stack[0]!=='(')
			{
				ans = false;
				break;
			}
		}

		if(input[i]==='}')
		{
			if(stack.length===0 || stack[0]!=='{')
			{
				ans = false;
				break;
			}
		}

		if(input[i]===']')
		{
			if(stack.length===0 || stack[0]!=='[')
			{
				ans = false;
				break;
			}
		}

		stack.shift();
	}
}

ans = ans && (stack.length!==0);

if(ans) console.log("Braces are balanced!!");
else console.log("Braces are NOT balanced!!");