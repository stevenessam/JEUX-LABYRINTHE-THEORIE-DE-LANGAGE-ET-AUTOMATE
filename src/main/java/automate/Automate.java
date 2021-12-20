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
		//recherche des tokens
		stream= stream.filter((Token t)->{
			//recheche des tokens qui peuvent existé après cette etat
			var c = !(t.includes(stat) && !t.includes(ALL));
			if( c )
				return false;
			//  et si le text peut correspondre au token
			return t.test(token);
		});
		Optional<Token> optionalToken = stream.findFirst();
		if( ! optionalToken.isEmpty())
			return optionalToken.get();// retoune le token si il existe
		return null;
	}
	private boolean debug = false;
	public void setDebug(boolean mode){
		debug = mode;
	}
	private Token token;
	private int stat;
	private String culsym;
	/**
	 * Analyse Lexical : 
	 * cette methode reconnait les tokens dans un text
	 * @param entree
	 * @return
	 */
	public List<RegonizeToken> recognize(AutomateString entree){
		stat = AutomateBase.ETAT_INITIAL;
		culsym = "";
		List<RegonizeToken> tokensLS = new ArrayList<RegonizeToken>();
		// pour chaque symbole de la chaine
		entree.forEach((Character c)->{
			//cherche le token dans les token connu
		token = this.getToken(culsym,stat);
		if(debug){
			System.out.println("input : '"+c+"' or "+((int) c));
			if(token == null){
				System.out.println("no token find at stat : "+stat+"; next token are : "+culsym);
			}else{
				System.out.println("token find : "+token+" stat : "+token.getStat());
			}
		}
		// si le token est connu
		if(token != null){ 
				// token reconnu = (entre utilisateur,nom etat,etat,token original)
				RegonizeToken regonize = new RegonizeToken(
					culsym,token.getOutput(),token.getStat(),token
					);
				tokensLS.add(regonize);
				stat = token.getStat();//on passe a l'etat suivant
				culsym = "";
			}
			if(c != null)
				culsym += c;//quand le text du token n'est pas connu on y ajoute le prochain symbole
		},(Character c)->{
			// a la fin en registre le dernier token non reconnu
			tokensLS.add(new RegonizeToken(culsym,stat));
		});
		return tokensLS;
	}
	/**
	 * Analyse lexical (avec regroupement)
	 * @see recognize
	 * @param text
	 * @param groups
	 * regroupe les tokens selon les groupes données
	 * @return liste de tokens
	 */
	public List<RegonizeToken> regroup(AutomateString text,List<List<Token>> groups){
		List<RegonizeToken> parsed = this.recognize(text);
		List<RegonizeToken> tokenLs = new ArrayList<RegonizeToken>();
		token = null;
		String t = "";
		String _out="";
		int s = 0;
		RegonizeToken last = null;
		List<RegonizeToken> regroup = new ArrayList<>();
		for (RegonizeToken regonizeToken : parsed) {// pour chaqque token reconnu
			List<Token> g = null;
			List<Token> gn = null;
			Optional<List<Token>> opt;
			// trouver le premier group conserner avec l'ancient token
			opt = groups.stream().filter((List<Token> gls)->{return gls.contains(token);}).findFirst();
			if(! opt.isEmpty()){
				g = opt.get();// g = group avec le token precende
			}
			// trouver le premier group conserner avec le nouveau token
			opt = groups.stream().filter((List<Token> gls)->gls.contains(regonizeToken.getToken())).findFirst();
			if(! opt.isEmpty()){
				gn = opt.get();//gn = group avec le nouveau token
			}
			if(gn != null && g==gn){//si le token a un group et que c'est le meme que le group precedent
				t += regonizeToken.getInput();// on group les entres
				regroup.add(regonizeToken);//on regroup les token
			}else{
				// Token token_correction = this.getBestToken(t,last.getToken());
				// if(token_correction != null){
				// 	s = token_correction.getStat();
				// 	_out = token_correction.getOutput();
				// }
				// if(regroup != null){

				// }
				last = new RegonizeToken(t, _out, s,token);
				tokenLs.add(last);
				t = regonizeToken.getInput();
				_out = regonizeToken.getOutput();
				s = regonizeToken.getStat();// changement etat
				regroup.clear();// creer un nouveau group
				regroup.add(regonizeToken);
			}
			token = regonizeToken.getToken();
		}
		tokenLs.add(new RegonizeToken(t,-1));
		return tokenLs;
	}
	/**
	 * cette methode était sensé traduire un text donnée
	 * regroupé les parties donnée
	 * puis en faire l'execution
	 */
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
	/** == Methode de debug == */
	/**
	 * BestToken (DEBUG)
	 * trouver le token qui correspond au text entre en parametre qui peut suivre le token last
	 * @param token
	 * @param last
	 * @return
	 */
	public Token getBestToken(String token,Token last){
		//les tokens qui correspond au text entre en parametre
		Stream<Token> proba = this.showToken(token).filter((Token t)->{
			return t.includes(last.getStat());//peut suivre le  token last
		});
		//les tokens qui correspond au text entre en parametre
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
	/**
	 * showToken (DEBUG)
	 * trouver les tokens avec un etat (entier)
	 * @param token
	 * @return
	 */
	public Stream<Token> showToken(int token){
		return this.reconizeToken.stream().filter(t->{
			return t.getStat() == token;
		});
	}
	/**
	 * showToken (DEBUG)
	 * trouver les tokens qui correspond au text entre en parametre
	 * @param token
	 * @return
	 */
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
	/**
	 * Ajouter un token a la liste des token reconnu par l'automate
	 * il defini aussi un etat pour se token
	 * @param token
	 */
	public void addToken(Token token){
		token.setStat(this.reconizeToken.size()+1);
		this.reconizeToken.add(token);
	}
	
}
