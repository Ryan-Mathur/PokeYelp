
package pokeyelp.grat.team.pokemonyelp.gson_pokemon_species;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genera {

    @SerializedName("genus")
    @Expose
    private String genus;
    @SerializedName("language")
    @Expose
    private Language language;

    public String getGenus() {
        return genus;
    }

    public void setGenus(String genus) {
        this.genus = genus;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

}
