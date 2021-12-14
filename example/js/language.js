const HTMLParse = new AutomateBuilder

var typevaluesStart = []
var typevaluesEnd = []
var startinstruction = []

var endinstruction = HTMLParse.addToken(/\n/,"endintrus")
var separator = HTMLParse.addToken(",","separate",startinstruction)
	
/* int */
var integer = HTMLParse.addToken(/[0-9]/,"integer",[separator,-1],[separator])
typevaluesStart.push(integer)
typevaluesEnd.push(integer)

	/* string */
var Stringstart = HTMLParse.addToken(/[";']/,"Stringstart",[separator])
var Stringcontent = HTMLParse.addToken(/[a-z;0-9;\.]/i,"Stringcontent",[Stringstart,-1])
var Stringend = HTMLParse.addToken(/[";']/,"Stringend",[Stringcontent],[separator])
typevaluesStart.push(Stringstart)
typevaluesEnd.push(Stringend)

/* params */
var paramsStart = HTMLParse.addToken("(","paramsStart",[],typevaluesStart)
var paramsEnd = HTMLParse.addToken(")","paramsEnd",[paramsStart,Stringend,integer],[endinstruction,AutomateBuilder.END])

/* block x y*/
var block = HTMLParse.addToken("block","startblock",[endinstruction,0,paramsStart,separator],[paramsStart])
	

const tests = [
	"block(\"moose\",0,0)",
	// "block(\"moose\",0,0)\nblock(\"moose\",1,1)",
	// "block(\"moose\",0,block(\"moose\",1,1))\n",

	// "spawn 1 0",
	// "guard zombie 1 0 ",
	// "pattern right top right top",
	// "spawn(\"moose\",0,0)\nblock(\"moose\",0,0)",
	// "block(\"moose,0,0)\nblock(\"moose\",0,0)",
	// "block(moose,0,0)\nblock(\"moose\",0,0)",
	// "block(\"moose\",0,0\nblock(\"moose\",0,0)",
]