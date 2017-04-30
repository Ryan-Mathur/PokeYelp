package pokeyelp.grat.team.pokemonyelp.singleton;

/**
 * Created by twang on 4/30/17.
 */

public class MrSingleton {

    private String mToken;

    public static MrSingleton sInstance;

    private MrSingleton(){
        mToken = new String();
    }

    public static MrSingleton getInstance(){
        if (sInstance == null){
            sInstance = new MrSingleton();
        }
        return sInstance;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

}
