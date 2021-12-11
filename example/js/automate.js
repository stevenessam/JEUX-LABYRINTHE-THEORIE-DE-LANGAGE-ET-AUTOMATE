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
Array.prototype.last = function(){
	return this[this.length-1]
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
	END = -3
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
			if(n>0 && n < this.#reconizeToken.length)
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
					token,
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
		tokensLS.clean = function(){
			return this.reduce((p,o)=>{
				if(o.input.length>0)p.push(o.input)
				return p
			},[])
		}
		return tokensLS
	}
	regroup(text,groups = []){
		var parsed = this.recognize(text)
		groups = groups.map(x=>x.map(y=>this.#reconizeToken[y]||y))
		var output = ""
		var t = ""
		var s = 0
		var n = 1
		var group = []
		var regroup = []
		text = parsed.reduce((p,o)=>{
			var g = groups.find((g)=>g.includes(output))
			var gn = groups.find((g)=>g.includes(o.output))
// gn == undefined ? output == o.output : g==gn
			if( gn != undefined && g==gn ){
				t += o.input
				s = s+o.stat
				regroup.push(o)
				n++
			}else{
				p.push({
					input:t,
					stat:s/n,
					n,
					regroup
				})
				t = o.input
				s = o.stat
				n = 1
				regroup = [o]
			}
			output = o.output

			return p
		},[])
		text.push({
			input:t,
			stat:s/n,
			n
		})
		text.clean = function(){
			return this.reduce((p,o)=>{
				if(o.input.length>0)p.push(o.input)
				return p
			},[])
		}
		return text
	}
	print(text,group){
		var parsed = this.regroup(text,group)
		var text = ""
		parsed.forEach(token=>{
			text += token.input.padStart(token.stat,"\t")+"\n"
		})
		return text
	}
	printExecution(text,group){
		var parsed = this.regroup(text,group)
		var text = ""
		var ident = 0
		parsed.forEach((token,n)=>{
			text += "".padStart(ident,"\t")+token.input+"\n"
			console.log(token)
			if(token.regroup && token.regroup.length){
				if(
					token.regroup[0].token.stats.includes(0) &&
					token.regroup[0].token.stats.length == 0
					){
					ident = 0
				} else if (
					token.regroup[0].token.stats.length == 1
					){
				} else if (
					! token.regroup[0].token.stats.includes(-1)
					){
					if(token.regroup[0].token.stats.includes(0))
						ident ++
				}else{
					ident++
				}
			}else{
				console.log(parsed[n+1] , parsed[n+1].regroup)
				if(parsed[n+1] && parsed[n+1].regroup && parsed[n+1].regroup.last().token.stats.includes(AutomateBuilder.END))
					ident--
			}
		})
		return text
	}
	execute(text,groups){
		var parsed;
		if(groups){
			parsed = this.regroup(text,groups).clean()
		}else{
			parsed = this.recognize(text).clean()
		}
		console.log(parsed)
	}
}