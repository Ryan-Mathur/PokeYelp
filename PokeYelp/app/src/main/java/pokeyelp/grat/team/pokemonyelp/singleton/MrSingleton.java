package pokeyelp.grat.team.pokemonyelp.singleton;

import java.util.ArrayList;
import java.util.List;

import pokeyelp.grat.team.pokemonyelp.gson_pokemon_species.Species;
import pokeyelp.grat.team.pokemonyelp.gson_yelp.BusinessDetail;

/**
 * Created by twang on 4/30/17.
 */

public class MrSingleton {

    private String mToken;

    private List<Species> mLocalSpeciesCache;

    private List<BusinessDetail> mLocalBusinessCache;


    public static MrSingleton sInstance;

    private MrSingleton(){
        mToken = new String();
        mLocalSpeciesCache = new ArrayList<>();
        mLocalBusinessCache = new ArrayList<>();
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

    public List<Species> getLocalSpeciesCache() {
        return mLocalSpeciesCache;
    }

    public List<BusinessDetail> getLocalBusinessCache() {
        return mLocalBusinessCache;
    }

    public boolean addSpecies(Species speciesToAdd){
        if (indexOfSpeciesName(speciesToAdd.getName())!=-1){
            return false;
        }
        mLocalSpeciesCache.add(speciesToAdd);
        return true;
    }

    public boolean addBusiness(BusinessDetail businessToAdd){
        if(indexOfYelpId(businessToAdd.getId())!=-1){
            return false;
        }
        mLocalBusinessCache.add(businessToAdd);
        return true;
    }

    public int indexOfSpeciesName(String name){
        for (int i = 0; i < mLocalSpeciesCache.size(); i++) {
            if (mLocalSpeciesCache.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }

    public int indexOfYelpId(String id){
        for (int i = 0; i < mLocalBusinessCache.size(); i++) {
            if (mLocalBusinessCache.get(i).getName().equals(id)){
                return i;
            }
        }
        return -1;
    }





}
