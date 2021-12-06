class AutomateString{
	chars = []
	get length(){
		return this.chars.length;
	}
	constructor(str){
		this.chars = str.split('')
	}
	cursor = 0
	next(){
		return this.cursor>=this.length?null:this.chars[this.cursor++]
	}
	last(){
		return this.chars[this.cursor-1]
	}
	hasNext(){
		return this.cursor<=this.length&&(this.chars[this.cursor])
	}
	forEach(callback){
		var c = this.next()
		while(this.hasNext()){
			callback(c,this)
			c = this.next()
		}
		callback(c,this)
		callback(null,this)
		return 
	}
	static concat(...args){var e = new AutomateString("");e.chars = args;return e}
}
class AutomateBase {
	static ETAT_INITIAL = 0
	static REJECT = false
	static ACCPET = true
	translate(entree){
		if(!(entree instanceof AutomateString))
			entree = new AutomateString(entree)
		return entree
	}
}
class AutomateBuilder extends AutomateBase{
	ALL = -2
	ME = -1
	#reconizeToken = []
	addToken(input,output,stats = [0],after = [],){
		var token = {input,output,stats}
		token.stat = this.#reconizeToken.length
		if(stats.includes(-1)){
			token.stats.push(token.stat)
		}
		this.#reconizeToken.push(token)
		after.forEach(n=>{
			this.#reconizeToken[n].stats.push(token.stat)
		})
		return token.stat
	}
	getToken(token,stat){
		var find = this.#reconizeToken.find(t=>{
			var i = t.input
			// if(t.output == "htmlcontent"){
			// 	console.log(t.stats,stat)
			// }
			if( ! t.stats.includes(stat) && !t.stats.includes(-2))
				return false
			if(i instanceof RegExp){
				return i.test(token)&&! token.split(i).join('').length
			}else{
				return i == token
			}
		})
		// if(find)
			// console.log(find,find.stats.includes(stat))
		return find
	}
	recognize(entree){
		entree = this.translate(entree)
		var stat = AutomateBase.ETAT_INITIAL
		var tokensLS = []
		var culsym = ""
		var token;
		// var c = entree.next()
		entree.forEach(c=>{
		if(token = this.getToken(culsym,stat)){ 
				// console.log(token,stat)
				tokensLS.push({
					input:culsym,
					output:token.output,
					stat:token.stat,
				})
				stat = token.stat
				culsym = ""
			}
			if(c)
				culsym += c
		})
		tokensLS.push({
			input:culsym,
			output:-1,
			stat,
		})
		return tokensLS
	}
	regroup(text,groups = []){
		var parsed = this.recognize(text)
		console.log(parsed)
		groups = groups.map(x=>x.map(y=>this.#reconizeToken[y]||y))
		var output = ""
		var t = ""
		var group = []
		text = parsed.reduce((p,o)=>{
			var g = groups.find((g)=>g.includes(output))
			var gn = groups.find((g)=>g.includes(o.output))
// gn == undefined ? output == o.output : g==gn
			if( gn != undefined && g==gn ){
				t += o.input
			}else{
				p.push(t)
				t = o.input
			}
			output = o.output

			return p
		},[])
		text.push(t)
		return text
	}
	print(text){
		var parsed = this.recognize(text)
		console.log(parsed)
		var text = ""
		parsed.forEach(token=>{
			text += token.input.padStart(token.stat,"\t")+"\n"
		})
		console.log(text)
	}
}