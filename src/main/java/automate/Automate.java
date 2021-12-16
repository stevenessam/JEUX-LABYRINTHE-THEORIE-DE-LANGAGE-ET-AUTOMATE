package automate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Automate extends AutomateBase{
	final public static int ALL = -2;
	final public static int ME = -1;
	private List<Token> reconizeToken = new ArrayList<Token>();
	private Token getToken(String token,int stat){
		Stream<Token> stream = this.reconizeToken.stream();
		stream= stream.filter((Token t)->{
			var c = !(t.includes(stat) && !t.includes(ALL));
			if( c )
				return false;
			return t.test(token);
		});
		Optional<Token> optionalToken = stream.findFirst();
		if( ! optionalToken.isEmpty())
			return optionalToken.get();
		return null;
	}
	private boolean debug = false;
	public void setDebug(boolean mode){
		debug = mode;
	}
	private Token token;
	private int stat;
	private String culsym;
	public List<RegonizeToken> recognize(AutomateString entree){
		stat = AutomateBase.ETAT_INITIAL;
		culsym = "";
		List<RegonizeToken> tokensLS = new ArrayList<RegonizeToken>();
		entree.forEach((Character c)->{
		token = this.getToken(culsym,stat);
		if(debug){
			System.out.println("input : '"+c+"' or "+((int) c));
			if(token == null){
				System.out.println("no token find at stat : "+stat+"; next token are : "+culsym);
			}else{
				System.out.println("token find : "+token+" stat : "+token.getStat());
			}
		}
		if(token != null){ 
				RegonizeToken regonize = new RegonizeToken(
					culsym,token.getOutput(),token.getStat(),token
					);
				tokensLS.add(regonize);
				stat = token.getStat();
				culsym = "";
			}
			if(c != null)
				culsym += c;
		},(Character c)->{
			tokensLS.add(new RegonizeToken(culsym,stat));
		});
		return tokensLS;
	}
	public List<RegonizeToken> regroup(AutomateString text,List<List<Token>> groups){
		List<RegonizeToken> parsed = this.recognize(text);
		List<RegonizeToken> tokenLs = new ArrayList<RegonizeToken>();
		token = null;
		String t = "";
		String _out="";
		int s = 0;
		RegonizeToken last = null;
		List<RegonizeToken> regroup = new ArrayList<>();
		for (RegonizeToken regonizeToken : parsed) {
			List<Token> g = null;
			List<Token> gn = null;
			Optional<List<Token>> opt;
			opt = groups.stream().filter((List<Token> gls)->{return gls.contains(token);}).findFirst();
			if(! opt.isEmpty()){
				g = opt.get();
			}
			opt = groups.stream().filter((List<Token> gls)->gls.contains(regonizeToken.getToken())).findFirst();
			if(! opt.isEmpty()){
				gn = opt.get();
			}
			if(gn != null && g==gn){
				t += regonizeToken.getInput();
				regroup.add(regonizeToken);
			}else{
				// Token token_correction = this.getBestToken(t,last.getToken());
				// if(token_correction != null){
				// 	s = token_correction.getStat();
				// 	_out = token_correction.getOutput();
				// }
				if(regroup != null){

				}
				last = new RegonizeToken(t, _out, s,token);
				tokenLs.add(last);
				t = regonizeToken.getInput();
				_out = regonizeToken.getOutput();
				s = regonizeToken.getStat();
				regroup.clear();
				regroup.add(regonizeToken);
			}
			token = regonizeToken.getToken();
		}
		tokenLs.add(new RegonizeToken(t,-1));
		return tokenLs;
	}
	/*
	execute(String text,int[] regroup,actions){
		var groups = this.regroup(text,regroup)
		// console.log(groups)
		groups.forEach(x=>{
			var fx = x.output
			fx = typeof fx == "function"?fx:actions[fx]
			fx = fx||actions.default
			if(fx && x){
				fx(x.input,x)
			}else{
				console.log(x.input)
			}
		})
	}
	*/
	public Token getBestToken(String token,Token last){
		Stream<Token> proba = this.showToken(token).filter((Token t)->{
			return t.includes(last.getStat());
		});
		proba = proba.filter(t->{
			Object i = t.getInput();
			if(i instanceof Pattern ){
				return ((Pattern)i).matcher(token).start() == 0;
			}else if(i instanceof String){
				return ((String) i) == token;
			}else{
				return false;
			}
		});
		Optional<Token> optionalToken = proba.findFirst();
		if( ! optionalToken.isEmpty())
			return optionalToken.get();
		return null;
	}
	public Stream<Token> showToken(int token){
		return this.reconizeToken.stream().filter(t->{
			return t.getStat() == token;
		});
	}
	public Stream<Token> showToken(String token){
		return this.reconizeToken.stream().filter(t->{
			var i = t.getInput();
			if(i instanceof Pattern ){
				return ((Pattern)i).matcher(token).start() == 0;
			}else if(i instanceof String){
				return ((String) i) == token;
			}else{
				return false;
			}
		});
	}
	public void addToken(Token token){
		token.setStat(this.reconizeToken.size()+1);
		this.reconizeToken.add(token);
	}
	
}
