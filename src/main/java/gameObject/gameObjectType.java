package gameObject;

public enum gameObjectType {
	NOCLIP,// n'est pas prie en compte dans les colisions
	SOLID,// pocede des colision
	PLAYER,// est un joueur (plus facil si on veux faire du multi joueur)
	PATH// est une route utlisable par un joueur (plus facil pour le deplacement interdit)
}
