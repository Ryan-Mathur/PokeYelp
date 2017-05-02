
package pokeyelp.grat.team.pokemonyelp.gson_pokemon_species;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Variety {

    @SerializedName("is_default")
    @Expose
    private Boolean isDefault;
    @SerializedName("mPokemonHolder")
    @Expose
    private PokemonHolder mPokemonHolder;

    public Boolean getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }

    public PokemonHolder getPokemonHolder() {
        return mPokemonHolder;
    }

    public void setPokemonHolder(PokemonHolder pokemonHolder) {
        this.mPokemonHolder = pokemonHolder;
    }

}
